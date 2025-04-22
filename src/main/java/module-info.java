module org.example.onside_fem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens org.example.onside_fem to javafx.fxml;


    exports org.example.onside_fem;
    exports org.example.onside_fem.Otros;


    opens org.example.onside_fem.Otros to javafx.fxml;

    exports org.example.onside_fem.Espana;
    opens org.example.onside_fem.Espana to javafx.fxml;
}
