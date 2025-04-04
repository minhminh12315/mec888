package com.home.mec888.controller.login;

import com.home.mec888.controller.IndexController;
import com.home.mec888.dao.AuditLogDao;
import com.home.mec888.dao.RoleDao;
import com.home.mec888.dao.UserDao;
import com.home.mec888.entity.AuditLog;
import com.home.mec888.entity.Role;
import com.home.mec888.entity.User;
import com.home.mec888.session.UserSession;
import com.home.mec888.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private VBox root;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label usernameError;
    @FXML
    private Label passwordError;

    private UserDao userDao = new UserDao();
    private RoleDao roleDao = new RoleDao();

    @FXML
    private void handleLogin(ActionEvent actionEvent) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (!validateFields(username, password)) {
            return;
        }

        User user = userDao.login(username, password);
        if (user != null) {
            Role role = roleDao.getRoleById(Long.valueOf(user.getRoleId()));
            IndexController.user = user;
            IndexController.userRole = role.getName();
            AuditLogDao auditLogDao = new AuditLogDao();
            AuditLog auditLog = new AuditLog(user.getId().intValue(), "Login", "Login");
            auditLogDao.saveAuditLog(auditLog);

            UserSession.getInstance().setUser(user);
//            errorLabel.setText("Login successful!");
            switch (role.getName()) {
                case "admin":
                    Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    SceneSwitcher.switchTo(currentStage, "index.fxml");
                    break;

                case "staff":

                    break;

                case "doctor":

                    break;

                case "patient":

                    break;

            }

        } else {
            System.out.println("login failed");
            showError(usernameField, usernameError, "Invalid username or password");
            showError(passwordField, passwordError, "Invalid username or password");
        }
    }

    public boolean validateFields(String username, String password) {
        boolean isValid = true;


        if (username.isEmpty()) {
            showError(usernameField, usernameError, "username must not leave empty");
            isValid = false;
        }
        if (password.isEmpty()) {
            showError(passwordField, passwordError, "password must not leave empty");
            isValid = false;
            return isValid;
        }
        if (password.length() < 8) {
            showError(passwordField, passwordError, "at least 8 characters");
            isValid = false;
            return isValid;
        }


        return isValid;
    }

    public void showError(TextField field, Label errorLabel, String message) {
        field.setStyle("-fx-border-color: -fx-secondary-color");
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-color: -fx-secondary-color");
    }
}