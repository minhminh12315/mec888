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
    public Button moveMedicineButton;
    @FXML
    public Button moveDepartmentButton;
    @FXML
    public Button moveDoctorButton;
    private Button currentActiveButton;

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
