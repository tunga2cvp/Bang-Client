package controller;

import entity.Card;
import entity.Player;
import views.screen.BoardScreenHandler;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class BoardController {
    private static Player player;
    private static ArrayList<Player> farOpponents;
    private static Player leftOpponent;
    private static Player rightOpponent;
    public static ArrayList<Player> playersList;
    public static int playerNum;
    public static String playerRole;
    public static Card playingCard;
    public static int idSheriff;
    public static List<Card> cardList;
    static boolean isMyTurn;
    public static boolean isActionRequireTurn;

    public BoardController(){
//        playerNum = 6;
//        playerRole = "vice";
//        playersList = new ArrayList<Player>();
//
//        playersList.add(new Player(1,4,4));
//        playersList.add(new Player(2,4,4));
//        playersList.add(new Player(3,5,5));
//        playersList.add(new Player(4,4,4));
//        playersList.add(new Player(5,4,4));
//        playersList.add(new Player(6,4,4));
//        int numberOfPlayer = playersList.size();
//
//        // setting 2 closet opponents
//        if (playerNum == 1) {
//            rightOpponent = playersList.get(numberOfPlayer - 1);
//            leftOpponent = playersList.get(1);
//        } else if (playerNum == numberOfPlayer){
//            rightOpponent  = playersList.get(playerNum - 2);
//            leftOpponent = playersList.get(0);
//        } else{
//            rightOpponent = playersList.get(playerNum - 2);
//            leftOpponent = playersList.get(playerNum);
//        }
//
//        // setting list of far opponents
//        farOpponents = new ArrayList<Player>(playersList);
//        farOpponents.remove(playerNum-1); // remove player
//        farOpponents.remove(leftOpponent); // remove left opponent
//        farOpponents.remove(rightOpponent); // remove right opponent
//
//        System.out.println("left opponent" + leftOpponent.getPlayerNum());
//        System.out.println("right opponent" + rightOpponent.getPlayerNum());
//        System.out.println("a far opponent" + farOpponents.get(0).getPlayerNum());
    }
    public static void setOpponentPosition(){
        int numberOfPlayer = playersList.size();

        if ( numberOfPlayer >=4 ) {
            // setting 2 closet opponents
            if (playerNum == 0) {
                rightOpponent = playersList.get(numberOfPlayer - 1);
                leftOpponent = playersList.get(1);
            } else if (playerNum == numberOfPlayer - 1) {
                rightOpponent = playersList.get(playerNum - 1);
                leftOpponent = playersList.get(0);
            } else {
                rightOpponent = playersList.get(playerNum - 1);
                leftOpponent = playersList.get(playerNum);
            }

            // setting list of far opponents
            farOpponents = (ArrayList<Player>) playersList.clone();
            farOpponents.remove(playerNum); // remove player
            farOpponents.remove(leftOpponent); // remove left opponent
            farOpponents.remove(rightOpponent); // remove right opponent
        }
        if ( numberOfPlayer < 4 ){
            if (numberOfPlayer == 2){
                farOpponents = (ArrayList<Player>) playersList.clone();
                farOpponents.remove(playerNum);
            }
            else if ( numberOfPlayer == 3){
                ArrayList<Player> closeOpponent = (ArrayList<Player>) playersList.clone();
                closeOpponent.remove(playerNum);
                leftOpponent = closeOpponent.get(0);
                rightOpponent = closeOpponent.get(1);
            }
        }
        // setting self
        player = playersList.get(playerNum);
    }
    public List getCardList() {
        return cardList;
    }
    public static Player getPlayer(){
        return player;
    }
    public List getPlayerList(){
        return playersList;
    }
    public Player getLeftOpponent() {
        return leftOpponent;
    }

    public List getFarOpponents() {
        return farOpponents;
    }
    public static void setIsMyTurn(boolean status){
        isMyTurn = status;
    }

    public static boolean isIsMyTurn() {
        return isMyTurn;
    }

    public Player getRightOpponent() {
        return rightOpponent;
    }
    public Card getPlayingCard(){
        return playingCard;
    }
    public void setPlayingCard(Card card){
        this.playingCard = card;
    }
    public int getPlayerHealth(){
        return playersList.get(playerNum).getHealth();
    }
    public String getPlayerRole(){
        return playerRole;
    }
}
