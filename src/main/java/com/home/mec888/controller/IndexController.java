package com.home.mec888.controller;


import com.home.mec888.controller.settings.SettingsController;
import com.home.mec888.dao.AuditLogDao;
import com.home.mec888.entity.AuditLog;
import com.home.mec888.entity.User;
import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class IndexController {
    public static String userRole;
    public static User user;
    //
    // Staff
    @FXML
    public Button moveAppointmentButton;
    // Admin
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
    @FXML
    public Button moveDoctorSchedule;
    @FXML
    public VBox navigationBar;
    @FXML
    public Button buttonLogout;
    @FXML
    public Region regionBLock;
    @FXML
    public Button moveRoomButton;
    @FXML
    public Button moveServiceButton;

    @FXML
    public Button moveSettingsButton;
    private Button currentActiveButton;

    @FXML
    public void initialize() {
        configureNavigationButtons();
    }

    private void configureNavigationButtons() {
        if (userRole == null) {
            return;
        }
        navigationBar.getChildren().clear();
        switch (userRole.toLowerCase()) {
            case "admin":
                navigationBar.getChildren().addAll(
                        moveHomeButton,
                        moveMedicineButton,
                        moveDepartmentButton,
                        moveRoomButton,
                        moveDoctorButton,
                        moveUserButton,
                        movePatientButton,
                        moveSettingsButton
                );
                break;
            case "staff":
                navigationBar.getChildren().addAll(
                        moveAppointmentButton,
                        moveDoctorSchedule
                );

                break;
            case "doctor":
                navigationBar.getChildren().addAll(
                        moveDoctorSchedule
                );
                break;
            case "patient":
                break;
        }
        navigationBar.getChildren().addAll(
                regionBLock,
                buttonLogout);
    }


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

    // Staff
    public void handleAppointment(ActionEvent actionEvent) {
        highlightActiveButton(moveAppointmentButton);
        SceneSwitcher.loadView("staff/appointment/appointment.fxml", actionEvent);
    }

    // Admin
    public void handleHome(ActionEvent actionEvent) {
        highlightActiveButton(moveHomeButton);
        SceneSwitcher.loadView("admin/index.fxml", actionEvent);
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
    public void handleSettings(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/home/mec888/settings/settings.fxml"));
            Parent newView = loader.load();

            // Lấy controller và gán User trước khi hiển thị
            SettingsController controller = loader.getController();
            controller.setUser(user); // ✅ Gán dữ liệu User vào trước

            // Gắn vào phần center của BorderPane
            AnchorPane anchorPane = (AnchorPane) ((Node) event.getSource()).getScene().getRoot();
            BorderPane mainPane = (BorderPane) anchorPane.lookup("#mainBorderPane");
            if (mainPane != null) {
                mainPane.setCenter(newView);
            } else {
                System.err.println("Không tìm thấy BorderPane với ID 'mainBorderPane'");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleService(ActionEvent actionEvent) {
        highlightActiveButton(moveServiceButton);
        SceneSwitcher.loadView("admin/service/service-management.fxml", actionEvent);
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

    public void handleDoctorSchedule(ActionEvent event) {
        highlightActiveButton(moveDoctorSchedule);
        SceneSwitcher.loadView("doctor/schedule/doctor-schedule.fxml", event);
    }

}
