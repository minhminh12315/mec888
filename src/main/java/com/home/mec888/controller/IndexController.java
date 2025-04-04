package com.home.mec888.controller;


import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class IndexController {
    public static String userRole;

    public void handleMedicine(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/medicine/medicine-management.fxml", actionEvent);
    }

    public void logout(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();
        SceneSwitcher.switchTo(new Stage(), "login/login.fxml");
    }

    public void setRole(String userRole) {
        this.userRole = userRole;
    }


    public void handleDepartment(ActionEvent event) {
        SceneSwitcher.loadView("admin/department/department-management.fxml", event);
    }
}
