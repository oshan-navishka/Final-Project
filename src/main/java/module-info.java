module lk.ijse.phoneshopmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires net.sf.jasperreports.core;
    requires java.desktop;
    requires javafx.base;

    opens lk.ijse.phoneshopmanagementsystem to javafx.fxml;
    exports lk.ijse.phoneshopmanagementsystem;

    opens lk.ijse.phoneshopmanagementsystem.controller to javafx.fxml;
    exports lk.ijse.phoneshopmanagementsystem.dto to javafx.fxml;
    opens lk.ijse.phoneshopmanagementsystem.dto to javafx.base;
    exports lk.ijse.phoneshopmanagementsystem.controller;
}