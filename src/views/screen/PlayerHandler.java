package views.screen;

import entity.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerHandler extends FXMLScreenHandler implements Initializable {
    @FXML
    Label playerName;
    Player player;
    public PlayerHandler(String screenPath, Player player) throws IOException {
        super(screenPath);
        this.player = player;
        Platform.runLater(()->{
            playerName.setText(player.getName());
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            playerName.setText(player.getName());
        });
    }
}
