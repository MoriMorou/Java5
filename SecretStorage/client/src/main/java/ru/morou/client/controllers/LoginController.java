package ru.morou.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import static ru.morou.client.ScreenManager.showWorkFlowScreen;
import static ru.morou.client.ScreenManager.showRegistrationScreen;

public class LoginController {

    @FXML
    TextField username;

    @FXML
    PasswordField password;


    public void btnConnection(ActionEvent actionEvent) throws Exception {





        showWorkFlowScreen();


    }





    public void btnlogIn(ActionEvent actionEvent) throws Exception {
        showRegistrationScreen();
    }
}
