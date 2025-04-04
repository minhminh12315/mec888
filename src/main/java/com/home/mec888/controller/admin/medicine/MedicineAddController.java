package com.home.mec888.controller.admin.medicine;

import com.home.mec888.dao.MedicineDao;
import com.home.mec888.dao.UserDao;
import com.home.mec888.entity.Medicine;
import com.home.mec888.entity.User;
import com.home.mec888.util.SceneSwitcher;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

public class MedicineAddController {

    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField manufacturerField;
    // Các label hiển thị lỗi
    @FXML
    private Label nameErrorLabel;
    @FXML
    private Label descriptionErrorLabel;
    @FXML
    private Label priceErrorLabel;
    @FXML
    private Label manufacturerErrorLabel;

    MedicineDao medicineDao = new MedicineDao();

    @FXML
    private void handleSave(ActionEvent event) {
        // Reset các thông báo lỗi
        clearErrorLabels();

        String medicineName = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String price = priceField.getText().trim();
        String manufacturer = manufacturerField.getText().trim();

        boolean valid = true;
        double priceValue = 0.0;


        // Validate từng field
        if (medicineName.isEmpty()) {
            nameErrorLabel.setText("Medicine name is required.");
            nameErrorLabel.setVisible(true);
            valid = false;
        } else if (medicineDao.getMedicineByName(medicineName) != null) {
            nameErrorLabel.setText("Medicine name already exists.");
            nameErrorLabel.setVisible(true);
            valid = false;
        }

        if (description.isEmpty()) {
            descriptionErrorLabel.setText("Description is required.");
            descriptionErrorLabel.setVisible(true);
            valid = false;
        }

        if (price.isEmpty()) {
            priceErrorLabel.setText("Price is required.");
            priceErrorLabel.setVisible(true);
            valid = false;
        } else if (!price.matches("\\d+(\\.\\d+)?")) {
            priceErrorLabel.setText("Price must be a number.");
            priceErrorLabel.setVisible(true);
            valid = false;
        } else {
            try {
                priceValue = Double.parseDouble(price);
                if (priceValue <= 0) {
                    priceErrorLabel.setText("Price must be a positive number.");
                    priceErrorLabel.setVisible(true);
                    valid = false;
                }
            } catch (NumberFormatException e) {
                priceErrorLabel.setText("Invalid price format.");
                priceErrorLabel.setVisible(true);
                valid = false;
            }
        }

        if (manufacturer.isEmpty()) {
            manufacturerErrorLabel.setText("Manufacturer is required.");
            manufacturerErrorLabel.setVisible(true);
            valid = false;
        }

        if (!valid) {
            return; // Nếu có lỗi, không thực hiện lưu
        }

        // Tạo đối tượng Medicine mới

        Medicine medicine = new Medicine();
        medicine.setName(medicineName);
        medicine.setDescription(description);
        medicine.setPrice(priceValue);
        medicine.setManufacturer(manufacturer);

        System.out.println(medicine);

        // Lưu medicine vào cơ sở dữ liệu
        medicineDao.saveMedicine(medicine);
        showAlert("Success", "Medicine added successfully!", Alert.AlertType.INFORMATION);

        // Clear các trường sau khi lưu thành công
        handleClear();
        returnToMedicineManagement(event);
    }

    @FXML
    private void handleClear() {
        nameField.clear();
        descriptionField.clear();
        priceField.clear();
        manufacturerField.clear();
        clearErrorLabels();
    }

    // Hàm xóa thông báo lỗi
    private void clearErrorLabels() {
        nameErrorLabel.setText("");
        nameErrorLabel.setVisible(false);
        descriptionErrorLabel.setText("");
        descriptionErrorLabel.setVisible(false);
        priceErrorLabel.setText("");
        priceErrorLabel.setVisible(false);
        manufacturerErrorLabel.setText("");
        manufacturerErrorLabel.setVisible(false);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleBack(ActionEvent actionEvent) {
        returnToMedicineManagement(actionEvent);
    }

    private void returnToMedicineManagement(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/medicine/medicine-management.fxml", actionEvent);
    }

}
