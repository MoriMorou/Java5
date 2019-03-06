package ru.morou;

import javafx.application.Application;

import javafx.stage.Stage;
import ru.morou.JavaFX.controllers.ScreenManager;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        // создание экземпляра ScreenManager и показ экрана авторизации
        ScreenManager.setStage (primaryStage);
        ScreenManager.showLoginScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
