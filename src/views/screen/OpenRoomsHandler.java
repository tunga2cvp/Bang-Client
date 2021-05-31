package views.screen;

import controller.OpenRoomsController;
import entity.Room;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OpenRoomsHandler extends FXMLScreenHandler implements Initializable {
    @FXML
    VBox roomList;
    private static OpenRoomsHandler openroomhandler;

    public static void setOpenroomhandler(String screenPath) throws IOException {
        openroomhandler = new OpenRoomsHandler(screenPath);
    }
    public static OpenRoomsHandler getOpenroomhandlerHandler() throws IOException {
        return openroomhandler;
    }
    public OpenRoomsHandler(String screenPath) throws IOException {
        super(screenPath);
    }
    public void reload(){
        Platform.runLater(()->{
            roomList.getChildren().clear();
            OpenRoomsController openRoomsController = new OpenRoomsController();
            List roomList = openRoomsController.getOpenRoomsList();
            List roomItems = new ArrayList<>();
            for (Object object : roomList) {
                Room room = (Room) object;
                RoomHandler c1 = null;
                try {
                    c1 = new RoomHandler(Configs.ROOM_PATH, room);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                roomItems.add(c1);
            }
            addRoom(roomItems);
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OpenRoomsController openRoomsController = new OpenRoomsController();
         //Add open rooms to the board
        Platform.runLater(()->{
        List roomList = openRoomsController.getOpenRoomsList();
        List roomItems = new ArrayList<>();
        for (Object object : roomList) {
            Room room = (Room) object;
            RoomHandler c1 = null;
            try {
                c1 = new RoomHandler(Configs.ROOM_PATH, room);
            } catch (IOException e) {
                e.printStackTrace();
            }
            roomItems.add(c1);
        }
            addRoom(roomItems);
        });
    }
    public void addRoom(List items){
        ArrayList openRoomItems = (ArrayList)((ArrayList) items).clone();
            while(!openRoomItems.isEmpty()){
                RoomHandler room = (RoomHandler) openRoomItems.get(0);
                roomList.getChildren().add(room.getContent());
                openRoomItems.remove(room);
        };
    }
}
