package entity;

import java.util.ArrayList;

public class Room {

    int playerNum;
    private ArrayList<User> members = new ArrayList<>();
    private String hostname;
    private int maxMember = 4;
    boolean isPlaying;

    public Room(String name, int playerNum ){
        this.hostname = name;
        this.playerNum = playerNum;
    }
    public Room(String name, int playerNum, boolean status){
        this.hostname = name;
        this.playerNum = playerNum;
        isPlaying = status;
    }
    public int getPlayerNum() {
        return playerNum;
    }
    public String getName() {
        return hostname;
    }
    public boolean isPlaying(){
        return isPlaying;
    }
    public ArrayList<User> getMembers() {
        return members;
    }
    public void setPlaying(boolean status){
        isPlaying = status;
    }
}
