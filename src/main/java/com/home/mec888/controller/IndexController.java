package com.home.mec888.controller;


import com.home.mec888.dao.AuditLogDao;
import com.home.mec888.entity.AuditLog;
import com.home.mec888.entity.User;
import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class IndexController {
    public static String userRole;
    public static User user;

    @FXML
    public Button moveHomeButton;
    @FXML
    public Button moveMedicineButton;
    @FXML
    public Button moveDepartmentButton;
    @FXML
    public Button moveDoctorButton;
    @FXML
    public Button moveUserButton;
    @FXML
    public Button movePatientButton;
    private Button currentActiveButton;

    @FXML
    public void initialize() {
        configureNavigationButtons();
    }

    private void configureNavigationButtons() {
        if (userRole == null) {
            return;
        }
        if (userRole.equalsIgnoreCase("admin")) {
            moveHomeButton.setVisible(true);
            moveMedicineButton.setVisible(true);
            moveDepartmentButton.setVisible(true);
            moveDoctorButton.setVisible(true);
            moveUserButton.setVisible(true);
            movePatientButton.setVisible(true);
        } else if (userRole.equalsIgnoreCase("doctor")) {
            moveHomeButton.setVisible(false);
            moveMedicineButton.setVisible(false);
            moveDepartmentButton.setVisible(false);
            moveDoctorButton.setVisible(false);
            moveUserButton.setVisible(false);
            movePatientButton.setVisible(false);
        } else if (userRole.equalsIgnoreCase("staff")) {
            moveHomeButton.setVisible(false);
            moveMedicineButton.setVisible(false);
            moveDepartmentButton.setVisible(false);
            moveDoctorButton.setVisible(false);
            moveUserButton.setVisible(false);
            movePatientButton.setVisible(false);
        }
    }

    @FXML
    public Button moveRoomButton;
    public void logout(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

        AuditLogDao auditLogDao = new AuditLogDao();
        AuditLog auditLog = new AuditLog(user.getId().intValue(), "Logout", "Logout");
        auditLogDao.saveAuditLog(auditLog);

        SceneSwitcher.switchTo(new Stage(), "login/login.fxml");
    }

    public void setRole(String userRole) {
        this.userRole = userRole;
    }

    public void handleHome(ActionEvent actionEvent) {
        highlightActiveButton(moveHomeButton);
        SceneSwitcher.loadView("index.fxml", actionEvent);
    }

    public void handleMedicine(ActionEvent actionEvent) {
        highlightActiveButton(moveMedicineButton);
        SceneSwitcher.loadView("admin/medicine/medicine-management.fxml", actionEvent);
    }

    public void handleDepartment(ActionEvent event) {
        highlightActiveButton(moveDepartmentButton);
        SceneSwitcher.loadView("admin/department/department-management.fxml", event);
    }

    public void handleDoctor(ActionEvent event) {
        highlightActiveButton(moveDoctorButton);
        SceneSwitcher.loadView("admin/doctor/doctor-management.fxml", event);
    }

    public void handleUser(ActionEvent actionEvent) {
        highlightActiveButton(moveUserButton);
        SceneSwitcher.loadView("admin/user/user-management.fxml", actionEvent);
    }
    public void handleRoom(ActionEvent actionEvent) {
        System.out.println("Room");
        highlightActiveButton(moveRoomButton);
        SceneSwitcher.loadView("admin/room/room-management.fxml", actionEvent);
    }

    public void handlePatient(ActionEvent actionEvent) {
        highlightActiveButton(movePatientButton);
        SceneSwitcher.loadView("admin/patient/patient-management.fxml", actionEvent);
    }

    private void highlightActiveButton(Button button) {
        if (currentActiveButton != null) {
            // Xóa class "active-button" khỏi nút đang được chọn trước đó
            currentActiveButton.getStyleClass().remove("active-button");
            currentActiveButton.getStyleClass().add("nav-btn");
        }

        // Thêm class "active-button" cho nút hiện tại
        button.getStyleClass().remove("nav-btn");
        button.getStyleClass().add("active-button");

        // Cập nhật nút hiện tại
        currentActiveButton = button;
    }

}
