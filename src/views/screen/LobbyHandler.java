package views.screen;

import controller.LobbyController;
import controller.OpenRoomsController;
import entity.Player;
import entity.Room;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LobbyHandler extends FXMLScreenHandler implements Initializable {
    private static LobbyHandler lobbyhandler;
    @FXML
    VBox playerList;
    @FXML
    Label roomName;

    public static void setLobbyHandler(String screenPath) throws IOException {
        lobbyhandler = new LobbyHandler(screenPath);
    }
    public static LobbyHandler getLobbyHandler() throws IOException {
        return lobbyhandler;
    }
    public LobbyHandler(String screenPath) throws IOException {
        super(screenPath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LobbyController lobbyController = new LobbyController();
        //Add open rooms to the board
        Platform.runLater(()->{
            roomName.setText(lobbyController.getRoomName().concat("'s room"));
            List playerList = lobbyController.getPlayerList();

            List playerItems = new ArrayList<>();
            for (Object object : playerList) {
                Player player = (Player) object;
                PlayerHandler c1 = null;
                try {
                    c1 = new PlayerHandler(Configs.PLAYER_PATH, player);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                playerItems.add(c1);
            }
            addPlayer(playerItems);
        });
    }
    public void addPlayer(List items){
        ArrayList playerItems = (ArrayList)((ArrayList) items).clone();
        while(!playerItems.isEmpty()){
            PlayerHandler player = (PlayerHandler) playerItems.get(0);
            playerList.getChildren().add(player.getContent());
            playerItems.remove(player);
        };
    }
}
