package ru.morou.client.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import static ru.morou.client.ScreenManager.showRegistrationScreen;

public class RegistrationController {


    public TextField login;
    public TextField firstName;
    public TextField secondName;
    public TextField email;
    public PasswordField password;


    public void btnDoRegistration(ActionEvent actionEvent) {



    }

    public void goHome(ActionEvent actionEvent) {
        showRegistrationScreen();
    }
}
