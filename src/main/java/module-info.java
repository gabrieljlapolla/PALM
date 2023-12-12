module palm {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires java.sql;
    requires org.bouncycastle.provider;
    requires spring.security.crypto;
    requires slf4j.nop;
    requires twilio;
    requires jazzer.junit;
    requires jazzer.api;

    opens palm to javafx.graphics, javafx.fxml;
    exports palm;
    exports palm.gui;
    opens palm.gui to javafx.fxml, javafx.graphics;
}