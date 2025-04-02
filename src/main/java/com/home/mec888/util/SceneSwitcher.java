package com.home.mec888.util;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.net.URL;

public class SceneSwitcher {

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
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(SceneSwitcher.class.getResource("/asset/css/an.css").toExternalForm());
            scene.getStylesheets().add(SceneSwitcher.class.getResource("/asset/css/minh.css").toExternalForm());
            scene.getStylesheets().add(SceneSwitcher.class.getResource("/asset/css/quan.css").toExternalForm());
            scene.getStylesheets().add(SceneSwitcher.class.getResource("/asset/css/cong.css").toExternalForm());
            scene.getStylesheets().add(SceneSwitcher.class.getResource("/asset/css/duong.css").toExternalForm());
            scene.getStylesheets().add(SceneSwitcher.class.getResource("/asset/css/main.css").toExternalForm());

            stage.setScene(scene);
            stage.setFullScreenExitHint("");

            if (fxmlFile.equals("login.fxml")) {
                stage.setMaximized(false);
                stage.setFullScreen(false);
            } else {
                stage.setMaximized(true);
            }
            stage.show();
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error switching scene: " + e.getMessage());
        }
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