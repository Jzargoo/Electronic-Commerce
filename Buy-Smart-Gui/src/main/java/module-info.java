module com.jzargo.buysmartgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires static lombok;
    requires java.net.http;
    requires java.prefs;
    requires jdk.httpserver;
    requires com.fasterxml.jackson.databind;
    requires com.jzargo.shared.main;
    requires org.aspectj.weaver;


    opens com.jzargo.buysmartgui;
    exports com.jzargo.buysmartgui;
    exports com.jzargo.buysmartgui.ui;
    opens com.jzargo.buysmartgui.ui;
}