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

public class MedicalReportController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField birthDateField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField genderField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField patientTypeField;

    @FXML
    private TextField patientTypeDetailField;

    @FXML
    private TextField insuranceNumberField;

    @FXML
    private TextField validFromField;

    @FXML
    private TextField validToField;

    @FXML
    private TextField registrationDateField;

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

    @FXML
    void handleExportAction(ActionEvent event) {
        // Show file chooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        fileChooser.setInitialFileName("phieu_chuyen_kham.pdf");

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

            // Define fonts
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
            Font smallFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8);

            // Add header
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);

            // Left header
            Paragraph hospitalInfo = new Paragraph();
            hospitalInfo.add(new Chunk("SỞ Y TẾ HÀ NỘI\n", headerFont));
            hospitalInfo.add(new Chunk("BỆNH VIỆN ĐA KHOA XANH PÔN\n", headerFont));
            hospitalInfo.add(new Chunk("Phòng 114A - Phòng khám bệnh\n", normalFont));
            hospitalInfo.add(new Chunk("truyền nhiễm", normalFont));

            PdfPCell leftCell = new PdfPCell(hospitalInfo);
            leftCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(leftCell);

            // Right header
            Paragraph rightHeader = new Paragraph();
            rightHeader.add(new Chunk("STT: ", headerFont));
            rightHeader.add(new Chunk("53\n", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24)));
            rightHeader.add(new Chunk("Mã hồ sơ: 2504280956", normalFont));

            PdfPCell rightCell = new PdfPCell(rightHeader);
            rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            rightCell.setBorder(Rectangle.NO_BORDER);
            headerTable.addCell(rightCell);

            document.add(headerTable);
            document.add(Chunk.NEWLINE);

            // Add title
            Paragraph title = new Paragraph("PHIẾU CHUYỂN KHÁM CHUYÊN KHOA", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add registration date
            Paragraph regDate = new Paragraph("Ngày đăng ký: " + registrationDateField.getText(), normalFont);
            regDate.setAlignment(Element.ALIGN_CENTER);
            document.add(regDate);
            document.add(Chunk.NEWLINE);

            // Create a table for the patient info
            PdfPTable infoTable = new PdfPTable(4);
            infoTable.setWidthPercentage(100);

            // Set column widths
            float[] columnWidths = {1f, 3f, 1f, 2f};
            infoTable.setWidths(columnWidths);

            // Add patient information
            addInfoRowToTable(infoTable, "Họ và tên:", nameField.getText(), null, null, 3);
            addInfoRowToTable(infoTable, "Ngày sinh:", birthDateField.getText(), "Tuổi:", ageField.getText(), 1);
            addInfoRowToTable(infoTable, "Giới tính:", genderField.getText(), null, null, 1);
            addInfoRowToTable(infoTable, "Địa chỉ:", addressField.getText(), null, null, 3);
            addInfoRowToTable(infoTable, "Đối tượng:", patientTypeField.getText(), "Loại đối tượng:", patientTypeDetailField.getText(), 1);
            addInfoRowToTable(infoTable, "Số thẻ BHYT:", insuranceNumberField.getText(), null, null, 3);
            addInfoRowToTable(infoTable, "Từ ngày:", validFromField.getText(), "đến ngày:", validToField.getText(), 1);

            document.add(infoTable);

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