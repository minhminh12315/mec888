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
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class DoctorScheduleMonthController {
    @FXML
    public Text monthTitle;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    private GridPane scheduleGrid;
    @FXML
    private Text roomNumberField;
    @FXML
    private Text departmentNameField;

    private DoctorDao doctorDao;
    private DoctorScheduleDao doctorScheduleDao;
    private RoomDao roomDao;
    private DepartmentDao departmentDao;
    private Department currentDepartment;
    private Room currentRoom;
    private Doctor currentDoctor;
    private Runnable updateMonthScheduleCallback;


    @FXML
    public void initialize() {
        // Khởi tạo các DAO
        doctorDao = new DoctorDao();
        doctorScheduleDao = new DoctorScheduleDao();
        roomDao = new RoomDao();
        departmentDao = new DepartmentDao();

        // Lấy thông tin bác sĩ và phòng ban hiện tại
        Long userId = IndexController.user.getId();
        currentDoctor = doctorDao.findDoctorByUserId(userId);
        if (currentDoctor != null) {
            currentRoom = currentDoctor.getRoom();
            currentDepartment = currentRoom.getDepartment();
        }
        // Hiển thị thông tin phòng và khoa
        setupTextFields();
        // Xây dựng lịch tháng
        buildCalendarForMonth(LocalDate.now());
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
            monthTitle.setText(currentMonth + " " + currentYear);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadSchedule() {
        buildCalendarForMonth(LocalDate.now());
    }

    private void buildCalendarForMonth(LocalDate monthStart) {
        // Lấy ngày đầu tiên và ngày cuối cùng của tháng
        LocalDate firstDayOfMonth = monthStart.withDayOfMonth(1);
        LocalDate lastDayOfMonth = monthStart.withDayOfMonth(monthStart.lengthOfMonth());

        // Tính tuần bắt đầu và kết thúc trong tháng
        LocalDate startOfWeek = firstDayOfMonth.minusDays(firstDayOfMonth.getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = lastDayOfMonth.plusDays(7 - lastDayOfMonth.getDayOfWeek().getValue());

        int row = 1; // Bắt đầu từ hàng thứ 1 (sau tiêu đề)

        // Xóa lịch trước đó
        scheduleGrid.getChildren().clear();

        // Xây dựng tiêu đề (tên ngày trong tuần)
        buildHeader();

        // Lặp qua từng ngày từ tuần bắt đầu đến tuần kết thúc
        LocalDate currentDay = startOfWeek;
        while (!currentDay.isAfter(endOfWeek)) {
            for (int col = 1; col <= 7; col++) { // 7 ngày trong tuần
                if (currentDay.getMonth().equals(monthStart.getMonth())) {
                    // Ô có ngày thuộc tháng hiện tại
                    StackPane cell = createCell(currentDay.format(DateTimeFormatter.ofPattern("d/M")), currentDay, true);
                    scheduleGrid.add(cell, col, row);
                } else {
                    // Ô không có ngày thuộc tháng hiện tại
                    StackPane cell = createCell("", currentDay, false);
                    cell.getStyleClass().add("disabled-cell"); // Gán style cho ô bị vô hiệu hóa
                    scheduleGrid.add(cell, col, row);
                }
                currentDay = currentDay.plusDays(1); // Tăng ngày
            }
            row++; // Tăng hàng
        }
    }

    private void buildHeader() {
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (int col = 1; col <= days.length; col++) {
            StackPane cell = createCell(days[col - 1], null, false);
            scheduleGrid.add(cell, col, 0);
        }
    }

    private StackPane createCell(String content, LocalDate date, boolean clickable) {
        StackPane cell = new StackPane();

        StackPane cellInside = new StackPane();

        boolean dateInPast;
        if (date != null && date.isBefore(LocalDate.now())) {
            dateInPast = true;
        } else {
            dateInPast = false;
        }

        Text text = new Text(content);
        if (content != null && (content.equals("Mon") || content.equals("Tue") || content.equals("Wed") || content.equals("Thu") || content.equals("Fri") || content.equals("Sat") || content.equals("Sun"))) {
            text.setStyle("-fx-font-weight: bold;");
            cell.getStyleClass().add("grid-cell-month-header");// Đặt in đậm
        } else {
            cellInside.getStyleClass().add("grid-cell-month");
        }
        cellInside.getChildren().add(text);

        List<DoctorSchedule> listShiftRegistered = doctorScheduleDao.findTodaySchedules(date);
        if (!listShiftRegistered.isEmpty()) {
            VBox badgeContainer = new VBox(4);
            badgeContainer.setAlignment(Pos.BOTTOM_CENTER);
            badgeContainer.setMaxWidth(Double.MAX_VALUE);
            VBox.setVgrow(badgeContainer, Priority.ALWAYS);
            VBox.setMargin(badgeContainer, new Insets(0, 8, 8, 8));

            for (DoctorSchedule doctorSchedule : listShiftRegistered) {
                String shiftName = getShiftName(doctorSchedule.getStartTime().toLocalTime(), doctorSchedule.getEndTime().toLocalTime());
                String doctorName = doctorSchedule.getDoctor().getUser().getFirstName() + " " + doctorSchedule.getDoctor().getUser().getLastName();

                HBox shiftBadge = new HBox(4);
                shiftBadge.setAlignment(Pos.CENTER_LEFT);
                shiftBadge.setMaxWidth(Double.MAX_VALUE);
                shiftBadge.setPrefWidth(cell.getPrefWidth()); // hoặc bind width

                Label doctorNameLabel = new Label(doctorName);
                doctorNameLabel.setMaxWidth(Double.MAX_VALUE);
                doctorNameLabel.setPrefWidth(180); // hoặc bind như trên
                doctorNameLabel.setStyle("-fx-text-overrun: ellipsis;");
                doctorNameLabel.setTooltip(new Tooltip(doctorName));

                shiftBadge.setStyle("-fx-padding:0 8;");
                shiftBadge.getChildren().addAll(getShiftIcon(shiftName), doctorNameLabel);
                shiftBadge.getStyleClass().add(getShiftStyle(shiftName));

                badgeContainer.getChildren().add(shiftBadge);
            }

            cellInside.getChildren().add(badgeContainer);
        }

        if (clickable && date != null) {
            // Gắn sự kiện click
            cell.setOnMouseClicked(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/home/mec888/doctor/schedule/doctor-modal-schedule.fxml"));
                    StackPane modalPane = loader.load();

                    DoctorModalScheduleController controller = loader.getController();
                    controller.setShift(listShiftRegistered, date, this, dateInPast, currentDoctor);

                    AnchorPane root = (AnchorPane) ((Node) event.getSource()).getScene().getRoot();
                    root.getChildren().add(modalPane);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        cell.getChildren().add(cellInside);
        if (dateInPast) {
            Region overlay = new Region();
            overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);");
            overlay.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            StackPane.setAlignment(overlay, Pos.CENTER);
            cell.getChildren().add(overlay);
        }

        return cell;
    }

    private String getShiftName(LocalTime startTime, LocalTime endTime) {
        if (startTime.equals(LocalTime.of(7, 0)) && endTime.equals(LocalTime.of(13, 0))) {
            return "Morning";
        } else if (startTime.equals(LocalTime.of(13, 0)) && endTime.equals(LocalTime.of(19, 0))) {
            return "Afternoon";
        } else if (startTime.equals(LocalTime.of(19, 0)) && endTime.equals(LocalTime.of(7, 0))) {
            return "Night";
        }
        return "Unknown";
    }

    private Node getShiftIcon(String shiftName) {
        FontIcon icon;

        switch (shiftName) {
            case "Morning":
//                icon.setIconLiteral("fas-sun");    // Biểu tượng mặt trời
                icon = new FontIcon(FontAwesomeSolid.SUN);
                break;
            case "Afternoon":
//                icon.setIconLiteral("fas-cloud");  // Biểu tượng mây
                icon = new FontIcon(FontAwesomeSolid.CLOUD);
                break;
            case "Night":
//                icon.setIconLiteral("fas-moon");   // Biểu tượng mặt trăng
                icon = new FontIcon(FontAwesomeSolid.MOON);
                break;
            default:
                icon = new FontIcon(FontAwesomeSolid.CIRCLE); // Biểu tượng dấu hỏi
                break;
        }
        icon.setIconSize(16);
        icon.setIconColor(Paint.valueOf("#000000"));
        return icon;
    }

    private String getShiftStyle(String shiftName) {
        switch (shiftName) {
            case "Morning":
                return "badgeMorningShift";     // Xanh lá
            case "Afternoon":
                return "badgeAfternoonShift";   // Xanh dương
            case "Night":
                return "badgeNightShift";       // Tím
            default:
                return "";
        }
    }

}