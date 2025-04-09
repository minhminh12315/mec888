package com.home.mec888.controller.admin.patient;

import com.home.mec888.dao.PatientDao;
import com.home.mec888.dao.UserDao;
import com.home.mec888.entity.Patient;
import com.home.mec888.entity.User;
import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Date;

public class PatientManagementController {
    public TableView<Patient> patientManagementTable;
    public TableColumn<Patient, Long> patientColId;
    public TableColumn<Patient, Long> userColId;
    public TableColumn<Patient, String> patientColEmergency;
    public TableColumn<Patient, String> patientColMedical;
    public TableColumn<Patient, Void> actionColumn;

    private PatientDao patientDao;

    @FXML
    private void initialize() {
        patientDao = new PatientDao();
        loadPatientData();
        addButtonToTable();
    }

    @FXML
    public void handleAdd(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/patient/patient-add.fxml", actionEvent);
    }

    private void loadPatientData() {
        patientColId.setCellValueFactory(new PropertyValueFactory<>("id"));
        userColId.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        patientColEmergency.setCellValueFactory(new PropertyValueFactory<>("emergency_contact"));
        patientColMedical.setCellValueFactory(new PropertyValueFactory<>("medical_history"));

        patientManagementTable.getItems().clear();
        patientManagementTable.getItems().addAll(patientDao.getAllPatients());
    }

    private void addButtonToTable() {
        Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Patient, Void> call(final TableColumn<Patient, Void> param) {
                final TableCell<Patient, Void> cell = new TableCell<>() {

                    private final Button updateButton = new Button("Update");
                    private final Button deleteButton = new Button("Delete");

                    {
                        updateButton.setOnAction((ActionEvent event) -> {
                            Patient patient = getTableView().getItems().get(getIndex());
                            handleUpdate(patient, event);
                        });
                        deleteButton.setOnAction((ActionEvent event) -> {
                            Patient patient = getTableView().getItems().get(getIndex());
                            handleDelete(patient);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(updateButton, deleteButton);
                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        };
        actionColumn.setCellFactory(cellFactory);
    }

    @FXML
    private void handleUpdate(Patient patient, ActionEvent actionEvent) {
        FXMLLoader loader = SceneSwitcher.loadViewToUpdate("/com/home/mec888/admin/patient/patient-update.fxml");

        if (loader != null) {
            PatientUpdateController patientUpdateController = loader.getController();
            patientUpdateController.setPatient(patient);

            Parent newView = loader.getRoot();
            AnchorPane anchorPane = (AnchorPane) patientManagementTable.getScene().getRoot();
            BorderPane mainPane = (BorderPane) anchorPane.lookup("#mainBorderPane");

            if (mainPane != null) {
                mainPane.setCenter(newView);
            } else {
                System.err.println("BorderPane with ID 'mainBorderPane' not found");
            }
        } else {
            System.err.println("Could not load edit-showtime.fxml");
        }
    }

    private void handleDelete(Patient patient) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete patient "
                + patient.getEmergency_contact() + "?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            patientDao.deletePatient(patient.getId());
            loadPatientData();
        }
    }
}