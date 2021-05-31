package entity;

import java.util.ArrayList;

public class Room {

    int playerNum;
    private ArrayList<User> members = new ArrayList<>();
    private String hostname;
    private int maxMember = 4;

    public Room(String name, int playerNum ){
        this.hostname = name;
        this.playerNum = playerNum;
    }
    public int getPlayerNum() {
        return playerNum;
    }
    public String getName() {
        return hostname;
    }

    public ArrayList<User> getMembers() {
        return members;
    }
}
