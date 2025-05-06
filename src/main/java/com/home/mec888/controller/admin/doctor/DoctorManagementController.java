package com.home.mec888.controller.admin.doctor;

import com.home.mec888.dao.DoctorDao;
import com.home.mec888.dao.RoomDao;
import com.home.mec888.dao.UserDao;
import com.home.mec888.entity.Doctor;
import com.home.mec888.entity.Room;
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
    public TableColumn<Doctor, String> firstNameColumn;
    @FXML
    public TableColumn<Doctor, String> lastNameColumn;
    @FXML
    public TableColumn<Doctor, String> specializationColumn;
    @FXML
    public TableColumn<Doctor, String> roomIdColumn;
    @FXML
    public TableColumn<Doctor, String> licenseNumberColumn;
    @FXML
    public TableColumn<Doctor, Void> actionColumn;
    @FXML
    public TextField searchField;

    private List<Doctor> originalDoctorList;
    private DoctorDao doctorDao;
    private UserDao userDao;
    private RoomDao roomDao;

    @FXML
    public void initialize() {
        doctorDao = new DoctorDao();
        userDao = new UserDao();
        roomDao = new RoomDao();
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
        doctorManagementTable.setItems(FXCollections.observableArrayList(doctors));
    }

    private void filterDoctorList(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            // If no keyword, show all doctors
            updateTable(originalDoctorList);
            return;
        }

        List<Doctor> filteredList = new ArrayList<>();
//        for (Doctor doctor : originalDoctorList) {
//            System.out.println("id cua user trong doctor " + doctor.getUser().getId());
//            User user = userDao.getUserById(doctor.getUser().getId());
//            if (
//                    (user.getFirstName() != null && user.getFirstName().toLowerCase().contains(keyword.toLowerCase())) ||
//                            (user.getLastName() != null && user.getLastName().toLowerCase().contains(keyword.toLowerCase())) ||
//                            (doctor.getSpecialization() != null && doctor.getSpecialization().getName().toLowerCase().contains(keyword.toLowerCase())) ||
//                            (doctor.getLicense_number() != null && doctor.getLicense_number().toLowerCase().contains(keyword.toLowerCase()))
//            ) {
//                filteredList.add(doctor);
//            }
//        }

        updateTable(filteredList);
    }

    public void loadDoctorData() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));


        userIdColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().getUser().getId() != null) {
                User user = userDao.getUserById(cellData.getValue().getUser().getId());
                if (user != null) {
                    return new SimpleStringProperty(String.valueOf(user.getId()));
                } else {
                    return new SimpleStringProperty("Unknown");
                }
            } else {
                return new SimpleStringProperty("Unknown");
            }
        });
        firstNameColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().getUser().getId() != null) {
                User user = userDao.getUserById(cellData.getValue().getUser().getId());
                if (user != null) {
                    return new SimpleStringProperty(user.getFirstName());
                } else {
                    return new SimpleStringProperty("Unknown");
                }
            } else {
                return new SimpleStringProperty("Unknown");
            }
        });
        lastNameColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().getUser().getId() != null) {
                User user = userDao.getUserById(cellData.getValue().getUser().getId());
                if (user != null) {
                    return new SimpleStringProperty(user.getLastName());
                } else {
                    return new SimpleStringProperty("Unknown");
                }
            } else {
                return new SimpleStringProperty("Unknown");
            }
        });

        roomIdColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() != null && cellData.getValue().getRoom().getId() != null) {
                Room room = roomDao.getRoomById(cellData.getValue().getRoom().getId());
                if (room != null) {
                    return new SimpleStringProperty(room.getRoomNumber());
                } else {
                    return new SimpleStringProperty("Unknown");
                }
            } else {
                return new SimpleStringProperty("Unknown");
            }
        });

        specializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        licenseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("license_number"));

        doctorManagementTable.getItems().clear();
        List<Doctor> doctors = doctorDao.getAllDoctors();
        doctorManagementTable.getItems().addAll(doctors);
    }

    public void addButtonToTable() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final HBox actionBox = new HBox(10);

            private final FontIcon editIcon = new FontIcon(FontAwesomeSolid.EDIT);
            private final FontIcon deleteIcon = new FontIcon(FontAwesomeSolid.TRASH_ALT);

            {
                editIcon.setIconSize(20);
                editIcon.setIconColor(Paint.valueOf("#4CAF50"));
                deleteIcon.setIconSize(20);
                deleteIcon.setIconColor(Paint.valueOf("#F44336"));

                actionBox.getChildren().addAll(editIcon, deleteIcon);
                actionBox.setAlignment(Pos.CENTER);
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
            }
        } else {
            System.err.println("Could not load doctor-update.fxml");
        }
    }

    private void handleDelete(Doctor doctor) {
        System.out.println(doctor);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete this doctor?");
        alert.setContentText("This action cannot be undone.");
        ButtonType confirmButton = new ButtonType("Delete");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirmButton, cancelButton);
        alert.showAndWait().ifPresent(response -> {
            if (response == cancelButton) {
                alert.close();
            } else if (response == confirmButton) {
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
