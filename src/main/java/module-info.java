module palm {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires java.sql;
    requires org.bouncycastle.provider;
    requires spring.security.crypto;
    requires slf4j.nop;
    requires twilio;


    opens palm to javafx.graphics, javafx.fxml;
    exports palm;
    exports palmgui;
    exports palmtest;
    opens palmgui to javafx.fxml, javafx.graphics;
    opens palmtest to org.junit.jupiter.api;
}