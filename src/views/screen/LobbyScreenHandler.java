package views.screen;

import com.google.gson.Gson;
import controller.CreateRoomController;
import entity.message.LeaveRoomSend;
import entity.message.StartGameSend;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.Client;
import utils.Configs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LobbyScreenHandler extends FXMLScreenHandler implements Initializable {
    CreateRoomController createRoomController;
    @FXML
    AnchorPane middleBox;
    @FXML
    Button leaveRoomBtn;
    @FXML
    Button startGameBtn;
    private static LobbyScreenHandler lobbyscreenhandler;

    public static void setLobbyScreenHandler(String screenPath, Stage stage) throws IOException {
        lobbyscreenhandler = new LobbyScreenHandler(screenPath, stage);
    }
    public static LobbyScreenHandler getLobbyScreenHandler() throws IOException {
        return lobbyscreenhandler;
    }
    public LobbyScreenHandler(String screenPath, Stage stage) throws IOException {
        super(screenPath, stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createRoomController = new CreateRoomController();

        // add lobby to home screen
        Platform.runLater(()->{
            try {
                LobbyHandler.setLobbyHandler(Configs.LOBBY_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                middleBox.getChildren().add(LobbyHandler.getLobbyHandler().getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // set action for leave room btn
        leaveRoomBtn.setOnAction(e->{
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
                HomeScreenHandler.setHomeScreenHandler(Configs.HOME_SCREEN_PATH, this.stage);
                HomeScreenHandler.getHomescreenHandler().show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        //set action for start game btn
        startGameBtn.setOnAction(e->{
            //send start game message
            Gson gson = new Gson();
            StartGameSend startGameSend = new StartGameSend();
            String json = gson.toJson(startGameSend);
            try {
                Client.sendMessage(json);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }
    public void  reload(){
        Platform.runLater(()->{
            try {
                LobbyHandler.setLobbyHandler(Configs.LOBBY_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                middleBox.getChildren().add(LobbyHandler.getLobbyHandler().getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public void showBoard() throws IOException {
        BoardScreenHandler.setBoardScreenHandler(Configs.BOARD_SCREEN_PATH, this.stage);
        BoardScreenHandler.getBoardScreenHandler().show();
    }
}
