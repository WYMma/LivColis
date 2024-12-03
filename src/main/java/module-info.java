module org.example.livcolis {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.livcolis to javafx.fxml;
    exports org.example.livcolis;
}