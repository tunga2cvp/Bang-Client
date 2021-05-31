package views.screen;
import com.google.gson.Gson;
import controller.BoardController;
import entity.message.PlayTurnSend;
import javafx.fxml.FXML;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import utils.Client;
import utils.Configs;

import java.io.IOException;

public class TargetPopupHandler extends FXMLScreenHandler{
    @FXML
    TextField target;
    @FXML
    Button shootBtn;
    public TargetPopupHandler(String screenPath, Stage stage) throws IOException {
        super(screenPath, stage);
        shootBtn.setOnAction(e->{
            // send the move
            System.out.println("Target is " + target.getText());
            Gson gson = new Gson();
            PlayTurnSend playTurnSend = new PlayTurnSend(BoardController.playingCard.getName(), "SELECT_TARGET", target.getText(), BoardController.playerNum);
            String json = gson.toJson(playTurnSend);
            try {
                Client.sendMessage(json);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            BoardController.playingCard = null; // reset after play a card
            // close the popup
            PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
            delay.setOnFinished( event -> stage.close() );
            delay.play();
        });
    }

}
