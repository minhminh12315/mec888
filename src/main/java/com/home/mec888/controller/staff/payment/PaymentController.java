package com.home.mec888.controller.staff.payment;

import com.home.mec888.dao.AppointmentDao;
import com.home.mec888.entity.Appointment;
import com.home.mec888.entity.Patient;
import com.home.mec888.entity.Service;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.List;

public class PaymentController {
    @FXML
    public Text generalInformation;
    @FXML
    public Text addressInformation;

    public static Appointment currentAppointment1;
    public static Patient currentPatient1;
    AppointmentDao appointmentDao = new AppointmentDao();

    @FXML
    public void initialize() {

    }

    public void setAppointmentHeader() {
        if (currentAppointment1 != null) {
            String patientName = currentPatient1.getUser().getFirstName();
            String patientAge;

            if (currentPatient1.getUser().getDateOfBirth() != null) {
                patientAge = (LocalDate.now().getYear() - currentPatient1.getUser().getDateOfBirth().getYear()) + " (" +
                        currentPatient1.getUser().getDateOfBirth().getYear() + " years old)";
            } else {
                patientAge = "Chưa có thông tin";
            }

            String patientGender = currentPatient1.getUser().getGender();
            String patientAddress = currentPatient1.getUser().getAddress();
            generalInformation.setText(patientName + " | " + patientAge + " | " + patientGender);
            addressInformation.setText(patientAddress);
        }
    }

    public void setAppointment(Appointment appointment) {
        try {
            currentAppointment1 = appointment;
            currentPatient1 = currentAppointment1.getPatient();
            List<Service> services = appointmentDao.getServiceByAppointmentId(currentAppointment1.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
