package ru.morou.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import static ru.morou.client.ScreenManager.showWorkFlowScreen;

public class LoginController {

    @FXML
    PasswordField password;

    @FXML
    TextField login;

    public void btnSignInOnAction(ActionEvent actionEvent) throws Exception {
        showWorkFlowScreen();
    }
}
