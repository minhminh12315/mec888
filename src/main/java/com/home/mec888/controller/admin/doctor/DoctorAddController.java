package com.home.mec888.controller.admin.doctor;

import com.home.mec888.dao.DepartmentDao;
import com.home.mec888.dao.DoctorDao;
import com.home.mec888.dao.RoomDao;
import com.home.mec888.dao.UserDao;
import com.home.mec888.entity.*;
import com.home.mec888.util.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class DoctorAddController {
    @FXML
    public ComboBox<User> userComboBox;
    @FXML
    public ComboBox<Room> roomComboBox;
    @FXML
    public TextField specializationField;
    @FXML
    public TextField licenseField;
    @FXML
    private Label userErrorLabel;
    @FXML
    private Label roomErrorLabel;
    @FXML
    private Label specializationErrorLabel;
    @FXML
    private Label licenseErrorLabel;
    @FXML
    private Button addUserButton,backButton;

    @FXML
    public TextField firstNameField, lastNameField, phoneField, emailField;
    @FXML
    public ComboBox<String> genderComboBox;
    @FXML
    public Label first_name_error, last_name_error, phone_error, email_error, gender_error, username_error;

    private UserDao userDao;
    private RoomDao roomDao;
    private DoctorDao doctorDao;

    private static final String word = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String digits = "0123456789";
    private static final Random random = new Random();

    @FXML
    public void initialize() {
        userDao = new UserDao();
        roomDao = new RoomDao();
        doctorDao = new DoctorDao();

//        userComboBox.getItems().addAll(userDao.getAllUsers());
        roomComboBox.getItems().addAll(roomDao.getAllRooms());
//
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
//
        genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female"));

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

        resetErrorLabels();
    }

//    @FXML
//    public void handleAddUser() {
//        try {
//            String lastUserName = getLastUser();
//
//            if (lastUserName == null) {
//                showAlert("Error", "No users available to assign name", Alert.AlertType.ERROR);
//                return;
//            }
//
//            // Find the user by username (lastUserName) and set it in the ComboBox
//            User lastUser = userDao.getUserByUsername(lastUserName);
//            if (lastUser != null) {
//                userComboBox.getSelectionModel().select(lastUser);
//            } else {
//                showAlert("Error", "User not found: " + lastUserName, Alert.AlertType.ERROR);
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            showAlert("Error", "Failed to save id: " + e.getMessage(), Alert.AlertType.ERROR);
//        }
//    }
//
//    public void goToDoctorAdd(ActionEvent actionEvent) {
//        SceneSwitcher.loadView("admin/doctor/doctor-add-user.fxml", actionEvent);
//    }

    private User getLastUser() {
        UserDao userDao = new UserDao();
        ObservableList<User> userList = FXCollections.observableArrayList(userDao.getAllUsers());
        if (userList.isEmpty()) {
            return null;
        }
        User lastUser = userList.getFirst();
        return lastUser;
    }

    private void resetErrorLabels() {
//        userErrorLabel.setText("");
        roomErrorLabel.setText("");
        specializationErrorLabel.setText("");
        licenseErrorLabel.setText("");

        username_error.setText("");
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
//            isValid = false;
//        }
        // Kiểm tra ComboBox Room
        if (roomComboBox.getValue() == null) {
            showError(roomComboBox, roomErrorLabel, "Please select a Room.");
            isValid = false;
        }else {
            clearError(roomComboBox, roomErrorLabel);
        }

        // Kiểm tra Specialization Field
        if (specializationField.getText().trim().isEmpty()) {
            showError(specializationField, specializationErrorLabel, "Please enter specialization.");
            isValid = false;
        }else {
            clearError(specializationField, specializationErrorLabel);
        }

        // Kiểm tra License Field
        if (licenseField.getText().trim().isEmpty()) {
            showError(licenseField, licenseErrorLabel, "Please enter a license.");
            isValid = false;
        }else {
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
        } else if (userDao.isEmailExists(emailField.getText())) {
            showError(emailField, email_error, "Email already exists!");
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

    public void handleClear(ActionEvent event) {
        // Xóa lựa chọn của các ComboBox
//        userComboBox.getSelectionModel().clearSelection();
        roomComboBox.getSelectionModel().clearSelection();

        // Xóa nội dung của các TextField
        specializationField.clear();
        licenseField.clear();

        firstNameField.clear();
        lastNameField.clear();
        phoneField.clear();
        emailField.clear();
        genderComboBox.getSelectionModel().clearSelection();
        resetErrorLabels();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@gmail\\.com$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhone(String phone) {
        String phoneRegex = "\\d+";
        return phone.matches(phoneRegex);
    }

    public void handleSave(ActionEvent event) {
        if (!validateFields()) {
            return;
        }

        try {
            String email = emailField.getText();
            String phone = phoneField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String gender = genderComboBox.getValue();

            String username = firstName + " " + lastName;

            if (userDao.isUsernameExists(username)){
                username_error.setText("Username already exists!");
            } else {
                username_error.setText("");
            }

            String password = randomPassword();

            try {
                // Create a new User object
                User user = new User();
                user.setUsername(username);
                user.setPassword(hashPassword(password));
                user.setEmail(email);
                user.setPhone(phone);
                user.setRoleId(4); // 4: patient
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setGender(gender);
                user.setDateOfBirth(null);
                user.setAddress(null);

                // Save the user to the database
                userDao.saveUser(user);
//                showAlert("Success", "User added successfully!", Alert.AlertType.INFORMATION);
                showAlert("Patient" + username, "Random password!"+password, Alert.AlertType.INFORMATION);

            } catch (Exception e) {
                showAlert("Error", "Error adding user: " + e.getMessage(), Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            showAlert("Error", "Failed to save user", Alert.AlertType.ERROR);
        }

        User user = getLastUser();
        Room room = roomComboBox.getValue();
        String specialization = specializationField.getText().trim();
        String license_number = licenseField.getText().trim();

        resetErrorLabels(); // Xóa các thông báo lỗi cũ

        try {
            Doctor doctor = new Doctor();
            doctor.setUser(user);
            doctor.setRoom(room);
//            doctor.setSpecialization(specialization);
            doctor.setLicense_number(license_number);

            doctorDao.saveDoctor(doctor);

            showAlert("Success", "Doctor added successfully!", Alert.AlertType.INFORMATION);
            returnToDoctorManagement(event);
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            throw new RuntimeException(e);
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

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = messageDigest.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashedBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
