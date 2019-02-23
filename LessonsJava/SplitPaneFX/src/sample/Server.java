package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Server {
    public void btnClick(ActionEvent actionEvent) {
        System.out.println(((Button)actionEvent.getSource()).getText());
    }
}
