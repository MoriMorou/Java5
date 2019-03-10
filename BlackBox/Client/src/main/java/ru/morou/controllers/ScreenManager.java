package ru.morou.controllers;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Управление сценами.
 * @author morou
 */

public class ScreenManager {

//    public enum Stages {
//        AUTH,         // экран авторизации
//        MAIN_SCENE,   // основной экран работы приложения
//        REGIST }      // экран регистрации
//

    private static Stage stage;

    public static void setStage(Stage newStage){
        stage = newStage;
    }
//            scene.getStylesheets().add(mainCssFile);
    // Platform.runLater - структура FX(специальная), которая опрокидывает задачу в поток, который занимается.
    // Применется только если мы обращаемся к интерфейсу не из потока java FX
    // обработкой интерфейса


    public static void showLoginScreen(){
        Platform.runLater(()->{
            stage.close();
            try {
                Parent root = FXMLLoader.load(ScreenManager.class.getResource("/Login.fxml"));
                stage.setTitle("Secret Storage");
                stage.setScene(new Scene(root, 800, 600));
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void showRegistrationScreen(){
        Platform.runLater(()->{
            stage.close();
            try {
                Parent root = FXMLLoader.load(ScreenManager.class.getResource("/Registration.fxml"));
                stage.setTitle("Secret Storage");
                stage.setScene(new Scene(root, 800, 600));
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void showWorkFlowScreen(){
        Platform.runLater(()->{
            stage.close();
            try {
                Parent root = FXMLLoader.load(ScreenManager.class.getResource("/WorkFlow2.fxml"));
//                Parent root = FXMLLoader.load(ScreenManager.class.getResource("/main.fxml"));
                stage.setTitle("Cloud storage client");
                stage.setScene(new Scene(root, 800, 600));
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
