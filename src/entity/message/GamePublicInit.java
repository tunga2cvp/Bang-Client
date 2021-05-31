package entity.message;

import entity.PlayerPublicInitial;

import java.util.ArrayList;

public class GamePublicInit {
    private ArrayList<PlayerPublicInitial> playerList = new ArrayList<>();
    private int idSheriff;

    public ArrayList<PlayerPublicInitial> getPlayerList() {
        return playerList;
    }
    public int getIdSheriff(){
        return idSheriff;
    }
}
