module com.example.tap2024 {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;

    opens com.example.tap2024 to javafx.fxml;
    exports com.example.tap2024;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires javafx.graphics;
    requires java.desktop;
    requires itextpdf;
    opens com.example.tap2024.models;

}