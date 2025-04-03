package com.home.mec888.controller.login;

import com.home.mec888.dao.AuditLogDao;
import com.home.mec888.dao.RoleDao;
import com.home.mec888.dao.UserDao;
import com.home.mec888.entity.AuditLog;
import com.home.mec888.entity.Role;
import com.home.mec888.entity.User;
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
    private Label errorLabel;

    private UserDao userDao = new UserDao();
    private RoleDao roleDao = new RoleDao();

    @FXML
    private void handleLogin(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = userDao.getUserByUsername(username);
        Role role = roleDao.getRoleById(Long.valueOf(user.getRoleId()));
        if (user != null && user.getPassword().equals(password)) {

            AuditLogDao auditLogDao = new AuditLogDao();
            AuditLog auditLog = new AuditLog( user.getId().intValue(), "Login", "Login");
            auditLogDao.saveAuditLog(auditLog);

            errorLabel.setText("Login successful!");
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
            errorLabel.setText("Invalid username or password.");
        }
    }


}