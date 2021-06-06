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
    @FXML
    ImageView alive;

    public OpponentHandler(String screenPath, Player player) throws IOException {
        super(screenPath, new Stage());
        opponentName.setText(String.valueOf(player.getName()));
        cardNumber.setText(String.valueOf(player.getCardNum()));
        heath.setText(String.valueOf(player.getHealth()));
        // set role to be sheriff
        if (player.getIsSheriff()) {
            File file = new File("src/assets/Roles/sheriff.jpg");
            Image image = new Image(file.toURI().toString());
            role.setImage(image);
        }

        // set equipped card if there are any
        if (player.getEquippedCard()!=null && !"".equals(player.getEquippedCard().getName())) {
//            System.out.println("card equiped = " + player.getEquippedCard().getName());
            File file = new File(player.getEquippedCard().getImageURL());
            Image image = new Image(file.toURI().toString());
            equipCard.setImage(image);
        }

        // if player died
        if ( player.getHealth() <=0 ) {
            // set skull image
            File file = new File("src/assets/death.png");
            Image image = new Image(file.toURI().toString());
            alive.setImage(image);

            // set role
            File rolefile = new File(player.getRoleImageURL());
            Image roleimage = new Image(rolefile.toURI().toString());
            role.setImage(roleimage);
            System.out.println(player.getRoleImageURL());
        }
    }
}
