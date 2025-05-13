package com.home.mec888.controller.staff.payment;

import com.home.mec888.controller.IndexController;
import com.home.mec888.dao.AppointmentDao;
import com.home.mec888.dao.InvoiceDao;
import com.home.mec888.dao.PaymentDao;
import com.home.mec888.entity.*;
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

    public static Appointment currentAppointment1;
    public static Patient currentPatient1;
    AppointmentDao appointmentDao = new AppointmentDao();
    PaymentDao paymentDao = new PaymentDao();
    public static boolean isPaymentSaved = false;

    InvoiceDao invoiceDao = new InvoiceDao();


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
            if (services != null) {
                serviceListContainer.getChildren().clear();
                double[] totalPrice = {0.0}; // Use an array to hold the total price


                for (Service service : services) {
                    System.out.println(service);
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

                    // Add labels to the row
                    serviceRow.getChildren().addAll(serviceName, description, price, room);

                    // Add the row to the container
                    serviceListContainer.getChildren().add(serviceRow);

                    // Add the service price to the total
                    totalPrice[0] += service.getPrice();
                }

                // Display the total price
                Label totalLabel = new Label("Total Price: " + totalPrice[0]);
                Button savePaymentButton = new Button("Save Payment");
                savePaymentButton.setOnAction(event -> {
                    // Handle payment saving logic here
                    System.out.println("Payment saved for appointment ID: " + currentAppointment1.getId());

                    Payment payment = new Payment();
                    payment.setAppointment(currentAppointment1);
                    payment.setAmount(totalPrice[0]); // Access the total price from the array
                    payment.setPaymentMethod("Cash");
                    payment.setPaymentDate(java.sql.Timestamp.valueOf(LocalDate.now().atStartOfDay()));
                    payment.setStatus("Paid");
                    paymentDao.savePayment(payment);

                    Invoice invoice = new Invoice();
                    invoice.setPayment(payment);
                    invoice.setInvoiceNumber("INV-" + currentAppointment1.getId());
                    invoice.setInvoiceDate(java.sql.Timestamp.valueOf(LocalDate.now().atStartOfDay()));
                    invoice.setTotalAmount(totalPrice[0]);
                    invoice.setDetails("Payment for appointment ID: " + currentAppointment1.getId());
                    invoiceDao.saveInvoice(invoice);

                    showAlert("Success", "Medicine added successfully!", Alert.AlertType.INFORMATION);

                });
                totalLabel.getStyleClass().add("total-price-label");
                serviceListContainer.getChildren().add(totalLabel);
                serviceListContainer.getChildren().add(savePaymentButton);
            } else {
                System.out.println("No services found.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setTreatmentServiceFields() {
        // Set the fields with the selected service details
        // For example:
        Doctor doctor = IndexController.doctor;
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
