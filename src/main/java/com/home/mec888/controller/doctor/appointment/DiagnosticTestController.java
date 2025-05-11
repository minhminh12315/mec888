package com.home.mec888.controller.doctor.appointment;

import com.home.mec888.controller.IndexController;
import com.home.mec888.controller.doctor.appointment.modal.DiagnosticTestModalController;
import com.home.mec888.dao.ServiceDao;
import com.home.mec888.dao.TreatmentStepDao;
import com.home.mec888.dao.TreatmentStepServiceDao;
import com.home.mec888.entity.Service;
import com.home.mec888.entity.TreatmentStepServices;
import com.home.mec888.entity.TreatmentSteps;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class DiagnosticTestController {

    @FXML
    public Button btn_addServices;
    @FXML
    public GridPane diagnosticTestGrid;
    ServiceDao serviceDao;
    TreatmentStepServiceDao treatmentStepServiceDao;
    TreatmentStepDao treatmentStepDao;

    @FXML
    public void initialize() {
        serviceDao = new ServiceDao();
        treatmentStepServiceDao = new TreatmentStepServiceDao();
        treatmentStepDao = new TreatmentStepDao();
        // Initialize any necessary components or data here
        // For example, you might want to load the list of services when the controller is initialized
        getListServiceOrdered();
    }

    public void getListServiceOrdered() {
        List<TreatmentSteps> treatmentSteps = treatmentStepDao.getAllTreatmentStepsByAppointmentId(SeeADoctorContainerController.currentAppointment.getId());
        if (treatmentSteps != null) {
            // Clear the existing content in the diagnosticListContainer
//            diagnosticTestGrid.getChildren().clear();
            int row = 1;
            for (TreatmentSteps treatmentSteps1 : treatmentSteps) {
                TreatmentStepServices treatmentStepServices = treatmentStepServiceDao.getTreatmentStepServiceByTreatmentStepID(treatmentSteps1.getId());

                GridPane grid = new GridPane();

                if (treatmentStepServices != null) {
                    HBox hBox = new HBox();

                    Label serviceNameLabel = new Label(
                            treatmentStepServices.getService() != null ? treatmentStepServices.getService().getName() : ""
                    );
                    serviceNameLabel.setPrefWidth(400);
                    serviceNameLabel.getStyleClass().add("diagnosticCell");

                    Label room = new Label(
                            treatmentStepServices.getService() != null && treatmentStepServices.getService().getRoom() != null
                                    ? treatmentStepServices.getService().getRoom().getRoomNumber()
                                    : ""
                    );
                    room.getStyleClass().add("diagnosticCell");
                    room.setPrefWidth(150);

                    Label note = new Label(
                            treatmentStepServices.getTreatmentStep() != null && treatmentStepServices.getTreatmentStep().getStepDescription() != null
                                    ? treatmentStepServices.getTreatmentStep().getStepDescription()
                                    : ""
                    );
                    note.getStyleClass().add("diagnosticCell");
                    note.setPrefWidth(250);

                    Label startTime = new Label(
                            treatmentStepServices.getTreatmentStep() != null && treatmentStepServices.getTreatmentStep().getStartTime() != null
                                    ? treatmentStepServices.getTreatmentStep().getStartTime().toString()
                                    : ""
                    );
                    startTime.getStyleClass().add("diagnosticCell");
                    startTime.setPrefWidth(150);

                    Label endTime = new Label(
                            treatmentStepServices.getTreatmentStep() != null && treatmentStepServices.getTreatmentStep().getEndTime() != null
                                    ? treatmentStepServices.getTreatmentStep().getEndTime().toString()
                                    : ""
                    );
                    endTime.getStyleClass().add("diagnosticCell");
                    endTime.setPrefWidth(150);

                    Label diagnostic = new Label(
                            treatmentStepServices.getTreatmentStep() != null && treatmentStepServices.getTreatmentStep().getOutcome() != null
                                    ? treatmentStepServices.getTreatmentStep().getOutcome()
                                    : ""
                    );
                    diagnostic.getStyleClass().add("diagnosticCell");
                    diagnostic.setPrefWidth(300);

                    diagnosticTestGrid.add(serviceNameLabel, 0, row);
                    diagnosticTestGrid.add(room, 1, row);
                    diagnosticTestGrid.add(note, 2, row);
                    diagnosticTestGrid.add(startTime, 3, row);
                    diagnosticTestGrid.add(endTime, 4, row);
                    diagnosticTestGrid.add(diagnostic, 5, row);

                    row++;
                    System.out.println(treatmentStepServices);
                }
            }
        } else {
            System.out.println("No services found.");
        }
    }


    public void openModalDiagnosticTest(javafx.event.ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/home/mec888/doctor/appointment/modal/diagnostic-test-modal.fxml"));
            StackPane modal = loader.load();

            DiagnosticTestModalController controller = loader.getController();
            controller.diagnosticTestController = this;
            // Get the current scene and add the modal to it
            AnchorPane root = (AnchorPane) ((Node) event.getSource()).getScene().getRoot();
            root.getChildren().add(modal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        // Reload the list of services or any other necessary data
        getListServiceOrdered();
    }
}