package views.screen;

import com.google.gson.Gson;
import entity.message.LeaveRoomSend;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utils.Client;
import utils.Configs;

import java.io.IOException;

public class EndGameHandler extends FXMLScreenHandler{
    @FXML
    Label content;
    @FXML
    Button homeBtn;

    public EndGameHandler(String screenPath, Stage stage) throws IOException {
        super(screenPath, stage);
    }
    public void EndGame(String winRole, Stage bigStage){
        content.setText(winRole);
        homeBtn.setOnAction(e->{
            // send leave room
            // send leave room message to server
            Gson gson = new Gson();
            LeaveRoomSend leaveRoomSend = new LeaveRoomSend();
            String json = gson.toJson(leaveRoomSend);
            try {
                Client.sendMessage(json);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            // change to home screen
            try {
                HomeScreenHandler.setHomeScreenHandler(Configs.HOME_SCREEN_PATH, bigStage);
                HomeScreenHandler.getHomescreenHandler().show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            // close the popup
            stage.close();
        });
        show();
    }
}
