package com.home.mec888.controller.admin.medicine;

import com.home.mec888.entity.Medicine;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.home.mec888.dao.MedicineDao;
import com.home.mec888.util.SceneSwitcher;
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
    private TableColumn<Medicine, String> activeIngredientColumn;
    @FXML
    private TableColumn<Medicine, String> dosageColumn;
    @FXML
    private TableColumn<Medicine, String> unitColumn;
    @FXML
    private TableColumn<Medicine, String> formColumn;
    @FXML
    private TableColumn<Medicine, String> manufacturerCodeColumn;
    @FXML
    private TableColumn<Medicine, String> slCodeColumn;
    @FXML
    private TableColumn<Medicine, Double> priceColumn;
    @FXML
    private TableColumn<Medicine, String> expiryDateColumn;
    @FXML
    private TableColumn<Medicine, String> usageInstructionsColumn;
    @FXML
    private TableColumn<Medicine, String> manufacturerColumn;
    @FXML
    private TableColumn<Medicine, Void> actionColumn;

    private MedicineDao medicineDao;
    private List<Medicine> originalMedicineList;

    @FXML
    private void initialize() {
        medicineDao = new MedicineDao();
        loadMedicineData();
        addButtonToTable();

        originalMedicineList = medicineDao.getAllMedicines();

        updateTable(originalMedicineList);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterMedicineList(newValue);
        });
    }

    private void loadMedicineData() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        activeIngredientColumn.setCellValueFactory(new PropertyValueFactory<>("activeIngredient"));
        dosageColumn.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        formColumn.setCellValueFactory(new PropertyValueFactory<>("form"));
        manufacturerCodeColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturerCode"));
        slCodeColumn.setCellValueFactory(new PropertyValueFactory<>("slCode"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Format the date for display
        expiryDateColumn.setCellFactory(column -> {
            return new TableCell<Medicine, String>() {
                private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        Medicine medicine = getTableView().getItems().get(getIndex());
                        if (medicine.getExpiryDate() != null) {
                            setText(format.format(medicine.getExpiryDate()));
                        } else {
                            setText("");
                        }
                    }
                }
            };
        });

        usageInstructionsColumn.setCellValueFactory(new PropertyValueFactory<>("usageInstructions"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

        // Load data from the database
        medicineManagementTable.getItems().clear();
        medicineManagementTable.getItems().addAll(medicineDao.getAllMedicines());
    }

    public void addButtonToTable() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final HBox actionBox = new HBox(10);

            private final FontIcon editIcon = new FontIcon(FontAwesomeSolid.EDIT);
            private final FontIcon deleteIcon = new FontIcon(FontAwesomeSolid.TRASH_ALT);

            {
                // Set icon sizes and colors
                editIcon.setIconSize(20);
                editIcon.setIconColor(Paint.valueOf("#4CAF50")); // Green for "Edit"

                deleteIcon.setIconSize(20);
                deleteIcon.setIconColor(Paint.valueOf("#F44336")); // Red for "Delete"

                actionBox.getChildren().addAll(editIcon, deleteIcon);
                actionBox.setAlignment(Pos.CENTER); // Center HBox
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
            System.err.println("Could not load medicine-update.fxml");
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
    }

    @FXML
    private void handleAdd(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/medicine/medicine-add.fxml", actionEvent);
    }

    private void updateTable(List<Medicine> medicines) {
        // Convert List to ObservableList for TableView
        medicineManagementTable.setItems(FXCollections.observableArrayList(medicines));
    }

    private void filterMedicineList(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            // If no keyword, display full list
            updateTable(originalMedicineList);
            return;
        }

        // Create filtered list
        List<Medicine> filteredList = new ArrayList<>();
        for (Medicine medicine : originalMedicineList) {
            if (medicine.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    (medicine.getActiveIngredient() != null && medicine.getActiveIngredient().toLowerCase().contains(keyword.toLowerCase())) ||
                    (medicine.getDosage() != null && medicine.getDosage().toLowerCase().contains(keyword.toLowerCase())) ||
                    (medicine.getManufacturer() != null && medicine.getManufacturer().toLowerCase().contains(keyword.toLowerCase())) ||
                    (medicine.getUsageInstructions() != null && medicine.getUsageInstructions().toLowerCase().contains(keyword.toLowerCase()))) {
                filteredList.add(medicine);
            }
        }

        // Update table with filtered list
        updateTable(filteredList);
    }
}