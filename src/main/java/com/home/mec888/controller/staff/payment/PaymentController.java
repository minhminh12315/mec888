package com.home.mec888.controller.staff.payment;

import com.home.mec888.controller.IndexController;
import com.home.mec888.dao.AppointmentDao;
import com.home.mec888.dao.InvoiceDao;
import com.home.mec888.dao.PaymentDao;
import com.home.mec888.entity.*;
import com.home.mec888.util.SceneSwitcher;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.List;

public class PaymentController {
    @FXML
    public Text generalInformation;
    @FXML
    public Text addressInformation;
    @FXML
    public VBox serviceListContainer;
    @FXML
    public VBox diagnosticListWrapper;
    @FXML
    public VBox treatmentStepServiceContainer;

    AppointmentDao appointmentDao;
    PaymentDao paymentDao;
    InvoiceDao invoiceDao;

    List<Service> services;
    public static Appointment currentAppointment1;
    public static Patient currentPatient1;
    public static boolean isPaymentSaved = false;

    @FXML
    public void initialize() {
        appointmentDao = new AppointmentDao();
        paymentDao = new PaymentDao();
        invoiceDao = new InvoiceDao();
    }

//    public void setAppointmentHeader() {
//        if (currentAppointment1 != null) {
//            String patientName = currentPatient1.getUser().getFirstName();
//            String patientAge;
//
//            if (currentPatient1.getUser().getDateOfBirth() != null) {
//                patientAge = (LocalDate.now().getYear() - currentPatient1.getUser().getDateOfBirth().getYear()) + " (" +
//                        currentPatient1.getUser().getDateOfBirth().getYear() + " years old)";
//            } else {
//                patientAge = "Chưa có thông tin";
//            }
//
//            String patientGender = currentPatient1.getUser().getGender();
//            String patientAddress = currentPatient1.getUser().getAddress();
//            generalInformation.setText(patientName + " | " + patientAge + " | " + patientGender);
//            addressInformation.setText(patientAddress);
//        }
//    }

    public void setAppointment(Appointment appointment) {
        try {
            currentAppointment1 = appointmentDao.getPatientAppointmentById(appointment.getId());
            currentPatient1 = currentAppointment1.getPatient();

            services = appointmentDao.getServiceByAppointmentId(currentAppointment1.getId());
            if (services != null) {
                setupServiceList(services);
                setupTotalAndPaymentButton(services);
            } else {
                System.out.println("No services found.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setupServiceList(List<Service> services) {
        serviceListContainer.getChildren().clear();
        for (Service service : services) {
            HBox serviceRow = new HBox();
            serviceRow.getStyleClass().add("service-row");

            Label serviceName = new Label(service.getName());
            serviceName.setPrefWidth(200);

            Label description = new Label("|  " + service.getDescription());
            description.setPrefWidth(300);

            Label price = new Label("|  " + service.getPrice().toString());
            price.setPrefWidth(75);

            Label room = new Label("|  " + service.getRoom().getRoomNumber());
            room.setPrefWidth(150);

            serviceRow.getChildren().addAll(serviceName, description, price, room);
            serviceListContainer.getChildren().add(serviceRow);
            serviceListContainer.setFillWidth(true);
        }
    }

    private void setupTotalAndPaymentButton(List<Service> services) {
        double total = services.stream().mapToDouble(Service::getPrice).sum();

        Label totalLabel = new Label("Total Price: " + total);
        totalLabel.getStyleClass().add("total-price-label");

        Button savePaymentButton = new Button("Save Payment");
        savePaymentButton.setOnAction(event -> {
            processPayment(total);

            ActionEvent actionEvent = new ActionEvent(event.getSource(), event.getTarget());
            SceneSwitcher.loadView("staff/payment/list-appointment.fxml", actionEvent);
        });

        serviceListContainer.getChildren().addAll(totalLabel, savePaymentButton);
    }

    private void processPayment(double totalAmount) {
        try {
            Payment payment = new Payment();
            payment.setAppointment(currentAppointment1);

            // Add this line to set the patient
            payment.setPatient(currentPatient1);

            payment.setAmount(totalAmount);
            payment.setPaymentMethod("Cash");
            payment.setPaymentDate(java.sql.Timestamp.valueOf(LocalDate.now().atStartOfDay()));
            payment.setStatus("Paid");

            // Save payment first
            paymentDao.savePayment(payment);

            // Then create and save invoice
            Invoice invoice = new Invoice();
            invoice.setPayment(payment);
            invoice.setInvoiceNumber("INV-" + currentAppointment1.getId());
            invoice.setInvoiceDate(java.sql.Timestamp.valueOf(LocalDate.now().atStartOfDay()));
            invoice.setTotalAmount(totalAmount);
            invoice.setDetails("Payment for appointment ID: " + currentAppointment1.getId());

            invoiceDao.saveInvoice(invoice);

            isPaymentSaved = true;
            showAlert("Success", "Payment processed successfully!", Alert.AlertType.INFORMATION);

            currentAppointment1.setStatus("confirmed");
            appointmentDao.updateAppointment(currentAppointment1);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to process payment: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

//    public void setTreatmentServiceFields() {
//        // Set the fields with the selected service details
//        // For example:
//        Doctor doctor = IndexController.doctor;
//    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}