package com.home.mec888.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.net.URL;

public class SceneSwitcher {

    // Phương thức ban đầu load FXML từ tên file

    // Phương thức switchTo nhận Parent đã load sẵn, sử dụng cho trường hợp cần truyền dữ liệu cho controller
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

            // Nếu chuyển sang login thì không cần tối đa hóa
            if (fxmlFile.equals("login/login.fxml")) {
                stage.setMaximized(false);
                stage.setFullScreen(false);
            } else {
                stage.setMaximized(true);
            }
            stage.show();
            stage.centerOnScreen();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void loadView(String fxmlFile, ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource("/com/home/mec888/" + fxmlFile));
        try {
            loader.load(); // Load FXML trước
            Parent newView = loader.getRoot();
            AnchorPane anchorPane = (AnchorPane) ((Node) event.getSource()).getScene().getRoot();
            BorderPane mainPane = (BorderPane) anchorPane.lookup("#mainBorderPane");
            if (mainPane != null) {
                mainPane.setCenter(newView);
            } else {
                System.err.println("BorderPane with ID 'mainBorderPane' not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FXMLLoader loadViewToUpdate(String fxmlFile) {
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