package com.home.mec888;

import com.home.mec888.dao.AuditLogDao;
import com.home.mec888.dao.DepartmentDao;
import com.home.mec888.dao.MedicineDao;
import com.home.mec888.dao.PatientDao;
import com.home.mec888.dao.UserDao;
import com.home.mec888.entity.AuditLog;
import com.home.mec888.entity.Department;
import com.home.mec888.entity.Medicine;
import com.home.mec888.entity.Patient;
import com.home.mec888.entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/home/mec888/login/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        // title
        stage.setTitle("Hello!");
        stage.setScene(scene);

        // css tung thang
        scene.getStylesheets().add(getClass().getResource("/asset/css/main.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/asset/css/duong.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/asset/css/minh.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/asset/css/quan.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/asset/css/an.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/asset/css/cong.css").toExternalForm());


        // Lấy độ phân giải màn hình
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // Đặt kích thước ứng dụng khớp với màn hình
//        stage.setX(bounds.getMinX());
//        stage.setY(bounds.getMinY());
//        stage.setWidth(bounds.getWidth());
//        stage.setHeight(bounds.getHeight());

        // Không dùng full-screen
        // stage.setFullScreen(true); // Không sử dụng dòng này
        stage.setFullScreen(false);
        stage.show();
    }

    public static void main(String[] args) {

        launch();






    }
}