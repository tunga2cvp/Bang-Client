package entity;

import java.util.ArrayList;

public class Room {

    String name;
    int playerNum;

    public Room(String name, int playerNum ){
        this.name = name;
        this.playerNum = playerNum;
    }
    public int getPlayerNum() {
        return playerNum;
    }
    public String getName() {
        return name;
    }
}
