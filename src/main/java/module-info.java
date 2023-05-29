module com.github.hezavehir {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.github.hezavehir to javafx.fxml;
    exports com.github.hezavehir;
}
