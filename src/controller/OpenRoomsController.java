package controller;

import entity.Room;

import java.util.ArrayList;
import java.util.List;

public class OpenRoomsController {
    public static List<Room> roomList;
    public List getOpenRoomsList(){
        if (roomList != null)
            return roomList;
        else return new ArrayList<Room>();
    }
}
