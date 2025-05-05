package com.home.mec888.controller.doctor.appointment;

import com.home.mec888.controller.IndexController;
import com.home.mec888.dao.MedicineDao;
import com.home.mec888.dao.ServiceDao;
import com.home.mec888.entity.Medicine;
import com.home.mec888.entity.Service;
import javafx.fxml.FXML;

import java.util.List;

public class PrescriptionController {

    MedicineDao medicineDao = new MedicineDao();


    @FXML
    public void initialize() {
        // Initialize any necessary components or data here
        // For example, you might want to load the list of services when the controller is initialized
        getListMedicines();
    }

    public void getListMedicines() {
        List<Medicine> medicines = medicineDao.getAllMedicines();
        if (medicines != null) {
            for (Medicine medicine : medicines) {
                System.out.println("=============");
                System.out.println(medicine);
            }
        } else {
            System.out.println("No medicines found.");
        }
    }

}
