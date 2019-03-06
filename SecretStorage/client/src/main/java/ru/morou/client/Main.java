package ru.morou.client;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Основной класс.
 * Управление сценами идет через менеджер сцен.
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

