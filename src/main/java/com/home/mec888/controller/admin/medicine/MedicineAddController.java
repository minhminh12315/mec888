package com.home.mec888.controller.admin.medicine;

import com.home.mec888.dao.MedicineDao;
import com.home.mec888.entity.Medicine;
import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class MedicineAddController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField activeIngredientField;
    @FXML
    private TextField dosageField;
    @FXML
    private TextField unitField;
    @FXML
    private TextField formField;
    @FXML
    private TextField manufacturerCodeField;
    @FXML
    private TextField slCodeField;
    @FXML
    private TextField priceField;
    @FXML
    private DatePicker expiryDatePicker;
    @FXML
    private TextArea usageInstructionsField;
    @FXML
    private TextField manufacturerField;

    // Error labels
    @FXML
    private Label nameErrorLabel;
    @FXML
    private Label activeIngredientErrorLabel;
    @FXML
    private Label dosageErrorLabel;
    @FXML
    private Label unitErrorLabel;
    @FXML
    private Label formErrorLabel;
    @FXML
    private Label manufacturerCodeErrorLabel;
    @FXML
    private Label slCodeErrorLabel;
    @FXML
    private Label priceErrorLabel;
    @FXML
    private Label expiryDateErrorLabel;
    @FXML
    private Label usageInstructionsErrorLabel;
    @FXML
    private Label manufacturerErrorLabel;

    MedicineDao medicineDao = new MedicineDao();

    @FXML
    private void initialize() {
        // Initialize date picker if needed
        expiryDatePicker.setValue(LocalDate.now());
    }

    @FXML
    private void handleSave(ActionEvent event) {
        // Reset error messages
        clearErrorLabels();

        // Get values from fields
        String medicineName = nameField.getText().trim();
        String activeIngredient = activeIngredientField.getText().trim();
        String dosage = dosageField.getText().trim();
        String unit = unitField.getText().trim();
        String form = formField.getText().trim();
        String manufacturerCode = manufacturerCodeField.getText().trim();
        String slCode = slCodeField.getText().trim();
        String price = priceField.getText().trim();
        LocalDate expiryDate = expiryDatePicker.getValue();
        String usageInstructions = usageInstructionsField.getText().trim();
        String manufacturer = manufacturerField.getText().trim();

        boolean valid = true;
        double priceValue = 0.0;

        // Validate required fields
        if (medicineName.isEmpty()) {
            nameErrorLabel.setText("Medicine name is required.");
            nameErrorLabel.setVisible(true);
            valid = false;
        } else if (medicineDao.getMedicineByName(medicineName) != null) {
            nameErrorLabel.setText("Medicine name already exists.");
            nameErrorLabel.setVisible(true);
            valid = false;
        }

        // Validate price
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

        // Validate manufacturer
        if (manufacturer.isEmpty()) {
            manufacturerErrorLabel.setText("Manufacturer is required.");
            manufacturerErrorLabel.setVisible(true);
            valid = false;
        }

        if (!valid) {
            return; // If there are errors, don't save
        }

        // Create medicine object
        Medicine medicine = new Medicine();
        medicine.setName(medicineName);
        medicine.setActiveIngredient(activeIngredient);
        medicine.setDosage(dosage);
        medicine.setUnit(unit);
        medicine.setForm(form);
        medicine.setManufacturerCode(manufacturerCode);
        medicine.setSlCode(slCode);
        medicine.setPrice(priceValue);
        if (expiryDate != null) {
            medicine.setExpiryDate(Date.from(expiryDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        medicine.setUsageInstructions(usageInstructions);
        medicine.setManufacturer(manufacturer);

        // Save to database
        medicineDao.saveMedicine(medicine);
        showAlert("Success", "Medicine added successfully!", Alert.AlertType.INFORMATION);

        // Clear fields
        handleClear();
        returnToMedicineManagement(event);
    }

    @FXML
    private void handleClear() {
        nameField.clear();
        activeIngredientField.clear();
        dosageField.clear();
        unitField.clear();
        formField.clear();
        manufacturerCodeField.clear();
        slCodeField.clear();
        priceField.clear();
        expiryDatePicker.setValue(LocalDate.now());
        usageInstructionsField.clear();
        manufacturerField.clear();
        clearErrorLabels();
    }

    private void clearErrorLabels() {
        nameErrorLabel.setText("");
        nameErrorLabel.setVisible(false);
        activeIngredientErrorLabel.setText("");
        activeIngredientErrorLabel.setVisible(false);
        dosageErrorLabel.setText("");
        dosageErrorLabel.setVisible(false);
        unitErrorLabel.setText("");
        unitErrorLabel.setVisible(false);
        formErrorLabel.setText("");
        formErrorLabel.setVisible(false);
        manufacturerCodeErrorLabel.setText("");
        manufacturerCodeErrorLabel.setVisible(false);
        slCodeErrorLabel.setText("");
        slCodeErrorLabel.setVisible(false);
        priceErrorLabel.setText("");
        priceErrorLabel.setVisible(false);
        expiryDateErrorLabel.setText("");
        expiryDateErrorLabel.setVisible(false);
        usageInstructionsErrorLabel.setText("");
        usageInstructionsErrorLabel.setVisible(false);
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