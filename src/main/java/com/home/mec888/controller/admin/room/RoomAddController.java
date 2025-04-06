package com.home.mec888.controller.admin.room;

import com.home.mec888.dao.RoomDao;
import com.home.mec888.entity.Room;
import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RoomAddController {

    // FXML Fields
    @FXML
    private TextField roomNumberField;
    @FXML
    private TextField roomTypeField;
    @FXML
    private ComboBox<String> statusComboBox;  // Đảm bảo rằng trường này được khai báo
    @FXML
    private Label roomNumberErrorLabel;
    @FXML
    private Label roomTypeErrorLabel;
    @FXML
    private Label statusErrorLabel;

    private RoomDao roomDao; // RoomDao to handle database operations

    // Constructor to initialize the RoomDao
    public RoomAddController() {
        roomDao = new RoomDao(); // Assuming you have a RoomDao class
    }
    @FXML
    private void initialize() {
        // Add listeners to clear error labels when the user types in any field
        roomNumberField.textProperty().addListener((observable, oldValue, newValue) -> clearErrorMessages(roomNumberField));
        roomTypeField.textProperty().addListener((observable, oldValue, newValue) -> clearErrorMessages(roomTypeField));
    }
    // Handle the save button click
    @FXML
    private void handleSave(ActionEvent event) {
        String roomNumber = roomNumberField.getText().trim();
        String roomType = roomTypeField.getText().trim();
        String status = statusComboBox.getValue(); // Lấy giá trị được chọn từ ComboBox

        // Reset all error messages
        roomNumberErrorLabel.setVisible(false);
        roomTypeErrorLabel.setVisible(false);
        statusErrorLabel.setVisible(false);

        boolean isValid = true;

        // Kiểm tra phòng trống
        if (roomNumber.isEmpty()) {
            roomNumberErrorLabel.setText("Room number is required.");
            roomNumberErrorLabel.setVisible(true);
            isValid = false;
        }

        // Kiểm tra loại phòng
        if (roomType.isEmpty()) {
            roomTypeErrorLabel.setText("Room type is required.");
            roomTypeErrorLabel.setVisible(true);
            isValid = false;
        }

        // Kiểm tra trạng thái
        if (status == null || status.isEmpty()) {
            statusErrorLabel.setText("Status is required.");
            statusErrorLabel.setVisible(true);
            isValid = false;
        } else if (!status.equals("Available") && !status.equals("Occupied")) {
            statusErrorLabel.setText("Status must be 'Available' or 'Occupied'.");
            statusErrorLabel.setVisible(true);
            isValid = false;
        }

        // Nếu dữ liệu không hợp lệ, dừng lại
        if (!isValid) {
            return;
        }

        // Lấy thời gian hiện tại cho createdAt và updatedAt
        Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());
        Timestamp updatedAt = Timestamp.valueOf(LocalDateTime.now());

        // Tạo một đối tượng Room mới
        Room newRoom = new Room(roomNumber, roomType, status, createdAt, updatedAt);

        // Lưu vào cơ sở dữ liệu
        try {
            boolean isSaved = roomDao.saveRoom(newRoom);
            if (isSaved) {
                System.out.println("Room saved successfully!");
                handleBack(event);
                handleClear(); // Xóa các trường sau khi lưu
            } else {
                showErrorDialog("Failed to save room.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog("An error occurred while saving the room. Please try again.");
        }
    }

    // Show an error dialog with a message
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Handle the clear button click
    @FXML
    private void handleClear() {
        // Only clear the fields if they have data
        if (!roomNumberField.getText().isEmpty()) {
            roomNumberField.clear();
        }

        if (!roomTypeField.getText().isEmpty()) {
            roomTypeField.clear();
        }



    }

    private void clearErrorMessages(TextField field) {
        if (field == roomNumberField) {
            roomNumberErrorLabel.setVisible(false); // Hide error label for room number
        } else if (field == roomTypeField) {
            roomTypeErrorLabel.setVisible(false); // Hide error label for room type
        }
    }
    // Handle the back button click
    @FXML
    private void handleBack(ActionEvent event) {
        // Chuyển về màn hình Room Management
        SceneSwitcher.loadView("admin/room/room-management.fxml", event);
    }
}
