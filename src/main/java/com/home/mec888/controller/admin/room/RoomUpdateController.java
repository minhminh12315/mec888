package com.home.mec888.controller.admin.room;

import com.home.mec888.dao.RoomDao;
import com.home.mec888.entity.Room;
import com.home.mec888.util.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RoomUpdateController {

    // FXML Fields for room data and error labels
    @FXML
    private TextField roomNumberField;
    @FXML
    private TextField roomTypeField;
    @FXML
    private ComboBox<String> statusComboBox;

    // Error labels
    @FXML
    private Label roomNumberErrorLabel;
    @FXML
    private Label roomTypeErrorLabel;

    private RoomDao roomDao;
    private Room room;

    @FXML
    private void initialize() {
        roomDao = new RoomDao(); // Initialize RoomDao for database operations

        // Tạo danh sách trạng thái cho ComboBox
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Available", "Occupied");

        // Đặt các giá trị vào ComboBox mà không bị trùng lặp
        statusComboBox.setItems(statusOptions);

        // Hide error labels initially
        roomNumberErrorLabel.setVisible(false);
        roomTypeErrorLabel.setVisible(false);

        // Lắng nghe sự kiện nhập liệu của từng trường
        roomNumberField.setOnKeyReleased(event -> clearErrorMessage(roomNumberErrorLabel));
        roomTypeField.setOnKeyReleased(event -> clearErrorMessage(roomTypeErrorLabel));
    }

    private void clearErrorMessage(Label errorLabel) {
        // Ẩn thông báo lỗi khi người dùng bắt đầu nhập liệu
        errorLabel.setVisible(false);
    }

    // Phương thức khởi tạo hoặc setRoom để nhận đối tượng Room từ trang chính
    public void setRoom(Room room) {
        this.room = room;

        // Thiết lập dữ liệu từ đối tượng Room vào các trường nhập liệu
        roomNumberField.setText(room.getRoomNumber());
        roomTypeField.setText(room.getRoomType());
        statusComboBox.setValue(room.getStatus());
    }

    // Handle the Save button action
    @FXML
    private void handleUpdate(ActionEvent event) {
        // Lấy các giá trị nhập vào từ người dùng
        String roomNumber = roomNumberField.getText().trim();
        String roomType = roomTypeField.getText().trim();
        String status = statusComboBox.getValue();

        // Reset visibility của các label lỗi
        roomNumberErrorLabel.setVisible(false);
        roomTypeErrorLabel.setVisible(false);

        boolean isValid = true;

        // Kiểm tra tính hợp lệ của Room Number
        if (roomNumber.isEmpty()) {
            roomNumberErrorLabel.setText("Room Number is required.");
            roomNumberErrorLabel.setVisible(true);
            isValid = false;
        }

        // Kiểm tra tính hợp lệ của Room Type
        if (roomType.isEmpty()) {
            roomTypeErrorLabel.setText("Room Type is required.");
            roomTypeErrorLabel.setVisible(true);
            isValid = false;
        }

        // Nếu bất kỳ trường nào không hợp lệ, dừng lại
        if (!isValid) {
            return;
        }

        // Lấy thời gian hiện tại cho trường updatedAt
        Timestamp updatedAt = Timestamp.valueOf(LocalDateTime.now());

        // Giả sử createdAt không thay đổi, lấy giá trị createdAt cũ từ đối tượng room hiện tại
        Timestamp createdAt = room.getCreatedAt(); // Giữ nguyên giá trị createdAt từ phòng hiện tại

        // Cập nhật thông tin phòng
        room.setRoomNumber(roomNumber);
        room.setRoomType(roomType);
        room.setStatus(status);
        room.setUpdatedAt(updatedAt); // Cập nhật trường updatedAt, nhưng không thay đổi createdAt

        try {
            // Cập nhật thông tin phòng trong cơ sở dữ liệu
            roomDao.updateRoom(room);
            System.out.println("Room updated successfully!");

            // Sau khi lưu thành công, xóa các trường nhập liệu
            handleClear();

            // Quay lại màn hình Room Management sau khi cập nhật thành công
            handleBack(event);  // Sử dụng event từ nút Save
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to update the room.");
        }
    }

    // Handle the Clear button action
    @FXML
    private void handleClear() {
        // Clear all input fields
        roomNumberField.clear();
        roomTypeField.clear();

        // Hide error labels
        roomNumberErrorLabel.setVisible(false);
        roomTypeErrorLabel.setVisible(false);
    }

    // Handle the Back button action
    @FXML
    private void handleBack(ActionEvent event) {
        // Chuyển về màn hình Room Management
        SceneSwitcher.loadView("admin/room/room-management.fxml", event);
    }

}
