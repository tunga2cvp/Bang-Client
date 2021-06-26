package views.screen;

import com.google.gson.Gson;
import controller.CreateRoomController;
import entity.message.ChatSend;
import entity.message.LeaveRoomSend;
import entity.message.StartGameSend;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    @FXML
    GridPane chatDisplay;
    @FXML
    TextField content;
    @FXML
    Button sendBtn;
    int i = 0;
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
        sendBtn.setOnAction(e->{
            ChatSend chatSend = new ChatSend(content.getText());
            Gson gson = new Gson();
            String json = gson.toJson(chatSend);
            try {
                Client.sendMessage(json);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            content.setText("");
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
    public void addChat(String playerName, String msg){
        Text playerText = new Text();
        playerText.setFont(Font.font("Baskerville Old Face", 15));
        playerText.setText(playerName.concat(":"));
        playerText.setFill(Color.RED);
        GridPane.setConstraints(playerText, 0, i);
        GridPane.setHalignment(playerText, HPos.CENTER);

        Text msgText = new Text();
        msgText.setFont(Font.font("Baskerville Old Face", 15));
        msgText.setText(msg);
        msgText.setWrappingWidth(100);
        GridPane.setConstraints(msgText, 1, i);
        GridPane.setHalignment(msgText, HPos.LEFT);

        i++;
        chatDisplay.getChildren().addAll(playerText, msgText);
    }
}
