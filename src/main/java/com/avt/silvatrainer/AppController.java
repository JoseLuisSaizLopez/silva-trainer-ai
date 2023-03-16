package com.avt.silvatrainer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.lang.module.ModuleDescriptor;

public class AppController {

    @FXML
    GridPane container;

    @FXML
    protected void initialize() {
        loadContentWindow();
    }

    private void loadContentWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("content-window.fxml"));
            Pane contentWindow = fxmlLoader.load();
            container.getChildren().add(contentWindow);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
