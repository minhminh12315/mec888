package com.home.mec888.controller.patient;

import com.home.mec888.controller.admin.patient.PatientAppointmentController;
import com.home.mec888.dao.AppointmentDao;
import com.home.mec888.dao.AuditLogDao;
import com.home.mec888.dao.PatientDao;
import com.home.mec888.entity.AuditLog;
import com.home.mec888.entity.Patient;
import com.home.mec888.entity.User;
import com.home.mec888.session.UserSession;
import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;

public class PatientMain {
    public static User user;
    @FXML
    public Button moveF1;
    @FXML
    public Button moveF2;
    @FXML
    public Button moveHomeButton;
    @FXML
    public Button buttonLogout;
    public HBox mainBox;

    private Button currentActiveButton;

    @FXML
    public Button moveListAppointmentForPatient;

    private PatientDao patientDao;
    private Patient patient;

    @FXML
    public void initialize() {
        patientDao = new PatientDao();

        user = UserSession.getInstance().getUser();
        patient = patientDao.findPatientByUserId(user.getId());
    }

    public void handleHome(ActionEvent actionEvent) {
        highlightActiveButton(moveHomeButton);
        SceneSwitcher.loadView("patient/dashboard.fxml", actionEvent);
    }

    public void handlePatientAppointment(ActionEvent event) {
        highlightActiveButton(moveListAppointmentForPatient);
//        SceneSwitcher.loadView("patient/list-patients.fxml", event);
        FXMLLoader loader = SceneSwitcher.loadViewToUpdate("patient/list-patients.fxml");

        if (loader != null) {
            ListPatientsController listPatientsController = loader.getController();
            listPatientsController.setPatientAppointment(patient);

            Parent newView = loader.getRoot();
            AnchorPane anchorPane = (AnchorPane) mainBox.getScene().getRoot();
            BorderPane mainPane = (BorderPane) anchorPane.lookup("#mainBorderPane");

            if (mainPane != null) {
                mainPane.setCenter(newView);
            } else {
                System.err.println("BorderPane with ID 'mainBorderPane' not found");
            }
        } else {
            System.err.println("Could not load patient-appointment.fxml");
        }
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