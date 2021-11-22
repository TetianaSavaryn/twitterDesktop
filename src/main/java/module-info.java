module com.concordia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.concordia to javafx.fxml;
    exports com.concordia.controller;
    opens com.concordia.controller to javafx.fxml;
}