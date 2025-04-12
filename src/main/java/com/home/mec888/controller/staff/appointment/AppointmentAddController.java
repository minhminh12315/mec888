package com.home.mec888.controller.staff.appointment;

import com.home.mec888.dao.*;
import com.home.mec888.entity.Doctor;
import com.home.mec888.entity.Patient;
import com.home.mec888.entity.Role;
import com.home.mec888.entity.User;
import com.home.mec888.util.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AppointmentAddController {
    @FXML
    public ComboBox<User> user_id;
    @FXML
    public TextField emergency_contact;
    public TextArea medical_history;
    @FXML
    public Label contact_error, medical_error, user_id_error, doctorErrorLabel, appointmentDateErrorLabel, timeErrorLabel;
    @FXML
    private TextField firstNameField, lastNameField, emailField, phoneField, addressField;
    @FXML
    public Button clearButton, saveButton, backButton;
    @FXML
    private ComboBox<String> genderComboBox, timePicker;
    @FXML
    private DatePicker dateOfBirthPicker, appointmentDatePicker;
    @FXML
    private Label usernameErrorLabel, emailErrorLabel, phoneErrorLabel, firstNameErrorLabel, lastNameErrorLabel, genderErrorLabel, dateOfBirthErrorLabel, addressErrorLabel;

    @FXML
    public ComboBox<Doctor> doctorComboBox;

    @FXML
    private GridPane timeSlotGrid;

    // Biến lưu trữ giờ được chọn
    private String selectedTimeSlot = null;

    UserDao userDao = new UserDao();
    private RoleDao roleDao = new RoleDao();
    DoctorDao doctorDao = new DoctorDao();
    DoctorScheduleDao doctorScheduleDao = new DoctorScheduleDao();
    RoomDao roomDao = new RoomDao();
    DepartmentDao departmentDao = new DepartmentDao();

    private static final String word = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String digits = "0123456789";
    private static final Random random = new Random();
    private List<LocalDate> workDateOnMonth;

    private final Map<String, String> dayMap = new HashMap<>() {{
        put("Mon", "Monday");
        put("Tue", "Tuesday");
        put("Wed", "Wednesday");
        put("Thur", "Thursday");
        put("Fri", "Friday");
        put("Sat", "Saturday");
        put("Sun", "Sunday");
    }};

    @FXML
    private void initialize() {

        // Gender options
        genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female"));

        // Doctor ComboBox
        ObservableList<Doctor> doctorList = FXCollections.observableArrayList(doctorDao.getAllDoctors());
        doctorComboBox.setItems(doctorList);

        // Time picker options set to 30-minute intervals
        ObservableList<String> timeOptions = FXCollections.observableArrayList();
        for (int hour = 8; hour < 18; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                String time = String.format("%02d:%02d", hour, minute);
                timeOptions.add(time);
            }
        }

        // Appointment Date Picker
        appointmentDatePicker.setValue(null);
        appointmentDatePicker.setEditable(false);
        appointmentDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(java.time.LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                LocalDate today = LocalDate.now();
                YearMonth currentMonth = YearMonth.now();
                if (item.isBefore(today)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #eeeeee;");
                }
                if (!YearMonth.from(item).equals(currentMonth)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #eeeeee;");
                    return;
                }
                if (workDateOnMonth != null && !workDateOnMonth.contains(item)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #eeeeee;");
                }
            }
        });
        // Date of Birth - Set initial value to null if you want.
        dateOfBirthPicker.setValue(null);

        buildTimeSlotGrid();
    }

    private ObservableList<String> generateTimeSlots() {
        ObservableList<String> slots = FXCollections.observableArrayList();
        // Giả sử giờ mở cửa là 08:00 và giờ đóng cửa là 18:00
        for (int hour = 7; hour < 19; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                String time = String.format("%02d:%02d", hour, minute);
                slots.add(time);
            }
        }
        return slots;
    }

    /**
     * Phương thức xây dựng lưới các ô button hiển thị các ca khám theo 30 phút.
     */
    private void buildTimeSlotGrid() {
        ObservableList<String> timeSlots = generateTimeSlots();
        int columns = 4; // Số cột, bạn có thể điều chỉnh tùy ý
        int row = 0;
        int col = 0;

        // Xóa các thành phần cũ nếu có
        timeSlotGrid.getChildren().clear();

        for (String slot : timeSlots) {
            Button btn = new Button(slot);
            btn.setPrefWidth(80);
            btn.getStyleClass().add("time-slot-button");

            // Nếu slot đã được chọn, đánh dấu màu khác
            if (slot.equals(selectedTimeSlot)) {
                btn.setStyle("-fx-background-color: #90ee90;"); // xanh nhạt
            }

            // Xử lý sự kiện khi bấm vào button
            btn.setOnAction(e -> {
                selectedTimeSlot = slot;
                // Cập nhật ComboBox timePicker (nếu bạn dùng) hoặc hiển thị ở nơi khác
                timePicker.setValue(slot);
                // Cập nhật lại giao diện lưới để đánh dấu button được chọn
                buildTimeSlotGrid();
            });

            timeSlotGrid.add(btn, col, row);
            col++;
            if (col >= columns) {
                col = 0;
                row++;
            }
        }
    }


    @FXML
    public void handleSave(ActionEvent actionEvent) {
        try {
            String email = emailField.getText();
            String phone = phoneField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String gender = genderComboBox.getValue();

            String username = firstName + " " + lastName;
            String password = randomPassword();

            try {
                // Create a new User object
                User user = new User();
                user.setUsername(username);
                user.setPassword(hashPassword(password));
                user.setEmail(email);
                user.setPhone(phone);
                user.setRoleId(4);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setGender(gender);
                user.setDateOfBirth(null);
                user.setAddress(null);

                // Save the user to the database
                userDao.saveUser(user);
                showAlert("Success", "User added successfully!", Alert.AlertType.INFORMATION);

            } catch (Exception e) {
                showAlert("Error", "Error adding user: " + e.getMessage(), Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            showAlert("Error", "Failed to save user", Alert.AlertType.ERROR);
        }


        try {
            Long last_user_id = getLastUserId();

            // Create patient object with user_id, emergency_contact, and medical_history
            PatientDao patientDao = new PatientDao();
            Patient patient = new Patient(last_user_id, emergency_contact.getText(), medical_history.getText());
            patientDao.savePatient(patient);


            // Show success message
            showAlert("Success", "Patient added successfully", Alert.AlertType.INFORMATION);

//            SceneSwitcher.loadView("admin/patient/patient-management.fxml", actionEvent);
        } catch (Exception e) {
            showAlert("Error", "Failed to save patient", Alert.AlertType.ERROR);
        }


    }

    private Long getLastUserId() {
        UserDao userDao = new UserDao();
        ObservableList<User> userList = FXCollections.observableArrayList(userDao.getAllUsers());
        if (userList.isEmpty()) {
            return null;
        }
        User lastUser = userList.getFirst();
        return lastUser.getId();
    }

    private Long getLastPatientId() {
        PatientDao patientDao = new PatientDao();
        ObservableList<Patient> patientList = FXCollections.observableArrayList(patientDao.getAllPatients());
        if (patientList.isEmpty()) {
            return null;
        }
        Patient lastPatient = patientList.getFirst();
        return lastPatient.getId();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void validateInput(KeyEvent event) {
        Object source = event.getSource();

        if (source == user_id) {
            try {
                Long.parseLong(String.valueOf(user_id.getValue()));
                user_id_error.setText("");  // Clear error if valid number
                saveButton.setDisable(false);
            } catch (NumberFormatException e) {
                user_id_error.setText("User ID must be a valid number.");
                saveButton.setDisable(true);
            }
        } else if (source == emergency_contact) {
            String contact = emergency_contact.getText();
            if (contact.length() > 255) {
                contact_error.setText("Emergency Contact cannot exceed 255 characters.");
                saveButton.setDisable(true);
            } else if (contact.isEmpty()) {
                contact_error.setText("Emergency Contact cannot be empty.");
                saveButton.setDisable(true);
            } else {
                contact_error.setText("");
                saveButton.setDisable(false);
            }
        } else if (source == medical_history) {
            String history = medical_history.getText();
            if (history.length() > 500) {
                medical_error.setText("Medical History cannot exceed 500 characters.");
                saveButton.setDisable(true);
            } else {
                medical_error.setText("");
                saveButton.setDisable(false);
            }
        } else if (source == firstNameField) {
            String firstName = firstNameField.getText();
            if (firstName == null) {
                firstNameErrorLabel.setText("First Name cannot be empty.");
                saveButton.setDisable(true);
            } else {
                firstNameErrorLabel.setText("");
                saveButton.setDisable(false);
            }
        } else if (source == lastNameField) {
            String lastName = lastNameField.getText();
            if (lastName == null) {
                lastNameErrorLabel.setText("Last Name cannot be empty.");
                saveButton.setDisable(true);
            } else {
                lastNameErrorLabel.setText("");
                saveButton.setDisable(false);
            }
        } else if (source == genderComboBox) {
            String gender = genderComboBox.getValue();
            if (gender == null) {
                genderErrorLabel.setText("Gender cannot be empty.");
                saveButton.setDisable(true);
            } else {
                genderErrorLabel.setText("");
                saveButton.setDisable(false);
            }
        } else if (source == emailField) {
            String email = emailField.getText();
            if (email == null) {
                emailErrorLabel.setText("Email cannot be empty.");
                saveButton.setDisable(true);
            } else if (!isValidEmail(email)) {
                emailErrorLabel.setText("Invalid email format!");
                saveButton.setDisable(true);
            } else if (userDao.isEmailExists(email)) {
                emailErrorLabel.setText("Email already exists!");
                saveButton.setDisable(true);
            } else {
                emailErrorLabel.setText("");
                saveButton.setDisable(false);
            }
        } else if (source == phoneField) {
            String phone = phoneField.getText();
            if (phone == null) {
                phoneErrorLabel.setText("Phone cannot be empty.");
                saveButton.setDisable(true);
            } else if (!isValidPhone(phone)) {
                phoneErrorLabel.setText("Invalid phone format!");
                saveButton.setDisable(true);
            } else {
                phoneErrorLabel.setText("");
                saveButton.setDisable(false);
            }
        } else if (source == dateOfBirthPicker) {
            if (dateOfBirthPicker.getValue() == null) {
                dateOfBirthErrorLabel.setText("Date of Birth cannot be empty.");
                saveButton.setDisable(true);
            } else {
                dateOfBirthErrorLabel.setText("");
                saveButton.setDisable(false);
            }
        } else if (source == addressField) {
            String address = addressField.getText();
            if (address.length() > 255) {
                addressErrorLabel.setText("Address cannot exceed 255 characters.");
                saveButton.setDisable(true);
            } else {
                addressErrorLabel.setText("");
                saveButton.setDisable(false);
            }

        } else if (source == appointmentDatePicker) {
            if (appointmentDatePicker.getValue() == null) {
                appointmentDateErrorLabel.setText("Appointment Date cannot be empty.");
                saveButton.setDisable(true);
            } else {
                appointmentDateErrorLabel.setText("");
                saveButton.setDisable(false);
            }
        } else if (source == timePicker) {
            String time = timePicker.getValue();
            if (time == null) {
                timeErrorLabel.setText("Time cannot be empty.");
                saveButton.setDisable(true);
            } else {
                timeErrorLabel.setText("");
                saveButton.setDisable(false);
            }
        }
    }

    @FXML
    private void handleDoctor(ActionEvent event) {
        Doctor selectedDoctor = doctorComboBox.getValue();
        if (selectedDoctor != null) {
            System.out.println("Selected Doctor: " + selectedDoctor.getId());
            try {
                LocalDate today = LocalDate.now();
                int currentYear = today.getYear();
                int currentMonth = today.getMonthValue(); // từ 1 đến 12

                workDateOnMonth = doctorScheduleDao.findWorkDatesByDoctorInMonth(selectedDoctor.getId(), currentYear, currentMonth);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@gmail\\.com$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhone(String phone) {
        String phoneRegex = "\\d+";
        return phone.matches(phoneRegex);
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = messageDigest.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashedBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    @FXML
    public void handleClear() {
        firstNameField.clear();
        lastNameField.clear();
        phoneField.clear();
        emailField.clear();

        firstNameErrorLabel.setText("");
        lastNameErrorLabel.setText("");
        phoneErrorLabel.setText("");
        emailErrorLabel.setText("");
        genderErrorLabel.setText("");

        saveButton.setDisable(true);
    }

    public void handleBack(ActionEvent actionEvent) {
        returnToPatientManagement(actionEvent);
    }

    public void returnToPatientManagement(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/patient/patient-management.fxml", actionEvent);
    }

    public String randomPassword() {
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(word.length());
            password.append(word.charAt(index));
        }

        for (int i = 0; i < 2; i++) {
            int index = random.nextInt(digits.length());
            password.append(digits.charAt(index));
        }

        return password.toString();
    }

}
