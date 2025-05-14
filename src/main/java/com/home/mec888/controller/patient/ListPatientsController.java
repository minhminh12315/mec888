package com.home.mec888.controller.patient;

import com.home.mec888.controller.IndexController;
import com.home.mec888.dao.AppointmentDao;
import com.home.mec888.dao.PatientDao;
import com.home.mec888.entity.Appointment;
import com.home.mec888.entity.Patient;
import com.home.mec888.entity.User;
import com.home.mec888.session.UserSession;
import com.home.mec888.util.SceneSwitcher;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListPatientsController {
    @FXML
    public VBox listPatientsContainer;

    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private TableColumn<Appointment, Long> appColId;
    @FXML
    private TableColumn<Appointment, String> appColDoctorName;
    @FXML
    private TableColumn<Appointment, Date> appColDate;
    @FXML
    private TableColumn<Appointment, Time> appColTime;
    @FXML
    private TableColumn<Appointment, String> appColStatus;
    @FXML
    private TableColumn<Appointment, String> appColCreatedAt;
    @FXML
    private Label selectedPatientLabel;

    private PatientDao patientDao;
    private AppointmentDao appointmentDao;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private User user;

    private Patient patient;
    @FXML
    public void initialize() {
        patientDao = new PatientDao();
        appointmentDao = new AppointmentDao();

        configureAppointmentTableColumns();

        user = UserSession.getInstance().getUser();
        patient = patientDao.findPatientByUserId(user.getId());

        loadPatientAppointments(patient);
    }

    private void configureAppointmentTableColumns() {
        // Set cell value factories for appointment table columns
        appColId.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());

        appColDoctorName.setCellValueFactory(cellData -> {
            String firstName = cellData.getValue().getDoctor().getUser().getFirstName();
            String lastName = cellData.getValue().getDoctor().getUser().getLastName();
            return new SimpleStringProperty(firstName + " " + lastName);
        });

        appColDate.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getAppointmentDate()));

        appColTime.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getAppointmentTime()));

        appColStatus.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus()));

        appColCreatedAt.setCellValueFactory(cellData -> {
            Timestamp timestamp = cellData.getValue().getCreatedAt();
            String formattedDate = (timestamp != null) ?
                    timestamp.toLocalDateTime().format(dateFormatter) : "";
            return new SimpleStringProperty(formattedDate);
        });

        // Set column resize policies
        appointmentsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void loadPatientAppointments(Patient patient) {
        try {
            // Get appointments for the selected patient
            List<Appointment> appointments = getAppointmentsForPatient(patient.getId());
            if (appointments != null) {
                ObservableList<Appointment> observableList = FXCollections.observableArrayList(appointments);
                appointmentsTable.setItems(observableList);
            } else {
                appointmentsTable.setItems(FXCollections.observableArrayList(new ArrayList<>()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load appointment data", e.getMessage());
        }
    }

    private List<Appointment> getAppointmentsForPatient(Long patientId) {
        List<Appointment> allAppointments = appointmentDao.getAllAppointments();
        List<Appointment> patientAppointments = new ArrayList<>();

        if (allAppointments != null) {
            for (Appointment appointment : allAppointments) {
                if (appointment.getPatient().getId().equals(patientId)) {
                    patientAppointments.add(appointment);
                }
            }
        }

        return patientAppointments;
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}