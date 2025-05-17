package com.home.mec888.controller.admin.specialization;

import com.home.mec888.dao.SpecializationDao;
import com.home.mec888.entity.Specialization;
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

public class SpecializationManagementController {
    @FXML
    public TableView<Specialization> specializationTable;

    @FXML
    public TableColumn<Specialization, Long> idColumn;

    @FXML
    public TableColumn<Specialization, String> nameColumn;

    @FXML
    public TableColumn<Specialization, Void> actionColumn;

    @FXML
    public TextField searchField;

    private SpecializationDao specializationDao;
    private List<Specialization> originalSpecializationList;

    @FXML
    public void initialize() {
        specializationDao = new SpecializationDao();
        loadSpecializationData();
        addButtonToTable();

        originalSpecializationList = specializationDao.getAllSpecializations();

        updateTable(originalSpecializationList);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterSpecializationList(newValue);
        });
    }

    private void loadSpecializationData() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        specializationTable.getItems().clear();
        List<Specialization> specializations = specializationDao.getAllSpecializations();

        specializationTable.getItems().addAll(specializations);
    }

    public void addButtonToTable() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final HBox actionBox = new HBox(10);

            private final FontIcon editIcon = new FontIcon(FontAwesomeSolid.EDIT);

            {
                // Set size and color for icons
                editIcon.setIconSize(20);
                editIcon.setIconColor(Paint.valueOf("#4CAF50")); // Green for "Edit"

                actionBox.getChildren().addAll(editIcon);
                actionBox.setAlignment(Pos.CENTER); // Center align the HBox
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Specialization specialization = getTableView().getItems().get(getIndex());
                    setActionHandlers(actionBox, specialization);
                    setGraphic(actionBox);
                }
            }
        });
    }

    private void setActionHandlers(HBox actionBox, Specialization specialization) {
        FontIcon editIcon = (FontIcon) actionBox.getChildren().get(0);

        editIcon.setOnMouseClicked(event -> handleUpdate(specialization));
    }

    @FXML
    private void handleUpdate(Specialization specialization) {
        FXMLLoader loader = SceneSwitcher.loadViewToUpdate("admin/specialization/specialization-update.fxml");
        if (loader != null) {
            SpecializationUpdateController controller = loader.getController();
            controller.setSpecialization(specialization);

            Parent newView = loader.getRoot();
            AnchorPane anchorPane = (AnchorPane) specializationTable.getScene().getRoot();
            BorderPane mainPane = (BorderPane) anchorPane.lookup("#mainBorderPane");

            if (mainPane != null) {
                mainPane.setCenter(newView);
            } else {
                System.err.println("BorderPane with ID 'mainBorderPane' not found");
            }
        } else {
            System.err.println("Could not load specialization-update.fxml");
        }
    }

    @FXML
    private void handleAdd(ActionEvent actionEvent) {
        SceneSwitcher.loadView("admin/specialization/specialization-add.fxml", actionEvent);
    }

    private void updateTable(List<Specialization> specializations) {
        // Convert from List to ObservableList to display in TableView
        specializationTable.setItems(FXCollections.observableArrayList(specializations));
    }

    private void filterSpecializationList(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            // If no search keyword, display the full list
            updateTable(originalSpecializationList);
            return;
        }

        // Create filtered list
        List<Specialization> filteredList = new ArrayList<>();
        for (Specialization specialization : originalSpecializationList) {
            if (specialization.getName().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(specialization);
            }
        }

        // Update table with filtered list
        updateTable(filteredList);
        addButtonToTable();
    }
}