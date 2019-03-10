package ru.morou.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;


/**
 * Осуществляет управление экраном аутентификации
 * @author morou
 */
public class AuthController implements Initializable {

//    private static final Logger logger = Logger.getLogger(AuthController.class);
    public PasswordField textFieldPass;
    public TextField textFieldLogin;
    public Button btnLogin;
    public Button btnReg;

    public void btnConnectionUser(ActionEvent actionEvent) {



        ScreenManager.showWorkFlowScreen();
    }

    public void goToRegistrationForm(ActionEvent actionEvent) {

        ScreenManager.showRegistrationScreen();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
