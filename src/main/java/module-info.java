module org.example.proyectounidad2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires java.desktop;

    opens org.example.proyectounidad2 to javafx.fxml;
    opens org.example.proyectounidad2.Model to javafx.base;
    exports org.example.proyectounidad2;
    exports org.example.proyectounidad2.Controller;
    exports org.example.proyectounidad2.Model; // Allow Jackson to access this package

    opens org.example.proyectounidad2.Controller to javafx.fxml;

}