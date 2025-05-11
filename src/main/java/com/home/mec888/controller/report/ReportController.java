package com.home.mec888.controller.report;

import com.home.mec888.controller.doctor.appointment.SeeADoctorContainerController;
import com.home.mec888.entity.Appointment;
import com.home.mec888.entity.Patient;
import com.home.mec888.util.DBConnection;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ReportController {
    public static void handleReport(String fxmlFile) {
        Appointment a = SeeADoctorContainerController.currentAppointment;
        HashMap<String, Object> map = new HashMap<>();
        map.put("getReport", 401L);
        try {
            Connection conn = DBConnection.getInstance().getConn();
            InputStream input = ReportController.class.getResourceAsStream("/com/home/mec888/reports/" + fxmlFile);
            JasperDesign design = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);
            if (jasperPrint.getPages().isEmpty()) {
                showNoDataMessage();
            } else {
                // 5. Hiển thị report
                JasperViewer.viewReport(jasperPrint, false);
            }
        } catch (JRException | SQLException | ClassNotFoundException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    private static void showNoDataMessage() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Báo cáo");
            alert.setHeaderText(null);
            alert.setContentText("Không có dữ liệu để hiển thị báo cáo.");
            alert.showAndWait();
        });
    }
}
