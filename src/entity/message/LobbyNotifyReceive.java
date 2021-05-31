package entity.message;

import java.util.Map;

public class LobbyNotifyReceive extends Receive{
    private Map<String, Integer> lobby;
    public Map<String, Integer> getLobby() {
        return lobby;
    }
}
