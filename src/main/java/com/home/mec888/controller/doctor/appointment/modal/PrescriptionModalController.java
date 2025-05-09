package com.home.mec888.controller.doctor.appointment.modal;

import com.home.mec888.controller.doctor.appointment.PrescriptionController;
import com.home.mec888.dao.MedicineDao;
import com.home.mec888.entity.Medicine;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionModalController {
    @FXML
    private TextField findMedicine;
    @FXML
    public TableView<Medicine> chooseMedicineTable;
    @FXML
    public TableColumn<String, Medicine> chooseName, chooseIngredient, chooseForm, chooseUnit, chooseManuCode, chooseSlCode;
    @FXML
    public TableColumn<Double, Medicine> chooseDosage, choosePrice;
    @FXML
    public TableColumn<Date, Medicine> chooseExpiredDate;
    @FXML
    public Rectangle overlay;
    @FXML
    public StackPane modalPrescription;
    private List<Medicine> originalMedicineList;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) modalPrescription.getScene().getWindow();

            // Bind StackPane để mở rộng theo cửa sổ
            modalPrescription.prefWidthProperty().bind(stage.widthProperty());
            modalPrescription.prefHeightProperty().bind(stage.heightProperty());

            // Overlay phủ kín toàn màn hình
            overlay.widthProperty().bind(modalPrescription.widthProperty());
            overlay.heightProperty().bind(modalPrescription.heightProperty());

            modalPrescription.lookup("#overlay").setOnMouseClicked(event -> {
                AnchorPane root = (AnchorPane) modalPrescription.getScene().getRoot();
                root.getChildren().remove(modalPrescription);
            });
        });

        MedicineDao medicineDao = new MedicineDao();
        originalMedicineList = medicineDao.getAllMedicines();

        setMedicines(originalMedicineList);

        findMedicine.textProperty().addListener((observable, oldValue, newValue) -> {
            handleFindMedicine(newValue);
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

        chooseMedicineTable.getItems().clear();
    }

    public void handleFindMedicine(String keyword) {
        keyword = findMedicine.getText();
        if (keyword.isBlank()) {
            loadMedicineData();
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

        setMedicines(filteredMedicines);
    }

    public void setMedicines(List<Medicine> medicines) {
        loadMedicineData();
        for (Medicine medicine : medicines) {
            chooseMedicineTable.getItems().add(medicine);
        }
    }

    private void handleChooseMedicine() {
        // Lấy medicine đã chọn
        Medicine chosen = chooseMedicineTable.getSelectionModel().getSelectedItem();
        if (chosen == null) return;
        AnchorPane root = (AnchorPane) findMedicine.getScene().getRoot();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/home/mec888/doctor/appointment/modal/prescription-modal-details.fxml"));
            StackPane modalPrescriptionDetails = loader.load();
            PrescriptionDetailsModalController modalController = loader.getController();

            // Truyền StackPane vào để modal controller có thể gỡ đúng
            modalController.modalPrescriptionDetail = modalPrescriptionDetails;
            modalController.setMedicine(chosen);

            // Thiết lập callback: khi đóng modal thì mở modal cũ
            modalController.setOnCloseCallback(() -> {
                modalPrescription.setVisible(true);
                modalPrescription.setManaged(true);
            });
            root.getChildren().add(modalPrescriptionDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }

        modalPrescription.setVisible(false);
        modalPrescription.setManaged(false);
    }

    public void closeModal() {
        AnchorPane root = (AnchorPane) modalPrescription.getScene().getRoot();
        root.getChildren().remove(modalPrescription);
    }
}
