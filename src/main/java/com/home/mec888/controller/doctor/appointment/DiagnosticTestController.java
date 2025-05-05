package com.home.mec888.controller.doctor.appointment;

import com.home.mec888.controller.IndexController;
import com.home.mec888.dao.ServiceDao;
import com.home.mec888.entity.Service;
import javafx.fxml.FXML;

import java.util.List;

public class DiagnosticTestController {

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

}
