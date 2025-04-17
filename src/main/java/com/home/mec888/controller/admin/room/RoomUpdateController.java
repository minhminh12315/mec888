package com.home.mec888.controller.admin.room;

import com.home.mec888.dao.DepartmentDao;
import com.home.mec888.dao.RoomDao;
import com.home.mec888.entity.Department;
import com.home.mec888.entity.Room;
import com.home.mec888.util.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomUpdateController {

    @FXML private TextField roomNumberField;
    @FXML private TextField roomTypeField;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private ComboBox<String> departmentComboBox;

    @FXML private Label roomNumberErrorLabel;
    @FXML private Label roomTypeErrorLabel;
    @FXML private Label departmentErrorLabel;

    private RoomDao roomDao;
    private DepartmentDao departmentDao;
    private Room room;

    private final Map<String, Department> departmentMap = new HashMap<>();

    @FXML
    private void initialize() {
        roomDao = new RoomDao();
        departmentDao = new DepartmentDao();

        // Load status
        statusComboBox.setItems(FXCollections.observableArrayList("Available", "Occupied"));

        // Load departments
        List<Department> departments = departmentDao.getAllDepartments();
        for (Department dept : departments) {
            departmentMap.put(dept.getName(), dept);
        }
        departmentComboBox.setItems(FXCollections.observableArrayList(departmentMap.keySet()));

        // Hide error labels initially
        roomNumberErrorLabel.setVisible(false);
        roomTypeErrorLabel.setVisible(false);
        departmentErrorLabel.setVisible(false);

        // Clear errors when typing
        roomNumberField.setOnKeyReleased(e -> roomNumberErrorLabel.setVisible(false));
        roomTypeField.setOnKeyReleased(e -> roomTypeErrorLabel.setVisible(false));
    }

    // Gọi từ controller cha để truyền room cần update
    public void setRoom(Room room) {
        this.room = room;

        roomNumberField.setText(room.getRoomNumber());
        roomTypeField.setText(room.getRoomType());
        statusComboBox.setValue(room.getStatus());

        if (room.getDepartment() != null) {
            departmentComboBox.setValue(room.getDepartment().getName());
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        String roomNumber = roomNumberField.getText().trim();
        String roomType = roomTypeField.getText().trim();
        String status = statusComboBox.getValue();
        String selectedDepartmentName = departmentComboBox.getValue();

        roomNumberErrorLabel.setVisible(false);
        roomTypeErrorLabel.setVisible(false);
        departmentErrorLabel.setVisible(false);

        boolean isValid = true;

        if (roomNumber.isEmpty()) {
            roomNumberErrorLabel.setText("Room Number is required.");
            roomNumberErrorLabel.setVisible(true);
            isValid = false;
        }

        if (roomType.isEmpty()) {
            roomTypeErrorLabel.setText("Room Type is required.");
            roomTypeErrorLabel.setVisible(true);
            isValid = false;
        }

        Department selectedDepartment = departmentMap.get(selectedDepartmentName);
        if (selectedDepartment == null) {
            departmentErrorLabel.setText("Department is required.");
            departmentErrorLabel.setVisible(true);
            isValid = false;
        }

        if (!isValid) return;

        room.setRoomNumber(roomNumber);
        room.setRoomType(roomType);
        room.setStatus(status);
        room.setDepartment(selectedDepartment);
        room.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        try {
            roomDao.updateRoom(room);
            System.out.println("Room updated successfully!");
            handleBack(event);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog("Failed to update room. Please try again.");
        }
    }

    @FXML
    private void handleClear() {
        roomNumberField.clear();
        roomTypeField.clear();
        statusComboBox.getSelectionModel().clearSelection();
        departmentComboBox.getSelectionModel().clearSelection();

        roomNumberErrorLabel.setVisible(false);
        roomTypeErrorLabel.setVisible(false);
        departmentErrorLabel.setVisible(false);
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
