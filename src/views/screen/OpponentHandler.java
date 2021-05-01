package views.screen;

import entity.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;

public class OpponentHandler extends FXMLScreenHandler{
    private Player player;
    @FXML
    Label opponentName;
    @FXML
    Label cardNumber;
    @FXML
    Label heath;
    @FXML
    ImageView equipCard;
    @FXML
    ImageView role;

    public OpponentHandler(String screenPath, Player player) throws IOException {
        super(screenPath, new Stage());
        opponentName.setText(String.valueOf(player.getPlayerNum()));
        cardNumber.setText(String.valueOf(player.getCardNum()));
        heath.setText(String.valueOf(player.getHealth()));
        if (player.getIsSheriff()) {
            File file = new File("src/assets/Roles/sheriff.jpg");
            Image image = new Image(file.toURI().toString());
            role.setImage(image);
        }
    }
}
