package com.home.mec888.controller.admin.patient;

import com.home.mec888.dao.PatientDao;
import com.home.mec888.dao.UserDao;
import com.home.mec888.entity.Doctor;
import com.home.mec888.entity.Patient;
import com.home.mec888.entity.User;
import com.home.mec888.util.SceneSwitcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientManagementController {
    public TableView<Patient> patientManagementTable;
    public TableColumn<Patient, Long> patientColId;
    public TableColumn<Patient, Long> userColId;
    @FXML
    public TableColumn<Patient, String> firstNameColumn;
    @FXML
    public TableColumn<Patient, String> lastNameColumn;
    public TableColumn<Patient, String> patientColEmergency;
    public TableColumn<Patient, String> patientColMedical;
    public TableColumn<Patient, Void> actionColumn;
    public TextField searchField;

    private PatientDao patientDao;
    private UserDao userDao;
    private List<Patient> originalList;

    @FXML
    private void initialize() {
        patientDao = new PatientDao();
        userDao = new UserDao();
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
        firstNameColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().getUser().getId() != null) {
                User user = userDao.getUserById((long) cellData.getValue().getUser().getId());
                if (user != null) {
                    return new SimpleStringProperty(user.getFirstName());
                } else {
                    return new SimpleStringProperty("Unknown");
                }
            } else {
                return new SimpleStringProperty("Unknown");
            }
        });
        lastNameColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().getUser().getId() != null) {
                User user = userDao.getUserById((long) cellData.getValue().getUser().getId());
                if (user != null) {
                    return new SimpleStringProperty(user.getLastName());
                } else {
                    return new SimpleStringProperty("Unknown");
                }
            } else {
                return new SimpleStringProperty("Unknown");
            }
        });
        patientColEmergency.setCellValueFactory(new PropertyValueFactory<>("emergency_contact"));
        patientColMedical.setCellValueFactory(new PropertyValueFactory<>("medical_history"));

        patientManagementTable.getItems().clear();
        patientManagementTable.getItems().addAll(patientDao.getAllPatients());


        originalList = new ArrayList<>(patientDao.getAllPatients());
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterPatientList(newValue);
        });
    }

    private void updateTable(List<Patient> patients) {
        patientManagementTable.setItems(FXCollections.observableArrayList(patients));
    }

    private void filterPatientList(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            updateTable(originalList);
            return;
        }

        // Tạo danh sách lọc
        // nhớ sửa dùng sql để lấy dữ liệu,
        List<Patient> filteredList = new ArrayList<>();
        for (Patient patient : originalList) {
            User user = userDao.getUserById( (long) patient.getUser().getId());
            if (
                    (user.getFirstName() != null && user.getFirstName().toLowerCase().contains(keyword.toLowerCase())) ||
                            (user.getLastName() != null && user.getLastName().toLowerCase().contains(keyword.toLowerCase())) ||
                            (patient.getMedical_history() != null && patient.getMedical_history().toLowerCase().contains(keyword.toLowerCase())) ||
                            (patient.getEmergency_contact() != null && patient.getEmergency_contact().toLowerCase().contains(keyword.toLowerCase()))
            ) {
                filteredList.add(patient);
            }
        }

        // Cập nhật bảng với danh sách lọc
        updateTable(filteredList);
        addButtonToTable();
    }

    private void addButtonToTable() {
        Callback<TableColumn<Patient, Void>, TableCell<Patient, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Patient, Void> call(final TableColumn<Patient, Void> param) {
                return new TableCell<>() {

                    private final FontIcon editIcon = new FontIcon(FontAwesomeSolid.EDIT);
                    private final FontIcon deleteIcon = new FontIcon(FontAwesomeSolid.TRASH_ALT);
                    private final HBox actionBox = new HBox(10); // spacing between icons

                    {
                        // Style the icons
                        editIcon.setIconSize(20);
                        editIcon.setIconColor(Paint.valueOf("#4CAF50")); // Green
                        deleteIcon.setIconSize(20);
                        deleteIcon.setIconColor(Paint.valueOf("#F44336")); // Red

                        // Add event handlers
                        editIcon.setOnMouseClicked(event -> {
                            Patient patient = getTableView().getItems().get(getIndex());
                            handleUpdate(patient, new ActionEvent());
                        });

                        deleteIcon.setOnMouseClicked(event -> {
                            Patient patient = getTableView().getItems().get(getIndex());
                            handleDelete(patient);
                        });

                        // Configure HBox
                        actionBox.getChildren().addAll(editIcon, deleteIcon);
                        actionBox.setAlignment(Pos.CENTER);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(actionBox);
                        }
                    }
                };
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }

    @FXML
    private void handleUpdate(Patient patient, ActionEvent actionEvent) {
        FXMLLoader loader = SceneSwitcher.loadViewToUpdate("admin/patient/patient-update.fxml");

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