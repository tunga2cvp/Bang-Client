package controller;

import entity.Room;

import java.util.ArrayList;
import java.util.List;

public class OpenRoomsController {

    public List getOpenRoomsList(){
        List<Room> roomList = new ArrayList<Room>();
        roomList.add(new Room("room1",2));
        roomList.add(new Room("room2",3));
        roomList.add(new Room("room3",4));
        roomList.add(new Room("r",5));
        roomList.add(new Room("tung's room",6));
        return roomList;
    }
}
