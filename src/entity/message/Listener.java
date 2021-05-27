package entity.message;
import com.google.gson.Gson;
import controller.LoginController;
import controller.OpenRoomsController;
import controller.SignUpController;
import entity.Room;
import utils.Client;
import utils.JsonHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class Listener {
    public static void readReceiveMessage(String message) throws InterruptedException {
        Gson gson = new Gson();
        String command = JsonHandler.getStringAttribute(message, "command");
        switch (command){
            case "signup":
                SignUpController signUpController = new SignUpController();
                signUpController.setResult(JsonHandler.getStringAttribute(message, "result"));
                System.out.println("result = " + JsonHandler.getStringAttribute(message, "result"));
                break;
            case "login":
                // get the login result
                LoginController loginController = new LoginController();
                loginController.setResult(JsonHandler.getStringAttribute(message, "result"));
                System.out.println("login result = " + JsonHandler.getStringAttribute(message, "result"));

                // get the open rooms
                LoginReceive loginReceive = gson.fromJson(message, LoginReceive.class);
                Set<String> set = loginReceive.getLobby().keySet();
                for (String key : set) {
                    System.out.println(key + " " + loginReceive.getLobby().get(key));
                    Room room = new Room(key, loginReceive.getLobby().get(key));
                    OpenRoomsController.roomList = new ArrayList<Room>();
                    OpenRoomsController.roomList.add(room);
                }
                break;
            case "logout":
                System.out.println("logout result = " + JsonHandler.getStringAttribute(message, "msg"));
                break;
            default:
                System.out.println("unknown command:" + command);
        }
    }
}
