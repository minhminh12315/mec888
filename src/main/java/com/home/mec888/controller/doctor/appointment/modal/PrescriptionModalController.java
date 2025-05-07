package com.home.mec888.controller.doctor.appointment.modal;

import com.home.mec888.controller.doctor.appointment.PrescriptionController;
import com.home.mec888.dao.MedicineDao;
import com.home.mec888.entity.Medicine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.util.List;

public class PrescriptionModalController {
    @FXML
    public TableView<Medicine> chooseMedicineTable;
    @FXML
    public TableColumn<String, Medicine> chooseName, chooseIngredient, chooseForm, chooseUnit, chooseManuCode, chooseSlCode;
    @FXML
    public TableColumn<Double, Medicine> chooseDosage, choosePrice;
    @FXML
    public TableColumn<Date, Medicine> chooseExpiredDate;

    private MedicineDao medicineDao;

    @FXML
    public void initialize() {
        medicineDao = new MedicineDao();
        loadMedicineData();

        chooseMedicineTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Nhấp đúp
                handleChooseMedicine();
            }
        });
    }

    private void loadMedicineData() {
        chooseName.setCellValueFactory(new PropertyValueFactory<>("name"));
        chooseIngredient.setCellValueFactory(new PropertyValueFactory<>("activeIngredient"));
        chooseDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        chooseForm.setCellValueFactory(new PropertyValueFactory<>("form"));
        chooseUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        chooseManuCode.setCellValueFactory(new PropertyValueFactory<>("manufacturerCode"));
        choosePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        chooseSlCode.setCellValueFactory(new PropertyValueFactory<>("slCode"));
        chooseExpiredDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
    }

    private void handleChooseMedicine() {
        PrescriptionController.selectedMedicine = chooseMedicineTable.getSelectionModel().getSelectedItem();
        if (PrescriptionController.selectedMedicine != null) {
            PrescriptionController.findMedicine.setText(PrescriptionController.selectedMedicine.getName());
        }
    }

    public void setMedicines(List<Medicine> medicines) {
        loadMedicineData();
        for (Medicine medicine : medicines) {
            chooseMedicineTable.getItems().add(medicine);
        }
    }

    public void closeModal(ActionEvent actionEvent) {
    }
}
