package com.home.mec888.controller.doctor.schedule;

import com.home.mec888.controller.IndexController;
import com.home.mec888.dao.DepartmentDao;
import com.home.mec888.dao.DoctorDao;
import com.home.mec888.dao.DoctorScheduleDao;
import com.home.mec888.dao.RoomDao;
import com.home.mec888.entity.Department;
import com.home.mec888.entity.Doctor;
import com.home.mec888.entity.DoctorSchedule;
import com.home.mec888.entity.Room;
import com.home.mec888.util.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;

import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class DoctorScheduleWeekController {
    @FXML
    public Text roomNumberField;
    @FXML
    public Text departmentNameField;
    @FXML
    public Text weekTitle;
    @FXML
    private GridPane scheduleGrid;

    private DoctorDao doctorDao;
    private DoctorScheduleDao doctorScheduleDao;
    private RoomDao roomDao;
    private DepartmentDao departmentDao;
    private Department currentDepartment;
    private Room currentRoom;
    private Doctor currentDoctor;

    private final String[] days = {"Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "Sun"};
    private final String[] shifts = {
            "Morning\n(07:00 - 13:00)",
            "Afternoon\n(13:00 - 19:00)",
            "Night\n(19:00 - 07:00)"};

    private final String[][] shiftTimes = {
            {"07:00:00", "13:00:00"},
            {"13:00:00", "19:00:00"},
            {"19:00:00", "07:00:00"}
    };

    private final Map<String, String> dayMap = new HashMap<>() {{
        put("Mon", "Monday");
        put("Tue", "Tuesday");
        put("Wed", "Wednesday");
        put("Thur", "Thursday");
        put("Fri", "Friday");
        put("Sat", "Saturday");
        put("Sun", "Sunday");
    }};

    @FXML
    public void initialize() {
        doctorDao = new DoctorDao();
        doctorScheduleDao = new DoctorScheduleDao();
        roomDao = new RoomDao();
        departmentDao = new DepartmentDao();

        Long doctorId = IndexController.user.getId();
        currentDoctor = doctorDao.getDoctorById(doctorId);
        currentRoom = currentDoctor.getRoom();
        currentDepartment = currentRoom.getDepartment();

        buildHeader();
        buildShiftLabels();
        buildShiftCells();
        setupTextFields();
    }

    public void setupTextFields() {
        try {
            //room number
            roomNumberField.setText(currentRoom.getRoomNumber());
            //department
            departmentNameField.setText(currentDepartment.getName());
            //month title
            String currentMonth = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            int currentYear = LocalDate.now().getYear();
            weekTitle.setText(currentMonth + " " + currentYear);        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildHeader() {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.minusDays(today.getDayOfWeek().getValue() - 1);

        StackPane firstBlockInGrid = createCell("Shift/Day", false, null);
        firstBlockInGrid.setStyle("-fx-font-weight: bold;");
        scheduleGrid.add(firstBlockInGrid, 0, 0);

        for (int col = 1; col <= days.length; col++) {
            LocalDate day = monday.plusDays(col - 1);
            String headerText = days[col - 1] + "\n(" + day.format(DateTimeFormatter.ofPattern("dd/MM")) + ")";
            StackPane cell = createCell(headerText, false, null);
            scheduleGrid.add(cell, col, 0);
        }
    }

    private void buildShiftLabels() {
        for (int row = 1; row <= shifts.length; row++) {
            StackPane cell = createCell(shifts[row - 1], false, null);
            scheduleGrid.add(cell, 0, row);
        }
    }

    private void buildShiftCells() {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.minusDays(today.getDayOfWeek().getValue() - 1);
        for (int row = 1; row <= shifts.length; row++) {
            for (int col = 1; col <= days.length; col++) {
                LocalDate workDate = monday.plusDays(col - 1);
                System.out.println("workDate: " + workDate);
                String dayOfWeek = days[col - 1];
                String startTime = shiftTimes[row - 1][0];
                String endTime = shiftTimes[row - 1][1];

                ShiftInfo info = new ShiftInfo(dayOfWeek, shifts[row - 1], startTime, endTime, workDate);
                StackPane cell = createCell("", true, info);
                scheduleGrid.add(cell, col, row);
            }
        }
    }

    private StackPane createCell(String content, boolean clickable, ShiftInfo shiftInfo) {
        StackPane cell = new StackPane();
        cell.getStyleClass().add("grid-cell");

        if (shiftInfo != null) {
            DoctorSchedule existingSchedule = doctorScheduleDao.findByDayAndShift(dayMap.get(shiftInfo.dayOfWeek), shiftInfo.startTime, shiftInfo.endTime, currentRoom, shiftInfo.workDate);
            System.out.println(existingSchedule);
            if (existingSchedule != null) {
                // Nếu bác sĩ hiện tại đã đăng ký
                if (existingSchedule.getDoctor().getId().equals(IndexController.user.getId())) {
                    cell.getStyleClass().add("your-shift");
                    clickable = false;// Style cho lịch của bác sĩ hiện tại
                } else {
                    cell.getStyleClass().add("taken-by-other-shift");
                    clickable = false;// Style cho lịch của bác sĩ khác
                }
            }
        }
        // Kiểm tra trạng thái của ô thông qua cơ sở dữ liệu

        Text text = new Text(content);
        if (content != null && (content.contains("Mon") || content.contains("Tue") || content.contains("Wed") ||
                content.contains("Thu") || content.contains("Fri") || content.contains("Sat") ||
                content.contains("Sun"))) {
            text.setStyle("-fx-font-weight: bold;"); // Đặt in đậm
        }
        if (content != null && (content.contains("Morning") || content.contains("Afternoon") || content.contains("Night"))) {
            text.setStyle("-fx-font-weight: bold;"); // Đặt in đậm
        }
        cell.getChildren().add(text);

        if (clickable && shiftInfo != null) {
            cell.setUserData(shiftInfo);

            cell.setOnMouseClicked((MouseEvent e) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to register for this shift?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    ShiftInfo info = (ShiftInfo) cell.getUserData();
                    System.out.println("Registering shift: " + info);

                    DoctorSchedule doctorSchedule = new DoctorSchedule();
                    Long doctorId = IndexController.user.getId();
                    Doctor doctor = doctorDao.getDoctorById(doctorId);
                    // Call your save logic here using currentDoctorId, info.dayOfWeek, info.startTime, info.endTime

                    doctorSchedule.setDoctor(doctor);
                    doctorSchedule.setDayOfWeek(dayMap.get(shiftInfo.dayOfWeek));
                    doctorSchedule.setStartTime(Time.valueOf(shiftInfo.startTime));
                    doctorSchedule.setEndTime(Time.valueOf(shiftInfo.endTime));
                    doctorSchedule.setWorkDate(shiftInfo.workDate);
                    doctorScheduleDao.save(doctorSchedule);

                    cell.getStyleClass().clear(); // Xóa các style hiện tại nếu cần
                    cell.getStyleClass().add("your-shift"); // Thêm style class mới
                    cell.setOnMouseClicked(null);
                    showAlert("Success", "Doctor added successfully!", Alert.AlertType.INFORMATION);

                    cell.getStyleClass().add("shift-registered-by-me");
                }
            });

        }

        return cell;
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleMonthSchedule(javafx.event.ActionEvent event) {
        SceneSwitcher.loadView("doctor/schedule/doctor-schedule-month.fxml", event);
    }

    public void handleWeekSchedule(javafx.event.ActionEvent event) {
        SceneSwitcher.loadView("doctor/schedule/doctor-schedule-week.fxml", event);
    }

    // ShiftInfo inner class
    public static class ShiftInfo {
        public LocalDate workDate;
        public String dayOfWeek;
        public String shift;
        public String startTime;
        public String endTime;

        public ShiftInfo(String dayOfWeek, String shift, String startTime, String endTime, LocalDate workDate) {
            this.dayOfWeek = dayOfWeek;
            this.shift = shift;
            this.startTime = startTime;
            this.endTime = endTime;
            this.workDate = workDate;
        }

        @Override
        public String toString() {
            return "ShiftInfo{" +
                    "dayOfWeek='" + dayOfWeek + '\'' +
                    ", shift='" + shift + '\'' +
                    ", startTime='" + startTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    '}';
        }
    }
}
