package com.home.mec888.controller.admin.medicine;

import com.home.mec888.entity.Medicine;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.*;
import java.sql.*;

import com.home.mec888.dao.MedicineDao;
import com.home.mec888.util.SceneSwitcher;
import javafx.util.Callback;

public class MedicineManagementController {
    @FXML
    private TableView<Medicine> medicineManagementTable;

    @FXML
    private TableColumn<Medicine, Integer> idColumn;

    @FXML
    private TableColumn<Medicine, String> nameColumn;

    @FXML
    private TableColumn<Medicine, String> descriptionColumn;

    @FXML
    private TableColumn<Medicine, String> priceColumn;

    @FXML
    private TableColumn<Medicine, String> manufacturerColumn;

    @FXML
    private TableColumn<Medicine, Void> actionColumn;

    private MedicineDao medicineDao;

    @FXML
    private void initialize() {
        medicineDao = new MedicineDao();
        loadMedicineData();
        addButtonToTable();
    }

    private void loadMedicineData() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

        // Load data from the database
        medicineManagementTable.getItems().clear();
//        for (Medicine medicine : medicineDao.getAllMedicines()) {
//            medicineManagementTable.getItems().add(medicine);
//        }
        medicineManagementTable.getItems().addAll(medicineDao.getAllMedicines());

    }

    private void addButtonToTable() {
        Callback<TableColumn<Medicine, Void>, TableCell<Medicine, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Medicine, Void> call(final TableColumn<Medicine, Void> param) {
                final TableCell<Medicine, Void> cell = new TableCell<>() {

                    private final Button updateButton = new Button("Update");
                    private final Button deleteButton = new Button("Delete");

                    {
                        updateButton.setOnAction((ActionEvent event) -> {
                            Medicine medicine = getTableView().getItems().get(getIndex());
                            handleUpdate(medicine, event);
                        });
                        deleteButton.setOnAction((ActionEvent event) -> {
                            Medicine medicine = getTableView().getItems().get(getIndex());
                            handleDelete(medicine);
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
    private void handleUpdate(Medicine medicine, ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/home/mec888/admin/medicine/medicine-update.fxml"));
            Parent root = loader.load();
            MedicineUpdateController medicineUpdateController = loader.getController();
            medicineUpdateController.setMedicine(medicine);
            SceneSwitcher.loadView("admin/medicine/medicine-update.fxml", actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDelete(Medicine medicine) {
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
                medicineDao.deleteMedicine(medicine.getId());
                loadMedicineData();
            }
        });

        // Implement the delete logic here
        medicineDao.deleteMedicine(medicine.getId());
        loadMedicineData();
    }

    @FXML
    private void handleAdd(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/medicine/medicine-add.fxml", actionEvent);
    }
}
