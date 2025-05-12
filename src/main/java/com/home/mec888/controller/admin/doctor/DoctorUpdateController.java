package com.home.mec888.controller.admin.doctor;

import com.home.mec888.dao.DoctorDao;
import com.home.mec888.dao.RoomDao;
import com.home.mec888.dao.SpecializationDao;
import com.home.mec888.dao.UserDao;
import com.home.mec888.entity.Doctor;
import com.home.mec888.entity.Room;
import com.home.mec888.entity.Specialization;
import com.home.mec888.entity.User;
import com.home.mec888.util.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DoctorUpdateController {
    @FXML
    public ComboBox<User> userComboBox;
    @FXML
    public ComboBox<Room> roomComboBox;
    @FXML
    public TextField licenseField;
    @FXML
    private Label userErrorLabel;
    @FXML
    private Label roomErrorLabel;
    @FXML
    public ComboBox<Specialization> specializationComboBox;
    @FXML
    private Label specializationErrorLabel;

    @FXML
    private Label licenseErrorLabel;

    @FXML
    public TextField firstNameField, lastNameField, phoneField, emailField;
    @FXML
    public ComboBox<String> genderComboBox;
    @FXML
    public Label first_name_error, last_name_error, phone_error, email_error, gender_error;

    private UserDao userDao;
    private RoomDao roomDao;
    private DoctorDao doctorDao;
    private SpecializationDao specializationDao;

    private Long doctorId;
    private User user;

    @FXML
    public void initialize() {
        userDao = new UserDao();
        roomDao = new RoomDao();
        doctorDao = new DoctorDao();
        specializationDao = new SpecializationDao(); // Initialize the specialization DAO
//        userComboBox.getItems().addAll(userDao.getAllUsers());
        roomComboBox.getItems().addAll(roomDao.getAllRooms());

        specializationComboBox.getItems().addAll(specializationDao.getAllSpecializations());

//        userComboBox.setCellFactory(param -> new ListCell<>() {
//            @Override
//            protected void updateItem(User user, boolean empty) {
//                super.updateItem(user, empty);
//                setText((user == null || empty) ? "" : String.valueOf(user.getUsername()));
//            }
//        });
//
//        userComboBox.setConverter(new StringConverter<>() {
//            @Override
//            public String toString(User user) {
//                return (user != null) ? String.valueOf(user.getUsername()) : "";
//            }
//
//            @Override
//            public User fromString(String string) {
//                return null;
//            }
//        });

        roomComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Room room, boolean empty) {
                super.updateItem(room, empty);
                setText((room == null || empty) ? "" : String.valueOf(room.getRoomNumber()));
            }
        });

        roomComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Room room) {
                return (room != null) ? String.valueOf(room.getRoomNumber()) : "";
            }

            @Override
            public Room fromString(String string) {
                return null;
            }
        });

        // Set up display for specialization combobox
        specializationComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Specialization specialization, boolean empty) {
                super.updateItem(specialization, empty);
                setText((specialization == null || empty) ? "" : specialization.getName());
            }
        });

        specializationComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Specialization specialization) {
                return (specialization != null) ? specialization.getName() : "";
            }

            @Override
            public Specialization fromString(String string) {
                return null;
            }
        });

        try {
            genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        resetErrorLabels();
    }
    private void resetErrorLabels() {
//        userErrorLabel.setText("");
        roomErrorLabel.setText("");
        specializationErrorLabel.setText("");
        licenseErrorLabel.setText("");

        first_name_error.setText("");
        last_name_error.setText("");
        phone_error.setText("");
        email_error.setText("");
        gender_error.setText("");
    }
    public void showError(Control field, Label errorLabel, String message) {
        if (field instanceof TextField || field instanceof PasswordField) {
            field.setStyle("-fx-border-color: red");
        } else if (field instanceof ComboBox) {
            field.setStyle("-fx-border-color: red");
        }
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: red");
    }

    private void clearError(Control field, Label errorLabel){
        field.setStyle("-fx-border-color: #111827");
        errorLabel.setText("");
    }

    private boolean validateFields() {
        boolean isValid = true;

        // Kiểm tra ComboBox User
//        if (userComboBox.getValue() == null) {
//            showError(userComboBox, userErrorLabel, "Please select a user.");
////            userErrorLabel.setText("Please select a user.");
//            isValid = false;
//        }
        // Kiểm tra ComboBox Room
        if (roomComboBox.getValue() == null) {
            showError(roomErrorLabel, roomErrorLabel, "Please select a user.");
            isValid = false;
        } else {
            clearError(roomComboBox, roomErrorLabel);
        }

        // Kiểm tra Specialization Field
        if (specializationComboBox.getValue() == null) {
            showError(specializationComboBox, specializationErrorLabel, "Please select a specialization.");
            isValid = false;
        } else {
            clearError(specializationComboBox, specializationErrorLabel);
        }

        // Kiểm tra License Field
        if (licenseField.getText().trim().isEmpty()) {
            showError(licenseField, licenseErrorLabel, "Please select a user.");
            isValid = false;
        } else {
            clearError(licenseField,licenseErrorLabel);
        }


        if (firstNameField.getText().trim().isEmpty()) {
            showError(firstNameField, first_name_error, "Address cannot be empty.");
            isValid = false;
        }else {
            clearError(firstNameField,first_name_error);
        }
        if (lastNameField.getText().trim().isEmpty()) {
            showError(lastNameField, last_name_error, "Address cannot be empty.");
            isValid = false;
        }else {
            clearError(lastNameField,last_name_error);
        }
        if (genderComboBox.getValue().isEmpty() || genderComboBox == null) {
            showError(genderComboBox, gender_error, "Address cannot be empty.");
            isValid = false;
        } else {
            clearError(genderComboBox, gender_error);
        }
        if (emailField.getText().isEmpty()) {
            showError(emailField, email_error, "Email cannot be empty.");
            isValid = false;
        } else if (!isValidEmail(emailField.getText())) {
            showError(emailField, email_error, "Invalid email format!");
            isValid = false;
        } else {
            clearError(emailField, email_error);
        }
        if (phoneField.getText().isEmpty()) {
            showError(phoneField, phone_error, "Phone cannot be empty.");
            isValid = false;
        } else if (!isValidPhone(phoneField.getText())) {
            showError(phoneField, phone_error, "Invalid phone format!");
            isValid = false;
        } else {
            clearError(phoneField, phone_error);
        }

        return isValid;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+\\d\\.com$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhone(String phone) {
        String phoneRegex = "\\d+";
        return phone.matches(phoneRegex);
    }

    public void setDoctor(Doctor doctor) {
        if (doctor == null) {
            return;
        }
        doctorId = doctor.getId();
        // Gán dữ liệu vào các TextField
        if (doctor.getSpecialization() != null) {
            for (Specialization spec : specializationComboBox.getItems()) {
                if (spec.getId().equals(doctor.getSpecialization().getId())) {
                    specializationComboBox.setValue(spec);
                    break;
                }
            }
        }
        licenseField.setText(doctor.getLicense_number());
//        if (doctor.getUser().getId()!= null) {
//            User user = userDao.getUserById(doctor.getUser().getId());
//            if (user != null) {
//                userComboBox.getSelectionModel().select(user);
//            }
//        }

        // Xử lý ComboBox Room
        if (doctor.getRoom().getId() != null) {
            Room room = roomDao.getRoomById(doctor.getRoom().getId());
            if (room != null) {
                roomComboBox.getSelectionModel().select(room);
            }
        }

        if (doctor.getUser().getId()!= null) {
            this.user = userDao.getUserById(doctor.getUser().getId());
            if (user != null) {
                firstNameField.setText(user.getFirstName());
                lastNameField.setText(user.getLastName());
                phoneField.setText(user.getPhone());
                emailField.setText(user.getEmail());
                genderComboBox.setValue(user.getGender());
            }
        }
    }

    public void handleClear(ActionEvent event) {
        // Xóa lựa chọn của các ComboBox
//        userComboBox.getSelectionModel().clearSelection();
        roomComboBox.getSelectionModel().clearSelection();
        specializationComboBox.getSelectionModel().clearSelection();

        // Đặt giá trị ComboBox về null để đảm bảo xóa hoàn toàn
//        userComboBox.setValue(null);
        roomComboBox.setValue(null);
        specializationComboBox.setValue(null);

        // Xóa nội dung của các TextField
        licenseField.clear();

        firstNameField.clear();
        lastNameField.clear();
        phoneField.clear();
        emailField.clear();
    }

    public void handleSave(ActionEvent event) {
        if (!validateFields()) {
            return; // Nếu có lỗi, dừng việc lưu
        }

        {
            String email = emailField.getText();
            String phone = phoneField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String gender = genderComboBox.getValue();

            String username = user.getUsername();

            String password = user.getPassword();

            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setPhone(phone);
            user.setRoleId(4);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setGender(gender);
            user.setDateOfBirth(null);
            user.setAddress(null);

            // Save the user to the database
            userDao.updateUser(user);
        }

        {
//            User user = userComboBox.getValue();
            Room roomId = roomComboBox.getValue();
            Specialization specialization = specializationComboBox.getValue();
            String license_number = licenseField.getText().trim();

            try {
                Doctor doctor = new Doctor();
                doctor.setId(doctorId);
                doctor.setUser(user);
                doctor.setRoom(roomId);
                doctor.setSpecialization(specialization);
                doctor.setLicense_number(license_number);
                doctor.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

                doctorDao.updateDoctor(doctor);

                showAlert("Success", "Doctor added successfully!", Alert.AlertType.INFORMATION);
                returnToDoctorManagement(event);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void returnToDoctorManagement(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/doctor/doctor-management.fxml", actionEvent);
    }

    public void handleBack(ActionEvent event) {
        returnToDoctorManagement(event);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}