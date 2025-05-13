package com.home.mec888.controller.admin.dashboard;

import com.home.mec888.entity.Appointment;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Dashboard implements Initializable {
    @FXML
    private ComboBox<String> surveyTimeRangeComboBox;

    @FXML private AnchorPane revenueChartPane;
    @FXML private Label revenueAmountLabel;
    @FXML private Label revenueGrowthLabel;
    @FXML private Label revenueTrendLabel; // Thêm dòng này!
    @FXML private javafx.scene.shape.Circle hoverCardColor1;
    @FXML private javafx.scene.shape.Circle hoverCardColor2;

    @FXML private AnchorPane cardPane;
    @FXML private AnchorPane chartPane;
    @FXML private Label appointmentCount;
    @FXML private VBox hoverCard;
    @FXML private Label hoverCardDate;
    @FXML private Label hoverCardValue;
    @FXML private Label hoverCardValue_2;
    @FXML private AnchorPane cardPane2;
    @FXML private AnchorPane chartPane2;
    @FXML private Label newPatientCount;

    @FXML private AnchorPane cardPane3;
    @FXML private AnchorPane chartPane3;
    @FXML private Label earningAmount;

    @FXML private AnchorPane surveyPane;
    @FXML private AnchorPane surveyChartPane;
    @FXML private TableView<AppointmentView> appointmentTable;
    @FXML private TableColumn<AppointmentView, String> patientNameColumn;
    @FXML private TableColumn<AppointmentView, String> assignedDoctorColumn;
    @FXML private TableColumn<AppointmentView, LocalDate> dateColumn;
    @FXML private TableColumn<AppointmentView, String> diseaseColumn;
    @FXML
    private TableColumn<Appointment, Void> actionsColumn;
    private final Map<String, Integer> appointmentData = new LinkedHashMap<>();
    private final Map<String, Integer> newPatientsData = new LinkedHashMap<>();
    private final Map<String, Integer> earningsData = new LinkedHashMap<>();
    private final Map<String, Integer> oldPatientsData = new LinkedHashMap<>();
    private final LinkedHashMap<String, Integer> revenueData = new LinkedHashMap<>();
    private double ChartSurveyWidth;
    private double ChartSurveyHeight;
    private final LinkedHashMap<Appointment, String> appointmentMap = new LinkedHashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupAppointmentTable(); // cấu hình hiển thị bảng
        loadSampleData();
// Set mặc định nếu chưa chọn
        if (surveyTimeRangeComboBox.getValue() == null) {
            surveyTimeRangeComboBox.setValue("Daily");
        }

        // ✨ Lắng nghe sự kiện chọn ComboBox
        surveyTimeRangeComboBox.setOnAction(event -> {
            redrawSurveyChart(); // khi chọn lại gọi hàm vẽ lại
        });
        hoverCard.setVisible(true);
        hoverCard.setMouseTransparent(true);
        hoverCard.setOpacity(0);
        // Tháng 1 - January
        revenueData.put("10-01-2024", 600);
        revenueData.put("25-01-2024", 550);

// Tháng 2 - February
        revenueData.put("05-02-2024", 520);
        revenueData.put("20-02-2024", 510);

// Tháng 3 - March
        revenueData.put("10-03-2024", 500);
        revenueData.put("25-03-2024", 480);

// Tháng 4 - April
        revenueData.put("05-04-2024", 470);
        revenueData.put("20-04-2024", 450);

// Tháng 5 - May
        revenueData.put("10-05-2024", 430);
        revenueData.put("25-05-2024", 400);

// Tháng 6 - June
        revenueData.put("05-06-2024", 390);
        revenueData.put("20-06-2024", 370);

// Tháng 7 - July
        revenueData.put("10-07-2024", 360);
        revenueData.put("25-07-2024", 340);

// Tháng 8 - August
        revenueData.put("05-08-2024", 330);
        revenueData.put("20-08-2024", 300);




        // Dữ liệu mẫu
        appointmentData.put("17-07-2018", 40);
        appointmentData.put("18-07-2018", 55);
        appointmentData.put("19-07-2018", 45);
        appointmentData.put("20-07-2018", 70);
        appointmentData.put("21-07-2018", 52);
        appointmentData.put("22-07-2018", 80);
        appointmentData.put("23-07-2018", 50);
        appointmentData.put("24-07-2018", 65);



        earningsData.put("17-07-2018", 1500);
        earningsData.put("18-07-2018", 1700);
        earningsData.put("19-07-2018", 1600);
        earningsData.put("20-07-2018", 2100);
        earningsData.put("21-07-2018", 1800);
        earningsData.put("22-07-2018", 2500);
        earningsData.put("23-07-2018", 1900);
        earningsData.put("24-07-2018", 2200);

        appointmentCount.setText("650");
        newPatientCount.setText("129");
        earningAmount.setText("20125");
        // oldPatientsData
        oldPatientsData.put("17-07-2018", 10);
        oldPatientsData.put("18-07-2018", 168);
        oldPatientsData.put("19-08-2018", 15);
        oldPatientsData.put("20-08-2018", 22);
        oldPatientsData.put("21-09-2018", 17);
        oldPatientsData.put("22-09-2019", 26);
        oldPatientsData.put("23-10-2019", 23);
        oldPatientsData.put("24-11-2020", 28);

// newPatientsData
        newPatientsData.put("17-07-2018", 20);
        newPatientsData.put("18-07-2018", 335);
        newPatientsData.put("19-08-2018", 28);
        newPatientsData.put("20-08-2018", 45);
        newPatientsData.put("21-09-2018", 30);
        newPatientsData.put("22-09-2019", 55);
        newPatientsData.put("23-10-2019", 42);
        newPatientsData.put("24-11-2020", 60);

        calculateAndDisplayRevenueStats();
        Platform.runLater(() -> {


            PauseTransition wait = new PauseTransition(Duration.millis(200));
            wait.setOnFinished(event -> {
                double width1 = chartPane.getLayoutBounds().getWidth();
                double width2 = chartPane2.getLayoutBounds().getWidth();
                double width3 = chartPane3.getLayoutBounds().getWidth();

                drawSmoothChart("Appointments",chartPane, cardPane, appointmentData, width1, Color.web("#7D3EF0"));
                drawSmoothChart("New Patients",chartPane2, cardPane2, newPatientsData, width2, Color.web("#23A455"));
                drawSmoothChart("Earning",chartPane3,cardPane3, earningsData, width3, Color.web("#007BFF"));
                ChartSurveyWidth = surveyChartPane.getLayoutBounds().getWidth();
                ChartSurveyHeight = surveyPane.getLayoutBounds().getHeight();
                drawSmoothChartWithTwoLines(
                        surveyChartPane,
                        surveyPane,
                        newPatientsData,
                        oldPatientsData,
                        ChartSurveyWidth,
                        ChartSurveyHeight,
                        Color.web("#2D8CFF"),  // New Patients (xanh dương)
                        Color.web("#F57C00")   // Old Patients (cam)
                );
                drawBarChartRevenue();


            });
            wait.play();
        });

    }

    private void redrawSurveyChart() {

        drawSmoothChartWithTwoLines(
                surveyChartPane,
                surveyPane,
                newPatientsData,
                oldPatientsData,
                ChartSurveyWidth,
                ChartSurveyHeight,
                Color.web("#2D8CFF"),
                Color.web("#F57C00")
        );
    }

    private String getShortMonthName(int monthNumber) {
        return new java.text.DateFormatSymbols(Locale.ENGLISH).getShortMonths()[monthNumber - 1];
    }

    private void drawBarChartRevenue() {
        Platform.runLater(() -> {
            revenueChartPane.getChildren().clear();

            if (revenueData == null || revenueData.isEmpty()) return;

            // 1. Gom doanh thu theo tháng
            Map<String, Integer> monthRevenueMap = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> entry : revenueData.entrySet()) {
                String fullDate = entry.getKey(); // "10-01-2024"
                String[] parts = fullDate.split("-"); // parts[0] = day, parts[1] = month, parts[2] = year

                int monthNumber = Integer.parseInt(parts[1]);
                String year = parts[2];

                String monthName = getShortMonthName(monthNumber); // Gọi hàm convert
                String monthKey = monthName + " " + year; // Ví dụ: "Jan 2024"

                monthRevenueMap.put(monthKey, monthRevenueMap.getOrDefault(monthKey, 0) + entry.getValue());
            }


            String[] months = monthRevenueMap.keySet().toArray(new String[0]);
            Integer[] revenues = monthRevenueMap.values().toArray(new Integer[0]);

            int maxRevenue = Arrays.stream(revenues).max(Integer::compareTo).orElse(1);

            double width = revenueChartPane.getWidth();
            double chartHeight = revenueChartPane.getHeight();

            double usableHeight = chartHeight * 0.8;
            double baseY = (chartHeight - usableHeight) / 2;

            double barWidth = width / revenues.length * 0.6;
            double gap = width / revenues.length * 0.4;

            for (int i = 0; i < revenues.length; i++) {
                double percent = revenues[i] / (double) maxRevenue;
                double barHeight = percent * usableHeight;

                Rectangle bar = new Rectangle(barWidth, barHeight);
                bar.setArcWidth(6);
                bar.setArcHeight(6);
                bar.setFill(Color.web("#2D8CFF"));

                bar.setLayoutX(i * (barWidth + gap));
                bar.setLayoutY(baseY + (usableHeight - barHeight));

                final int index = i;

                bar.setOnMouseEntered(e -> {
                    hoverCardDate.setText(months[index]); // Tháng thay vì ngày
                    hoverCardValue.setText("Revenue : " + revenues[index]);
                    hoverCardColor1.setFill(Color.web("#2D8CFF"));
                    hoverCardColor2.setFill(Color.TRANSPARENT);

                    double sceneX = bar.localToScene(bar.getX() + bar.getWidth() / 2, bar.getY()).getX();
                    double sceneY = bar.localToScene(bar.getX() + bar.getWidth() / 2, bar.getY()).getY();

                    double localX = hoverCard.getParent().sceneToLocal(sceneX, sceneY).getX();
                    double localY = hoverCard.getParent().sceneToLocal(sceneX, sceneY).getY();

                    double offsetX = (localX < hoverCard.getParent().getLayoutBounds().getWidth() / 2)
                            ? localX + 20
                            : localX - hoverCard.getPrefWidth() - 20;
                    double offsetY = Math.max(0, localY - hoverCard.getPrefHeight() - 10);

                    hoverCard.setLayoutX(offsetX);
                    hoverCard.setLayoutY(offsetY);
                    hoverCard.setVisible(true);
                    hoverCard.setOpacity(1);
                    hoverCard.setMouseTransparent(true);
                    hoverCard.toFront();
                });

                bar.setOnMouseExited(e -> {
                    hoverCard.setVisible(false);
                    hoverCard.setOpacity(0);
                });

                revenueChartPane.getChildren().add(bar);
            }
        });
    }

    private void calculateAndDisplayRevenueStats() {
        if (revenueData == null || revenueData.isEmpty()) {
            revenueAmountLabel.setText("$0");
            revenueGrowthLabel.setText("N/A");
            revenueTrendLabel.setText("-");
            return;
        }

        Integer[] revenues = revenueData.values().toArray(new Integer[0]);

        // 1. Tính tổng doanh thu
        int totalRevenue = Arrays.stream(revenues).mapToInt(Integer::intValue).sum();
        revenueAmountLabel.setText(String.format("$%,d", totalRevenue)); // Format dấu phẩy

        // 2. Nếu chỉ có 1 dữ liệu thì không tính tăng trưởng
        if (revenues.length < 2) {
            revenueGrowthLabel.setText("N/A");
            revenueTrendLabel.setText("-");
            return;
        }

        // 3. Tính tăng trưởng trung bình
        double totalGrowth = 0;
        int count = 0;

        for (int i = 1; i < revenues.length; i++) {
            int previous = revenues[i - 1];
            int current = revenues[i];

            if (previous > 0) {
                double growth = (current - previous) / (double) previous;
                totalGrowth += growth;
                count++;
            }
        }

        double averageGrowth = count > 0 ? (totalGrowth / count) : 0;

        // 4. Set tỷ lệ tăng trưởng
        String formattedGrowth = String.format("%.1f%%", averageGrowth * 100);
        revenueGrowthLabel.setText((averageGrowth >= 0 ? "+" : "") + formattedGrowth);

        // 5. Set biểu tượng xu hướng và màu sắc
        if (averageGrowth >= 0) {
            revenueGrowthLabel.setStyle("-fx-text-fill: #2D8CFF; -fx-font-size: 14px; -fx-font-weight: bold;");
            revenueTrendLabel.setText("↑");
            revenueTrendLabel.setStyle("-fx-text-fill: #2D8CFF; -fx-font-size: 14px;");
        } else {
            revenueGrowthLabel.setStyle("-fx-text-fill: #FF4D4F; -fx-font-size: 14px; -fx-font-weight: bold;");
            revenueTrendLabel.setText("↓");
            revenueTrendLabel.setStyle("-fx-text-fill: #FF4D4F; -fx-font-size: 14px;");
        }
    }

    private void drawSmoothChart(
            String ChartName,
            AnchorPane chartPane,
            AnchorPane cardPane,
            Map<String, Integer> data,
            double maxWidth,
            Color baseColor
    ) {
        hoverCardValue.setText(null);
        hoverCardValue_2.setText(null);
        hoverCardColor1.setFill(Color.TRANSPARENT);
        hoverCardColor2.setFill(Color.TRANSPARENT);
        chartPane.getChildren().clear();

        SVGPath linePath = new SVGPath();
        SVGPath fillPath = new SVGPath();

        StringBuilder line = new StringBuilder();
        StringBuilder fill = new StringBuilder();

        int dataSize = data.size();
        double chartWidth = Math.min(maxWidth, 600);
        double xScale = chartWidth / (dataSize - 1);
        double baseY = cardPane.getHeight();
        double chartHeight = baseY*0.5;

        String[] dates = data.keySet().toArray(new String[0]);
        Integer[] values = data.values().toArray(new Integer[0]);
        int maxValue = Arrays.stream(values).max(Integer::compareTo).orElse(1);

        double y0 = baseY - (values[0] / (double) maxValue) * chartHeight;
        line.append("M 0 ").append(y0);
        fill.append("M 0 ").append(y0);

        for (int i = 1; i < dataSize; i++) {
            double x0 = (i - 1) * xScale;
            double yPrev = baseY - (values[i - 1] / (double) maxValue) * chartHeight;
            double x1 = i * xScale;
            double yCurr = baseY - (values[i] / (double) maxValue) * chartHeight;

            double cpx1 = x0 + xScale / 2.0;
            double cpy1 = yPrev;
            double cpx2 = x1 - xScale / 2.0;
            double cpy2 = yCurr;

            line.append(" C ")
                    .append(cpx1).append(" ").append(cpy1).append(", ")
                    .append(cpx2).append(" ").append(cpy2).append(", ")
                    .append(x1).append(" ").append(yCurr);
        }

        fill.append(line.substring(1));
        fill.append(" L ").append((dataSize - 1) * xScale).append(" ").append(baseY);
        fill.append(" L 0 ").append(baseY).append(" Z");

        linePath.setContent(line.toString());
        linePath.setStroke(baseColor);
        linePath.setStrokeWidth(2);
        linePath.setFill(null);

        fillPath.setContent(fill.toString());
        fillPath.setFill(new LinearGradient(
                0, 0, 0, 1, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, baseColor.deriveColor(0, 1, 1, 0.53)),
                new Stop(1, Color.TRANSPARENT)
        ));

        chartPane.getChildren().addAll(fillPath, linePath);

        for (int i = 0; i < dataSize; i++) {
            double x = i * xScale;
            double y = baseY - (values[i] / (double) maxValue) * chartHeight;

            Circle dot = new Circle(x, y, 4, baseColor);
            dot.setVisible(false);
            dot.setManaged(false);

            double topY = baseY - chartHeight * 1.1;  // cách trên một chút
            double bottomY = baseY - chartHeight * 0.1;  // không sát đáy

            Line verticalLine = new Line(x, topY, x, bottomY);

            verticalLine.setStroke(baseColor);
            verticalLine.setStrokeWidth(1);
            verticalLine.getStrokeDashArray().addAll(5.0, 5.0);
            verticalLine.setOpacity(0.5);
            verticalLine.setVisible(false);

            final int index = i;
            final Circle dotRef = dot;
            final Line vLineRef = verticalLine;

            Circle hoverZone = new Circle(x, y, 12); // rộng hơn để dễ hover
            hoverZone.setFill(Color.TRANSPARENT);
            hoverZone.setCursor(Cursor.HAND);
            hoverZone.setManaged(false);
            hoverZone.setMouseTransparent(false);

            // Delay khi rời khỏi dot
            PauseTransition hideDelay = new PauseTransition(Duration.millis(200));

            hoverZone.setOnMouseEntered(e -> {
                hideDelay.stop();

                hoverCardDate.setText(dates[index]);
                hoverCardValue.setText(ChartName + " : " + values[index]);
                hoverCardColor1.setFill(baseColor);

                double sceneX = dot.localToScene(dot.getCenterX(), dot.getCenterY()).getX();
                double sceneY = dot.localToScene(dot.getCenterX(), dot.getCenterY()).getY();
                double localX = hoverCard.getParent().sceneToLocal(sceneX, sceneY).getX();
                double localY = hoverCard.getParent().sceneToLocal(sceneX, sceneY).getY();

                double offsetX = (localX < hoverCard.getParent().getLayoutBounds().getWidth() / 2)
                        ? localX + 20
                        : localX - hoverCard.getPrefWidth() - 20;
                double offsetY = Math.max(0, localY - hoverCard.getPrefHeight() - 10);

                hoverCard.setLayoutX(offsetX);
                hoverCard.setLayoutY(offsetY);
                hoverCard.setVisible(true);
                hoverCard.setMouseTransparent(true); // để không chặn hover tiếp
                hoverCard.setOpacity(1);
                hoverCard.toFront();

                vLineRef.setVisible(true);
                dotRef.setVisible(true);
            });

            hoverZone.setOnMouseExited(e -> {
                hideDelay.setOnFinished(ev -> {
                    hoverCard.setVisible(false);
                    hoverCard.setMouseTransparent(true);
                    hoverCard.setOpacity(0);
                    vLineRef.setVisible(false);
                    dotRef.setVisible(false);
                    hoverCardValue.setText(null);
                    hoverCardValue_2.setText(null);
                });
                hideDelay.play();
            });

            chartPane.getChildren().addAll(dot, hoverZone);

            if (index != 0 && index != dataSize - 1) {
                chartPane.getChildren().add(verticalLine);
            }
        }
    }
    private Map<String, Integer> filterAndAggregateDataByRangeType(Map<String, Integer> originalData, String rangeType) {
        Map<String, Integer> aggregatedData = new LinkedHashMap<>();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (Map.Entry<String, Integer> entry : originalData.entrySet()) {
            String dateStr = entry.getKey();
            Integer value = entry.getValue();

            try {
                LocalDate date = LocalDate.parse(dateStr, inputFormatter);

                String key;
                switch (rangeType) {
                    case "Monthly":
                        key = String.format("%02d-%d", date.getMonthValue(), date.getYear()); // "08-2018"
                        break;
                    case "Yearly":
                        key = String.valueOf(date.getYear()); // "2019"
                        break;
                    case "Daily":
                    default:
                        key = dateStr; // giữ nguyên "19-08-2018"
                }

                // Nếu key đã tồn tại thì cộng dồn
                aggregatedData.put(key, aggregatedData.getOrDefault(key, 0) + value);

            } catch (Exception e) {
                System.err.println("Invalid date format: " + dateStr);
            }
        }

        return aggregatedData;
    }


    private void drawSmoothChartWithTwoLines(
            AnchorPane chartPane,
            AnchorPane cardPane,
            Map<String, Integer> data1,
            Map<String, Integer> data2,
            double maxWidth,
            double maxHeight,
            Color baseColor1,
            Color baseColor2
    ) {
        chartPane.getChildren().clear();

        String rangeType = surveyTimeRangeComboBox.getValue();
        if (rangeType == null) rangeType = "Daily";

// ✨ Lọc + cộng gộp theo yêu cầu
        Map<String, Integer> filteredData1 = filterAndAggregateDataByRangeType(data1, rangeType);
        Map<String, Integer> filteredData2 = filterAndAggregateDataByRangeType(data2, rangeType);


        String[] dates = filteredData1.keySet().toArray(new String[0]);
        Integer[] values1 = filteredData1.values().toArray(new Integer[0]);
        Integer[] values2 = filteredData2.values().toArray(new Integer[0]);
        int dataSize = dates.length;

        double xScale = maxWidth / (dataSize - 1);
        double baseY = maxHeight;
        double chartHeight = baseY * 0.9;

        int maxValue = Math.max(
                Arrays.stream(values1).max(Integer::compareTo).orElse(1),
                Arrays.stream(values2).max(Integer::compareTo).orElse(1)
        );

        // Giữ nguyên logic vẽ như cũ
        drawBothLinesWithUnifiedHover(
                chartPane,
                dates,
                values1,
                values2,
                xScale,
                chartHeight,
                baseY,
                maxValue,
                baseColor1,
                baseColor2
        );
    }


    // Tích hợp hover hiển thị cả 2 giá trị của 2 line tại x = xScale (cùng tọa độ X)
// Tách phần tạo điểm và hover ra xử lý tập trung cho cả hai line

    private void drawBothLinesWithUnifiedHover(
            AnchorPane chartPane,
            String[] dates,
            Integer[] values1,
            Integer[] values2,
            double xScale,
            double chartHeight,
            double baseY,
            int maxValue,
            Color color1,
            Color color2
    ) {
        chartPane.getChildren().clear();

        // Tạo đường line và fill path cho values1
        SVGPath path1 = new SVGPath();
        SVGPath fillPath1 = new SVGPath();
        StringBuilder line1 = new StringBuilder();
        StringBuilder fill1 = new StringBuilder();

        // Tạo đường line và fill path cho values2
        SVGPath path2 = new SVGPath();
        SVGPath fillPath2 = new SVGPath();
        StringBuilder line2 = new StringBuilder();
        StringBuilder fill2 = new StringBuilder();

        double y0_1 = baseY - (values1[0] / (double) maxValue) * chartHeight;
        double y0_2 = baseY - (values2[0] / (double) maxValue) * chartHeight;

        line1.append("M 0 ").append(y0_1);
        fill1.append("M 0 ").append(y0_1);

        line2.append("M 0 ").append(y0_2);
        fill2.append("M 0 ").append(y0_2);

        for (int i = 1; i < dates.length; i++) {
            double x0 = (i - 1) * xScale;
            double x1 = i * xScale;

            double yPrev1 = baseY - (values1[i - 1] / (double) maxValue) * chartHeight;
            double yCurr1 = baseY - (values1[i] / (double) maxValue) * chartHeight;

            double yPrev2 = baseY - (values2[i - 1] / (double) maxValue) * chartHeight;
            double yCurr2 = baseY - (values2[i] / (double) maxValue) * chartHeight;

            double cpx1 = x0 + xScale / 2.0;
            double cpx2 = x1 - xScale / 2.0;

            line1.append(" C ").append(cpx1).append(" ").append(yPrev1).append(", ")
                    .append(cpx2).append(" ").append(yCurr1).append(", ")
                    .append(x1).append(" ").append(yCurr1);

            line2.append(" C ").append(cpx1).append(" ").append(yPrev2).append(", ")
                    .append(cpx2).append(" ").append(yCurr2).append(", ")
                    .append(x1).append(" ").append(yCurr2);
        }

        // Tạo vùng fill cho cả hai đường
        fill1.append(line1.substring(1))
                .append(" L ").append((dates.length - 1) * xScale).append(" ").append(baseY)
                .append(" L 0 ").append(baseY).append(" Z");

        fill2.append(line2.substring(1))
                .append(" L ").append((dates.length - 1) * xScale).append(" ").append(baseY)
                .append(" L 0 ").append(baseY).append(" Z");

        // Thiết lập stroke và fill
        path1.setContent(line1.toString());
        path1.setStroke(color1);
        path1.setStrokeWidth(2);
        path1.setFill(null);

        path2.setContent(line2.toString());
        path2.setStroke(color2);
        path2.setStrokeWidth(2);
        path2.setFill(null);

        fillPath1.setContent(fill1.toString());
        fillPath1.setFill(new LinearGradient(
                0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, color1.deriveColor(0, 1, 1, 0.3)),
                new Stop(1, Color.TRANSPARENT)
        ));

        fillPath2.setContent(fill2.toString());
        fillPath2.setFill(new LinearGradient(
                0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, color2.deriveColor(0, 1, 1, 0.3)),
                new Stop(1, Color.TRANSPARENT)
        ));

        chartPane.getChildren().addAll(fillPath1, fillPath2, path1, path2);

        // Tạo hover zones
        for (int i = 0; i < dates.length; i++) {
            double x = i * xScale;
            double y1 = baseY - (values1[i] / (double) maxValue) * chartHeight;
            double y2 = baseY - (values2[i] / (double) maxValue) * chartHeight;

            Circle dot1 = new Circle(x, y1, 4, color1);
            Circle dot2 = new Circle(x, y2, 4, color2);
            dot1.setVisible(false);
            dot2.setVisible(false);

            Line verticalLine = new Line(x, baseY - chartHeight * 1, x, baseY - chartHeight * 0.1);
            verticalLine.setStroke(Color.GRAY);
            verticalLine.setStrokeWidth(1);
            verticalLine.getStrokeDashArray().addAll(4.0, 4.0);
            verticalLine.setVisible(false);

            Rectangle hoverZone = new Rectangle(x - 6, 0, 12, baseY);
            hoverZone.setFill(Color.TRANSPARENT);
            hoverZone.setCursor(Cursor.HAND);
            hoverZone.setMouseTransparent(false);

            final int index = i;
            PauseTransition hideDelay = new PauseTransition(Duration.millis(200));

            hoverZone.setOnMouseEntered(e -> {
                hideDelay.stop();
                hoverCardDate.setText(dates[index]);
                hoverCardColor1.setFill(color1);
                hoverCardColor2.setFill(color2);
                hoverCardValue.setText("New Patients: " + values1[index]);
                hoverCardValue_2.setText("Old Patients: " + values2[index]);

                double sceneX = dot1.localToScene(dot1.getCenterX(), dot1.getCenterY()).getX();
                double sceneY = dot1.localToScene(dot1.getCenterX(), dot1.getCenterY()).getY();

                double localX = hoverCard.getParent().sceneToLocal(sceneX, sceneY).getX();
                double localY = hoverCard.getParent().sceneToLocal(sceneX, sceneY).getY();

                double offsetX = (localX < hoverCard.getParent().getLayoutBounds().getWidth() / 2)
                        ? localX + 20
                        : localX - hoverCard.getPrefWidth() - 20;
                double offsetY = Math.max(0, localY - hoverCard.getPrefHeight() - 10);

                hoverCard.setLayoutX(offsetX);
                hoverCard.setLayoutY(offsetY);
                hoverCard.setVisible(true);
                hoverCard.setOpacity(1);
                hoverCard.toFront();

                dot1.setVisible(true);
                dot2.setVisible(true);
                verticalLine.setVisible(true);
            });

            hoverZone.setOnMouseExited(e -> {
                hideDelay.setOnFinished(ev -> {
                    hoverCard.setVisible(false);
                    hoverCard.setOpacity(0);
                    dot1.setVisible(false);
                    dot2.setVisible(false);
                    verticalLine.setVisible(false);
                    hoverCardValue.setText(null);
                    hoverCardValue_2.setText(null);
                    hoverCardColor1.setFill(null);
                    hoverCardColor2.setFill(null);
                });
                hideDelay.play();
            });
            drawAxis(chartPane, dates, maxValue, chartHeight, baseY, xScale);
            chartPane.getChildren().addAll(dot1, dot2, verticalLine, hoverZone);
        }
    }

    private void drawAxis(
            AnchorPane chartPane,
            String[] dates,
            int maxValue,
            double chartHeight,
            double baseY,
            double xScale
    ) {
        // Trục Oy – Vẽ các đường ngang và nhãn
        int stepY = 20;
        double chartWidth = (dates.length - 1) * xScale; // Tính tổng chiều rộng biểu đồ

        for (int value = 0; value <= maxValue; value += stepY) {
            double y = baseY - (value / (double) maxValue) * chartHeight;

            Line line = new Line(0, y, chartWidth, y); // Từ 0 đến cuối chart
            line.setStroke(Color.LIGHTGRAY);
            line.setStrokeWidth(0.5);
            line.getStrokeDashArray().setAll(2.0, 2.0); // Đường kẻ ngang nét đứt
            line.setOpacity(0.4);

            Label label = new Label(String.valueOf(value));
            label.setStyle("-fx-font-size: 10px; -fx-text-fill: #999;");
            label.setLayoutX(-20); // Đẩy nhãn ra ngoài mép
            label.setLayoutY(y - 7); // Căn giữa theo chiều cao

            chartPane.getChildren().addAll(line, label);
        }

        // Vẽ đường baseline Ox (full chiều rộng)
        Line axisX = new Line(0, baseY, chartWidth, baseY);
        axisX.setStroke(Color.LIGHTGRAY);
        axisX.setStrokeWidth(0.8);
        axisX.setOpacity(0.5);
        chartPane.getChildren().add(axisX);

        // Trục Ox – Vạch nhỏ và nhãn ngày tháng
        for (int i = 0; i < dates.length; i++) {
            double x = i * xScale;

            Line tick = new Line(x, baseY, x, baseY + 3); // Tick nhỏ hơn cho đẹp
            tick.setStroke(Color.GRAY);
            tick.setStrokeWidth(0.5);
            tick.setOpacity(0.5);

            String rawDate = dates[i];
            String formattedDate = formatDate(rawDate);

            Label dateLabel = new Label(formattedDate);
            dateLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #555;");
            dateLabel.setLayoutX(x - 18); // Căn chỉnh label cho gần giữa hơn
            dateLabel.setLayoutY(baseY + 6); // Đặt dưới tick

            chartPane.getChildren().addAll(tick, dateLabel);
        }
    }



    private String formatDate(String rawDate) {
        try {
            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH);
            LocalDate date = LocalDate.parse(rawDate, inputFormat);
            return outputFormat.format(date);
        } catch (Exception e) {
            return rawDate;
        }
    }

    private void loadSampleData() {
        Image avatar = new Image(getClass().getResourceAsStream("/asset/images/avatar.png")); // ✔ Đường dẫn đúng theo folder chị gửi

        ObservableList<AppointmentView> data = FXCollections.observableArrayList(
                new AppointmentView(avatar, "John Doe", "Dr. Jacob Ryan", LocalDate.of(2016, 5, 12), "Fever"),
                new AppointmentView(avatar, "Sarah Smith", "Dr. Rajesh", LocalDate.of(2016, 5, 12), "Cholera"),
                new AppointmentView(avatar, "Airi Satou", "Dr. Jay Soni", LocalDate.of(2016, 5, 12), "Jaundice"),
                new AppointmentView(avatar, "Angelica Ramos", "Dr. John Deo", LocalDate.of(2016, 5, 12), "Typhoid"),
                new AppointmentView(avatar, "Ashton Cox", "Dr. Megha Trivedi", LocalDate.of(2016, 5, 12), "Malaria"),
                new AppointmentView(avatar, "Cara Stevens", "Dr. Sarah Smith", LocalDate.of(2016, 5, 12), "Infection"),
                new AppointmentView(avatar, "Michael Bruce", "Dr. Anna Taylor", LocalDate.of(2016, 5, 12), "Pneumonia")
        );

        appointmentTable.setItems(data);
    }

    private void setupAppointmentTable() {
        // BÁC SĨ: hiển thị ảnh đại diện + tên bác sĩ trong cùng một ô
        assignedDoctorColumn.setCellFactory(col -> new TableCell<>() {
            private final HBox container = new HBox(8); // khoảng cách giữa ảnh và tên
            private final ImageView imgView = new ImageView();
            private final Label doctorLabel = new Label();

            {
                imgView.setFitWidth(36);
                imgView.setFitHeight(36);
                imgView.setClip(new Circle(18, 18, 18));
                container.getChildren().addAll(imgView, doctorLabel);
            }

            @Override
            protected void updateItem(String doctorName, boolean empty) {
                super.updateItem(doctorName, empty);

                int index = getIndex();
                if (empty || doctorName == null || index < 0 || index >= getTableView().getItems().size()) {
                    setGraphic(null);
                    return;
                }

                AppointmentView item = getTableView().getItems().get(index);
                if (item.getImage() == null) {
                    setGraphic(null);
                    return;
                }

                imgView.setImage(item.getImage());
                doctorLabel.setText(doctorName);
                doctorLabel.setStyle("-fx-text-fill: #007bff; -fx-underline: true;");
                setGraphic(container);
            }
        });
        assignedDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("doctorName"));

        // TÊN BỆNH NHÂN
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));

        // NGÀY: hiển thị theo định dạng dd/MM/yyyy
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setCellFactory(col -> new TableCell<>() {
            private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setText(empty || date == null ? null : fmt.format(date));
            }
        });

        // DISEASE: hiển thị label có màu theo loại bệnh
        diseaseColumn.setCellFactory(col -> new TableCell<>() {
            private final Label label = new Label();
            {
                label.setStyle("-fx-padding: 2 8; -fx-border-radius: 6; -fx-background-radius: 6; -fx-border-width: 1; -fx-font-size: 11px;");
            }

            @Override
            protected void updateItem(String dis, boolean empty) {
                super.updateItem(dis, empty);
                if (empty || dis == null) {
                    setGraphic(null);
                } else {
                    label.setText(dis);
                    switch (dis.toLowerCase()) {
                        case "fever":
                            label.setStyle(label.getStyle() + "-fx-border-color: #e74c3c; -fx-text-fill: #e74c3c;");
                            break;
                        case "cholera":
                            label.setStyle(label.getStyle() + "-fx-border-color: #2ecc71; -fx-text-fill: #2ecc71;");
                            break;
                        default:
                            label.setStyle(label.getStyle() + "-fx-border-color: gray; -fx-text-fill: gray;");
                    }
                    setGraphic(label);
                }
            }
        });
        diseaseColumn.setCellValueFactory(new PropertyValueFactory<>("disease"));
    }


}
