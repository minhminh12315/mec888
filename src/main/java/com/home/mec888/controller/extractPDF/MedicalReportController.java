package com.home.mec888.controller.extractPDF;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;


public class MedicalReportController {

    @FXML private TextField nameField;
    @FXML private TextField birthDateField;
    @FXML private TextField ageField;
    @FXML private TextField genderField;
    @FXML private TextField addressField;
    @FXML private TextField patientTypeField;
    @FXML private TextField patientTypeDetailField;
    @FXML private TextField insuranceNumberField;
    @FXML private TextField validFromField;
    @FXML private TextField validToField;
    @FXML private TextField registrationDateField;

    @FXML
    void handleClearAction(ActionEvent event) {
        nameField.clear();
        birthDateField.clear();
        ageField.clear();
        genderField.clear();
        addressField.clear();
        patientTypeField.clear();
        patientTypeDetailField.clear();
        insuranceNumberField.clear();
        validFromField.clear();
        validToField.clear();
        registrationDateField.clear();
    }

//    tu chon folder der luu file pdf
//    @FXML
//    void handleExportAction(ActionEvent event) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Save PDF File");
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
//        fileChooser.setInitialFileName("phieu_chuyen_kham.pdf");
//
//        Stage stage = (Stage) nameField.getScene().getWindow();
//        File file = fileChooser.showSaveDialog(stage);
//
//        if (file != null) {
//            exportToPdfAdvanced(file);
//        }
//    }


//    tu luu nhung dat ten cho file duoc
    @FXML
    void handleExportAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        fileChooser.setInitialFileName("Medical_Report.pdf");  // Set the initial suggested name

        Stage stage = (Stage) nameField.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            exportToPdfAdvanced(file);
        }
    }

//  tu luu, tu dat ten(dung timestamp cho ko bi trung ten)
//    @FXML
//    void handleExportAction(ActionEvent event) {
//        // Define the directory to save the PDF (e.g., SavePdf inside your project)
//        File exportDir = new File("SavePdf");  // This will be inside the root of your project
//        if (!exportDir.exists()) {
//            exportDir.mkdirs();  // Create the directory if it doesn't exist
//        }
//
//        // Define the file name, either static or dynamic
//        String fileName = "Medical_Report_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";
//        File exportFile = new File(exportDir, fileName);
//
//        // Now export the PDF to the desired location
//        exportToPdfAdvanced(exportFile);
//    }



    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void handlePreviewAction(ActionEvent event) {
        try {
            File tempPdf = File.createTempFile("preview_medical_report", ".pdf");
            tempPdf.deleteOnExit();
            exportToPdfAdvanced(tempPdf);
            showPdfPreviewWithPdfBox(tempPdf);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Preview Error", "Could not generate preview file", e.getMessage());
        }
    }

    /**
     * Shows the PDF preview in a new JavaFX window using WebView
     * @param pdfFile The PDF file to display
     */
    private void showPdfPreviewWithPdfBox(File pdfFile) {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFRenderer renderer = new PDFRenderer(document);
            VBox vbox = new VBox(10);
            vbox.setStyle("-fx-padding: 10;");

            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                BufferedImage bufferedImage = renderer.renderImageWithDPI(page, 150, ImageType.RGB);
                Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
                ImageView imageView = new ImageView(fxImage);
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(800); // Resize as needed
                vbox.getChildren().add(imageView);
            }

            ScrollPane scrollPane = new ScrollPane(vbox);
            scrollPane.setFitToWidth(true);

            Stage stage = new Stage();
            stage.setTitle("PDF Preview");
            stage.setScene(new Scene(scrollPane, 820, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Preview Error", "Failed to load or render PDF", e.getMessage());
        }
    }

    private void exportToPdfAdvanced(File file) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);

            Paragraph hospitalInfo = new Paragraph();
            hospitalInfo.add(new Chunk("HANOI DEPARTMENT OF HEALTH\n", headerFont));
            hospitalInfo.add(new Chunk("XANH PON GENERAL HOSPITAL\n", headerFont));
            hospitalInfo.add(new Chunk("Room 114A - Outpatient Department\n", normalFont));
            hospitalInfo.add(new Chunk("Infectious diseases", normalFont));

            PdfPCell leftCell = new PdfPCell(hospitalInfo);
            leftCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(leftCell);

            Paragraph rightHeader = new Paragraph();
            rightHeader.add(new Chunk("Order No: ", headerFont));
            rightHeader.add(new Chunk("53\n", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24)));
            rightHeader.add(new Chunk("File ID: 2504280956", normalFont));

            PdfPCell rightCell = new PdfPCell(rightHeader);
            rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            rightCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(rightCell);

            document.add(headerTable);
            document.add(Chunk.NEWLINE);

            Paragraph title = new Paragraph("SPECIALIST REFERRAL FORM", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph regDate = new Paragraph("Registration Date: " + registrationDateField.getText(), normalFont);
            regDate.setAlignment(Element.ALIGN_CENTER);
            document.add(regDate);
            document.add(Chunk.NEWLINE);

            PdfPTable infoTable = new PdfPTable(4);
            infoTable.setWidthPercentage(100);
            float[] columnWidths = {1f, 3f, 1f, 2f};
            infoTable.setWidths(columnWidths);

            addInfoRowToTable(infoTable, "Full Name:", nameField.getText(), null, null, 3);
            addInfoRowToTable(infoTable, "Date of Birth:", birthDateField.getText(), "Age:", ageField.getText(), 1);
            addInfoRowToTable(infoTable, "Gender:", genderField.getText(), null, null, 1);
            addInfoRowToTable(infoTable, "Address:", addressField.getText(), null, null, 3);
            addInfoRowToTable(infoTable, "Patient Type:", patientTypeField.getText(), "Type Details:", patientTypeDetailField.getText(), 1);
            addInfoRowToTable(infoTable, "Insurance Number:", insuranceNumberField.getText(), null, null, 3);
            addInfoRowToTable(infoTable, "From Date:", validFromField.getText(), "To Date:", validToField.getText(), 1);

            document.add(infoTable);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Export Error", "Failed to export PDF", e.getMessage());
        } finally {
            document.close();
        }
    }

    private void addInfoRowToTable(PdfPTable table, String label1, String value1, String label2, String value2, int colspan) {
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        PdfPCell labelCell = new PdfPCell(new Phrase(label1, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value1, valueFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        if (colspan > 1) {
            valueCell.setColspan(colspan);
        }
        table.addCell(valueCell);

        if (label2 != null && value2 != null) {
            PdfPCell labelCell2 = new PdfPCell(new Phrase(label2, labelFont));
            labelCell2.setBorder(Rectangle.NO_BORDER);
            table.addCell(labelCell2);

            PdfPCell valueCell2 = new PdfPCell(new Phrase(value2, valueFont));
            valueCell2.setBorder(Rectangle.NO_BORDER);
            table.addCell(valueCell2);
        }
    }
}