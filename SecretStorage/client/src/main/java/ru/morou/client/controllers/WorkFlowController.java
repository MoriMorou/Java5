package ru.morou.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import ru.morou.client.views.LocalStorageView;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

public class WorkFlowController implements Initializable {

    @FXML
    AnchorPane CloudStorage;

    @FXML
    AnchorPane LocalStorage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");

    }

}