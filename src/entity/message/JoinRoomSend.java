package entity.message;

public class JoinRoomSend {
    String roomid;
    String command;
    public JoinRoomSend(String roomid, String command){
        this.roomid = roomid;
        this.command = command;
    }
}
