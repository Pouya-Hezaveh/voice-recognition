package com.github.hezavehir;

import java.io.IOException;

import javafx.fxml.FXML;

public class PrimaryController {

    //todo : Setting up AreaChart
    //  AreaChart mfccChart = new AreaChart<>(null, null)

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
