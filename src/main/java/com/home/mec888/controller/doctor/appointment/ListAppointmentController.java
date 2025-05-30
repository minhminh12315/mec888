package com.home.mec888.controller.doctor.appointment;

import com.home.mec888.controller.IndexController;
import com.home.mec888.dao.AppointmentDao;
import com.home.mec888.dao.DoctorDao;
import com.home.mec888.entity.Appointment;
import com.home.mec888.entity.Doctor;
import com.home.mec888.util.SceneSwitcher;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListAppointmentController {
    @FXML
    public VBox listAppointmentContainer;
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, Long> colId;
    @FXML
    private TableColumn<Appointment, String> colPatientId;
    @FXML
    private TableColumn<Appointment, String> colDoctorName;
    @FXML
    private TableColumn<Appointment, String> colAppointmentDate;
    @FXML
    private TableColumn<Appointment, String> colAppointmentTime;

    private AppointmentDao appointmentDao;
    private DoctorDao doctorDao;

    @FXML
    public void initialize() {
        appointmentDao = new AppointmentDao();
        doctorDao = new DoctorDao();

        appointmentTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        colId.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
        colPatientId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getUser().getFirstName()));
        colDoctorName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDoctor().getUser().getFirstName()));
        colAppointmentDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointmentDate().toLocalDate().toString()));
        colAppointmentTime.setCellValueFactory(cellData -> {
            Time time = cellData.getValue().getAppointmentTime();
            String formattedTime = (time != null) ? time.toLocalTime().toString() : "";
            return new SimpleStringProperty(formattedTime);
        });
        appointmentTable.setOnMouseClicked(event -> {
            ActionEvent actionEvent = new ActionEvent(event.getSource(), event.getTarget());
            if (event.getClickCount() == 2 && appointmentTable.getSelectionModel().getSelectedItem() != null) {
                Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
                FXMLLoader loader = SceneSwitcher.loadViewToCallController("doctor/appointment/see-a-doctor-container.fxml", actionEvent);
                assert loader != null;
                SeeADoctorContainerController controller = loader.getController();
                boolean isMainDoctor = selectedAppointment != null
                        && selectedAppointment.getDoctor() != null
                        && selectedAppointment.getDoctor().getId() != null
                        && selectedAppointment.getDoctor().getId().equals(IndexController.doctor.getId());

                System.out.println("is main doctor: " + isMainDoctor);
                System.out.println("is main doctor: " + isMainDoctor);
                controller.setAppointment(selectedAppointment, isMainDoctor);
            }
        });
        appointmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colId.setPrefWidth(1f * Integer.MAX_VALUE * 0.5);
        colPatientId.setPrefWidth(1f * Integer.MAX_VALUE * 1.0);
        colDoctorName.setPrefWidth(1f * Integer.MAX_VALUE * 1.0);
        colAppointmentDate.setPrefWidth(1f * Integer.MAX_VALUE * 1.0);
        colAppointmentTime.setPrefWidth(1f * Integer.MAX_VALUE * 1.0);

        getListAppointment();
    }

    public void getListAppointment() {
        try {
            Long userId = IndexController.user.getId();
            Doctor currentDoctor = doctorDao.findDoctorByUserId(userId);
            List<Appointment> appointments = new ArrayList<>();
            if (currentDoctor != null) {
                appointments = appointmentDao.getAppointmentsServiceByDoctorId(currentDoctor.getId());
            }
            if (appointments != null) {
                ObservableList<Appointment> observableList = FXCollections.observableArrayList(appointments);
                appointmentTable.setItems(observableList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
