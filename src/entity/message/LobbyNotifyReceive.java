package entity.message;

import entity.LobbyStatus;

import java.util.Map;

public class LobbyNotifyReceive extends Receive{
    private Map<String, LobbyStatus> lobby;
    public Map<String, LobbyStatus> getLobby() {
        return lobby;
    }
}
