module palm {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires java.sql;
    requires org.bouncycastle.provider;
    requires spring.security.crypto;
    requires slf4j.nop;
    requires twilio;


    opens PALM to javafx.graphics, javafx.fxml;
    exports PALM;
    exports PALMGUI;
    opens PALMGUI to javafx.fxml, javafx.graphics;
}