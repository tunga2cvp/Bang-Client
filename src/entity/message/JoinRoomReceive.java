package entity.message;

import entity.Player;
import entity.User;

import java.util.ArrayList;

public class JoinRoomReceive extends Receive{
    boolean result;
    ArrayList<User> roomMembers = new ArrayList<User>();
}
