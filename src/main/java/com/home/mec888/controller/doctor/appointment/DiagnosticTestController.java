package com.home.mec888.controller.doctor.appointment;

import com.home.mec888.controller.IndexController;
import com.home.mec888.dao.ServiceDao;
import com.home.mec888.entity.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class DiagnosticTestController {

    @FXML
    public Button btn_addServices;
    ServiceDao serviceDao;

    @FXML
    public void initialize() {
        // Initialize any necessary components or data here
        // For example, you might want to load the list of services when the controller is initialized
        getListServices();
    }

    public void getListServices() {
        serviceDao = new ServiceDao();
        List<Service> services = serviceDao.getServiceByDoctorRoom(IndexController.doctorId);
        if (services != null) {
            for (Service service : services) {
                System.out.println(service.getName());
            }
        } else {
            System.out.println("No services found.");
        }
    }

    public void openModalDiagnosticTest(javafx.event.ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/home/mec888/doctor/appointment/modal/diagnostic-test-modal.fxml"));
            StackPane modal = loader.load();

            // Get the current scene and add the modal to it
            AnchorPane root = (AnchorPane) ((Node) event.getSource()).getScene().getRoot();
            root.getChildren().add(modal);

            // Set the modal to be centered in the window

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}