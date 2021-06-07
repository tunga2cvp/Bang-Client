package controller;

import entity.Player;
import entity.Room;

import java.util.ArrayList;
import java.util.List;

public class LobbyController {
    public static List<Player> playerList ;
    public static String roomName;

    public List getPlayerList(){
        if (playerList != null) {
            return playerList;
        }
        else {
            playerList = new ArrayList<Player>();
            Player host = new Player(roomName);
            playerList.add(host);
            return playerList;
        }
//        playerList = new ArrayList<Player>();;
//        Player player1 = new Player("zebub");
//        playerList.add(player1);
//        return playerList;
    }
    public String getRoomName(){
        return roomName;
    }
}
