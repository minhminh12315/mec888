package com.home.mec888.controller.admin.service;

import com.home.mec888.dao.ServiceDao;
import com.home.mec888.entity.Service;
import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.AccessibleAction;
import javafx.scene.control.*;

public class ServiceAddController {
    @FXML
    public TextField nameField;

    @FXML
    public TextArea descriptionField;

    @FXML
    public TextField priceField;

    @FXML
    public Label nameErrorLabel;

    @FXML
    public Label descriptionErrorLabel;

    @FXML
    public Label priceErrorLabel;

    @FXML
    public Button saveButton, clearButton, backButton;

    ServiceDao serviceDao = new ServiceDao();

    @FXML
    private void handleSave(ActionEvent actionEvent) {
        String serviceName = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String price = priceField.getText().trim();

        boolean valid = true;
        double priceValue = 0.0;


        // Validate từng field
        if (serviceName.isEmpty()) {
            nameErrorLabel.setText("Medicine name is required.");
            nameErrorLabel.setVisible(true);
            valid = false;
        } else if (serviceDao.getServiceByName(serviceName) != null) {
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

        if (!valid) {
            return; // Nếu có lỗi, không thực hiện lưu
        }

        Service service = new Service();
        service.setName(serviceName);
        service.setDescription(description);
        service.setPrice(priceValue);

        serviceDao.saveService(service);
        showAlert("Succes", "Service added successfullt!", Alert.AlertType.INFORMATION);

        handleClear();
        returnToServiceManagement(actionEvent);
    }

    @FXML
    private void handleClear() {
        nameField.clear();
        descriptionField.clear();
        priceField.clear();

        clearErrorLabels();
    }

    private void clearErrorLabels() {
        nameErrorLabel.setText("");
        nameErrorLabel.setVisible(false);
        descriptionErrorLabel.setText("");
        descriptionErrorLabel.setVisible(false);
        priceErrorLabel.setText("");
        priceErrorLabel.setVisible(false);
    }

    @FXML
    public void handleBack(ActionEvent actionEvent) {
        returnToServiceManagement(actionEvent);
    }

    private void returnToServiceManagement(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/service/service-management.fxml", actionEvent);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
