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

    private UserDao userDao;
    private RoomDao roomDao;
    private DoctorDao doctorDao;

    String lastUserName = null;

    @FXML
    public void initialize() {
        userDao = new UserDao();
        roomDao = new RoomDao();
        doctorDao = new DoctorDao();

        userComboBox.getItems().addAll(userDao.getAllUsers());
        roomComboBox.getItems().addAll(roomDao.getAllRooms());

        userComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                setText((user == null || empty) ? "" : String.valueOf(user.getUsername()));
            }
        });

        userComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(User user) {
                return (user != null) ? String.valueOf(user.getUsername()) : "";
            }

            @Override
            public User fromString(String string) {
                return null;
            }
        });

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

    @FXML
    public void handleAddUser() {
        try {
            lastUserName = getLastUserName();

            if (lastUserName == null) {
                showAlert("Error", "No users available to assign name", Alert.AlertType.ERROR);
                return;
            }

            // Find the user by username (lastUserName) and set it in the ComboBox
            User lastUser = userDao.getUserByUsername(lastUserName);
            if (lastUser != null) {
                userComboBox.getSelectionModel().select(lastUser);
            } else {
                showAlert("Error", "User not found: " + lastUserName, Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            showAlert("Error", "Failed to save id: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void goToDoctorAdd(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/doctor/doctor-add-user.fxml", actionEvent);
    }

    private String getLastUserName() {
        UserDao userDao = new UserDao();
        ObservableList<User> userList = FXCollections.observableArrayList(userDao.getAllUsers());
        if (userList.isEmpty()) {
            return null;
        }
        User lastUser = userList.getFirst();
        return lastUser.getUsername();
    }

    private void resetErrorLabels() {
        userErrorLabel.setText("");
        roomErrorLabel.setText("");
        specializationErrorLabel.setText("");
        licenseErrorLabel.setText("");
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

    private boolean validateFields() {
        boolean isValid = true;

        // Kiểm tra ComboBox User
        if (userComboBox.getValue() == null) {
            showError(userComboBox, userErrorLabel, "Please select a user.");
            isValid = false;
        }
        // Kiểm tra ComboBox Room
        if (roomComboBox.getValue() == null) {
            showError(roomComboBox, roomErrorLabel, "Please select a Room.");
            isValid = false;
        }

        // Kiểm tra Specialization Field
        if (specializationField.getText().trim().isEmpty()) {
            showError(specializationField, specializationErrorLabel, "Please enter specialization.");
            isValid = false;
        }

        // Kiểm tra License Field
        if (licenseField.getText().trim().isEmpty()) {
            showError(licenseField, licenseErrorLabel, "Please enter a license.");
            isValid = false;
        }
        return isValid;
    }

    public void handleClear(ActionEvent event) {
        // Xóa lựa chọn của các ComboBox
        userComboBox.getSelectionModel().clearSelection();
        roomComboBox.getSelectionModel().clearSelection();

        // Xóa nội dung của các TextField
        specializationField.clear();
        licenseField.clear();

        resetErrorLabels();
    }

    public void handleSave(ActionEvent event) {
        if (!validateFields()) {
            return; // Nếu có lỗi, dừng việc lưu
        }
        User user = userComboBox.getValue();
        Room room = roomComboBox.getValue();
        String specialization = specializationField.getText().trim();
        String license_number = licenseField.getText().trim();

        resetErrorLabels(); // Xóa các thông báo lỗi cũ

        try {
            Doctor doctor = new Doctor();
            doctor.setUser(user);
            doctor.setRoom(room);
            doctor.setSpecialization(specialization);
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
}
