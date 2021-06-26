package views.screen;

import com.google.gson.Gson;
import controller.HomeController;
import controller.LogoutController;
import entity.Room;
import entity.message.CreateRoomSend;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeScreenHandler extends FXMLScreenHandler implements Initializable {

    private static HomeScreenHandler homescreenhandler;

    public static void setHomeScreenHandler(String screenPath, Stage stage) throws IOException {
        homescreenhandler = new HomeScreenHandler(screenPath, stage);
    }
    public static HomeScreenHandler getHomescreenHandler() throws IOException {
        return homescreenhandler;
    }

    List homeItems;
    HomeController homeController;
    @FXML
    AnchorPane middleBox;
    @FXML
    Button logoutBtn;
    @FXML
    Button createRoomBtn;

    public HomeScreenHandler(String screenPath, Stage stage) throws IOException {
        super(screenPath, stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeController = new HomeController();

        // Add the Open Room to Home Screen
        {
            Platform.runLater(()->{
                try {
                    OpenRoomsHandler.setOpenroomhandler(Configs.OPEN_ROOMS_PATH);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    middleBox.getChildren().add(OpenRoomsHandler.getOpenroomhandlerHandler().getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }
        createRoomBtn.setOnAction(e->{
            CreateRoomSend createRoomSend = new CreateRoomSend();
            Gson gson = new Gson();
            // Serialization
            String json = gson.toJson(createRoomSend);
//            System.out.println(json);
            // send to server
            try {
                Client.sendMessage(json);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        logoutBtn.setOnAction(e->{
            LogoutController logoutController = new LogoutController();
            try {
                logoutController.sendMessage("");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            LoginScreenHandler loginScreenHandler = null;
            try {
                loginScreenHandler = new LoginScreenHandler(Configs.LOGIN_SCREEN_PATH, this.stage);
                loginScreenHandler.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }
    public void reload(){
//        middleBox.getChildren().clear();
        Platform.runLater(()->{
            try {
                OpenRoomsHandler.setOpenroomhandler(Configs.OPEN_ROOMS_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                middleBox.getChildren().add(OpenRoomsHandler.getOpenroomhandlerHandler().getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public void showroom() throws IOException {
        Platform.runLater(()-> {
            try {
                LobbyScreenHandler.setLobbyScreenHandler(Configs.LOBBY_SCREEN_PATH, this.stage);
                LobbyScreenHandler.getLobbyScreenHandler().show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
