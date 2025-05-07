package com.home.mec888.controller.admin.service;

import com.home.mec888.dao.RoomDao;
import com.home.mec888.dao.ServiceDao;
import com.home.mec888.entity.Room;
import com.home.mec888.entity.Service;
import com.home.mec888.util.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.util.List;

public class ServiceUpdateController {
    @FXML
    public TextField nameField;

    @FXML
    public TextArea descriptionField;

    @FXML
    public TextField priceField;

    @FXML
    public ComboBox<Room> roomComboBox;

    @FXML
    public Label nameErrorLabel;

    @FXML
    public Label descriptionErrorLabel;

    @FXML
    public Label priceErrorLabel;

    @FXML
    public Label roomErrorLabel;

    @FXML
    public Button saveButton, clearButton, backButton;

    private ServiceDao serviceDao;
    private RoomDao roomDao;
    private Service service;

    @FXML
    private void initialize() {
        serviceDao = new ServiceDao();
        roomDao = new RoomDao();
        loadRooms();
    }

    private void loadRooms() {
        List<Room> rooms = roomDao.getAllRooms();
        roomComboBox.setItems(FXCollections.observableArrayList(rooms));

        // Add an option for no room (null)
        Room noRoom = new Room();
        noRoom.setId(null);
        noRoom.setRoomNumber("-- No Room --");
        roomComboBox.getItems().add(0, noRoom);

        // Set up the cell factory to show only room number
        roomComboBox.setCellFactory(cell -> new ListCell<Room>() {
            @Override
            protected void updateItem(Room room, boolean empty) {
                super.updateItem(room, empty);
                if (empty || room == null) {
                    setText(null);
                } else {
                    setText(room.getRoomNumber());
                }
            }
        });

        // Set up the string converter for the selected value display
        roomComboBox.setConverter(new StringConverter<Room>() {
            @Override
            public String toString(Room room) {
                if (room == null) {
                    return null;
                }
                return room.getRoomNumber();
            }

            @Override
            public Room fromString(String string) {
                // This method is needed for the converter but not used in this context
                return null;
            }
        });
    }

    @FXML
    private void handleUpdate(ActionEvent actionEvent) {
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String priceText = priceField.getText().trim();
        Room selectedRoom = roomComboBox.getValue();

        boolean valid = true;
        double price = 0.0;

        // Validate fields
        if (name.isEmpty()) {
            nameErrorLabel.setText("Service name is required.");
            nameErrorLabel.setVisible(true);
            valid = false;
        } else {
            Service existingService = serviceDao.getServiceByName(name);
            if (existingService != null && !existingService.getId().equals(service.getId())) {
                nameErrorLabel.setText("Service name already exists.");
                nameErrorLabel.setVisible(true);
                valid = false;
            } else {
                nameErrorLabel.setVisible(false);
            }
        }

        if (description.isEmpty()) {
            descriptionErrorLabel.setText("Description is required.");
            descriptionErrorLabel.setVisible(true);
            valid = false;
        } else {
            descriptionErrorLabel.setVisible(false);
        }

        if (priceText.isEmpty()) {
            priceErrorLabel.setText("Price is required.");
            priceErrorLabel.setVisible(true);
            valid = false;
        } else if (!priceText.matches("\\d+(\\.\\d+)?")) {
            priceErrorLabel.setText("Price must be a number.");
            priceErrorLabel.setVisible(true);
            valid = false;
        } else {
            try {
                price = Double.parseDouble(priceText);
                if (price <= 0) {
                    priceErrorLabel.setText("Price must be a positive number.");
                    priceErrorLabel.setVisible(true);
                    valid = false;
                } else {
                    priceErrorLabel.setVisible(false);
                }
            } catch (NumberFormatException e) {
                priceErrorLabel.setText("Invalid price format.");
                priceErrorLabel.setVisible(true);
                valid = false;
            }
        }

        if (!valid) {
            return; // If there are errors, don't update
        }

        service.setName(name);
        service.setDescription(description);
        service.setPrice(price);

        // Set room ID (null if "No Room" is selected)
        if (selectedRoom != null && selectedRoom.getId() != null) {
            service.setRoom(selectedRoom);
        } else {
            service.setRoom(null);
        }

        serviceDao.updateService(service);
        showAlert("Successful", "Updated service successfully", Alert.AlertType.INFORMATION);
        returnToServiceManagement(actionEvent);
    }

    public void setService(Service service) {
        this.service = service;

        if (service != null) {
            nameField.setText(service.getName());
            descriptionField.setText(service.getDescription());
            priceField.setText(String.valueOf(service.getPrice()));

            // Select the appropriate room in the ComboBox
            if (service.getRoom().getId() != null) {
                for (Room room : roomComboBox.getItems()) {
                    if (room.getId() != null && room.getId().equals(service.getRoom().getId())) {
                        roomComboBox.getSelectionModel().select(room);
                        break;
                    }
                }
            } else {
                // Select "No Room" option
                roomComboBox.getSelectionModel().selectFirst();
            }
        }
    }

    public void handleClear(ActionEvent actionEvent) {
        if (service != null) {
            nameField.setText(service.getName());
            descriptionField.setText(service.getDescription());
            priceField.setText(String.valueOf(service.getPrice()));

            // Reset room selection to current value
            if (service.getRoom().getId() != null) {
                for (Room room : roomComboBox.getItems()) {
                    if (room.getId() != null && room.getId().equals(service.getRoom().getId())) {
                        roomComboBox.getSelectionModel().select(room);
                        break;
                    }
                }
            } else {
                roomComboBox.getSelectionModel().selectFirst();
            }
        }

        clearErrorLabels();
    }

    private void clearErrorLabels() {
        nameErrorLabel.setText("");
        nameErrorLabel.setVisible(false);
        descriptionErrorLabel.setText("");
        descriptionErrorLabel.setVisible(false);
        priceErrorLabel.setText("");
        priceErrorLabel.setVisible(false);
        roomErrorLabel.setText("");
        roomErrorLabel.setVisible(false);
    }

    @FXML
    public void handleBack(ActionEvent actionEvent) {
        returnToServiceManagement(actionEvent);
    }

    private void returnToServiceManagement(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/service/service-management.fxml", actionEvent);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}