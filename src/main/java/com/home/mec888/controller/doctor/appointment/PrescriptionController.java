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

import java.util.ArrayList;
import java.util.List;

public class PrescriptionController {
    @FXML
    private TextField findMedicine, medicineQty, medicineUsage, medicineNote;
    @FXML
    public TableView<Medicine> showMedicineTable;
    @FXML
    public TableColumn<String, Medicine> showName, showIngredient, showForm, showUnit, showUsage, showNote;
    @FXML
    public TableColumn<Double, Medicine> showDosage, showQuantity, showUnitPrice, showTotal;
    @FXML
    public Button applyButton;
    @FXML
    public StackPane modalPrescription;

    public Label findMedicineError, medicineQtyError, medicineUsageError, medicineNoteError;
    private MedicineDao medicineDao;
    private List<Medicine> originalMedicineList;
    public static Medicine selectedMedicine;
    private PrescriptionModalController prescriptionModal;
    private StackPane currentModal;
    private String lastKeyword = "";
    private boolean internalChange = false;

    @FXML
    public void initialize() {
        medicineDao = new MedicineDao();
        originalMedicineList = medicineDao.getAllMedicines();

        findMedicine.textProperty().addListener((obs, o, n) -> {
            if (internalChange) {
                internalChange = false;
                return;
            }
            openModal(findMedicine.getText());
        });
    }

    public void loadShowMedicineData() {
        showName.setCellValueFactory(new PropertyValueFactory<>("name"));
        showIngredient.setCellValueFactory(new PropertyValueFactory<>("activeIngredient"));
        showDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        showForm.setCellValueFactory(new PropertyValueFactory<>("form"));
        showUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        showUsage.setCellValueFactory(new PropertyValueFactory<>("usageInstructions"));
        showNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        showUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        showQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        showTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    @FXML
    public void handleApply() {
        if (selectedMedicine == null) {
            findMedicineError.setText("No medicine has been selected!");
            return;
        }

        if (medicineQty.getText().isBlank()) {
            medicineQtyError.setText("Please enter quantity!");
            return;
        }

        if (medicineUsage.getText().isBlank()) {
            medicineUsageError.setText("Please enter usage instructions!");
            return;
        }

        if (medicineNote.getText().isBlank()) {
            medicineNoteError.setText("Please enter note!");
            return;
        }
        if (!medicineQty.getText().matches("\\d+")) {
            medicineQtyError.setText("Quantity must be a number!");
            return;
        }
        if (Integer.parseInt(medicineQty.getText()) <= 0) {
            medicineQtyError.setText("Quantity must be greater than 0!");
            return;
        }
        findMedicineError.setText("");
        medicineQtyError.setText("");
        medicineUsageError.setText("");
        medicineNoteError.setText("");

        Medicine medicineToShow = getMedicine();

        for (Medicine medicine : showMedicineTable.getItems()) {
            if (medicine.getName().equals(selectedMedicine.getName())) {
                int newQuantity = medicine.getQuantity() + Integer.parseInt(medicineQty.getText());
                medicine.setQuantity(newQuantity);
                medicine.setTotal(medicine.getPrice() * newQuantity);
                showMedicineTable.refresh();
                selectedMedicine = null;
                findMedicine.clear();
                return;
            }
        }

        // Nếu không có thuốc nào trùng tên thì thêm mới
        loadShowMedicineData();
        // Tính tổng
        double total = medicineToShow.getPrice() * medicineToShow.getQuantity();
        medicineToShow.setTotal(total);

        showMedicineTable.getItems().add(medicineToShow);

        // Xóa chọn để tránh apply lại
        selectedMedicine = null;
        findMedicine.clear();
        medicineQty.clear();
        medicineUsage.clear();
        medicineNote.clear();
    }

    private Medicine getMedicine() {
        Medicine medicineToShow = new Medicine();
        medicineToShow.setName(selectedMedicine.getName());
        medicineToShow.setActiveIngredient(selectedMedicine.getActiveIngredient());
        medicineToShow.setDosage(selectedMedicine.getDosage());
        medicineToShow.setForm(selectedMedicine.getForm());
        medicineToShow.setUnit(selectedMedicine.getUnit());
        medicineToShow.setPrice(selectedMedicine.getPrice());
        medicineToShow.setUsageInstructions(medicineUsage.getText());
        medicineToShow.setQuantity(Integer.parseInt(medicineQty.getText()));
        medicineToShow.setNote(medicineNote.getText());
        return medicineToShow;
    }

    public void openModal(String keyword) {
        if (keyword.isBlank()) {
            closeCurrentModal();
            lastKeyword = "";
            return;
        }
        if (keyword.equals(lastKeyword) && currentModal != null) {
            // chính là từ khóa mà ta vừa đóng modal
            return;
        }
        lastKeyword = keyword;

        // Tim kiem thuoc dua tren ten thuoc va hoat chat
        List<Medicine> filteredMedicines = new ArrayList<>();
        for (Medicine medicine : originalMedicineList) {
            if (medicine.getName().toLowerCase().contains(keyword.toLowerCase())
                    || (medicine.getActiveIngredient() != null
                    && medicine.getActiveIngredient().toLowerCase().contains(keyword.toLowerCase()))) {
                filteredMedicines.add(medicine);
            }
        }

        AnchorPane root = (AnchorPane) findMedicine.getScene().getRoot();
        if (currentModal != null && root.getChildren().contains(currentModal)) {
            prescriptionModal.setMedicines(filteredMedicines);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/home/mec888/doctor/appointment/modal/prescription-modal.fxml"));
            modalPrescription = loader.load();

            PrescriptionModalController controller = loader.getController();
            controller.setParentController(this);
            controller.setMedicines(filteredMedicines);
            controller.setFindMedicine(findMedicine);

            root.getChildren().add(modalPrescription);
            currentModal = modalPrescription;
            prescriptionModal = controller;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleSetFromModal(String name) {
        internalChange = true;
        findMedicine.setText(name);
        closeCurrentModal();
    }

    public void closeCurrentModal() {
        if (currentModal != null) {
            AnchorPane root = (AnchorPane) findMedicine.getScene().getRoot();
            root.getChildren().remove(currentModal);
            currentModal = null;
            prescriptionModal = null;
        }
    }
}
