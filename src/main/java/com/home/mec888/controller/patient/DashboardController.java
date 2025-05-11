package com.home.mec888.controller.patient;

import com.home.mec888.dao.AuditLogDao;
import com.home.mec888.entity.AuditLog;
import com.home.mec888.entity.User;
import com.home.mec888.session.UserSession;
import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DashboardController {
    public static User user;
    @FXML
    public Button moveF1;
    @FXML
    public Button moveF2;
    @FXML
    public Button moveHomeButton;
    @FXML
    public Button buttonLogout;

    private Button currentActiveButton;

    public void handleHome(ActionEvent actionEvent) {
        highlightActiveButton(moveHomeButton);
        SceneSwitcher.loadView("patient/dashboard.fxml", actionEvent);
    }

    public void handleF1(ActionEvent actionEvent){
        highlightActiveButton(moveF1);
        SceneSwitcher.loadView("patient/f1.fxml", actionEvent);
    }

    public void handleF2(ActionEvent actionEvent){
        highlightActiveButton(moveF2);
        SceneSwitcher.loadView("patient/f2.fxml", actionEvent);
    }

    public void logout(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentStage.close();

        user = UserSession.getInstance().getUser();
        AuditLogDao auditLogDao = new AuditLogDao();
        AuditLog auditLog = new AuditLog(user.getId().intValue(), "Logout", "Logout");
        auditLogDao.saveAuditLog(auditLog);

        SceneSwitcher.switchTo(new Stage(), "login/login.fxml");
    }

    private void highlightActiveButton(Button button) {
        if (currentActiveButton != null) {
            // Xóa class "active-button" khỏi nút đang được chọn trước đó
            currentActiveButton.getStyleClass().remove("dashboard-active");
            currentActiveButton.getStyleClass().add("dashboard-btn");
        }

        // Thêm class "dashboard-active" cho nút hiện tại
        button.getStyleClass().remove("dashboard-btn");
        button.getStyleClass().add("dashboard-active");

        // Cập nhật nút hiện tại
        currentActiveButton = button;
    }
}
