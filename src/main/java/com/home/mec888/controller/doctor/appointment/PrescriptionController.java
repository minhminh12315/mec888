package com.home.mec888.controller.doctor.appointment;

import com.home.mec888.dao.MedicalRecordDao;
import com.home.mec888.dao.PrescriptionDao;
import com.home.mec888.dao.PrescriptionDetailsDao;
import com.home.mec888.entity.Prescription;
import com.home.mec888.entity.PrescriptionDetails;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionController {
    @FXML
    public TableView<PrescriptionDetails> prescriptionTable;
    @FXML
    public TableColumn<PrescriptionDetails, String> colMedicineName, colMedicineIngredient, colMedicineForm, colMedicineDosage,
            colMedicineUnit, colPrescInstruction, colPrescFrequency, colPrescDuration;
    @FXML
    public TableColumn<PrescriptionDetails, Double> colPrescDosage, colMedicineUnitPrice, colPrescAmount;
    @FXML
    public Button findMedicineButton;
    @FXML
    public VBox prescriptionVBoxContainer;

    public static final ObservableList<PrescriptionDetails> STORE = FXCollections.observableArrayList();
    public Button clearButton, saveButton;
    private PrescriptionDetailsDao prescDetailDao;
    private PrescriptionDao prescriptionDao;
    private MedicalRecordDao medicalRecordDao;

    @FXML
    public void initialize() {
        prescDetailDao = new PrescriptionDetailsDao();
        prescriptionDao = new PrescriptionDao();
        medicalRecordDao = new MedicalRecordDao();
        loadShowMedicineData();
        prescriptionTable.setItems(STORE);

        Platform.runLater(() -> {
            if (SeeADoctorContainerController.currentMedicalRecord == null) {
                prescriptionVBoxContainer.setDisable(true);
            } else {
                prescriptionVBoxContainer.setDisable(false);
            }
        });


    }

    public void loadShowMedicineData() {
        colMedicineName.setCellValueFactory(cd -> {
            PrescriptionDetails prescDetails = cd.getValue();
            String name = (prescDetails.getMedicine() != null) ? prescDetails.getMedicine().getName() : "";
            return new SimpleStringProperty(name);
        });

        colMedicineIngredient.setCellValueFactory(cd -> {
            PrescriptionDetails prescDetails = cd.getValue();
            String ingredient = (prescDetails.getMedicine() != null) ? prescDetails.getMedicine().getActiveIngredient() : "";
            return new SimpleStringProperty(ingredient);
        });

        colMedicineForm.setCellValueFactory(cd -> {
            PrescriptionDetails prescDetails = cd.getValue();
            String form = (prescDetails.getMedicine() != null) ? prescDetails.getMedicine().getForm() : "";
            return new SimpleStringProperty(form);
        });

        colMedicineUnit.setCellValueFactory(cd -> {
            PrescriptionDetails prescDetails = cd.getValue();
            String unit = (prescDetails.getMedicine() != null) ? prescDetails.getMedicine().getUnit() : "";
            return new SimpleStringProperty(unit);
        });
        colMedicineDosage.setCellValueFactory(cd -> {
            PrescriptionDetails prescDetails = cd.getValue();
            String dosage = (prescDetails.getMedicine() != null) ? prescDetails.getMedicine().getDosage() : "";
            return new SimpleStringProperty(dosage);
        });
        colMedicineUnitPrice.setCellValueFactory(cd -> {
            PrescriptionDetails prescDetails = cd.getValue();
            double price = (prescDetails.getMedicine() != null) ? prescDetails.getMedicine().getPrice() : 0.0;
            return new SimpleDoubleProperty(price).asObject();
        });
        colPrescDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        colPrescFrequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        colPrescDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colPrescInstruction.setCellValueFactory(new PropertyValueFactory<>("instructions"));
        colPrescAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        prescriptionTable.getItems().clear();
    }

    public void openModal() {
        AnchorPane root = (AnchorPane) findMedicineButton.getScene().getRoot();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/home/mec888/doctor/appointment/modal/prescription-modal.fxml"));
            StackPane modalPrescription = loader.load();

            root.getChildren().add(modalPrescription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleSave() {
        if (STORE.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No prescription details found");
            alert.setContentText("Please add prescription details before saving.");
            alert.showAndWait();
            return;
        }

//        prescDetailDao.saveAllPrescriptionDetails(STORE);
//        prescriptionTable.getItems().clear();
//        STORE.clear();
        Prescription existingPresciprtion = prescriptionDao.getPrescriptionByRecord(SeeADoctorContainerController.currentMedicalRecord);

        Prescription prescription;
        if (existingPresciprtion != null) {
            prescription = existingPresciprtion;
        } else {
            prescription = new Prescription();
            prescription.setRecord(SeeADoctorContainerController.currentMedicalRecord);
            prescriptionDao.savePrescription(prescription);
        }
        for (PrescriptionDetails presc : STORE) {
            presc.setPrescription(prescription);
            System.out.println(presc);
        }
        prescriptionTable.getItems().clear();
        STORE.clear();
    }

    public void handleClear() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Clear Prescription");
        alert.setContentText("Are you sure you want to clear the prescription?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                prescriptionTable.getItems().clear();
                STORE.clear();
            }
        });
    }
}
