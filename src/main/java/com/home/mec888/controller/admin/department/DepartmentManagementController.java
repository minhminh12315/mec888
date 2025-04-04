package com.home.mec888.controller.admin.department;

import com.home.mec888.dao.DepartmentDao;
import com.home.mec888.entity.Department;
import com.home.mec888.util.SceneSwitcher;
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

import java.io.IOException;
import java.util.List;

public class DepartmentManagementController {
    @FXML
    public TableView<Department> departmentManagementTable;
    @FXML
    public TableColumn<Department, Integer> idColumn;
    @FXML
    public TableColumn<Department, String> nameColumn;
    @FXML
    public TableColumn<Department, String> descriptionColumn;
    @FXML
    public TableColumn<Department, Void> actionColumn;

    private DepartmentDao departmentDao;

    @FXML
    private void initialize() {
        departmentDao = new DepartmentDao();
        loadDepartmentData();
        addButtonToTable();
    }

    public void loadDepartmentData() {
        System.out.println("--------------------");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        departmentManagementTable.getItems().clear();

        List<Department> departments = departmentDao.getAllDepartments();
        departmentManagementTable.getItems().addAll(departments);
        departmentManagementTable.getItems().addAll(departmentDao.getAllDepartments());

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
                    Department department = getTableView().getItems().get(getIndex());
                    setActionHandlers(actionBox, department);
                    setGraphic(actionBox);
                }
            }
        });
    }

    private void setActionHandlers(HBox actionBox, Department department) {
        FontIcon editIcon = (FontIcon) actionBox.getChildren().get(0);
        FontIcon trashIcon = (FontIcon) actionBox.getChildren().get(1);

        editIcon.setOnMouseClicked(event -> handleUpdate(department));
        trashIcon.setOnMouseClicked(event -> handleDelete(department));
    }

    private void handleUpdate(Department department) {
        FXMLLoader loader = SceneSwitcher.loadViewToUpdate("admin/department/department-update.fxml");
        if (loader != null) {
            DepartmentUpdateController controller = loader.getController();
            controller.setDepartment(department);

            Parent newView = loader.getRoot();
            AnchorPane anchorPane = (AnchorPane) departmentManagementTable.getScene().getRoot();
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

    private void handleDelete(Department department) {
        System.out.println(department);
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
                departmentDao.deleteDepartment(department.getId());
                loadDepartmentData();
            }
        });
    }

    public void handleAdd(ActionEvent event) {
        SceneSwitcher.loadView("admin/department/department-add.fxml", event);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
