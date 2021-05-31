package entity;

import java.util.ArrayList;
import java.util.List;

public class Player {
    String name;
    int playerNum;
    int cardNum;
    int health;
    boolean isSheriff;
    Card equippedCard;
    String role;
    public Player(){
    }
    public Player(String name){
        this.name = name;
    }
    public Player(String name, int playerNum, int cardNum, int health, String equippedCard) {
        this.name = name;
        this.playerNum = playerNum;
        this.cardNum = cardNum;
        this.health = health;
        isSheriff = false;
        if ( equippedCard!= null) {
            Card card = new Card(equippedCard);
            this.equippedCard = card;
        }
    }

    public void setIsSheriff(){
        isSheriff = true;
    }
    public int getPlayerNum() {
        return playerNum;
    }
    public int getCardNum(){
        return cardNum;
    }
    public int getHealth(){
        return health;
    }
    public boolean getIsSheriff(){
        return isSheriff;
    }
    public Card getEquippedCard(){
        return equippedCard;
    }
    public String getRole(){
        return  role;
    }
    public String getName(){
        return name;
    }
}
