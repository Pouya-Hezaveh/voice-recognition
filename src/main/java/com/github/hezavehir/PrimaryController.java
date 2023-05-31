package com.github.hezavehir;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;

public class PrimaryController {

    AreaChart mfccChart = new AreaChart<>(null, null);

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
