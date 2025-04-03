package com.home.mec888.controller;


import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class IndexController {
    public static String userRole;

    public void handleMedicine(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        SceneSwitcher.switchTo(currentStage, "admin/medicine/medicine-management.fxml");
    }

    public void logout(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        SceneSwitcher.switchTo(currentStage, "login/login.fxml");
    }

    public void setRole(String userRole) {
        this.userRole = userRole;
    }


}
