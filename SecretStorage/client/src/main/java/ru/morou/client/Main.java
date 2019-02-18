package ru.morou.client;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Global instance of the scopes required by this quickstart.
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        ScreenManager.setStage(primaryStage);
        ScreenManager.showLoginScreen();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

