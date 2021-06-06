package entity.message;

import entity.LobbyStatus;

import java.util.Map;

public class LoginReceive {
    private Map<String, LobbyStatus> lobby;
    private boolean result;
    String type;
    String command;
    String msg;
    public Map<String, LobbyStatus> getLobby() {
        return lobby;
    }
}
