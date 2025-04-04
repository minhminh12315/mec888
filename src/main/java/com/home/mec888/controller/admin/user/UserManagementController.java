package com.home.mec888.controller.admin.user;

import com.home.mec888.controller.admin.department.DepartmentUpdateController;
import com.home.mec888.dao.RoleDao;
import com.home.mec888.entity.Role;
import com.home.mec888.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.*;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.home.mec888.dao.UserDao;
import com.home.mec888.util.SceneSwitcher;
import javafx.util.Callback;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class UserManagementController {
    @FXML
    public TextField searchField;
    @FXML
    private TableView<User> userManagementTable;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> phoneColumn;

    @FXML
    private TableColumn<User, Integer> roleColumn;

    @FXML
    private TableColumn<User, Void> actionColumn;

    private UserDao userDao;
    private RoleDao roleDao;
    private List<User> originalUserList;

    @FXML
    private void initialize() {
        userDao = new UserDao();
        roleDao = new RoleDao();

        loadUserData();
        addButtonToTable();
    }

    private void loadUserData() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("roleId"));
        // Take roleId and get role name
        roleColumn.setCellFactory(column -> new TableCell<User, Integer>() {
            @Override
            protected void updateItem(Integer roleId, boolean empty) {
                super.updateItem(roleId, empty);

                if (empty || roleId == null) {
                    setText(null);
                } else {
                    try {
                        // Convert roleId to Long for lookup
                        Long id = roleId.longValue();
                        // Lookup role name from roleDao
                        Role role = roleDao.getRoleById(id);
                        if (role != null) {
                            setText(role.getName());
                        } else {
                            setText("Unknown");
                        }
                    } catch (Exception e) {
                        setText("Invalid");
                    }
                }
            }
        });
        // Load data from the database
        List<User> currentUsers = userDao.getAllUsers();
        userManagementTable.getItems().clear();
        userManagementTable.getItems().addAll(currentUsers);

        // Update the original list for filtering
        originalUserList = new ArrayList<>(currentUsers);

        // Set up search listener
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterUserList(newValue);
        });
    }

    private void updateTable(List<User> users) {
        // Chuyển từ List sang ObservableList để hiển thị trong TableView
        userManagementTable.setItems(FXCollections.observableArrayList(users));
    }

    private void filterUserList(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            // Nếu không có từ khóa, hiển thị danh sách đầy đủ
            updateTable(originalUserList);
            return;
        }

        // Tạo danh sách lọc
        // nhớ sửa dùng sql để lấy dữ liệu,
        List<User> filteredList = new ArrayList<>();
        for (User user : originalUserList) {
            if (user.getUsername().toLowerCase().contains(keyword.toLowerCase()) ||
                    user.getEmail().toLowerCase().contains(keyword.toLowerCase()) ||
                    user.getPhone().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(user);
            }
        }

        // Cập nhật bảng với danh sách lọc
        updateTable(filteredList);
        addButtonToTable();
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
                    User user = getTableView().getItems().get(getIndex());
                    setActionHandlers(actionBox, user);
                    setGraphic(actionBox);
                }
            }
        });
    }

    private void setActionHandlers(HBox actionBox, User user) {
        FontIcon editIcon = (FontIcon) actionBox.getChildren().get(0);
        FontIcon trashIcon = (FontIcon) actionBox.getChildren().get(1);

        editIcon.setOnMouseClicked(event -> handleUpdate(user));
        trashIcon.setOnMouseClicked(event -> handleDelete(user));
    }

    @FXML
    private void handleUpdate(User user) {
        FXMLLoader loader = SceneSwitcher.loadViewToUpdate("admin/user/user-update.fxml");
        if (loader != null) {
            UserUpdateController controller = loader.getController();
            controller.setUser(user);

            Parent newView = loader.getRoot();
            AnchorPane anchorPane = (AnchorPane) userManagementTable.getScene().getRoot();
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

    private void handleDelete(User user) {
        // Show a confirmation dialog before deleting
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete this user?");
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
                userDao.deleteUser(user.getId());
                loadUserData();
            }
        });
    }

    @FXML
    private void handleAdd(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/user/user-add.fxml", actionEvent);
    }
}
