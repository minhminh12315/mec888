package com.home.mec888.controller.extractPDF;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class UserFormController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField notesField;

    @FXML
    void handleClearAction(ActionEvent event) {
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        addressField.clear();
        notesField.clear();
    }

    @FXML
    void handleExportAction(ActionEvent event) {
        // Show file chooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        fileChooser.setInitialFileName("user_information.pdf");

        Stage stage = (Stage) nameField.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            exportToPdfAdvanced(file);
        }
    }

    /**
     * Shows an alert dialog
     */
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void exportToPdfAdvanced(File file) {
        Document document = new Document();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            // Add header with logo (if available)
            // Image logo = Image.getInstance("path/to/logo.png");
            // logo.scaleToFit(100, 50);
            // document.add(logo);

            // Add title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.DARK_GRAY);
            Paragraph title = new Paragraph("User Information Form", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add generation timestamp
            Font smallFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8);
            Paragraph timestamp = new Paragraph("Generated: " + new Date(), smallFont);
            timestamp.setAlignment(Element.ALIGN_RIGHT);
            document.add(timestamp);

            document.add(Chunk.NEWLINE);

            // Create a table for the form data
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Set column widths
            float[] columnWidths = {1f, 3f};
            table.setWidths(columnWidths);

            // Add table headers
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            PdfPCell headerCell = new PdfPCell(new Phrase("Field", headerFont));
            headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
            headerCell.setPadding(5);
            table.addCell(headerCell);

            headerCell = new PdfPCell(new Phrase("Value", headerFont));
            headerCell.setBackgroundColor(BaseColor.DARK_GRAY);
            headerCell.setPadding(5);
            table.addCell(headerCell);

            // Add form data to table
            addRowToTable(table, "Full Name", nameField.getText());
            addRowToTable(table, "Email", emailField.getText());
            addRowToTable(table, "Phone", phoneField.getText());
            addRowToTable(table, "Address", addressField.getText());
            addRowToTable(table, "Notes", notesField.getText());

            document.add(table);

            // Add footer
            document.add(Chunk.NEWLINE);
            Paragraph footer = new Paragraph("This is an automatically generated document.", smallFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "PDF Export",
                    "PDF successfully exported",
                    "The form data has been exported to " + file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Export Error",
                    "Failed to export PDF",
                    "Error: " + e.getMessage());
        } finally {
            document.close();
        }
    }

    private void addRowToTable(PdfPTable table, String fieldName, String fieldValue) {
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        PdfPCell labelCell = new PdfPCell(new Phrase(fieldName, labelFont));
        labelCell.setPadding(5);
        labelCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(fieldValue, valueFont));
        valueCell.setPadding(5);
        table.addCell(valueCell);
    }
}
