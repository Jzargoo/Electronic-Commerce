module com.jzargo.buysmartgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires static lombok;
    requires java.net.http;
    requires java.prefs;
    requires jdk.httpserver;
    requires com.jzargo.shared.main;
    requires org.aspectj.weaver;
    requires jdk.jdi;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;
    requires org.hibernate.orm.core;
    requires javafx.swing;


    opens com.jzargo.buysmartgui;
    exports com.jzargo.buysmartgui;
    exports com.jzargo.buysmartgui.ui.template.dialog.profileCards;
    opens com.jzargo.buysmartgui.ui.template.dialog.profileCards;
    exports com.jzargo.buysmartgui.ui.pages;
    opens com.jzargo.buysmartgui.ui.pages;
    exports com.jzargo.buysmartgui.ui.template;
    opens com.jzargo.buysmartgui.ui.template;
    exports com.jzargo.buysmartgui.ui.template.dialog;
    opens com.jzargo.buysmartgui.ui.template.dialog;
    exports com.jzargo.buysmartgui.errors;
    opens com.jzargo.buysmartgui.errors;
}