module org.example.onside_fem {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.onside_fem to javafx.fxml;
    exports org.example.onside_fem;
}