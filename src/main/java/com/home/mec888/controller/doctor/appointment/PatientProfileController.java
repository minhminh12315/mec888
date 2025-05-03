package com.home.mec888.controller.doctor.appointment;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PatientProfileController {
    @FXML
    private HBox hboxLine1;

    @FXML
    public void initialize() {

    }

    private void applyHgrowToVBoxes(Parent root) {
        for (Node node : root.getChildrenUnmodifiable()) {
            if (node instanceof HBox hbox) {
                for (Node child : hbox.getChildren()) {
                    if (child instanceof VBox vbox) {
                        HBox.setHgrow(vbox, Priority.ALWAYS);
                        vbox.setMaxWidth(Double.MAX_VALUE);
                    }
                }
            } else if (node instanceof Parent parent) {
                applyHgrowToVBoxes(parent); // tiếp tục đệ quy nếu là container
            }
        }
    }

}
