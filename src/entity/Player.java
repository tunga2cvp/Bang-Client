package entity;

import java.util.ArrayList;
import java.util.List;

public class Player {
    int playerNum;
    int cardNum;
    int health;
    boolean isSheriff;
    Card equippedCard;
    String role;
    public Player(){
    }
    public Player(int playerNum, int cardNum, int health) {
        this.playerNum = playerNum;
        this.cardNum = cardNum;
        this.health = health;
        isSheriff = (health == 5);
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
}
