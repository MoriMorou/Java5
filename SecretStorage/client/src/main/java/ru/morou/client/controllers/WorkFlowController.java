package ru.morou.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ru.morou.api.AbstractMessage;
import ru.morou.api.AuthMessage;
import ru.morou.api.CodeRequest;
import ru.morou.client.Network;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static ru.morou.client.ScreenManager.showRegistrationScreen;

public class WorkFlowController implements Initializable {

    @FXML
    TextField tfNickname;

    @FXML
    PasswordField pfPassword;

    @FXML
    Button btnConnection;

    @FXML
    public TextField tfFileName;

    @FXML
    public ListView filesList;

    @FXML
    AnchorPane CloudStorage;

    @FXML
    AnchorPane LocalStorage;


    private boolean isAuthrozied;
    private boolean isConnection;

    public void registration(ActionEvent actionEvent) throws Exception{
        showRegistrationScreen();
    }


    public void connection(ActionEvent actionEvent) throws Exception{

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        Consumer<KeyEvent> сheckingСredentials = keyEvent -> {
//            if (tfNickname.getText().trim().isEmpty() || pfPassword.getText().trim().isEmpty()) {
//                btnConnection.setDisable(true);
//            } else {
//                btnConnection.setDisable(false);
//                if (keyEvent.getCode().equals(KeyCode.ENTER))
//                    btnConnection.fire();
//            }
//        };
//
//        tfNickname
//            .setOnKeyReleased(сheckingСredentials::accept);
//
//        pfPassword
//            .setOnKeyReleased(сheckingСredentials::accept);
//


        Network.start();
        Thread t = new Thread(() -> {
            try {
                while (true) {
                    AbstractMessage am = Network.readObject();
                    if (am instanceof AuthMessage) {
                        AuthMessage authMessage = (AuthMessage) am;
                    }
                }
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            } finally {
                Network.stop();
            }
        });
        t.setDaemon(true);
        t.start();
        refreshLocalFilesList();
    }

    public void pressOnDownloadBtn(ActionEvent actionEvent) {
        if (tfFileName.getLength() > 0) {
            Network.sendMsg(new CodeRequest(tfFileName.getText()));
            tfFileName.clear();
        }
    }

//    public void pressOnSendData(ActionEvent actionEvent) {
//        NettyNetwork.getInstance().sendData();
//    }

    public void refreshLocalFilesList() {
        if (Platform.isFxApplicationThread()) {
            try {
                filesList.getItems().clear();
                Files.list(Paths.get("client_storage/")).map(p -> p.getFileName().toString()).forEach(o -> filesList.getItems().add(o));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Platform.runLater(() -> {
                try {
                    filesList.getItems().clear();
                    Files.list(Paths.get("client_storage/")).map(p -> p.getFileName().toString()).forEach(o -> filesList.getItems().add(o));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}