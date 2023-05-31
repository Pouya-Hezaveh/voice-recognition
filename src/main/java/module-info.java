module com.github.hezavehir {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires TarsosDSP;
    requires JTransforms;
    requires JLargeArrays;
    requires commons.math3;

    opens com.github.hezavehir to javafx.fxml;

    exports com.github.hezavehir;
}
