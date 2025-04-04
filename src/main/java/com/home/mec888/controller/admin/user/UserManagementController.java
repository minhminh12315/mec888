package com.home.mec888.controller.admin.user;

import com.home.mec888.dao.RoleDao;
import com.home.mec888.entity.Role;
import com.home.mec888.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.*;
import java.sql.*;

import com.home.mec888.dao.UserDao;
import com.home.mec888.util.SceneSwitcher;
import javafx.util.Callback;

public class UserManagementController {
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
    private TableColumn<User, String> roleColumn;

    @FXML
    private TableColumn<User, Void> actionColumn;

    private UserDao userDao;
    private RoleDao roleDao;

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
//        roleColumn.setCellFactory(column -> new TableCell<User, String>() {
//            @Override
//            protected void updateItem(String roleId, boolean empty) {
//                super.updateItem(roleId, empty);
//
//                if (empty || roleId == null) {
//                    setText(null);
//                } else {
//                    try {
//                        // Convert roleId to Long for lookup
//                        Long id = Long.valueOf(roleId);
//                        // Lookup role name from roleDao
//                        Role role = roleDao.getRoleById(id);
//                        if (role != null) {
//                            setText(role.getName());
//                        } else {
//                            setText("Unknown");
//                        }
//                    } catch (NumberFormatException e) {
//                        setText("Invalid");
//                    }
//                }
//            }
//        });
        // Load data from the database
        userManagementTable.getItems().clear();
        userManagementTable.getItems().addAll(userDao.getAllUsers());
    }

    private void addButtonToTable() {
        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<>() {

                    private final Button updateButton = new Button("Update");
                    private final Button deleteButton = new Button("Delete");

                    {
                        updateButton.setOnAction((ActionEvent event) -> {
                            User user = getTableView().getItems().get(getIndex());
                            handleUpdate(user, event);
                        });
                        deleteButton.setOnAction((ActionEvent event) -> {
                            User user = getTableView().getItems().get(getIndex());
                            handleDelete(user);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(updateButton, deleteButton);
                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }

    @FXML
    private void handleUpdate(User user, ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/home/mec888/admin/user/user-update.fxml"));
            Parent root = loader.load();

            UserUpdateController userUpdateController = loader.getController();
            userUpdateController.setUser(user);

            SceneSwitcher.loadViewWithPreloadedRoot(root, actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
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
