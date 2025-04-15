package com.home.mec888.controller.admin.room;

import com.home.mec888.entity.Room;
import com.home.mec888.dao.RoomDao;
import com.home.mec888.util.SceneSwitcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.*;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class RoomManagementController {
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Room> roomManagementTable;

    @FXML
    private TableColumn<Room, Long> idColumn;

    @FXML
    private TableColumn<Room, String> roomNumberColumn;

    @FXML
    private TableColumn<Room, String> roomTypeColumn;

    @FXML
    private TableColumn<Room, String> statusColumn;

    @FXML
    private TableColumn<Room, Void> actionColumn;
    @FXML
    private TableColumn<Room, String> departmentNameColumn;
    private RoomDao roomDao;

    @FXML
    private void initialize() {
        roomDao = new RoomDao();
        loadRoomData();
        addButtonToTable();
        // Listen for changes in the search field (when the user types)
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Call the search method when the user types in the search field
            System.out.println("searchr");
            handleSearch(newValue);
        });
    }
    private void handleSearch(String query) {
        // If the search query is empty, load all rooms
        if (query.isEmpty()) {
            loadRoomData();  // Load all rooms if the search field is empty
        } else {
            // Search for rooms by room number using LIKE
            List<Room> rooms = roomDao.getRoomByRoomNumber(query); // Get a list of rooms based on the query
            roomManagementTable.getItems().clear(); // Clear current table data

            if (rooms != null && !rooms.isEmpty()) {
                roomManagementTable.getItems().addAll(rooms); // Add all found rooms to the table
            } else {
                // Handle case when no rooms are found, e.g., show an alert
                System.out.println("No rooms found with the given search query.");
            }
        }
    }

    private void loadRoomData() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        departmentNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDepartment().getName()));
        // Load data from the database
        roomManagementTable.getItems().clear();
        List<Room> rooms = roomDao.getAllRooms();
        if(rooms == null){
            System.out.println("ListRoomData NULL");
        }
        if (rooms != null) {
            System.out.println("ListRoomData");
            roomManagementTable.getItems().addAll(rooms);
        }
    }

    private void addButtonToTable() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final HBox actionBox = new HBox(10);
            private final FontIcon editIcon = new FontIcon(FontAwesomeSolid.EDIT);
            private final FontIcon deleteIcon = new FontIcon(FontAwesomeSolid.TRASH_ALT);

            {
                // Set icon size and color
                editIcon.setIconSize(20);
                editIcon.setIconColor(Paint.valueOf("#4CAF50")); // Green for "Edit"

                deleteIcon.setIconSize(20);
                deleteIcon.setIconColor(Paint.valueOf("#F44336")); // Red for "Delete"

                actionBox.getChildren().addAll(editIcon, deleteIcon);
                actionBox.setAlignment(Pos.CENTER); // Center align HBox
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Room room = getTableView().getItems().get(getIndex());
                    setActionHandlers(actionBox, room);
                    setGraphic(actionBox);
                }
            }
        });
    }

    private void setActionHandlers(HBox actionBox, Room room) {
        FontIcon editIcon = (FontIcon) actionBox.getChildren().get(0);
        FontIcon trashIcon = (FontIcon) actionBox.getChildren().get(1);

        editIcon.setOnMouseClicked(event -> handleUpdate(room));
        trashIcon.setOnMouseClicked(event -> handleDelete(room));
    }

    @FXML
    private void handleUpdate(Room room) {
        FXMLLoader loader = SceneSwitcher.loadViewToUpdate("admin/room/room-update.fxml");
        if (loader != null) {
            RoomUpdateController controller = loader.getController();
            controller.setRoom(room);

            Parent newView = loader.getRoot();
            AnchorPane anchorPane = (AnchorPane) roomManagementTable.getScene().getRoot();
            BorderPane mainPane = (BorderPane) anchorPane.lookup("#mainBorderPane");

            if (mainPane != null) {
                mainPane.setCenter(newView);
            } else {
                System.err.println("BorderPane with ID 'mainBorderPane' not found");
            }
        } else {
            System.err.println("Could not load room-update.fxml");
        }
    }

    private void handleDelete(Room room) {
        // Show a confirmation dialog before deleting
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete this room?");
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
                roomDao.deleteRoom(room.getId());
                loadRoomData();
            }
        });
    }

    @FXML
    private void handleAdd(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/room/room-add.fxml", actionEvent);
    }
}
