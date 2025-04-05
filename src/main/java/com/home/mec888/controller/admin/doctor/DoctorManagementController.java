package com.home.mec888.controller.admin.doctor;

import com.home.mec888.controller.admin.department.DepartmentUpdateController;
import com.home.mec888.dao.DepartmentDao;
import com.home.mec888.dao.DoctorDao;
import com.home.mec888.dao.UserDao;
import com.home.mec888.entity.Department;
import com.home.mec888.entity.Doctor;
import com.home.mec888.entity.User;
import com.home.mec888.util.SceneSwitcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;
import java.util.List;

public class DoctorManagementController {
    @FXML
    public TableView<Doctor> doctorManagementTable;
    @FXML
    public TableColumn<Doctor, String> idColumn;
    @FXML
    public TableColumn<Doctor, String> userIdColumn;
    @FXML
    public TableColumn<Doctor, String> specializationColumn;
    @FXML
    public TableColumn<Doctor, String> departmentIdColumn;
    @FXML
    public TableColumn<Doctor, String> licenseNumberColumn;
    @FXML
    public TableColumn<Doctor, Void> actionColumn;
    @FXML
    public TextField searchField;
    @FXML
    public TableColumn<Doctor, String> firstNameColumn;
    @FXML
    public TableColumn<Doctor, String> lastNameColumn;
    private List<Doctor> originalDoctorList;
    private DoctorDao doctorDao;
    private UserDao userDao;
    private DepartmentDao departmentDao;

    @FXML
    public void initialize() {
        doctorDao = new DoctorDao();
        userDao = new UserDao();
        departmentDao = new DepartmentDao();
        loadDoctorData();
        addButtonToTable();

        originalDoctorList = doctorDao.getAllDoctors();
        // Cập nhật bảng với danh sách ban đầu
        updateTable(originalDoctorList);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterDoctorList(newValue);
        });
    }

    private void updateTable(List<Doctor> doctors) {
        // Chuyển từ List sang ObservableList để hiển thị trong TableView
        doctorManagementTable.setItems(FXCollections.observableArrayList(doctors));
    }

    private void filterDoctorList(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            // Nếu không có từ khóa, hiển thị danh sách đầy đủ
            updateTable(originalDoctorList);
            return;
        }

        // Tạo danh sách lọc
        List<Doctor> filteredList = new ArrayList<>();
        for (Doctor doctor : originalDoctorList) {
            if (doctor.getSpecialization().toLowerCase().contains(keyword.toLowerCase()) ||
                    doctor.getLicense_number().toLowerCase().contains(keyword.toLowerCase()) ||
                    doctor.getFirst_name().toLowerCase().contains(keyword.toLowerCase()) ||
                    doctor.getLast_name().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(doctor);
            }
        }

        // Cập nhật bảng với danh sách lọc
        updateTable(filteredList);
    }

    public void loadDoctorData() {
        // Setting up cell value factory for each column
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        userIdColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().getUser().getId() != null) {
                // Fetching the user by their ID using the DAO
                User user = userDao.getUserById(cellData.getValue().getUser().getId());
                if (user != null) {
                    return new SimpleStringProperty(user.getUsername());
                } else {
                    return new SimpleStringProperty("Unknown");
                }
            } else {
                return new SimpleStringProperty("Unknown");
            }
        });

        departmentIdColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().getDepartment().getId() != null) {
                // Fetching the user by their ID using the DAO
                Department department = departmentDao.getDepartmentById(cellData.getValue().getDepartment().getId());
                if (department != null) {
                    return new SimpleStringProperty(department.getName());
                } else {
                    return new SimpleStringProperty("Unknown");
                }
            } else {
                return new SimpleStringProperty("Unknown");
            }
        });


        specializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        licenseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("license_number"));


        // Clear existing items in the table
        doctorManagementTable.getItems().clear();
        List<Doctor> doctors = doctorDao.getAllDoctors();
        // Populate the table with data fetched from DoctorDao
        doctorManagementTable.getItems().addAll(doctors);
    }

    public void addButtonToTable() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final HBox actionBox = new HBox(10);

            private final FontIcon editIcon = new FontIcon(FontAwesomeSolid.EDIT);
            private final FontIcon deleteIcon = new FontIcon(FontAwesomeSolid.TRASH_ALT);

            {
                // Đặt kích thước và màu sắc cho các biểu tượng
                editIcon.setIconSize(20);
                editIcon.setIconColor(Paint.valueOf("#4CAF50")); // Màu xanh lá cho "Sửa"

                deleteIcon.setIconSize(20);
                deleteIcon.setIconColor(Paint.valueOf("#F44336")); // Màu đỏ cho "Xóa"

                actionBox.getChildren().addAll(editIcon, deleteIcon);
                actionBox.setAlignment(Pos.CENTER); // Căn giữa HBox
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Doctor doctor = getTableView().getItems().get(getIndex());
                    setActionHandlers(actionBox, doctor);
                    setGraphic(actionBox);
                }
            }
        });
    }

    private void setActionHandlers(HBox actionBox, Doctor doctor) {
        FontIcon editIcon = (FontIcon) actionBox.getChildren().get(0);
        FontIcon trashIcon = (FontIcon) actionBox.getChildren().get(1);

        editIcon.setOnMouseClicked(event -> handleUpdate(doctor));
        trashIcon.setOnMouseClicked(event -> handleDelete(doctor));
    }

    private void handleUpdate(Doctor doctor) {
        FXMLLoader loader = SceneSwitcher.loadViewToUpdate("admin/doctor/doctor-update.fxml");
        if (loader != null) {
            DoctorUpdateController controller = loader.getController();
            controller.setDoctor(doctor);

            Parent newView = loader.getRoot();
            AnchorPane anchorPane = (AnchorPane) doctorManagementTable.getScene().getRoot();
            BorderPane mainPane = (BorderPane) anchorPane.lookup("#mainBorderPane");

            if (mainPane != null) {
                mainPane.setCenter(newView);
            } else {
                System.err.println("BorderPane with ID 'mainBorderPane' not found");
            }
        } else {
            System.err.println("Could not load edit-showtime.fxml");
        }
    }

    private void handleDelete(Doctor doctor) {
        System.out.println(doctor);
        // Show a confirmation dialog before deleting
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete this medicine?");
        alert.setContentText("This action cannot be undone.");
        ButtonType confirmButton = new ButtonType("Delete");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirmButton, cancelButton);
        alert.showAndWait().ifPresent(response -> {
            if (response == cancelButton) {
                // User chose cancel, do nothing
                alert.close();
            } else if (response == confirmButton) {
                // User chose delete, proceed with deletion
                doctorDao.deleteDoctor(doctor.getId());
                loadDoctorData();
            }
        });
    }

    public void handleAdd(ActionEvent event) {
        SceneSwitcher.loadView("admin/doctor/doctor-add.fxml", event);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
