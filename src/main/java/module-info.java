module pl.numerki.numerki {
    requires javafx.controls;
    requires javafx.fxml;
    opens pl.numerki.frontend to javafx.fxml;
    exports pl.numerki.frontend;
}