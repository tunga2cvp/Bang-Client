package entity.message;

import java.util.Map;

public class LoginReceive {
    private Map<String, Integer> lobby;
    private boolean result;
    String type;
    String command;
    String msg;
    public Map<String, Integer> getLobby() {
        return lobby;
    }
}
