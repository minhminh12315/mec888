module com.home.mec888 {
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.swing;

    // Hibernate và JPA
    requires java.naming;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.persistence;

    // Logging nếu bạn dùng SLF4J
    requires org.slf4j;

    // JavaFX & UI Libraries
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.javafx;

    // Miscellaneous
    requires junit;
    // requires mysql.connector.j;
    requires spring.security.crypto;
    requires java.desktop;
    requires javax.mail.api;
    requires jasperreports;
    requires itextpdf;
    requires org.apache.pdfbox;

    // Mở package chứa entity cho Hibernate ORM (để nó có thể quét bằng reflection)
    opens com.home.mec888.entity to org.hibernate.orm.core, javafx.base;

    // Nếu sử dụng JavaFX với FXML, cần mở package chứa controller
    opens com.home.mec888.controller to javafx.fxml;
    opens com.home.mec888.controller.login to javafx.fxml;
    opens com.home.mec888.controller.extractPDF to javafx.fxml;
    // opens com.home.mec888.controller.admin to javafx.fxml;
    opens com.home.mec888.controller.admin.medicine to javafx.fxml;
    opens com.home.mec888.controller.admin.department to javafx.fxml;
    opens com.home.mec888.controller.admin.doctor to javafx.fxml;
    opens com.home.mec888.controller.admin.user to javafx.fxml;
    opens com.home.mec888.controller.admin.room to javafx.fxml;
    opens com.home.mec888.controller.admin.service to javafx.fxml;
    opens com.home.mec888.controller.admin.specialization to javafx.fxml;
    opens com.home.mec888.controller.admin.patient to javafx.fxml;
    opens com.home.mec888.controller.settings to javafx.fxml;
    opens com.home.mec888.controller.staff.appointment to javafx.fxml;
    opens com.home.mec888.controller.admin.dashboard to javafx.fxml;
//    opens com.home.mec888.controller.admin to javafx.fxml;
//    opens com.home.mec888.controller.doctor to javafx.fxml;
//    opens com.home.mec888.controller.patient to javafx.fxml;
//    opens com.home.mec888.controller.staff to javafx.fxml;
    opens com.home.mec888.controller.staff.payment to javafx.fxml;
    // opens com.home.mec888.controller.doctor to javafx.fxml;
    // opens com.home.mec888.controller.patient to javafx.fxml;
    // opens com.home.mec888.controller.staff to javafx.fxml;
    opens com.home.mec888.controller.doctor.schedule to javafx.fxml;
    opens com.home.mec888.controller.doctor.appointment to javafx.fxml;
    opens com.home.mec888.controller.doctor.appointment.modal to javafx.fxml;
    opens com.home.mec888.controller.patient to javafx.fxml;

    opens com.home.mec888 to javafx.fxml;

    // Exported packages
    exports com.home.mec888;
    exports com.home.mec888.entity;
    exports com.home.mec888.controller;
    exports com.home.mec888.controller.login;
    exports com.home.mec888.controller.admin;
    exports com.home.mec888.controller.extractPDF;
    exports com.home.mec888.controller.admin.medicine;
    exports com.home.mec888.controller.admin.patient;
    exports com.home.mec888.controller.admin.specialization;
    exports com.home.mec888.controller.doctor.schedule;
    exports com.home.mec888.controller.doctor.appointment;
    exports com.home.mec888.controller.patient;
    exports com.home.mec888.controller.doctor.appointment.modal to javafx.fxml;
    exports  com.home.mec888.controller.admin.dashboard;
}