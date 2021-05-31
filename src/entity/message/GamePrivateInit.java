package entity.message;

import entity.GameRole;

import java.util.ArrayList;

public class GamePrivateInit {
    private ArrayList<String> cardsHand = new ArrayList<>();
    private String role;
    private int id_player;
    public ArrayList<String> getCardsHand() {
        return cardsHand;
    }

    public String getRole() {
        return role;
    }
    public int getId_player(){
        return id_player;
    }
}
