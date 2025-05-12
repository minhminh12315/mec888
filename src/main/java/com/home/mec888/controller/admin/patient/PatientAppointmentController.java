package com.home.mec888.controller.admin.patient;

import com.home.mec888.dao.*;
import com.home.mec888.entity.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class PatientAppointmentController implements Initializable {
    @FXML
    private VBox medicineBox;
    @FXML
    private VBox prescriptionBox;
    @FXML
    private VBox medicalRecordBox;
    @FXML
    private VBox appointmentBox;

    @FXML
    private Button btnMedicines;
    @FXML
    private Button btnPrescriptions;
    @FXML
    private Button btnMedicalRecords;
    @FXML
    private Button btnAppointments;

    @FXML
    private HBox patientInfoBox;
    @FXML
    private Label patientNameLabel;
    @FXML
    private Label patientIDLabel;
    @FXML
    private Label dateLabel;

    @FXML
    private TableView<Medicine> medicineTable;
    @FXML
    private TableView<PrescriptionDetails> prescriptionTable;
    @FXML
    private TableView<MedicalRecord> medicalRecordTable;
    @FXML
    private TableView<Appointment> appointmentTable;

    // Observable lists for tables
    private ObservableList<Medicine> medicines = FXCollections.observableArrayList();
    private ObservableList<PrescriptionDetails> prescriptions = FXCollections.observableArrayList();
    private ObservableList<MedicalRecord> medicalRecords = FXCollections.observableArrayList();
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    private Long selectedAppointmentId = null;
    private Patient selectedPatient = null;
    private boolean initialized = false;

    public void setPatientAppointment(Patient patient) {
        if (patient != null) {
            this.selectedPatient = patient;

            // Update patient info immediately when patient is set
            updatePatientInfo(patient);

            if (initialized) {
                // Load appointments for the selected patient
                loadAppointmentsBySelectedPatient();

                // Make the appointment table visible
                appointmentTable.setVisible(true);
                appointmentTable.setManaged(true);
                btnAppointments.setText("Hide");

                // Clear other tables since no appointment is selected yet
                medicines.clear();
                prescriptions.clear();
                medicalRecords.clear();
            }
        } else {
            System.out.println("Patient is null");
        }
    }

    public void setAppointmentInfo(Appointment appointment) {
        if (appointment != null) {
            this.selectedAppointmentId = appointment.getId();
            System.out.println("Appointment id: " + selectedAppointmentId);

            // Update patient info immediately when appointment is set
            updatePatientInfo(appointment);

            if (initialized) {
                // Load data for all tables
                loadMedicinesBySelectedAppointment();
                loadPrescriptionsBySelectedAppointment();
                loadMedicalRecordsBySelectedAppointment();
            }
        } else {
            System.out.println("Appointment null");
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initially hide the tables
        medicineTable.setVisible(false);
        medicineTable.setManaged(false);

        prescriptionTable.setVisible(false);
        prescriptionTable.setManaged(false);

        medicalRecordTable.setVisible(false);
        medicalRecordTable.setManaged(false);

        appointmentTable.setVisible(false);
        appointmentTable.setManaged(false);

        // Setup tables
        setupMedicineTable();
        setupPrescriptionTable();
        setupMedicalRecordTable();
        setupAppointmentTable();

        // Setup button actions
        setupButtonActions();

        initialized = true;
        if (selectedAppointmentId != null) {
            // Load data for all tables once initialization is complete
            loadMedicinesBySelectedAppointment();
            loadPrescriptionsBySelectedAppointment();
            loadMedicalRecordsBySelectedAppointment();
        }

        if (selectedPatient != null) {
            // Load appointments for the selected patient
            loadAppointmentsBySelectedPatient();
        }
    }

    private void setupButtonActions() {
        btnMedicines.setOnAction(e -> {
            toggleTableVisibility(medicineTable);
            if (medicineTable.isVisible()) {
                loadMedicinesBySelectedAppointment();
                btnMedicines.setText("Hide");
            } else {
                btnMedicines.setText("Show");
            }
        });

        btnPrescriptions.setOnAction(e -> {
            toggleTableVisibility(prescriptionTable);
            if (prescriptionTable.isVisible()) {
                loadPrescriptionsBySelectedAppointment();
                btnPrescriptions.setText("Hide");
            } else {
                btnPrescriptions.setText("Show");
            }
        });

        btnMedicalRecords.setOnAction(e -> {
            toggleTableVisibility(medicalRecordTable);
            if (medicalRecordTable.isVisible()) {
                loadMedicalRecordsBySelectedAppointment();
                btnMedicalRecords.setText("Hide");
            } else {
                btnMedicalRecords.setText("Show");
            }
        });

        btnAppointments.setOnAction(e -> {
            toggleTableVisibility(appointmentTable);
            if (appointmentTable.isVisible()) {
                loadAppointmentsBySelectedPatient();
                btnAppointments.setText("Hide");
            } else {
                btnAppointments.setText("Show");
            }
        });
    }

    private void toggleTableVisibility(TableView<?> table) {
        table.setVisible(!table.isVisible());
        table.setManaged(table.isVisible());
    }

    private void setupMedicineTable() {
        TableColumn<Medicine, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Medicine, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Medicine, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Medicine, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Medicine, String> manufacturerCol = new TableColumn<>("Manufacturer");
        manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));

        medicineTable.getColumns().addAll(idCol, nameCol, descriptionCol, priceCol, manufacturerCol);
        medicineTable.setItems(medicines);

        // Set column resize policy to fill width
        medicineTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setupPrescriptionTable() {
        TableColumn<PrescriptionDetails, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<PrescriptionDetails, String> medicineCol = new TableColumn<>("Medicine");
        medicineCol.setCellValueFactory(new PropertyValueFactory<>("medicineName"));

        TableColumn<PrescriptionDetails, String> dosageCol = new TableColumn<>("Dosage");
        dosageCol.setCellValueFactory(new PropertyValueFactory<>("dosage"));

        TableColumn<PrescriptionDetails, String> frequencyCol = new TableColumn<>("Frequency");
        frequencyCol.setCellValueFactory(new PropertyValueFactory<>("frequency"));

        TableColumn<PrescriptionDetails, String> instructionsCol = new TableColumn<>("Instructions");
        instructionsCol.setCellValueFactory(new PropertyValueFactory<>("instructions"));

        prescriptionTable.getColumns().addAll(idCol, medicineCol, dosageCol, frequencyCol, instructionsCol);
        prescriptionTable.setItems(prescriptions);

        // Set column resize policy to fill width
        prescriptionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setupMedicalRecordTable() {
        TableColumn<MedicalRecord, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<MedicalRecord, String> diagnosisCol = new TableColumn<>("Diagnosis");
        diagnosisCol.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));

        TableColumn<MedicalRecord, String> treatmentCol = new TableColumn<>("Treatment");
        treatmentCol.setCellValueFactory(new PropertyValueFactory<>("treatment"));

        TableColumn<MedicalRecord, String> notesCol = new TableColumn<>("Notes");
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));

        medicalRecordTable.getColumns().addAll(idCol, diagnosisCol, treatmentCol, notesCol);
        medicalRecordTable.setItems(medicalRecords);

        // Set column resize policy to fill width
        medicalRecordTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setupAppointmentTable() {
        TableColumn<Appointment, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);

        TableColumn<Appointment, String> doctorCol = new TableColumn<>("Doctor");
        doctorCol.setCellValueFactory(cellData -> {
            Doctor doctor = cellData.getValue().getDoctor();
            if (doctor != null) {
                UserDao userDao = new UserDao();
                User user = userDao.getUserById((long) doctor.getUser().getId());
                if (user != null) {
                    return new SimpleStringProperty(user.getFirstName() + " " + user.getLastName());
                }
            }
            return new SimpleStringProperty("Unknown");
        });
        doctorCol.setPrefWidth(120);

        TableColumn<Appointment, Date> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        dateCol.setPrefWidth(100);

        TableColumn<Appointment, Time> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTime"));
        timeCol.setPrefWidth(80);

        TableColumn<Appointment, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(100);

        // Add select button column
        TableColumn<Appointment, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button selectBtn = new Button("Select");

            {
                selectBtn.setOnAction(event -> {
                    Appointment appointment = getTableView().getItems().get(getIndex());
                    setAppointmentInfo(appointment);

                    // Show the other tables when an appointment is selected
                    medicineTable.setVisible(true);
                    medicineTable.setManaged(true);
                    btnMedicines.setText("Hide");

                    prescriptionTable.setVisible(true);
                    prescriptionTable.setManaged(true);
                    btnPrescriptions.setText("Hide");

                    medicalRecordTable.setVisible(true);
                    medicalRecordTable.setManaged(true);
                    btnMedicalRecords.setText("Hide");

                    // Show success message
                    showAlert("Appointment Selected", "Appointment ID: " + appointment.getId() + " has been selected.");
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(selectBtn);
                }
            }
        });
        actionCol.setPrefWidth(100);

        appointmentTable.getColumns().addAll(idCol, doctorCol, dateCol, timeCol, statusCol, actionCol);
        appointmentTable.setItems(appointments);

        // Set column resize policy to fill width
        appointmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void updatePatientInfo(Appointment appointment) {
        if (appointment != null) {
            UserDao userDao = new UserDao();
            User user = userDao.getUserById(appointment.getPatient().getUser().getId());
            patientNameLabel.setText(user.getFirstName() + " " + user.getLastName());
            patientIDLabel.setText("ID: " + appointment.getPatient().getId());
            dateLabel.setText("Date: " + appointment.getAppointmentDate());
        }
    }

    private void updatePatientInfo(Patient patient) {
        if (patient != null) {
            UserDao userDao = new UserDao();
            User user = userDao.getUserById(patient.getUser().getId());
            patientNameLabel.setText(user.getFirstName() + " " + user.getLastName());
            patientIDLabel.setText("ID: " + patient.getId());
            dateLabel.setText(""); // Clear date since no appointment is selected yet
        }
    }

    private void loadMedicinesBySelectedAppointment() {
        if (selectedAppointmentId == null) {
            return;
        }

        medicines.clear(); // Clear the list before adding new items
        MedicineDao medicineDao = new MedicineDao();
        List<Medicine> medicineList = medicineDao.getMedicinesBySelectedAppointmentId(selectedAppointmentId);

        if (medicineList != null && !medicineList.isEmpty()) {
            medicines.addAll(medicineList);
            System.out.println("Medicines loaded: " + medicineList.size());
        } else {
            System.out.println("No medicines found for appointment ID: " + selectedAppointmentId);
        }
    }

    private void loadPrescriptionsBySelectedAppointment() {
        if (selectedAppointmentId == null) {
            return;
        }

        prescriptions.clear(); // Clear the list before adding new items
        PrescriptionDetailsDao prescriptionDetailsDao = new PrescriptionDetailsDao();
        List<PrescriptionDetails> prescriptionList = prescriptionDetailsDao.getPrescriptionsBySelectedAppointmentId(selectedAppointmentId);

        if (prescriptionList != null && !prescriptionList.isEmpty()) {
            prescriptions.addAll(prescriptionList);
            System.out.println("Prescriptions loaded: " + prescriptionList.size());
        } else {
            System.out.println("No prescriptions found for appointment ID: " + selectedAppointmentId);
        }
    }

    private void loadMedicalRecordsBySelectedAppointment() {
        if (selectedAppointmentId == null) {
            return;
        }

        medicalRecords.clear(); // Clear the list before adding new items
        MedicalRecordDao medicalRecordDao = new MedicalRecordDao();
        List<MedicalRecord> medicalRecordList = medicalRecordDao.getMedicalRecordsBySelectedAppointmentId(selectedAppointmentId);

        if (medicalRecordList != null && !medicalRecordList.isEmpty()) {
            medicalRecords.addAll(medicalRecordList);
            System.out.println("Medical records loaded: " + medicalRecordList.size());
        } else {
            System.out.println("No medical records found for appointment ID: " + selectedAppointmentId);
        }
    }

    private void loadAppointmentsBySelectedPatient() {
        if (selectedPatient == null) {
            return;
        }

        appointments.clear(); // Clear the list before adding new items
        AppointmentDao appointmentDao = new AppointmentDao();
        List<Appointment> appointmentList = appointmentDao.getAllAppointments(); // Get all appointments

        // Filter appointments for the selected patient
        appointmentList.removeIf(appointment ->
                !appointment.getPatient().getId().equals(selectedPatient.getId())
        );

        if (appointmentList != null && !appointmentList.isEmpty()) {
            appointments.addAll(appointmentList);
            System.out.println("Appointments loaded: " + appointmentList.size());
        } else {
            System.out.println("No appointments found for patient ID: " + selectedPatient.getId());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}