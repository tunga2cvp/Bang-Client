package views.screen;

import controller.TestController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class TestHandler extends FXMLScreenHandler implements Initializable {
    @FXML
    Label value;
    TestController testController;
    public TestHandler(String screenPath, Stage stage) throws IOException {
        super(screenPath, stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        testController = new TestController();
//        Timeline fiveSecondsWonder = new Timeline(
//                new KeyFrame(Duration.seconds(1),
//                        event -> {
//                            value.setText(testController.value);
//                            System.out.println("this is called every 5 seconds on UI thread");
//                        }));
//        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
//        fiveSecondsWonder.play();
//        Platform.runLater(()->{
//            value.setText(testController.value);
//        });
        value.setText(testController.value);
    }
    public void updateText(String message){
        value.setText(message);
    }
}
