package com.home.mec888.controller.admin.dashboard;

import javafx.scene.image.Image;

import java.time.LocalDate;

public class AppointmentView {
    private final Image image;
    private final String patientName;
    private final String doctorName;
    private final LocalDate date;
    private final String disease;

    public AppointmentView(Image image, String patientName, String doctorName, LocalDate date, String disease) {
        this.image = image;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.date = date;
        this.disease = disease;
    }

    public Image getImage() {
        return image;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDisease() {
        return disease;
    }
}
