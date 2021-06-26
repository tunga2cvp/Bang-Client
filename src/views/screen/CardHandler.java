package views.screen;

import controller.BoardController;
import entity.Card;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CardHandler extends FXMLScreenHandler{
    private Card card;
    @FXML
    private ImageView cardImg;
    @FXML
    private Button selectBtn;

    public CardHandler(String screenPath, Card card) throws IOException {
        super(screenPath, new Stage());
        this.card = card;
        File file = new File(card.getImageURL());
        Image image = new Image(file.toURI().toString());
        cardImg.setImage(image);

        selectBtn.setOnAction(e-> {
            BoardController.playingCard = card;
            //System.out.println(BoardController.playingCard.getName());
        });
    }
    public Card getCard() {
        return card;
    }

}
