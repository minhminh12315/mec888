package com.home.mec888.controller.admin.medicine;

import com.home.mec888.controller.admin.doctor.DoctorUpdateController;
import com.home.mec888.entity.Doctor;
import com.home.mec888.entity.Medicine;
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

import com.home.mec888.dao.MedicineDao;
import com.home.mec888.util.SceneSwitcher;
import javafx.util.Callback;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class MedicineManagementController {
    @FXML
    public TextField searchField;
    @FXML
    private TableView<Medicine> medicineManagementTable;

    @FXML
    private TableColumn<Medicine, Integer> idColumn;

    @FXML
    private TableColumn<Medicine, String> nameColumn;

    @FXML
    private TableColumn<Medicine , String> descriptionColumn;

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
                    Medicine medicine = getTableView().getItems().get(getIndex());
                    setActionHandlers(actionBox, medicine);
                    setGraphic(actionBox);
                }
            }
        });
    }
    private void setActionHandlers(HBox actionBox, Medicine medicine) {
        FontIcon editIcon = (FontIcon) actionBox.getChildren().get(0);
        FontIcon trashIcon = (FontIcon) actionBox.getChildren().get(1);

        editIcon.setOnMouseClicked(event -> handleUpdate(medicine));
        trashIcon.setOnMouseClicked(event -> handleDelete(medicine));
    }
    @FXML
    private void handleUpdate(Medicine medicine) {
        FXMLLoader loader = SceneSwitcher.loadViewToUpdate("admin/medicine/medicine-update.fxml");
        if (loader != null) {
            MedicineUpdateController controller = loader.getController();
            controller.setMedicine(medicine);

            Parent newView = loader.getRoot();
            AnchorPane anchorPane = (AnchorPane) medicineManagementTable.getScene().getRoot();
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
