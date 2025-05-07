package com.home.mec888.controller.doctor.appointment;

import com.home.mec888.controller.doctor.appointment.modal.PrescriptionModalController;
import com.home.mec888.dao.MedicineDao;
import com.home.mec888.entity.Medicine;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionController {
    @FXML
    public static TextField findMedicine;
    @FXML
    public TableView<Medicine> showMedicineTable;
    @FXML
    public TableColumn<String, Medicine> showName, showIngredient, showForm, showUnit, showUsage, showNote;
    @FXML
    public TableColumn<Double, Medicine> showDosage, showQuantity, showUnitPrice, showTotal;
    @FXML
    public Button applyButton;
    public Label findMedicineError;
    private MedicineDao medicineDao;
    private List<Medicine> originalMedicineList;
    public static Medicine selectedMedicine;

    @FXML
    public void initialize() {
        medicineDao = new MedicineDao();
        originalMedicineList = medicineDao.getAllMedicines();
        findMedicine.textProperty().addListener((observable, oldValue, newValue) -> {
            findMedicine(newValue);
        });
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
            return;
        }


        // Tim kiem thuoc dua tren ten thuoc va hoat chat
        List<Medicine> filteredMedicines = new ArrayList<>();
        for (Medicine medicine : originalMedicineList) {
            if (medicine.getName().toLowerCase().contains(keyword.toLowerCase())
                    || (medicine.getActiveIngredient() != null
                    && medicine.getActiveIngredient().toLowerCase().contains(keyword.toLowerCase()))) {
                filteredMedicines.add(medicine);
            }
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/home/mec888/doctor/appointment/modal/prescription-modal.fxml"));
            StackPane modalPane = loader.load();

            PrescriptionModalController controller = loader.getController();
            controller.setMedicines(filteredMedicines);

            AnchorPane root = (AnchorPane) findMedicine.getScene().getRoot();
            root.getChildren().add(modalPane);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
