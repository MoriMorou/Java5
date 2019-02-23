package ru.morou.client;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Управление сценами.
 */

public class ScreenManager {
    private static Stage stage;

    public static void setStage(Stage newStage){
        stage = newStage;
    }

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

    public static void showWorkFlowScreen(){
        Platform.runLater(()->{
            stage.close();
            try {
//                Parent root = FXMLLoader.load(ScreenManager.class.getResource("/WorkFlow2.fxml"));
                Parent root = FXMLLoader.load(ScreenManager.class.getResource("/main.fxml"));
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
