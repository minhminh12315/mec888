package com.home.mec888.controller.doctor.appointment;

import com.home.mec888.dao.AppointmentDao;
import com.home.mec888.entity.Appointment;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class ListAppointmentController {
    @FXML
    public VBox listAppointmentContainer;

    private AppointmentDao appointmentDao;

    @FXML
    public void initialize(){
        appointmentDao = new AppointmentDao();
    }

    public void getListAppointment(){
        try{
            List<Appointment> appointments = appointmentDao.getAllAppointments();
            for (Appointment appointment : appointments){
                HBox appointmentWrapper = new HBox();
                Text id = new Text(String.valueOf(appointment.getId()));
                Text patientName = new Text(String.valueOf(appointment.getPatient().getId()));
                Text doctorName = new Text(appointment.getDoctor().getUser().getFirstName());
                Text apppointDate = new Text(String.valueOf(appointment.getAppointmentDate()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
