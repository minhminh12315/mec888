package com.home.mec888.controller.doctor.appointment.modal;

import com.home.mec888.controller.doctor.appointment.PrescriptionController;
import com.home.mec888.controller.doctor.appointment.SeeADoctorContainerController;
import com.home.mec888.dao.MedicalRecordDao;
import com.home.mec888.dao.PrescriptionDao;
import com.home.mec888.dao.PrescriptionDetailsDao;
import com.home.mec888.entity.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.List;

public class PrescriptionDetailsModalController {
    public TextField medicineName, medicineIngredient, prescDosage, prescFrequency, prescDuration;
    @FXML
    public TextArea prescInstruction;
    @FXML
    public Label prescDosageError, prescFrequencyError, prescInstructionError, prescDurationError;
    @FXML
    public Button applyButton, cancelButton;
    @FXML
    public StackPane modalPrescriptionDetail;
    @FXML
    public Rectangle overlay;
    @FXML
    public Button saveButton, clearButton;

    private Runnable onCloseCallback;
    private Medicine selectedMedicine;

    private PrescriptionDetailsDao prescDetailDao;
    private PrescriptionDao prescriptionDao;

    public void setMedicine(Medicine medicine) {
        this.selectedMedicine = medicine;
    }

    @FXML
    public void initialize() {
        prescDetailDao = new PrescriptionDetailsDao();
        prescriptionDao = new PrescriptionDao();
        Platform.runLater(() -> {
            Stage stage = (Stage) modalPrescriptionDetail.getScene().getWindow();

            // Bind StackPane để mở rộng theo cửa sổ
            modalPrescriptionDetail.prefWidthProperty().bind(stage.widthProperty());
            modalPrescriptionDetail.prefHeightProperty().bind(stage.heightProperty());

            // Overlay phủ kín toàn màn hình
            overlay.widthProperty().bind(modalPrescriptionDetail.widthProperty());
            overlay.heightProperty().bind(modalPrescriptionDetail.heightProperty());

            modalPrescriptionDetail.lookup("#overlay").setOnMouseClicked(event -> {
                AnchorPane root = (AnchorPane) modalPrescriptionDetail.getScene().getRoot();
                root.getChildren().remove(modalPrescriptionDetail);
            });

            System.out.println(selectedMedicine);
            medicineName.setText(selectedMedicine.getName());
            medicineIngredient.setText(selectedMedicine.getActiveIngredient());
            medicineName.setDisable(true);
            medicineIngredient.setDisable(true);
        });
    }

    // Setter để thiết lập callback
    public void setOnCloseCallback(Runnable onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }

    @FXML
    public void handleSave() {
        if (prescDosage.getText().isBlank()) {
            prescDosageError.setText("Please enter quantity!");
            return;
        } else {
            prescDosageError.setText("");
        }

        if (prescDosage.getText().length() > 255) {
            prescDosageError.setText("Quantity must be less than 255!");
            return;
        } else {
            prescDosageError.setText("");
        }

        if (prescFrequency.getText().isBlank()) {
            prescFrequencyError.setText("Please enter frequency!");
            return;
        } else {
            prescFrequencyError.setText("");
        }
        if (prescFrequency.getText().length() > 255) {
            prescFrequencyError.setText("Frequency must be less than 255!");
            return;
        } else {
            prescFrequencyError.setText("");
        }

        if (prescDuration.getText().isBlank()) {
            prescDurationError.setText("Please enter duration!");
            return;
        } else {
            prescDurationError.setText("");
        }

        if (prescDuration.getText().length() > 255) {
            prescDurationError.setText("Duration must be less than 255!");
            return;
        } else {
            prescDurationError.setText("");
        }

        if (prescInstruction.getText().isBlank()) {
            prescInstructionError.setText("Please enter note!");
            return;
        } else {
            prescInstructionError.setText("");
        }

        if (!prescDosage.getText().matches("\\d+")) {
            prescDosageError.setText("Quantity must be a number!");
            return;
        } else {
            prescDosageError.setText("");
        }
        if (Integer.parseInt(prescDosage.getText()) <= 0) {
            prescDosageError.setText("Quantity must be greater than 0!");
            return;
        } else {
            prescDosageError.setText("");
        }


        for (int i = 0; i < PrescriptionController.STORE.size(); i++) {
            PrescriptionDetails presc = PrescriptionController.STORE.get(i);
            if (presc.getMedicine().getName().equals(selectedMedicine.getName()) && !presc.isSaved()) {
                int newQty = Integer.parseInt(presc.getDosage()) + Integer.parseInt(prescDosage.getText());
                presc.setDosage(String.valueOf(newQty));
                presc.setAmount(presc.getMedicine().getPrice() * newQty);
                // Thay thế phần tử ở vị trí i để trigger change
                PrescriptionController.STORE.set(i, presc);
                handleClear();
                closeModal();
                return;
            }
        }

        // Nếu không có thuốc nào trùng tên thì thêm mới
        PrescriptionDetails medicineToShow = getPrescriptionDetails();

        PrescriptionController.STORE.add(medicineToShow);

        // Xóa chọn để tránh apply lại
        selectedMedicine = null;
        handleClear();
        closeModal();
    }

    private PrescriptionDetails getPrescriptionDetails() {
        PrescriptionDetails medicineToShow = new PrescriptionDetails();
        medicineToShow.setMedicine(selectedMedicine);
        medicineToShow.setDosage(prescDosage.getText());
        medicineToShow.setFrequency(prescFrequency.getText());
        medicineToShow.setDuration(prescDuration.getText());
        medicineToShow.setInstructions(prescInstruction.getText());
        medicineToShow.setAmount(selectedMedicine.getPrice() * Integer.parseInt(prescDosage.getText()));
        return medicineToShow;
    }

    public void handleClear() {
        prescDosage.clear();
        prescFrequency.clear();
        prescInstruction.clear();
        prescDuration.clear();
        prescDosageError.setText("");
        prescFrequencyError.setText("");
        prescInstructionError.setText("");
        prescDurationError.setText("");
    }

    public void closeModal() {
        AnchorPane root = (AnchorPane) modalPrescriptionDetail.getScene().getRoot();
        root.getChildren().remove(modalPrescriptionDetail);
    }

    public void closeModalDetails() {
        closeModal();
        // Gọi callback nếu đã thiết lập
        if (onCloseCallback != null) {
            onCloseCallback.run();
        }
    }
}
