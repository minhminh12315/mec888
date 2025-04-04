module com.home.mec888 {
    requires javafx.fxml;


    // Hibernate và JPA
    requires java.naming;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;

    // Logging nếu bạn dùng SLF4J
    requires org.slf4j;

    // Mở package chứa entity cho Hibernate ORM (để nó có thể quét bằng reflection)
    opens com.home.mec888.entity to org.hibernate.orm.core, javafx.base;

    // Nếu sử dụng JavaFX với FXML, cần mở package chứa controller
    opens com.home.mec888.controller to javafx.fxml;
    opens com.home.mec888.controller.login to javafx.fxml;
//    opens com.home.mec888.controller.admin to javafx.fxml;
    opens com.home.mec888.controller.admin.medicine to javafx.fxml;
    opens com.home.mec888.controller.admin.department to javafx.fxml;
    opens com.home.mec888.controller.admin.doctor to javafx.fxml;
    opens com.home.mec888.controller.admin.user to javafx.fxml;
//    opens com.home.mec888.controller.admin to javafx.fxml;
//    opens com.home.mec888.controller.doctor to javafx.fxml;
//    opens com.home.mec888.controller.patient to javafx.fxml;
//    opens com.home.mec888.controller.staff to javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.persistence;
    requires junit;
    requires mysql.connector.j;
    requires spring.security.crypto;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    opens com.home.mec888 to javafx.fxml;
    exports com.home.mec888;
}