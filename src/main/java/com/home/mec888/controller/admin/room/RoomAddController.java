package com.home.mec888.controller.admin.room;

import com.home.mec888.dao.DepartmentDao;
import com.home.mec888.dao.RoomDao;
import com.home.mec888.entity.Department;
import com.home.mec888.entity.Room;
import com.home.mec888.util.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomAddController {

    @FXML private TextField roomNumberField;
    @FXML private TextField roomTypeField;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private ComboBox<String> departmentComboBox;

    @FXML private Label roomNumberErrorLabel;
    @FXML private Label roomTypeErrorLabel;
    @FXML private Label statusErrorLabel;
    @FXML private Label departmentErrorLabel;

    private RoomDao roomDao;
    private DepartmentDao departmentDao;

    // Dùng để ánh xạ tên -> đối tượng Department
    private Map<String, Department> departmentMap = new HashMap<>();

    public RoomAddController() {
        roomDao = new RoomDao();
        departmentDao = new DepartmentDao();
    }

    @FXML
    private void initialize() {
        roomNumberField.textProperty().addListener((obs, oldVal, newVal) -> clearErrorMessages(roomNumberField));
        roomTypeField.textProperty().addListener((obs, oldVal, newVal) -> clearErrorMessages(roomTypeField));

        statusComboBox.setItems(FXCollections.observableArrayList("Available", "Occupied"));

        // Load department từ DB và đưa vào ComboBox
        List<Department> departments = departmentDao.getAllDepartments(); // bạn cần có DAO này
        for (Department dept : departments) {
            departmentMap.put(dept.getName(), dept); // lưu tên -> object
        }
        departmentComboBox.setItems(FXCollections.observableArrayList(departmentMap.keySet()));
    }

    @FXML
    private void handleSave(ActionEvent event) {
        String roomNumber = roomNumberField.getText().trim();
        String roomType = roomTypeField.getText().trim();
        String status = statusComboBox.getValue();
        String selectedDepartmentName = departmentComboBox.getValue();

        roomNumberErrorLabel.setVisible(false);
        roomTypeErrorLabel.setVisible(false);
        statusErrorLabel.setVisible(false);
        departmentErrorLabel.setVisible(false);

        boolean isValid = true;

        if (roomNumber.isEmpty()) {
            roomNumberErrorLabel.setText("Room number is required.");
            roomNumberErrorLabel.setVisible(true);
            isValid = false;
        }

        if (roomType.isEmpty()) {
            roomTypeErrorLabel.setText("Room type is required.");
            roomTypeErrorLabel.setVisible(true);
            isValid = false;
        }

        if (status == null || status.isEmpty()) {
            statusErrorLabel.setText("Status is required.");
            statusErrorLabel.setVisible(true);
            isValid = false;
        } else if (!status.equals("Available") && !status.equals("Occupied")) {
            statusErrorLabel.setText("Status must be 'Available' or 'Occupied'.");
            statusErrorLabel.setVisible(true);
            isValid = false;
        }

        Department selectedDepartment = departmentMap.get(selectedDepartmentName);
        if (selectedDepartment == null) {
            departmentErrorLabel.setText("Department is required.");
            departmentErrorLabel.setVisible(true);
            isValid = false;
        }

        if (!isValid) return;

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        Room newRoom = new Room(selectedDepartment, roomNumber, roomType, status, now, now);

        try {
            boolean isSaved = roomDao.saveRoom(newRoom);
            if (isSaved) {
                System.out.println("Room saved successfully!");
                handleBack(event);
                handleClear();
            } else {
                showErrorDialog("Failed to save room.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog("An error occurred while saving the room. Please try again.");
        }
    }

    @FXML
    private void handleClear() {
        roomNumberField.clear();
        roomTypeField.clear();
        statusComboBox.getSelectionModel().clearSelection();
        departmentComboBox.getSelectionModel().clearSelection();
    }

    private void clearErrorMessages(TextField field) {
        if (field == roomNumberField) {
            roomNumberErrorLabel.setVisible(false);
        } else if (field == roomTypeField) {
            roomTypeErrorLabel.setVisible(false);
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        SceneSwitcher.loadView("admin/room/room-management.fxml", event);
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
