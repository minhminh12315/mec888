package com.home.mec888.util;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.net.URL;

public class SceneSwitcher {

    // Phương thức ban đầu load FXML từ tên file
    public static void switchTo(Stage stage, String fxmlFile) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null.");
        }

        try {
            URL fxmlUrl = SceneSwitcher.class.getResource("/com/home/mec888/" + fxmlFile);
            if (fxmlUrl == null) {
                throw new IOException("FXML file not found: " + fxmlFile);
            }

            FXMLLoader fxmlLoader = new FXMLLoader(fxmlUrl);
            Parent root = fxmlLoader.load();
            switchTo(stage, root, fxmlFile); // Gọi phương thức switchTo đã được overload
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error switching scene: " + e.getMessage());
        }
    }

    // Phương thức switchTo nhận Parent đã load sẵn, sử dụng cho trường hợp cần truyền dữ liệu cho controller
    public static void switchTo(Stage stage, Parent root, String fxmlFile) {
        if (stage == null || root == null) {
            throw new IllegalArgumentException("Stage and root cannot be null.");
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneSwitcher.class.getResource("/asset/css/an.css").toExternalForm());
        scene.getStylesheets().add(SceneSwitcher.class.getResource("/asset/css/minh.css").toExternalForm());
        scene.getStylesheets().add(SceneSwitcher.class.getResource("/asset/css/quan.css").toExternalForm());
        scene.getStylesheets().add(SceneSwitcher.class.getResource("/asset/css/cong.css").toExternalForm());
        scene.getStylesheets().add(SceneSwitcher.class.getResource("/asset/css/duong.css").toExternalForm());
        scene.getStylesheets().add(SceneSwitcher.class.getResource("/asset/css/main.css").toExternalForm());

        stage.setScene(scene);
        stage.setFullScreenExitHint("");

        // Nếu chuyển sang login thì không cần tối đa hóa
        if (fxmlFile.equals("login.fxml")) {
            stage.setMaximized(false);
            stage.setFullScreen(false);
        } else {
            stage.setMaximized(true);
        }
        stage.show();
        stage.centerOnScreen();
    }

    public static FXMLLoader loadView(String fxmlFile) {
        FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource("/com/home/mec888/" + fxmlFile));
        try {
            loader.load(); // Load FXML trước
            return loader; // Trả về loader để lấy Controller
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}