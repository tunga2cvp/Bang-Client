package views.screen;

import controller.HomeController;
import controller.LogoutController;
import entity.Room;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.Configs;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeScreenHandler extends FXMLScreenHandler implements Initializable {
    List homeItems;
    HomeController homeController;
    @FXML
    AnchorPane middleBox;
    @FXML
    Button logoutBtn;

    public HomeScreenHandler(String screenPath, Stage stage) throws IOException {
        super(screenPath, stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeController = new HomeController();

        // Add the Open Room to Home Screen
        {
            Platform.runLater(()->{
                OpenRoomsHandler openRoomsHandler = null;
                try {
                    openRoomsHandler = new OpenRoomsHandler(Configs.OPEN_ROOMS_PATH);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                middleBox.getChildren().add(openRoomsHandler.getContent());
            });

        }
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
}
