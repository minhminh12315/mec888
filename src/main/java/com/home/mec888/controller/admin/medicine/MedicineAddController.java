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

    MedicineDao medicineDao = new MedicineDao();

    @FXML
    private void handleSave(ActionEvent event) {
        String medicineName = nameField.getText();
        String description = descriptionField.getText();
        String price = priceField.getText();
        String manufacturer = manufacturerField.getText();

        if (medicineName.isEmpty() || description.isEmpty() || price.isEmpty() || manufacturer.isEmpty()) {
            showAlert("Error", "Please fill in all required fields!", Alert.AlertType.ERROR);
            return;
        }

        try {
            double priceValue = Double.parseDouble(price);
            if (priceValue <= 0) {
                showAlert("Error", "Price must be a positive number!", Alert.AlertType.ERROR);
                return;
            }



            // Create a new Medicine object
            Medicine medicine = new Medicine();
            medicine.setName(medicineName);
            medicine.setDescription(description);
            medicine.setPrice(priceValue);
            medicine.setManufacturer(manufacturer);

            System.out.println(medicine);

//             Save the medicine to the database
            medicineDao.saveMedicine(medicine);
            showAlert("Success", "Medicine added successfully!", Alert.AlertType.INFORMATION);

            // Clear the fields after saving
            nameField.clear();
            descriptionField.clear();
            priceField.clear();
            manufacturerField.clear();

            returnToMedicineManagement(event);

        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid price format!", Alert.AlertType.ERROR);
        }

    }

    @FXML
    private void handleClear() {
        nameField.clear();
        descriptionField.clear();
        priceField.clear();
        manufacturerField.clear();
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
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        SceneSwitcher.switchTo(currentStage, "admin/medicine/medicine-management.fxml");
    }

}
