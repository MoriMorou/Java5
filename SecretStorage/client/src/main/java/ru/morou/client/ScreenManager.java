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

    // FIXME: Каждая сцена описана отдельно, это правильно? 2019-02-20
    // FIXME: Чем отличается Platform.runLater от Task, когда что следует использовать?
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
                Parent root = FXMLLoader.load(ScreenManager.class.getResource("/WorkFlow.fxml"));
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
