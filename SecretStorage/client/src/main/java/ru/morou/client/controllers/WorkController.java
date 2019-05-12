package ru.morou.client.controllers;

import com.airhacks.afterburner.views.FXMLView;
import javafx.application.Platform;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ru.morou.api.FileAbout;

public class WorkController {

    @FXML
    public TextField tfFileName;

    @FXML
    public ListView filesList;

    @FXML
    AnchorPane CloudStorage;

    @FXML
    AnchorPane LocalStorage;

    @FXML
    TableView<FileAbout> localFilesTable, cloudFilesTable;

    private ObservableList<FileAbout> cloudFilesList;
    private ObservableList<FileAbout> localFilesList;

    public void closeConnectionWithServer() {
        if(!Platform.isFxApplicationThread()) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try{
                        Parent mainScreen =
                    }
                }
            });
        }
    }

}
