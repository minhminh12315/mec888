package com.home.mec888.controller.staff.payment;


import com.home.mec888.controller.IndexController;
import com.home.mec888.dao.AppointmentDao;
import com.home.mec888.entity.Appointment;
import com.home.mec888.util.SceneSwitcher;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.sql.Time;
import java.util.List;

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

    @FXML
    public void initialize() {
        appointmentDao = new AppointmentDao();

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
                FXMLLoader loader = SceneSwitcher.loadViewToUpdate("staff/payment/payment.fxml");
                PaymentController controller = loader.getController();
                controller.setAppointment(selectedAppointment);

                Parent newView = loader.getRoot();
                AnchorPane anchorPane = (AnchorPane) appointmentTable.getScene().getRoot();
                BorderPane mainPane = (BorderPane) anchorPane.lookup("#mainBorderPane");

                if (mainPane != null) {
                    mainPane.setCenter(newView);
                } else {
                    System.err.println("BorderPane with ID 'mainBorderPane' not found");
                }
            }
        });
        getListAppointment();
    }

    public void getListAppointment() {
        try {
            List<Appointment> appointments;
            appointments = appointmentDao.getAppointmentWhereStatusIsCompleted();

            if (appointments != null) {
                ObservableList<Appointment> observableList = FXCollections.observableArrayList(appointments);
                appointmentTable.setItems(observableList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


