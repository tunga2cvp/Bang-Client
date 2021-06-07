package views.screen;

import com.google.gson.Gson;
import controller.BoardController;
import entity.Card;
import entity.Player;
import entity.message.EndturnSend;
import entity.message.PlayDiscard;
import entity.message.PlayRequire;
import entity.message.PlayTurnSend;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Client;
import utils.Configs;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BoardScreenHandler extends FXMLScreenHandler implements Initializable {
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
    @FXML
    Label playerTurn;
    @FXML
    private  ImageView equippedCard;
    @FXML
    Label myName;
    public BoardScreenHandler(String screenPath, Stage stage) throws IOException {
        super(screenPath, stage);
    }
    private static BoardScreenHandler boardscreenhandler;

    public static void setBoardScreenHandler(String screenPath, Stage stage) throws IOException {
        boardscreenhandler = new BoardScreenHandler(screenPath, stage);
    }
    public static BoardScreenHandler getBoardScreenHandler() throws IOException {
        return boardscreenhandler;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            boardController = new BoardController();
            // set my name
        if ( boardController.getPlayer() != null){
            myName.setText(boardController.getPlayer().getName());
        }
            // Add my equipped card to the board
        if (BoardController.getPlayer().getEquippedCard()!=null && !"".equals(BoardController.getPlayer().getEquippedCard().getName())) {
            File file = new File(BoardController.getPlayer().getEquippedCard().getImageURL());
            Image image = new Image(file.toURI().toString());
            equippedCard.setImage(image);
        }
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
            if (farOpponents != null) {
                List opponentItems = new ArrayList<>();
                for (Object object : farOpponents) {
                    Player player = (Player) object;
                    OpponentHandler o1 = null;
                    try {
                        o1 = new OpponentHandler(Configs.OPPONENT_PATH, player);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    opponentItems.add(o1);
                }
                addFarOpponents(opponentItems);
            }
            // Add 2 close opponents to the board
            // Add left opponent
        Player leftOpponent = boardController.getLeftOpponent();
        if ( leftOpponent != null) {
            OpponentHandler ol = null;
            try {
                ol = new OpponentHandler(Configs.OPPONENT_PATH, leftOpponent);
            } catch (IOException e) {
                e.printStackTrace();
            }
            leftOpponentPlace.getChildren().add(ol.getContent());
        }
        // Add right opponent
        Player rightOpponent = boardController.getRightOpponent();
        if (rightOpponent != null) {
            OpponentHandler or = null;
            try {
                or = new OpponentHandler(Configs.OPPONENT_PATH, rightOpponent);
            } catch (IOException e) {
                e.printStackTrace();
            }
            rightOpponentPlace.getChildren().add(or.getContent());
        }
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
            Gson gson = new Gson();
            if (BoardController.playingCard == null) {
//                System.out.println("no card where selected");
                // if in turn then notify user that no card was selected
                if ( BoardController.isIsMyTurn()){
                    try {
                        PopUpHandler popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                        popUpHandler.NoCardSelected();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                // if playing in action require then sending None card
                else{
                    PlayRequire playNone = new PlayRequire("None", BoardController.playerNum);
                    String playNoneMsg = gson.toJson(playNone);
                    try {
                        Client.sendMessage(playNoneMsg);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
            else
            switch (BoardController.playingCard.getName()){
                case "Bang":
                    // if is my turn then show popup to choose target
                    if ( BoardController.isIsMyTurn()){
                        // showing popup
                        try {
                            TargetPopupHandler targetPopupHandler = new TargetPopupHandler(Configs.TARGET_POPUP_PATH, new Stage());
                            targetPopupHandler.show();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    // else means playing in action required
                    else {
                        PlayRequire bangPlay = new PlayRequire("Bang", BoardController.playerNum);
                        String bangPlayMsg = gson.toJson(bangPlay);
                        try {
                            Client.sendMessage(bangPlayMsg);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        BoardController.playingCard = null;
                    }
                    break;
                case "Miss":
                    PlayRequire playRequire = new PlayRequire("Miss", BoardController.playerNum);
                    String playRequireMsg = gson.toJson(playRequire);
                    try {
                        Client.sendMessage(playRequireMsg);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    BoardController.playingCard = null; // reset after play a card
                    break;
                case "Gun":
                    PlayTurnSend playTurnSend = new PlayTurnSend("Gun", "SELF_TARGET","",BoardController.playerNum );
                    String json = gson.toJson(playTurnSend);
                    try {
                        Client.sendMessage(json);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    BoardController.playingCard = null; // reset after play a card
                    break;
                case "Indian":
                    PlayTurnSend playTurnSend1 = new PlayTurnSend("Indian", "ALL_TARGET","", BoardController.playerNum);
                    String json1 = gson.toJson(playTurnSend1);
                    try {
                        Client.sendMessage(json1);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    BoardController.playingCard = null; // reset after play a card
                    break;
                case "Beer":
                    PlayTurnSend playBeer = new PlayTurnSend("Beer", "SELF_TARGET","",BoardController.playerNum );
                    String playBeerMsg = gson.toJson(playBeer);
                    try {
                        Client.sendMessage(playBeerMsg);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    BoardController.playingCard = null; // reset after play a card
                    break;
                default:
                    System.out.println(BoardController.playingCard.getName());
            }
        });

        discardBtn.setOnAction(e->{
            // only used when in turn
            if ( BoardController.isIsMyTurn()){
                Gson gson = new Gson();
                if (BoardController.playingCard == null) System.out.println("no card where selected");
                else {
                    PlayDiscard playDiscard = new PlayDiscard(BoardController.playingCard.getName(), BoardController.playerNum);
                    String json = gson.toJson(playDiscard);
                    try {
                        Client.sendMessage(json);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    BoardController.playingCard = null; // reset after discard a card
                }
            }
            // notify if click when not in turn
            else {
                PopUpHandler popUpHandler = null;
                try {
                    popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                popUpHandler.NotYourTurn();
            }
        });
        endturnBtn.setOnAction(e->{
            if ( BoardController.isIsMyTurn()){
                Gson gson = new Gson();
                EndturnSend endturnSend = new EndturnSend();
                String endturnMsg = gson.toJson(endturnSend);
                try {
                    Client.sendMessage(endturnMsg);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            else {
                PopUpHandler popUpHandler = null;
                try {
                    popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                popUpHandler.NotYourTurn();
            }
        });
    }

    public void addCardBoard(List items){
        ArrayList boardItems = (ArrayList)((ArrayList) items).clone();
        cards.getChildren().clear();
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
        public void reload(){
            Platform.runLater(()->{

            });
        }
        public void setTurn(String player){
            playerTurn.setText(player);
        }

        public void reloadPlayerStatus(){
            // clear old player
            farOpponents.getChildren().clear();
            leftOpponentPlace.getChildren().clear();
            rightOpponentPlace.getChildren().clear();

            // Add my equipped card to the board
            if ( BoardController.getPlayer() != null){
                if (BoardController.getPlayer().getEquippedCard()!=null && !"".equals(BoardController.getPlayer().getEquippedCard().getName())) {
                    File file = new File(BoardController.getPlayer().getEquippedCard().getImageURL());
                    Image image = new Image(file.toURI().toString());
                    equippedCard.setImage(image);
                }
            }

            // Add opponents to the board
            List farOpponents = boardController.getFarOpponents();
            if (farOpponents != null) {
                List opponentItems = new ArrayList<>();
                for (Object object : farOpponents) {
                    Player player = (Player) object;
                    OpponentHandler o1 = null;
                    try {
                        o1 = new OpponentHandler(Configs.OPPONENT_PATH, player);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    opponentItems.add(o1);
                }
                addFarOpponents(opponentItems);
            }

            // Add 2 close opponents to the board
            // Add left opponent
            Player leftOpponent = boardController.getLeftOpponent();
            if ( leftOpponent != null) {
                OpponentHandler ol = null;
                try {
                    ol = new OpponentHandler(Configs.OPPONENT_PATH, leftOpponent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                leftOpponentPlace.getChildren().add(ol.getContent());
            }
            // Add right opponent
            Player rightOpponent = boardController.getRightOpponent();
            if (rightOpponent != null) {
                OpponentHandler or = null;
                try {
                    or = new OpponentHandler(Configs.OPPONENT_PATH, rightOpponent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                rightOpponentPlace.getChildren().add(or.getContent());
            }
            // set player health
            if (boardController.getPlayer() != null) {
                playerHealth.setText(String.valueOf(boardController.getPlayerHealth()));
            }
        }
        public void reloadCardList(){
            // remove old cards
            cards.getChildren().clear();
            // setting new cards
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
        }
        public void setDeathPlayerButtons(){
            playBtn.setOnAction(e->{
                try {
                    PopUpHandler popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                    popUpHandler.DeadButtons();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            });
            discardBtn.setOnAction(e->{
                try {
                    PopUpHandler popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                    popUpHandler.DeadButtons();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            });
            endturnBtn.setOnAction(e->{
                try {
                    PopUpHandler popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                    popUpHandler.DeadButtons();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            });
        }
        public void reloadButtons(){
        // clear old function
            playBtn.setOnAction(null);
            discardBtn.setOnAction(null);
            endturnBtn.setOnAction(null);
            // setting new function
            playBtn.setOnAction(e-> {

                if (!BoardController.isIsMyTurn() && !BoardController.isActionRequireTurn) {
                    PopUpHandler popUpHandler = null;
                    try {
                        popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    popUpHandler.NotYourTurn();
                } else {
                    Gson gson = new Gson();
                    // if no card were selected to play

                if (BoardController.playingCard == null) {
                    // if in turn then notify user that no card was selected
                    if (BoardController.isIsMyTurn()) {
                        try {
                            PopUpHandler popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                            popUpHandler.NoCardSelected();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    // if playing in action require then sending None card
                    else {
                        PlayRequire playNone = new PlayRequire("None", BoardController.playerNum);
                        String playNoneMsg = gson.toJson(playNone);
                        try {
                            Client.sendMessage(playNoneMsg);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
                // if there i a card to play
                else
                    switch (BoardController.playingCard.getName()) {
                        case "Bang":
                            // if is my turn then show popup to choose target
                            if (BoardController.isIsMyTurn()) {
                                // showing popup
                                try {
                                    TargetPopupHandler targetPopupHandler = new TargetPopupHandler(Configs.TARGET_POPUP_PATH, new Stage());
                                    targetPopupHandler.show();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                            // else means playing in action required
                            else {
                                PlayRequire bangPlay = new PlayRequire("Bang", BoardController.playerNum);
                                String bangPlayMsg = gson.toJson(bangPlay);
                                try {
                                    Client.sendMessage(bangPlayMsg);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                                BoardController.playingCard = null;
                                BoardController.isActionRequireTurn = false;
                                try {
                                    BoardScreenHandler.getBoardScreenHandler().reloadButtons();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                            break;
                        case "Miss":
                            PlayRequire playRequire = new PlayRequire("Miss", BoardController.playerNum);
                            String playRequireMsg = gson.toJson(playRequire);
                            try {
                                Client.sendMessage(playRequireMsg);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            BoardController.playingCard = null; // reset after play a card
                            break;
                        case "Gun":
                            PlayTurnSend playTurnSend = new PlayTurnSend("Gun", "SELF_TARGET", "", BoardController.playerNum);
                            String json = gson.toJson(playTurnSend);
                            try {
                                Client.sendMessage(json);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            BoardController.playingCard = null; // reset after play a card
                            break;
                        case "Indian":
                            PlayTurnSend playTurnSend1 = new PlayTurnSend("Indian", "ALL_TARGET", "", BoardController.playerNum);
                            String json1 = gson.toJson(playTurnSend1);
                            try {
                                Client.sendMessage(json1);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            BoardController.playingCard = null; // reset after play a card
                            break;
                        case "Beer":
                            PlayTurnSend playBeer = new PlayTurnSend("Beer", "SELF_TARGET","",BoardController.playerNum );
                            String playBeerMsg = gson.toJson(playBeer);
                            try {
                                Client.sendMessage(playBeerMsg);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            BoardController.playingCard = null; // reset after play a card
                            break;
                        default:
                            System.out.println("no card where selected");
                    }
            }
            });

            discardBtn.setOnAction(e->{
                // only used when in turn
                if ( BoardController.isIsMyTurn()){
                    Gson gson = new Gson();
                    if (BoardController.playingCard == null) System.out.println("no card where selected");
                    else {
                        PlayDiscard playDiscard = new PlayDiscard(BoardController.playingCard.getName(), BoardController.playerNum);
                        String json = gson.toJson(playDiscard);
                        try {
                            Client.sendMessage(json);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        BoardController.playingCard = null; // reset after discard a card
                    }
                }
                // notify if click when not in turn
                else {
                    PopUpHandler popUpHandler = null;
                    try {
                        popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    popUpHandler.NotYourTurn();
                }
            });
            endturnBtn.setOnAction(e->{
                if ( BoardController.isIsMyTurn()){
                    Gson gson = new Gson();
                    EndturnSend endturnSend = new EndturnSend();
                    String endturnMsg = gson.toJson(endturnSend);
                    try {
                        Client.sendMessage(endturnMsg);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                else {
                    PopUpHandler popUpHandler = null;
                    try {
                        popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    popUpHandler.NotYourTurn();
                }
            });
        }
    }

