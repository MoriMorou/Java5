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


    // FIXME: Я использую двойную панелью В статье описыватся пример, и я для каждой части панели создала свой
    //  контролер, но я так и не понимаю как отражать кнопки на каждой из панелей? Написано что нужно наследоваться от
    //  FXMLView но я так и не поняла что это?
    //  Нашла старье какое то https://mvnrepository.com/artifact/com.airhacks/afterburner.fx/1.7.0 Это это?
    //  На данный момент на что то ругается, не могу понять как исправить 2019-02-20
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
//        LocalStorageView localStorageView = new LocalStorageView();
//        LocalStorage.getChildren().add(localStorageView.getView());
    }

    @PostConstruct
    public void init(){
        System.out.println("----Initializing");
    }

}