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
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LoginController {
    @FXML
    public StackPane slideBackground;
    @FXML
    private VBox root;
    @FXML
    private VBox loginForm;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label usernameError;
    @FXML
    private Label passwordError;

    private final UserDao userDao = new UserDao();
    private final RoleDao roleDao = new RoleDao();

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

            // Log the login action
            AuditLogDao auditLogDao = new AuditLogDao();
            AuditLog auditLog = new AuditLog(user.getId().intValue(), "Login", "Login");
            auditLogDao.saveAuditLog(auditLog);

            // Set the user session
            UserSession.getInstance().setUser(user);

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            SceneSwitcher.switchTo(currentStage, "admin/index.fxml");

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

    public void slideShow() {
        // 1. Chuẩn bị danh sách ảnh
        List<Image> images = List.of(
                new Image(getClass().getResource("/asset/images/background_1.jpg").toExternalForm()),
                new Image(getClass().getResource("/asset/images/background_2.jpg").toExternalForm()),
                new Image(getClass().getResource("/asset/images/background_3.jpg").toExternalForm())
        );

        // 2. Index động
        AtomicInteger count = new AtomicInteger(0);

        // 3. Tạo ImageView và bind kích thước với StackPane
        ImageView imageView = new ImageView(images.get(0));
        imageView.setPreserveRatio(false);
        imageView.fitWidthProperty().bind(slideBackground.widthProperty());
        imageView.fitHeightProperty().bind(slideBackground.heightProperty());

        // 4. Thêm ImageView vào StackPane (lúc này slideBackground đã có trong scene)
        slideBackground.getChildren().add(imageView);

        // 5. Timeline để thay ảnh mỗi 5s
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), evt -> {
                    int next = (count.incrementAndGet()) % images.size();
                    imageView.setImage(images.get(next));
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public void initialize() {
        slideShow();
        loginForm.setVisible(false);
        slideBackground.setOnMouseClicked(event -> {
            loginForm.setVisible(true);
        });
    }
}