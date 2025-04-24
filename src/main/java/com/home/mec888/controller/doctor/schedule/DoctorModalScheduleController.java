package com.home.mec888.controller.doctor.schedule;

import com.home.mec888.controller.IndexController;
import com.home.mec888.dao.DoctorDao;
import com.home.mec888.dao.DoctorScheduleDao;
import com.home.mec888.entity.Doctor;
import com.home.mec888.entity.DoctorSchedule;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class DoctorModalScheduleController {
    @FXML
    public Rectangle overlay;
    @FXML
    public StackPane modalScheduleContainer;
    @FXML
    public VBox shiftContainer;
    @FXML
    public Label currentDay;

    private DoctorScheduleDao doctorScheduleDao;
    private DoctorDao doctorDao;
    private Doctor currentDoctor;
    private LocalDate chosenDate;
    private DoctorScheduleMonthController monthController;


    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) modalScheduleContainer.getScene().getWindow();

            // Bind StackPane để mở rộng theo cửa sổ
            modalScheduleContainer.prefWidthProperty().bind(stage.widthProperty());
            modalScheduleContainer.prefHeightProperty().bind(stage.heightProperty());

            // Overlay phủ kín toàn màn hình
            overlay.widthProperty().bind(modalScheduleContainer.widthProperty());
            overlay.heightProperty().bind(modalScheduleContainer.heightProperty());

            modalScheduleContainer.lookup("#overlay").setOnMouseClicked(event -> {
                AnchorPane root = (AnchorPane) modalScheduleContainer.getScene().getRoot();
                root.getChildren().remove(modalScheduleContainer);
            });
        });
        doctorScheduleDao = new DoctorScheduleDao();
        doctorDao = new DoctorDao();

        Long doctorId = IndexController.user.getId();
        currentDoctor = doctorDao.getDoctorById(doctorId);
    }

    public void setShift(List<DoctorSchedule> listShiftRegistered, LocalDate date, DoctorScheduleMonthController controller, boolean dateInPast, Doctor currentDoctor) {
        //monthController
        monthController = controller;
        //date title
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd/MM/yyyy", Locale.ENGLISH);
        String formattedDate = date.format(formatter);
        currentDay.setText(formattedDate);
        chosenDate = date;

        shiftContainer.getChildren().clear();

        addShiftRow("Morning", "07:00-13:00", listShiftRegistered, dateInPast, currentDoctor);
        addShiftRow("Afternoon", "13:00-19:00", listShiftRegistered, dateInPast, currentDoctor);
        addShiftRow("Night", "19:00-07:00", listShiftRegistered, dateInPast, currentDoctor);
    }

    private void addShiftRow(String shiftName, String time, List<DoctorSchedule> listShiftRegistered, boolean dateInPast, Doctor currentDoctor) {
        System.out.println("date in past" + dateInPast);
        HBox row = new HBox(8);
        row.setStyle("-fx-alignment: center;");

        VBox labelBox = new VBox();
        Label shiftLabel = new Label(shiftName);
        shiftLabel.setStyle("-fx-font-weight: 500; -fx-font-size: 18;");
        Text timeText = new Text("(" + time + ")");
        timeText.setFill(javafx.scene.paint.Color.web("#333333"));
        labelBox.getChildren().addAll(shiftLabel, timeText);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        DoctorSchedule registered = listShiftRegistered.stream().filter(s -> isSameShift(s, shiftName)).findFirst().orElse(null);

        if (registered != null) {
            if (registered.getDoctor().getId() == currentDoctor.getId() && dateInPast != true) {
                HBox cancelButtonContainer = new HBox(8);
                Text doctorName = new Text("You");
                cancelButtonContainer.setAlignment(Pos.CENTER);
                doctorName.setFill(javafx.scene.paint.Color.DARKRED);
                Button cancelButton = new Button("Cancel");
                cancelButton.getStyleClass().add("buttonCancelShift");
                cancelButton.setOnAction(e -> cancelWorkingSchedule(registered.getId()));
                cancelButtonContainer.getChildren().addAll(doctorName, cancelButton);
                row.getChildren().addAll(labelBox, spacer, cancelButtonContainer);
            } else {
                Text doctorName = new Text("Dr. " + registered.getDoctor().getUser().getFirstName());
                doctorName.setFill(javafx.scene.paint.Color.DARKRED);
                row.getChildren().addAll(labelBox, spacer, doctorName);
            }
        } else {
            Button registerButton = new Button("Register");
            registerButton.getStyleClass().add("buttonRegisterShift");
            if (dateInPast) {
                registerButton.setVisible(false);
            }
            registerButton.setOnAction(e -> showConfirmDialog(shiftName, time));
            row.getChildren().addAll(labelBox, spacer, registerButton);
        }

        shiftContainer.getChildren().add(row);
    }

    private void cancelWorkingSchedule(Long id) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Registration");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to cancel this working shift");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                doctorScheduleDao.deleteDoctorSchedule(id);
                showAlert("Success", "Doctor deleted successfully!", Alert.AlertType.INFORMATION);
                closeModal(new ActionEvent());
            }
        });
    }

    private boolean isSameShift(DoctorSchedule schedule, String shiftName) {
        LocalTime start = schedule.getStartTime().toLocalTime();
        LocalTime end = schedule.getEndTime().toLocalTime();

        switch (shiftName.toLowerCase()) {
            case "morning":
                return start.equals(LocalTime.of(7, 0)) && end.equals(LocalTime.of(13, 0));
            case "afternoon":
                return start.equals(LocalTime.of(13, 0)) && end.equals(LocalTime.of(19, 0));
            case "night":
                return start.equals(LocalTime.of(19, 0)) && end.equals(LocalTime.of(7, 0));
            default:
                return false;
        }
    }

    private void showConfirmDialog(String shift, String time) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Registration");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to register for the " + shift + " shift (" + time + ")?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String dayOfWeek = String.valueOf(chosenDate.getDayOfWeek());
                DoctorSchedule doctorSchedule = new DoctorSchedule();

                String[] timeParts = time.split("-"); // Tách bằng dấu "-"
                LocalTime startTime = LocalTime.parse(timeParts[0]); // Chuyển đổi thành LocalTime
                LocalTime endTime = LocalTime.parse(timeParts[1]);

                System.out.println("startTime: " + startTime);
                System.out.println("endTIme: " + endTime);


                doctorSchedule.setDoctor(currentDoctor);
                doctorSchedule.setWorkDate(chosenDate);
                doctorSchedule.setDayOfWeek(dayOfWeek);
                doctorSchedule.setStartTime(Time.valueOf(startTime));
                doctorSchedule.setEndTime(Time.valueOf(endTime));

                doctorScheduleDao.save(doctorSchedule);

                showAlert("Success", "Doctor added successfully!", Alert.AlertType.INFORMATION);
                closeModal(new ActionEvent());
            }
        });
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void closeModal(ActionEvent event) {
        AnchorPane root = (AnchorPane) modalScheduleContainer.getScene().getRoot();
        root.getChildren().remove(modalScheduleContainer);

        monthController.reloadSchedule();
    }
}