package com.home.mec888.controller.login;

import com.home.mec888.controller.IndexController;
import com.home.mec888.controller.email.SendMail;
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @FXML
    public StackPane slideBackground;
    @FXML
    private VBox root;
    @FXML
    private VBox loginForm;
    @FXML
    private VBox inputEmailForm;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label usernameError;
    @FXML
    private Label passwordError;

    @FXML
    public TextField emailField;
    @FXML
    public Label emailError;
    @FXML
    public TextField otpField;
    @FXML
    public Label otpError;
    @FXML
    public Button sendEmailButton;
    @FXML
    public Button verifyOtpButton;

    @FXML
    public VBox resetPasswordForm;
    @FXML
    public PasswordField newPasswordField;
    @FXML
    public Label newPasswordError;
    @FXML
    public PasswordField confirmPasswordField;
    @FXML
    public Label confirmPasswordError;
    @FXML
    public Button savePasswordButton;

    private final UserDao userDao = new UserDao();
    private final RoleDao roleDao = new RoleDao();

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int OTP_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();
    private static final String otp = generateOTP();

    private int countdown = 60;


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

            if (IndexController.userRole.equalsIgnoreCase("admin") ||
                    IndexController.userRole.equalsIgnoreCase("doctor") ||
                    IndexController.userRole.equalsIgnoreCase("staff")) {
                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                SceneSwitcher.switchTo(currentStage, "admin/index.fxml");
            } else if (IndexController.userRole.equalsIgnoreCase("patient")) {
                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                SceneSwitcher.switchTo(currentStage, "patient/dashboard.fxml");
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
        imageView.setOnKeyPressed(event -> {
            loginForm.setVisible(true);
        });

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

    @FXML
    public void forgotPassword(ActionEvent actionEvent) {
        loginForm.setVisible(false);
        inputEmailForm.setVisible(true);
    }

    @FXML
    public void handleSendOTP(ActionEvent actionEvent) {
        String email = emailField.getText().trim();

        if (!validateEmail(email)) {
            return;
        }

        User user = userDao.getUserByEmail(email);
        if (user == null) {
            showError(emailField, emailError, "No user found with this email. Please check and try again.");
            return;
        }

        // Disable nút verify để tránh spam
        sendEmailButton.setDisable(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            // Cập nhật bộ đếm ngược trên nút
            sendEmailButton.setText(countdown + "s");

            // Giảm bộ đếm ngược
            countdown--;

            // Khi đếm ngược xong, kích hoạt lại nút
            if (countdown < 0) {
                sendEmailButton.setText("Send Email");
                sendEmailButton.setDisable(false); // Kích hoạt lại nút
                countdown = 60; // Reset bộ đếm ngược
            }
        }));
        // Lặp lại Timeline mỗi giây (1s)
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Lay ra user tu session
        UserSession.getInstance().setUser(user);
        user.setOtp(otp);
        user.setExpiredDate(LocalDateTime.now());

        // Thuc hien send email
        SendMail sendMail = new SendMail();
        String subject = "Mec888";
        String content = "<html>"
                + "<head>"
                + "<style>"
                + "h1 { color: #4CAF50; font-family: Arial, sans-serif; }"
                + "p { font-size: 14px; color: #333; font-family: Arial, sans-serif; line-height: 1.5; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<h1>Password Reset</h1>"
                + "<p>Hello,</p>"
                + "<p>We have received a request to reset your password. Please use the following OTP to proceed with resetting your password:</p>"
                + "<p><strong>" + otp + "</strong></p>"
                + "<p>This OTP is valid only for a limited time. Please do not share this OTP with anyone. If you did not request this, please contact our support team immediately.</p>"
                + "<p>Best regards,</p>"
                + "<p>Mec888</p>"
                + "</body>"
                + "</html>";

        sendMail.handleSend(email, subject, content);
        verifyOtpButton.setDisable(false);
        showAlert("Success", "You have just sent a request to reset password. Please wait a few minute", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void handleVerify(ActionEvent actionEvent) {
        User user = UserSession.getInstance().getUser();

        if (user == null) {
            showError(emailField, emailError, "Email not found. Please try again");
            return;
        }
        String enteredOtp = otpField.getText().trim();

        if (!enteredOtp.equals(user.getOtp())) {
            showError(otpField, otpError, "The OTP you entered is incorrect.");
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredLimit = user.getExpiredDate().plusMinutes(2);

        if (now.isAfter(expiredLimit)) {
            showError(otpField, otpError, "The OTP has expired. Please request a new one.");
            return;
        }
        System.out.println("OTP verified successfully.");
        showAlert("Successfully", "Verify email successfully", Alert.AlertType.INFORMATION);
        emailField.setText("");
        otpField.setText("");

        inputEmailForm.setVisible(false);
        resetPasswordForm.setVisible(true);
    }

    @FXML
    public void handleSavePassword(ActionEvent actionEvent) {
        User user = UserSession.getInstance().getUser();

        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!validatePassword(newPassword, confirmPassword)) {
            return;
        }

        if (user == null) {
            return;
        }
        try {
            String savePassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            user.setPassword(savePassword);
            userDao.updateUser(user);
            showAlert("Success", "Reset password successfully", Alert.AlertType.INFORMATION);
            loginForm.setVisible(true);
            resetPasswordForm.setVisible(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void handleBack(ActionEvent actionEvent) {
        loginForm.setVisible(true);
        inputEmailForm.setVisible(false);
        resetPasswordForm.setVisible(false);

        emailField.setText("");
        otpField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }

    public boolean validateEmail(String email) {
        // Biểu thức chính quy kiểm tra định dạng email
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (email == null || email.isEmpty()) {
            showError(emailField, emailError, "Please enter your email!");
            return false;
        }

        return email.matches(emailRegex);
    }

    public boolean validatePassword(String newPassword, String confirmPassword) {
        if (newPassword.isEmpty()) {
            showError(newPasswordField, newPasswordError, "Please enter your new password");
            return false;
        }

        if (newPassword.length() < 8) {
            showError(newPasswordField, newPasswordError, "New password must be at least 8 character");
            return false;
        }

        if (confirmPassword.isEmpty()) {
            showError(confirmPasswordField, confirmPasswordError, "Please confirm your password");
            return false;
        }

        if (confirmPassword.length() < 8) {
            showError(confirmPasswordField, confirmPasswordError, "New password must be at least 8 character");
            return false;
        }

        if (!newPassword.equals(confirmPassword)) {
            showError(confirmPasswordField, confirmPasswordError, "Passwords do not match. Please try again");
            return false;
        }

        return true;
    }

    public static String generateOTP() {
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            otp.append(CHARACTERS.charAt(index));
        }
        return otp.toString();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void initialize() {
        slideShow();
        loginForm.setVisible(false);
        inputEmailForm.setVisible(false);
        resetPasswordForm.setVisible(false);
        verifyOtpButton.setDisable(true);

        slideBackground.setOnMouseClicked(event -> {
            if (!loginForm.isVisible() && !inputEmailForm.isVisible() && !resetPasswordForm.isVisible()) {
                loginForm.setVisible(true);
            }
        });

        Platform.runLater(() -> {
            // Lấy scene thông qua một node bất kỳ (ví dụ: slideBackground)
            Scene scene = slideBackground.getScene();
            if (scene != null) {
                scene.setOnKeyPressed(event -> {
                    if (!loginForm.isVisible() && !inputEmailForm.isVisible() && !resetPasswordForm.isVisible()) {
                        loginForm.setVisible(true);
                    }
                });
            }
        });

        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin(new ActionEvent(event.getSource(), event.getTarget()));
            }
        });
    }
}