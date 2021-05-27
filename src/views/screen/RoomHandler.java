package views.screen;

import entity.Room;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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

    public RoomHandler(String screenPath, Room room) throws IOException {
        super(screenPath);
        this.room = room;
        Platform.runLater(()->{
            roomName.setText(room.getName());
            playerNum.setText(String.valueOf(room.getPlayerNum()));
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
