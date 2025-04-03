package com.home.mec888.controller.admin.patient;

import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class PatientManagementController {
    @FXML
    public void handleAdd(ActionEvent actionEvent){
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        SceneSwitcher.switchTo(currentStage, "admin/patient/patient-add.fxml");
    }
}
