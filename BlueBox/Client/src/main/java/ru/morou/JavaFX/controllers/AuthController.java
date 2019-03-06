package ru.morou.JavaFX.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import ru.morou.JavaFX.Controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ru.morou.JavaFX.MessagesProcessor;
import ru.morou.queries.StandardJsonQuery;
import ru.morou.queries.json.JsonAuth;

/**
 * Осуществляет управление экраном аутентификации
 * @author morou
 */
public class AuthController extends Controller implements Initializable {

    private static final Logger logger = Logger.getLogger(AuthController.class);

    @FXML Button btnLogin;                 // логин
    @FXML Button btnReg;                   // зарегистрировать нового пользователя
    @FXML TextField textFieldLogin;        // логин
    @FXML TextField textFieldPass;         // пароль
    @FXML TextField first_name;
    @FXML TextField last_name;
    @FXML TextField email;
    @FXML TextField textFieldPassConfirm;  // подтверждение пароля
    @FXML Button btnSendAuth;              // отправить новые данные для аутентификации
    @FXML Button btnLoginWithoutReg;       // вернуться к аутентификации
    @FXML Label labelPassConfirm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * войти с указанными данными
     */
    @FXML
    public void btnConnectionUser() {

        JsonAuth jsonQuery = new JsonAuth(StandardJsonQuery.QueryType.AUTH_DATA, textFieldLogin.getText(),
                textFieldPass.getText());
        MessagesProcessor.getProcessor().setController(this).sendTransference(jsonQuery);
        clearTextFields();

    }

    /**
     * отправить новые данные для аутентификации (смена пароля)
     */
    @FXML public void btnbtnSendAuthClickMeReaction() {

        if (textFieldPass.getText().equals(textFieldPassConfirm.getText())) {

            JsonAuth jsonQuery = new JsonAuth(StandardJsonQuery.QueryType.REG_DATA, textFieldLogin.getText(), textFieldPass.getText());

            MessagesProcessor.getProcessor().setController(this).sendTransference(jsonQuery);

            clearTextFields();
        } else {
            textFieldPass.setStyle("-fx-text-inner-color: red;");
            textFieldPassConfirm.setStyle("-fx-text-inner-color: red;");
        }
    }

    /**
     * Перейти к экрану регистрации
     */
    @FXML public void doToRegistrationForm() {
        ScreenManager.showRegistrationScreen();
    }

    /**
     * зарегистрировать нового пользователя
     */
    @FXML public void btnNewUserRegistration() {

//        switchAvailability(false, btnLogin, btnReg);
//
//        switchAvailability(true,  labelPassConfirm,
//                textFieldPassConfirm,
//                btnSendAuth,
//
//                btnLoginWithoutReg);
//
//        clearTextFields();
    }

    /**
     * вернуться к аутентификации
     */
    @FXML public void btnBackToAuthScreen() {
        ScreenManager.showLoginScreen();
    }

    private void clearTextFields() {
        textFieldLogin.clear();
        textFieldLogin.requestFocus();
        textFieldPass.clear();
        textFieldPassConfirm.clear();
    }


    /**
     * переключает видимость и доступность элементов интерфейса
     * @param bool флаг переключения
     * @param nodes массив элементов
     */
    private void switchAvailability(boolean bool, Node...nodes) {
        for (Node node : nodes) {
            node.setManaged(bool);
            node.setVisible(bool);
        }
    }


}

