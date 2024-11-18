module org.example.proyectounidad2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.proyectounidad2 to javafx.fxml;
    exports org.example.proyectounidad2;
}