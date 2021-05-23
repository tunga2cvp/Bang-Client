package views.screen;

import controller.HomeController;
import entity.Room;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    public HomeScreenHandler(String screenPath, Stage stage) throws IOException {
        super(screenPath, stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeController = new HomeController();

        // Add the Open Room to Home Screen
        try {
            OpenRoomsHandler openRoomsHandler = new OpenRoomsHandler(Configs.OPEN_ROOMS_PATH);
            middleBox.getChildren().add(openRoomsHandler.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
