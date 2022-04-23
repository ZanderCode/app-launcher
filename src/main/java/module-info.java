module com.example.applauncher {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.applauncher to javafx.fxml;
    exports com.example.applauncher;
}