package views.screen;

import com.google.gson.Gson;
import entity.Me;
import entity.Room;
import entity.message.JoinRoomSend;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import utils.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RoomHandler extends FXMLScreenHandler implements Initializable {
    @FXML
    Label roomName;
    @FXML
    Label playerNum;
    @FXML
    AnchorPane roomSpace;
    @FXML
    Button roomBtn;
    Room room;
    @FXML
    Label status;

    public RoomHandler(String screenPath, Room room) throws IOException {
        super(screenPath);
        this.room = room;
        Platform.runLater(()->{
            roomName.setText(room.getName());
            playerNum.setText(String.valueOf(room.getPlayerNum()));
        });
        // set room status
        if ( room.isPlaying()){
            status.setText("playing");
        }
        else{
            status.setText("open");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            roomBtn.setOnAction(e->{
                JoinRoomSend joinRoomSend = new JoinRoomSend(room.getName(),"joinroom");
                Gson gson = new Gson();
                // Serialization
                String json = gson.toJson(joinRoomSend);
                // send to server
                try {
                    Client.sendMessage(json);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            roomName.setText(room.getName());
            playerNum.setText(String.valueOf(room.getPlayerNum()));

        });
    }
}
