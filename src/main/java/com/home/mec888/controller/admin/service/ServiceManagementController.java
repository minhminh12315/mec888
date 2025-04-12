package com.home.mec888.controller.admin.service;

import com.home.mec888.controller.admin.medicine.MedicineUpdateController;
import com.home.mec888.dao.ServiceDao;
import com.home.mec888.entity.Doctor;
import com.home.mec888.entity.Medicine;
import com.home.mec888.entity.Service;
import com.home.mec888.util.SceneSwitcher;
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

public class ServiceManagementController {
    @FXML
    public TableView<Service> serivceManagementTable;

    @FXML
    public TableColumn<Service, Integer> idColumn;

    @FXML
    public TableColumn<Service, String> nameColumn;

    @FXML
    public TableColumn<Service, String> descriptionColumn;

    @FXML
    public TableColumn<Service, Double> priceColumn;

    @FXML
    private TableColumn<Service, Void> actionColumn;

    @FXML
    public TextField searchField;

    private ServiceDao serviceDao;
    private List<Service> originalServiceList;

    @FXML
    public void initialize() {
        serviceDao = new ServiceDao();
        loadServiceData();
        addButtonToTable();

        originalServiceList = serviceDao.getAllServices();

        updateTable(originalServiceList);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterServiceList(newValue);
        });
    }

    private void loadServiceData() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        serivceManagementTable.getItems().clear();
        List<Service> services = serviceDao.getAllServices();

        serivceManagementTable.getItems().addAll(services);
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
                    Service service = getTableView().getItems().get(getIndex());
                    setActionHandlers(actionBox, service);
                    setGraphic(actionBox);
                }
            }
        });
    }

    private void setActionHandlers(HBox actionBox, Service service) {
        FontIcon editIcon = (FontIcon) actionBox.getChildren().get(0);
        FontIcon trashIcon = (FontIcon) actionBox.getChildren().get(1);

        editIcon.setOnMouseClicked(event -> handleUpdate(service));
        trashIcon.setOnMouseClicked(event -> handleDelete(service));
    }

    @FXML
    private void handleUpdate(Service service) {
        FXMLLoader loader = SceneSwitcher.loadViewToUpdate("admin/service/service-update.fxml");
        if (loader != null) {
            ServiceUpdateController controller = loader.getController();
            controller.setService(service);

            Parent newView = loader.getRoot();
            AnchorPane anchorPane = (AnchorPane) serivceManagementTable.getScene().getRoot();
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

    private void handleDelete(Service service) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete this service?");
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
                serviceDao.deleteService(service.getId());
                loadServiceData();
            }
        });

        // Implement the delete logic here
        serviceDao.deleteService(service.getId());
        loadServiceData();
    }

    @FXML
    private void handleAdd(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/service/service-add.fxml", actionEvent);
    }

    private void updateTable(List<Service> services) {
        // Chuyển từ List sang ObservableList để hiển thị trong TableView
        serivceManagementTable.setItems(FXCollections.observableArrayList(services));
    }

    private void filterServiceList(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            // Nếu không có từ khóa, hiển thị danh sách đầy đủ
            updateTable(originalServiceList);
            return;
        }

        // Tạo danh sách lọc
        List<Service> filteredList = new ArrayList<>();
        for (Service service : originalServiceList) {
            if (service.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    service.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(service);
            }
        }

        // Cập nhật bảng với danh sách lọc
        updateTable(filteredList);
    }
}
