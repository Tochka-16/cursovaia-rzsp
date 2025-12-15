module file.detector.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    exports app;
    requires javafx.graphics;
    exports model;
    opens controller to javafx.fxml;
    exports controller;
    exports service;
    exports util;
    opens model to com.fasterxml.jackson.databind;
}