package views.screen;

import controller.BoardController;
import controller.JSONSender;
import entity.Card;
import entity.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class BoardScreenHandler implements Initializable {
    private List boardItems;
    private BoardController boardController;
    @FXML
    private HBox cards;
    @FXML
    private HBox farOpponents;
    @FXML
    private Label playerHealth;
    @FXML
    private  VBox leftOpponentPlace;
    @FXML
    private VBox rightOpponentPlace;
    @FXML
    private ImageView playerRole;
    @FXML
    private Button playBtn;
    @FXML
    private Button endturnBtn;
    @FXML
    private Button discardBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            boardController = new BoardController();

            // Add player cards to the board
            List cardList = boardController.getCardList();
            List cardItems = new ArrayList<>();
            for (Object object : cardList) {
                Card card = (Card) object;
                CardHandler c1 = null;
                try {
                    c1 = new CardHandler(Configs.CARD_PATH,card);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                cardItems.add(c1);
            }
            addCardBoard(cardItems);

            // Add opponents to the board
            List farOpponents = boardController.getFarOpponents();
            List opponentItems = new ArrayList<>();
            for (Object object : farOpponents) {
                Player player = (Player) object;
                OpponentHandler o1 = null;
                try {
                    o1 = new OpponentHandler(Configs.OPPONENT_PATH,player);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            opponentItems.add(o1);
            }
            addFarOpponents(opponentItems);

            // Add 2 close opponents to the board
            // Add left opponent
        Player leftOpponent = boardController.getLeftOpponent();
        OpponentHandler ol = null;
        try {
            ol = new OpponentHandler(Configs.OPPONENT_PATH,leftOpponent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        leftOpponentPlace.getChildren().add(ol.getContent());

        // Add right opponent
        Player rightOpponent = boardController.getRightOpponent();
        OpponentHandler or = null;
        try {
            or = new OpponentHandler(Configs.OPPONENT_PATH,rightOpponent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        rightOpponentPlace.getChildren().add(or.getContent());

        // set player health
        playerHealth.setText(String.valueOf(boardController.getPlayerHealth()));

        // set player role image
        String begin = "src/assets/Roles/";
        String name = boardController.getPlayerRole();
        String last = ".jpg";
        String roleUrl = begin.concat(name).concat(last);

        File file = new File(roleUrl);
        Image image = new Image(file.toURI().toString());
        playerRole.setImage(image);

        // set action for play button

        playBtn.setOnAction(e->{
            if (BoardController.playingCard == null) System.out.println("no card where selected");
            else
            switch (BoardController.playingCard.getType()){
                case "offense":
                    try {
                        TargetPopupHandler targetPopupHandler = new TargetPopupHandler(Configs.TARGET_POPUP_PATH, new Stage());
                        targetPopupHandler.show();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    break;
                case "defense":
                    System.out.println(BoardController.playingCard.getName() + " is used to defense");
                    break;
                case "equip":
                    System.out.println(BoardController.playingCard.getName() + " is equipped");
                    break;
                default:
                    System.out.println("no card where selected");
            }
            BoardController.playingCard = null; // reset after play a card
        });
    }

    public void addCardBoard(List items){
        ArrayList boardItems = (ArrayList)((ArrayList) items).clone();
        while(!boardItems.isEmpty()){
                while(!boardItems.isEmpty()){
                    CardHandler card = (CardHandler) boardItems.get(0);
                    cards.getChildren().add(card.getContent());
                    boardItems.remove(card);
                }
            };
            return;
        }
        public void addFarOpponents(List items){
            ArrayList boardItems = (ArrayList)((ArrayList) items).clone();
            while(!boardItems.isEmpty()){
                while(!boardItems.isEmpty()){
                    OpponentHandler opponent = (OpponentHandler) boardItems.get(0);
                    farOpponents.getChildren().add(opponent.getContent());
                    boardItems.remove(opponent);
                }
            };
            return;
        }

    }

