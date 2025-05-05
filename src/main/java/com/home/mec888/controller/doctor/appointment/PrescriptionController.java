package com.home.mec888.controller.doctor.appointment;

import com.home.mec888.dao.MedicineDao;
import com.home.mec888.entity.Medicine;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionController {
    @FXML
    public TextField findMedicine;
    @FXML
    public TableView<Medicine> chooseMedicineTable, showMedicineTable;
    @FXML
    public TableColumn<String, Medicine> chooseName, chooseIngredient, chooseForm, chooseUnit, chooseManuCode, chooseSlCode, showName, showIngredient, showForm, showUnit, showUsage, showNote;
    @FXML
    public TableColumn<Double, Medicine> chooseDosage, choosePrice, showDosage, showQuantity, showUnitPrice, showTotal;
    @FXML
    public TableColumn<Date, Medicine> chooseExpiredDate;
    @FXML
    public Button applyButton;
    public Label findMedicineError;
    private MedicineDao medicineDao;
    private List<Medicine> originalMedicineList;
    private Medicine selectedMedicine;

    @FXML
    public void initialize() {
        medicineDao = new MedicineDao();
        originalMedicineList = medicineDao.getAllMedicines();
        findMedicine.textProperty().addListener((observable, oldValue, newValue) -> {
            findMedicine(newValue);
        });

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

    private void loadShowMedicineData() {
        showName.setCellValueFactory(new PropertyValueFactory<>("name"));
        showIngredient.setCellValueFactory(new PropertyValueFactory<>("activeIngredient"));
        showDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        showForm.setCellValueFactory(new PropertyValueFactory<>("form"));
        showUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
//        showUsage.setCellValueFactory(new PropertyValueFactory<>("usageInstructions"));
//        showNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        showUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    public void handleApply() {
        if (selectedMedicine == null) {
            findMedicineError.setText("No medicine has been selected!");
            return;
        }
        findMedicineError.setText("");
        chooseMedicineTable.setVisible(false);

        // Bạn có thể clone nếu muốn tạo object mới để chỉnh sửa riêng
        Medicine medicineToShow = new Medicine();
        medicineToShow.setName(selectedMedicine.getName());
        medicineToShow.setActiveIngredient(selectedMedicine.getActiveIngredient());
        medicineToShow.setDosage(selectedMedicine.getDosage());
        medicineToShow.setForm(selectedMedicine.getForm());
        medicineToShow.setUnit(selectedMedicine.getUnit());
        medicineToShow.setPrice(selectedMedicine.getPrice());

        loadShowMedicineData();
        // Tính tổng
//        double total = medicineToShow.getPrice() * medicineToShow.getQuantity();
//        medicineToShow.setTotal(total);

        showMedicineTable.getItems().add(medicineToShow);

        // Xóa chọn để tránh apply lại
        selectedMedicine = null;
        findMedicine.clear();
    }


    public void findMedicine(String keyword) {
        if (keyword.isEmpty()) {
            findMedicineError.setText("");
            chooseMedicineTable.getItems().clear();
            return;
        }
        chooseMedicineTable.setVisible(true);
        showMedicineTable.setVisible(false);
        // Tim kiem thuoc dua tren ten thuoc va hoat chat
        List<Medicine> filteredMedicines = new ArrayList<>();
        for (Medicine medicine : originalMedicineList) {
            if (medicine.getName().toLowerCase().contains(keyword.toLowerCase())
                    || (medicine.getActiveIngredient() != null
                    && medicine.getActiveIngredient().toLowerCase().contains(keyword.toLowerCase()))) {
                filteredMedicines.add(medicine);
            }
        }

        updateChooseMedicineTable(filteredMedicines);
    }

    private void updateChooseMedicineTable(List<Medicine> medicines) {
        chooseMedicineTable.getItems().clear();
        chooseMedicineTable.setVisible(true);
        chooseMedicineTable.setManaged(true);
        loadMedicineData();
        for (Medicine medicine : medicines) {
            chooseMedicineTable.getItems().add(medicine);
        }
    }

    private void handleChooseMedicine() {
        selectedMedicine = chooseMedicineTable.getSelectionModel().getSelectedItem();
        if (selectedMedicine != null) {
            findMedicine.setText(selectedMedicine.getName());
            chooseMedicineTable.setVisible(false);
            chooseMedicineTable.setManaged(false);
            showMedicineTable.setVisible(true);
        }
    }

}
