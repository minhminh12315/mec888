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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Date;

public class PatientManagementController {
    public TableView<Patient> patientManagementTable;
    public TableColumn<Patient, Long> patientColId;
    public TableColumn<Patient, Long> userColId;
    public TableColumn<Patient, String> patientColFname;
    public TableColumn<Patient, String> patientColLname;
    public TableColumn<Patient, Date> patientColDOB;
    public TableColumn<Patient, String> patientColGender;
    public TableColumn<Patient, String> patientColAddress;
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
        patientColFname.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        patientColLname.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        patientColDOB.setCellValueFactory(new PropertyValueFactory<>("date_of_birth"));
        patientColGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        patientColAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
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
                            UserDao userDao = new UserDao();
                            User user = userDao.getUserById(patient.getUser_id());
                            if (user != null) {
                                handleUpdate(patient, user, event);
                            } else {
                                System.out.println("User is not set!");
                            }
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
    private void handleUpdate(Patient patient, User user, ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/home/mec888/admin/patient/patient-update.fxml"));
            Parent root = loader.load();
            PatientUpdateController patientUpdateController = loader.getController();
            patientUpdateController.setPatient(patient, user);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDelete(Patient patient) {
        // Hiển thị hộp thoại xác nhận xoá
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete patient "
                + patient.getFirst_name() + " " + patient.getLast_name() + "?");

        // Nếu người dùng chọn OK, tiến hành xoá
        if (alert.showAndWait().get() == ButtonType.OK) {
            // Gọi phương thức xoá bệnh nhân dựa trên id
            patientDao.deletePatient(patient.getId());
            // Cập nhật lại dữ liệu bảng sau khi xoá
            loadPatientData();
        }
    }
}
