package com.home.mec888.controller.admin.medicine;

import com.home.mec888.dao.MedicineDao;
import com.home.mec888.entity.Medicine;
import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class MedicineUpdateController {

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
    private Button updateButton;
    @FXML
    private Button cancelButton;

    private MedicineDao medicineDao;
    private Medicine medicine;

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
        System.out.println("setMedicine: " + medicine);
        // Update the UI after medicine is set
        if (medicine != null) {
            nameField.setText(medicine.getName());
            activeIngredientField.setText(medicine.getActiveIngredient());
            dosageField.setText(medicine.getDosage());
            unitField.setText(medicine.getUnit());
            formField.setText(medicine.getForm());
            manufacturerCodeField.setText(medicine.getManufacturerCode());
            slCodeField.setText(medicine.getSlCode());
            priceField.setText(String.valueOf(medicine.getPrice()));

            // Handle the date conversion
            if (medicine.getExpiryDate() != null) {
                LocalDate expiryLocalDate = medicine.getExpiryDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                expiryDatePicker.setValue(expiryLocalDate);
            }

            usageInstructionsField.setText(medicine.getUsageInstructions());
        }
    }

    @FXML
    private void initialize() {
        System.out.println("initialize: ");
        medicineDao = new MedicineDao();
        // Don't update UI here as medicine might not be set yet
    }

    @FXML
    private void handleUpdate(ActionEvent actionEvent) {
        String name = nameField.getText();
        String activeIngredient = activeIngredientField.getText();
        String dosage = dosageField.getText();
        String unit = unitField.getText();
        String form = formField.getText();
        String manufacturerCode = manufacturerCodeField.getText();
        String slCode = slCodeField.getText();
        double price = Double.parseDouble(priceField.getText());
        LocalDate expiryLocalDate = expiryDatePicker.getValue();
        Date expiryDate = null;
        if (expiryLocalDate != null) {
            expiryDate = Date.from(expiryLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        String usageInstructions = usageInstructionsField.getText();

        medicine.setName(name);
        medicine.setActiveIngredient(activeIngredient);
        medicine.setDosage(dosage);
        medicine.setUnit(unit);
        medicine.setForm(form);
        medicine.setManufacturerCode(manufacturerCode);
        medicine.setSlCode(slCode);
        medicine.setPrice(price);
        medicine.setExpiryDate(expiryDate);
        medicine.setUsageInstructions(usageInstructions);

        medicineDao.updateMedicine(medicine);

        returnToMedicineManagement(actionEvent);
    }

    private void returnToMedicineManagement(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/medicine/medicine-management.fxml", actionEvent);
    }

    public void handleBack(ActionEvent event) {
        returnToMedicineManagement(event);
    }

    public void handleClear(ActionEvent event) {
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
    }
}