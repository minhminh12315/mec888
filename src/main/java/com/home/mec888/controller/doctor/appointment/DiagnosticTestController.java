package com.home.mec888.controller.doctor.appointment;

import com.home.mec888.controller.IndexController;
import com.home.mec888.controller.doctor.appointment.modal.DiagnosticTestModalController;
import com.home.mec888.controller.doctor.appointment.modal.ExamineServiceModalController;
import com.home.mec888.dao.ServiceDao;
import com.home.mec888.dao.TreatmentStepDao;
import com.home.mec888.dao.TreatmentStepServiceDao;
import com.home.mec888.entity.Service;
import com.home.mec888.entity.TreatmentStepServices;
import com.home.mec888.entity.TreatmentSteps;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class DiagnosticTestController {

    @FXML
    public Button btn_addServices;
    @FXML
    public GridPane diagnosticTestGrid;
    @FXML
    public TableView<TreatmentStepServices> diagnosticTestTabelView;
    @FXML
    public TableColumn<TreatmentStepServices, String> serviceNameColumn;
    @FXML
    public TableColumn<TreatmentStepServices, String> roomColumn;
    @FXML
    public TableColumn<TreatmentStepServices, String> noteColumn;
    @FXML
    public TableColumn<TreatmentStepServices, String> startTimeColumn;
    @FXML
    public TableColumn<TreatmentStepServices, String> endTimeColumn;
    @FXML
    public TableColumn<TreatmentStepServices, String> diagnosticColumn;
    public TableColumn<TreatmentStepServices, Void> actionColumn;
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
        setUpDiagnosticCell();
        addButtonToTable();
        getListServiceOrdered();
    }

    public void setUpDiagnosticCell() {
        diagnosticTestTabelView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        serviceNameColumn.setMaxWidth(1f * Integer.MAX_VALUE * 1.5);
//        roomColumn.setMaxWidth(1f * Integer.MAX_VALUE * 0.5);
//        noteColumn.setMaxWidth(1f * Integer.MAX_VALUE * 1.0);
//        startTimeColumn.setMaxWidth(1f * Integer.MAX_VALUE * 0.5);
//        endTimeColumn.setMaxWidth(1f * Integer.MAX_VALUE * 0.5);
//        diagnosticColumn.setMaxWidth(1f * Integer.MAX_VALUE * 1.5);
//        actionColumn.setMaxWidth(1f * Integer.MAX_VALUE * 0.5);

        serviceNameColumn.setCellValueFactory(cellData -> {
            TreatmentStepServices tss = cellData.getValue();
            String name = (tss.getService() != null) ? tss.getService().getName() : "";
            return new SimpleStringProperty(name);
        });

        roomColumn.setCellValueFactory(cellData -> {
            TreatmentStepServices tss = cellData.getValue();
            String roomNumber = (tss.getService() != null && tss.getService().getRoom() != null)
                    ? tss.getService().getRoom().getRoomNumber()
                    : "";
            return new SimpleStringProperty(roomNumber);
        });

        noteColumn.setCellValueFactory(cellData -> {
            TreatmentStepServices tss = cellData.getValue();
            String note = (tss.getTreatmentStep() != null && tss.getTreatmentStep().getStepDescription() != null)
                    ? tss.getTreatmentStep().getStepDescription()
                    : "";
            return new SimpleStringProperty(note);
        });

        startTimeColumn.setCellValueFactory(cellData -> {
            TreatmentStepServices tss = cellData.getValue();
            String startTime = (tss.getTreatmentStep() != null && tss.getTreatmentStep().getStartTime() != null)
                    ? tss.getTreatmentStep().getStartTime().toString()
                    : "";
            return new SimpleStringProperty(startTime);
        });

        endTimeColumn.setCellValueFactory(cellData -> {
            TreatmentStepServices tss = cellData.getValue();
            String endTime = (tss.getTreatmentStep() != null && tss.getTreatmentStep().getEndTime() != null)
                    ? tss.getTreatmentStep().getEndTime().toString()
                    : "";
            return new SimpleStringProperty(endTime);
        });

        diagnosticColumn.setCellValueFactory(cellData -> {
            TreatmentStepServices tss = cellData.getValue();
            String outcome = (tss.getTreatmentStep() != null && tss.getTreatmentStep().getOutcome() != null)
                    ? tss.getTreatmentStep().getOutcome()
                    : "";
            return new SimpleStringProperty(outcome);
        });

        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final FontIcon deleteIcon = new FontIcon(FontAwesomeSolid.TRASH_ALT);
            private final Button btn = new Button();

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    deleteIcon.setIconSize(20); // Nhỏ gọn
                    deleteIcon.setIconColor(Paint.valueOf("#F44336")); // Đỏ
                    btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
                    setGraphic(btn);
                    btn.setOnAction(event -> {
                        TreatmentStepServices treatmentStep = getTableView().getItems().get(getIndex());
                        // Logic xử lý huỷ
                        System.out.println("Cancel clicked for treatment step: " + treatmentStep);
                    });
                }
            }
        });

        diagnosticTestTabelView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TreatmentStepServices selectedService = diagnosticTestTabelView.getSelectionModel().getSelectedItem();
                if (selectedService != null) {
                    // Handle double-click event on the selected service
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/home/mec888/doctor/appointment/modal/examine-service-modal.fxml"));
                        StackPane modal = loader.load();

                        ExamineServiceModalController controller = loader.getController();
                        controller.setUpdateService(selectedService, this);
                        // Get the current scene and add the modal to it
                        AnchorPane root = (AnchorPane) ((Node) event.getSource()).getScene().getRoot();
                        root.getChildren().add(modal);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Double-clicked on service: " + selectedService.getService().getName());

                }
            }
        });
    }

    public void addButtonToTable() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final HBox actionBox = new HBox(10);

            private final FontIcon deleteIcon = new FontIcon(FontAwesomeSolid.TRASH_ALT);

            {
                deleteIcon.setIconSize(20);
                deleteIcon.setIconColor(Paint.valueOf("#F44336"));

                actionBox.getChildren().add(deleteIcon);
                actionBox.setAlignment(Pos.CENTER);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    TreatmentStepServices doctor = getTableView().getItems().get(getIndex());
                    setActionHandlers(actionBox, doctor);
                    setGraphic(actionBox);
                }
            }
        });
    }

    private void setActionHandlers(HBox actionBox, TreatmentStepServices treatmentStepServices) {
        FontIcon trashIcon = (FontIcon) actionBox.getChildren().getFirst();

        trashIcon.setOnMouseClicked(event -> handleDelete(treatmentStepServices));
    }


    private void handleDelete(TreatmentStepServices treatmentStepServices) {
        System.out.println(treatmentStepServices);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete this treatment step service?");
        alert.setContentText("This action cannot be undone.");
        ButtonType confirmButton = new ButtonType("Delete");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirmButton, cancelButton);
        alert.showAndWait().ifPresent(response -> {
            if (response == cancelButton) {
                alert.close();
            } else if (response == confirmButton) {
                // Perform the delete operation
                TreatmentSteps treatmentStep = treatmentStepServices.getTreatmentStep();
                System.out.println("Deleting treatment step service: " + treatmentStep);
                treatmentStep.setStatus("CANCELLED");
                treatmentStepDao.updateTreatmentStep(treatmentStep);
                // Refresh the table view or perform any other necessary actions
                getListServiceOrdered();
                System.out.println("Deleted treatment step service: " + treatmentStepServices);
            }
        });
    }

    public void getListServiceOrdered() {
        List<TreatmentSteps> treatmentSteps = treatmentStepDao.getAllTreatmentStepsByAppointmentId(SeeADoctorContainerController.currentAppointment.getId());
        if (treatmentSteps != null && !treatmentSteps.isEmpty()) {
            ObservableList<TreatmentStepServices> treatmentStepsList = diagnosticTestTabelView.getItems();
            treatmentStepsList.clear();

            for (TreatmentSteps treatmentStep : treatmentSteps) {
                TreatmentStepServices treatmentStepService = treatmentStepServiceDao.getTreatmentStepServiceByTreatmentStepID(treatmentStep.getId());
                if (treatmentStepService != null) {
                    treatmentStepsList.add(treatmentStepService);
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