package views.screen;

import controller.BoardController;
import entity.Card;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
        super(screenPath);
        this.card = card;
        File file = new File(card.getImageURL());
        Image image = new Image(file.toURI().toString());
        cardImg.setImage(image);

        BoardController boardController = new BoardController();
        selectBtn.setOnAction(e-> {
            boardController.setPlayingCard(card);
            System.out.println(boardController.getPlayingCard().getName());
        });
    }
    public Card getCard() {
        return card;
    }

}
