package com.home.mec888.controller.doctor.appointment.modal;

import com.home.mec888.controller.IndexController;
import com.home.mec888.controller.doctor.appointment.DiagnosticTestController;
import com.home.mec888.controller.doctor.appointment.SeeADoctorContainerController;
import com.home.mec888.dao.DoctorDao;
import com.home.mec888.dao.ServiceDao;
import com.home.mec888.dao.TreatmentStepDao;
import com.home.mec888.dao.TreatmentStepServiceDao;
import com.home.mec888.entity.Doctor;
import com.home.mec888.entity.Service;
import com.home.mec888.entity.TreatmentStepServices;
import com.home.mec888.entity.TreatmentSteps;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.print.Doc;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public class DiagnosticTestModalController {
    @FXML
    public StackPane modalDiagnosticTest;
    @FXML
    public Rectangle overlay;
    @FXML
    public VBox diagnosticListContainer;
    @FXML
    public VBox treatmentStepServiceContainer;
    @FXML
    public VBox diagnosticListWrapper;
    @FXML
    public TextField doctorReferral;
    @FXML
    public TextField doctorOrder;
    @FXML
    public TextArea noteField;
    @FXML
    public TextField searchBox;
    public DiagnosticTestController diagnosticTestController;

    ServiceDao serviceDao;
    TreatmentStepDao treatmentStepDao;
    TreatmentStepServiceDao treatmentStepServiceDao;
    Service selectedService;
    DoctorDao doctorDao;

    @FXML
    public void initialize() {
        serviceDao = new ServiceDao();
        treatmentStepDao = new TreatmentStepDao();
        treatmentStepServiceDao = new TreatmentStepServiceDao();
        doctorDao = new DoctorDao();
        Platform.runLater(() -> {
            Stage stage = (Stage) modalDiagnosticTest.getScene().getWindow();

            // Bind StackPane để mở rộng theo cửa sổ
            modalDiagnosticTest.prefWidthProperty().bind(stage.widthProperty());
            modalDiagnosticTest.prefHeightProperty().bind(stage.heightProperty());

            // Overlay phủ kín toàn màn hình
            overlay.widthProperty().bind(modalDiagnosticTest.widthProperty());
            overlay.heightProperty().bind(modalDiagnosticTest.heightProperty());

            modalDiagnosticTest.lookup("#overlay").setOnMouseClicked(event -> {
                AnchorPane root = (AnchorPane) modalDiagnosticTest.getScene().getRoot();
                root.getChildren().remove(modalDiagnosticTest);
            });
        });

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            searchService(newValue);
        });
        getListServices();
    }

    public void closeModal(ActionEvent event) {
        AnchorPane root = (AnchorPane) modalDiagnosticTest.getScene().getRoot();
        root.getChildren().remove(modalDiagnosticTest);

//        monthController.reloadSchedule();
    }

    public void getListServices() {
        // Fetch services from the database
        List<Service> services = serviceDao.getServiceByDoctorRoom(IndexController.doctorId); // Replace with your DAO method

        if (services != null) {
            diagnosticListContainer.getChildren().clear();
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

                // Add double-click event
                serviceRow.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {
                        System.out.println("Double-clicked on service: " + service.getName());
                        diagnosticListWrapper.setVisible(false);
                        diagnosticListWrapper.setManaged(false);
                        treatmentStepServiceContainer.setVisible(true);
                        treatmentStepServiceContainer.setManaged(true);

                        // Set the selected service
                        selectedService = service;
                        setTreatmentServiceFields();
                    }
                });
                // Add the row to the container
                diagnosticListContainer.getChildren().add(serviceRow);
            }
        } else {
            System.out.println("No services found.");
        }
    }

    public void setTreatmentServiceFields() {
        // Set the fields with the selected service details
        // For example:
        Doctor doctor = IndexController.doctor;
        doctorReferral.setText("Dr." + doctor.getUser().getFirstName());
        doctorOrder.setText(selectedService.getRoom().getRoomNumber());
    }

    public void closeTreatmentStep(ActionEvent event) {
        treatmentStepServiceContainer.setVisible(false);
        treatmentStepServiceContainer.setManaged(false);
        diagnosticListWrapper.setVisible(true);
        diagnosticListWrapper.setManaged(true);
    }

    public void clearTextField(ActionEvent event) {
        noteField.clear();
    }

    public void saveTreatmentStepService(ActionEvent event) {
        String doctorReferralText = doctorReferral.getText();
        String doctorOrderText = doctorOrder.getText();
        String noteText = noteField.getText();
        // Validate the input fields
        if (doctorReferralText.isEmpty() || doctorOrderText.isEmpty()) {
            // Show an error message or alert
            System.out.println("Please fill in all required fields.");
            return;
        }

//        Doctor doctor = doctorDao.findDoctorByRoomId(selectedService.getRoom().getId());
        // Save the treatment step service
        TreatmentSteps treatmentSteps = new TreatmentSteps();
        if (IndexController.doctor.getRoom().getId().equals(selectedService.getRoom().getId())) {
            treatmentSteps.setDoctor(IndexController.doctor);
        }
//        treatmentSteps.setDoctor(doctor);
        treatmentSteps.setStepDescription(noteText);
        treatmentSteps.setAppointment(SeeADoctorContainerController.currentAppointment);
        treatmentSteps.setStatus("PENDING");


        try {
            TreatmentSteps savedTreatmentStep = treatmentStepDao.saveTreatmentStep(treatmentSteps);
            if (savedTreatmentStep != null) {
                TreatmentStepServices treatmentStepServices = new TreatmentStepServices();
                treatmentStepServices.setService(selectedService);
                treatmentStepServices.setTreatmentStep(savedTreatmentStep);

                TreatmentStepServices savedTreatmentStepService = treatmentStepServiceDao.saveTreatmentStepService(treatmentStepServices);
                if (savedTreatmentStepService != null) {
                    System.out.println("Treatment step service saved successfully. ID: " + savedTreatmentStepService.getId());
                } else {
                    System.out.println("Failed to save treatment step service.");
                }
            } else {
                System.out.println("Failed to save treatment step.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lưu: " + e.getMessage());
            e.printStackTrace();
        }
//         After saving, close the modal
        closeModal(event);
        diagnosticTestController.reload();
    }

    public void searchService(String keyword) {
        keyword = searchBox.getText();
        if (keyword.isBlank()) {
            getListServices();
            return;
        }

        // Tim kiem dich vu dua tren ten dich vu
        List<Service> filteredServices = serviceDao.getServiceByDoctorRoom(IndexController.doctorId);
        diagnosticListContainer.getChildren().clear();
        for (Service service : filteredServices) {
            if (service.getName().toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT)) ||
            service.getRoom().getRoomNumber().toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT))||
            service.getDescription().toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT)) ||
            service.getPrice().toString().toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT))) {
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

                // Add double-click event
                serviceRow.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {
                        System.out.println("Double-clicked on service: " + service.getName());
                        diagnosticListWrapper.setVisible(false);
                        diagnosticListWrapper.setManaged(false);
                        treatmentStepServiceContainer.setVisible(true);
                        treatmentStepServiceContainer.setManaged(true);

                        // Set the selected service
                        selectedService = service;
                        setTreatmentServiceFields();
                    }
                });
                // Add the row to the container
                diagnosticListContainer.getChildren().add(serviceRow);
            }
        }
    }
}
