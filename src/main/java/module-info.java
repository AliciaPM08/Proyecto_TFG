module org.example.onside_fem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens org.example.onside_fem to javafx.fxml;


    exports org.example.onside_fem;
    exports org.example.onside_fem.Otros;


    opens org.example.onside_fem.Otros to javafx.fxml;
    opens org.example.onside_fem.BBDD to javafx.base;


    exports org.example.onside_fem.Espana;
    opens org.example.onside_fem.Espana to javafx.fxml;
    exports org.example.onside_fem.Australia;
    opens org.example.onside_fem.Australia to javafx.fxml;
    exports org.example.onside_fem.Japon;
    opens org.example.onside_fem.Japon to javafx.fxml;
    exports org.example.onside_fem.EEUU;
    opens org.example.onside_fem.EEUU to javafx.fxml;
    exports org.example.onside_fem.Francia;
    opens org.example.onside_fem.Francia to javafx.fxml;
    exports org.example.onside_fem.Inglaterra;
    opens org.example.onside_fem.Inglaterra to javafx.fxml;
    exports org.example.onside_fem.Selecciones;
    opens org.example.onside_fem.Selecciones to javafx.fxml;
}
