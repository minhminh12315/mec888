package com.home.mec888.controller.admin.service;

import com.home.mec888.dao.ServiceDao;
import com.home.mec888.entity.Service;
import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ServiceUpdateController {
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

    private ServiceDao serviceDao;
    private Service service;

    @FXML
    private void initialize() {
        serviceDao = new ServiceDao();
    }

    @FXML
    private void handleUpdate(ActionEvent actionEvent) {
        String name = nameField.getText();
        String description = descriptionField.getText();
        double price = Double.parseDouble(priceField.getText());

        service.setName(name);
        service.setDescription(description);
        service.setPrice(price);

        serviceDao.updateService(service);
        showAlert("Succesfull", "Updated service successfully", Alert.AlertType.INFORMATION);
        returnToServiceManagement(actionEvent);
    }

    public void setService(Service service) {
        this.service = service;

        if (service != null) {
            nameField.setText(service.getName());
            descriptionField.setText(service.getDescription());
            priceField.setText(String.valueOf(service.getPrice()));
        }
    }

    public void handleClear(ActionEvent actionEvent) {
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
