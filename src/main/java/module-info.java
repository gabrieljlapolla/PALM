module palm {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires java.sql;
    requires org.bouncycastle.provider;
    requires spring.security.crypto;


    opens PALM to javafx.graphics, javafx.fxml;
    exports PALM;
    exports PALMGUI;
    opens PALMGUI to javafx.fxml, javafx.graphics;
}