package com.home.mec888.controller.admin.medicine;

import com.home.mec888.dao.MedicineDao;
import com.home.mec888.entity.Medicine;
import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class MedicineUpdateController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField manufacturerField;
    @FXML
    private Button updateButton;
    @FXML
    private Button cancelButton;

    private MedicineDao medicineDao;
    private Medicine medicine;

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
        System.out.println("setMedicine: " + medicine);
        // Cập nhật giao diện ngay sau khi dữ liệu được set
        if (medicine != null) {
            nameField.setText(medicine.getName());
            descriptionField.setText(medicine.getDescription());
            priceField.setText(String.valueOf(medicine.getPrice()));
            manufacturerField.setText(medicine.getManufacturer());
        }
    }

    @FXML
    private void initialize() {
        System.out.println("initialize: ");
        medicineDao = new MedicineDao();
        // Không cập nhật giao diện ở đây, vì medicine có thể chưa được set
    }


    @FXML
    private void handleUpdate(ActionEvent actionEvent) {
        String name = nameField.getText();
        String description = descriptionField.getText();
        double price = Double.parseDouble(priceField.getText());
        String manufacturer = manufacturerField.getText();

        medicine.setName(name);
        medicine.setDescription(description);
        medicine.setPrice(price);
        medicine.setManufacturer(manufacturer);

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
        descriptionField.clear();
        manufacturerField.clear();
        priceField.clear();
    }
}
