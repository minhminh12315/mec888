package com.home.mec888.controller.admin.medicine;

import com.home.mec888.entity.Medicine;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.*;
import java.sql.*;
import com.home.mec888.dao.MedicineDao;
import com.home.mec888.util.SceneSwitcher;

public class MedicineManagementController {
//    @FXML
//    private TableView<Medicine> genreTable;
//
//    @FXML
//    private TableColumn<Medicine, Integer> idColumn;
//
//    @FXML
//    private TableColumn<Medicine, String> genreColumn;

//    @FXML
//    private TableColumn<Medicine, Void> actionColumn;

    @FXML
    private void handleAdd(ActionEvent actionEvent)
    {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        SceneSwitcher.switchTo(currentStage, "admin/medicine/medicine-add.fxml");
    }
}
