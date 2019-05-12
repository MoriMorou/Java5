package ru.morou.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.morou.api.AuthMessage;
import ru.morou.client.Network;
import ru.morou.client.ScreenManager;

public class LoginController {

    @FXML
    TextField tfNickname;

    @FXML
    PasswordField pfPassword;

    public void connectionToServer() {
        new Thread(() -> Network.getInstance().start(() -> ScreenManager.showWorkFlowScreen())).start();
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Попытка подлючения
     */
    public void handleAuth() {
        if(!Network.getInstance().isConnectionOpened()) {
            connectionToServer();
        }
        AuthMessage authMessage = new AuthMessage(tfNickname.getText(), pfPassword.getText());
        tfNickname.clear();
        pfPassword.clear();
        Network.getInstance().sendData(authMessage);
    }

    public void registration(ActionEvent actionEvent) {
    }
}