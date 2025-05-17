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

            {
                // Đặt kích thước và màu sắc cho các biểu tượng
                editIcon.setIconSize(20);
                editIcon.setIconColor(Paint.valueOf("#4CAF50")); // Màu xanh lá cho "Sửa"


                actionBox.getChildren().addAll(editIcon);
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

        editIcon.setOnMouseClicked(event -> handleUpdate(department));
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
