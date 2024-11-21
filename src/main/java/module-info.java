module org.example.proyectounidad2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens org.example.proyectounidad2 to javafx.fxml;
    opens org.example.proyectounidad2.Model to javafx.base;
    exports org.example.proyectounidad2;
    exports org.example.proyectounidad2.Controller;
    opens org.example.proyectounidad2.Controller to javafx.fxml;

}