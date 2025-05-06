package com.home.mec888.controller.doctor.appointment.modal;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DiagnosticTestModalController {
    @FXML
    public StackPane modalDiagnosticTest;
    @FXML
    public Rectangle overlay;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) modalDiagnosticTest.getScene().getWindow();

            // Bind StackPane để mở rộng theo cửa sổ
            modalDiagnosticTest.prefWidthProperty().bind(stage.widthProperty());
            modalDiagnosticTest.prefHeightProperty().bind(stage.heightProperty());

            // Overlay phủ kín toàn màn hình
            overlay.widthProperty().bind(modalDiagnosticTest.widthProperty());
            overlay.heightProperty().bind(modalDiagnosticTest.heightProperty());

            modalDiagnosticTest.lookup("#overlay").setOnMouseClicked(event -> {
                AnchorPane root = (AnchorPane) modalDiagnosticTest.getScene().getRoot();
                root.getChildren().remove(modalDiagnosticTest);
            });
        });
    }

    public void closeModal(ActionEvent event) {
        AnchorPane root = (AnchorPane) modalDiagnosticTest.getScene().getRoot();
        root.getChildren().remove(modalDiagnosticTest);

//        monthController.reloadSchedule();
    }
}
