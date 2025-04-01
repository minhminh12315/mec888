package com.home.mec888;

import com.home.mec888.entity.User;
import com.home.mec888.util.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        // title
        stage.setTitle("Hello!");
        stage.setScene(scene);

        // css tung thang
        scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/duong.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/minh.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/quan.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/an.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/cong.css").toExternalForm());

        // Lấy độ phân giải màn hình
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // Đặt kích thước ứng dụng khớp với màn hình
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

        // Không dùng full-screen
        // stage.setFullScreen(true); // Không sử dụng dòng này
        stage.setFullScreen(false);
        stage.show();
    }

    public static void main(String[] args) {
        User u1 = new User("user5", "email5");


        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();

        session.save(u1);

        transaction.commit();

        // test logging by slf4j
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("User saved successfully");

        session.close();

        HibernateUtil.shutdown();
    }
}