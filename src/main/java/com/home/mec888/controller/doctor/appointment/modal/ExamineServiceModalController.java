package com.home.mec888.controller.doctor.appointment.modal;

import com.home.mec888.controller.IndexController;
import com.home.mec888.controller.doctor.appointment.DiagnosticTestController;
import com.home.mec888.dao.ServiceDao;
import com.home.mec888.dao.TreatmentStepDao;
import com.home.mec888.dao.TreatmentStepServiceDao;
import com.home.mec888.entity.Service;
import com.home.mec888.entity.TreatmentStepServices;
import com.home.mec888.entity.TreatmentSteps;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ExamineServiceModalController {
    @FXML
    public Rectangle overlay;
    @FXML
    public StackPane modalExamineService;
    @FXML
    public TextField doctorExamine;
    @FXML
    public TextField serviceName;
    @FXML
    public TextField room;
    @FXML
    public TextField startTime;
    @FXML
    public TextField endTime;
    @FXML
    public TextArea note;
    @FXML
    public TextArea diagnostic;
    @FXML
    public Button clearTextFieldButton;
    @FXML
    public Button saveTreatmentStepServiceButton;

    ServiceDao serviceDao;
    TreatmentStepDao treatmentStepDao;
    TreatmentStepServiceDao treatmentStepServiceDao;
    Service selectedService;
    public DiagnosticTestController diagnosticTestController;
    TreatmentStepServices selectedTreatmentStepService;
    public boolean isServiceInRoom;

    @FXML
    public void initialize() {
        serviceDao = new ServiceDao();
        treatmentStepDao = new TreatmentStepDao();
        treatmentStepServiceDao = new TreatmentStepServiceDao();
        Platform.runLater(() -> {
            Stage stage = (Stage) modalExamineService.getScene().getWindow();

            // Bind StackPane để mở rộng theo cửa sổ
            modalExamineService.prefWidthProperty().bind(stage.widthProperty());
            modalExamineService.prefHeightProperty().bind(stage.heightProperty());

            // Overlay phủ kín toàn màn hình
            overlay.widthProperty().bind(modalExamineService.widthProperty());
            overlay.heightProperty().bind(modalExamineService.heightProperty());

            modalExamineService.lookup("#overlay").setOnMouseClicked(event -> {
                AnchorPane root = (AnchorPane) modalExamineService.getScene().getRoot();
                root.getChildren().remove(modalExamineService);
            });
        });
    }

    public void closeModal(ActionEvent event) {
        AnchorPane root = (AnchorPane) modalExamineService.getScene().getRoot();
        root.getChildren().remove(modalExamineService);
    }

    public void clearTextField(ActionEvent event) {
        diagnostic.clear();
    }


    public void setUpdateService(TreatmentStepServices selectedService, DiagnosticTestController diagnosticTestController, boolean isServiceInRoom) {
        System.out.println("is service in room: " + isServiceInRoom);
        this.selectedTreatmentStepService = selectedService;
        this.diagnosticTestController = diagnosticTestController;
        this.isServiceInRoom = isServiceInRoom;

        // Set the text fields with the selected service's data
        serviceName.setText(selectedService.getService().getName());
        room.setText(selectedService.getService().getRoom().getRoomNumber());
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = now.format(formatter);
        startTime.setText(formattedTime);
        note.setText(
                selectedService.getTreatmentStep() != null && selectedService.getTreatmentStep().getStepDescription() != null
                        ? selectedService.getTreatmentStep().getStepDescription()
                        : ""
        );
        diagnostic.setDisable(!isServiceInRoom);
        clearTextFieldButton.setDisable(!isServiceInRoom);
        saveTreatmentStepServiceButton.setDisable(!isServiceInRoom);


        // Set the doctorExamine field to the current user's name
        doctorExamine.setText("Dr." + IndexController.doctor.getUser().getFirstName()); // Replace with actual user name
    }

    public void saveTreatmentStepService(ActionEvent event) {
        // Get the values from the text fields
        String startTimeText = startTime.getText().trim() + ".0"; // Add seconds to the time
        String endTimeText = endTime.getText();
        String noteText = note.getText();
        String diagnosticText = diagnostic.getText();

        // Parse the start and end time

        // Create a new TreatmentStepServices object
        TreatmentSteps treatmentSteps = new TreatmentSteps();
        treatmentSteps.setId(selectedTreatmentStepService.getTreatmentStep().getId());
        treatmentSteps.setAppointment(selectedTreatmentStepService.getTreatmentStep().getAppointment());
        treatmentSteps.setStartTime(Timestamp.valueOf(startTimeText));
        treatmentSteps.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
        treatmentSteps.setStepDescription(noteText);
        treatmentSteps.setOutcome(diagnosticText);
        treatmentSteps.setDoctor(IndexController.doctor);
        treatmentSteps.setStatus("COMPLETED");
        treatmentSteps.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        System.out.println(treatmentSteps);


        // Save the TreatmentStepServices object to the database
        treatmentStepDao.updateTreatmentStep(treatmentSteps);

        // Close the modal
        closeModal(event);
        diagnosticTestController.reload();
    }
}
