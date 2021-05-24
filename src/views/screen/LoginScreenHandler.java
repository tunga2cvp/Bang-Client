package views.screen;

import controller.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Configs;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class LoginScreenHandler extends FXMLScreenHandler{
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Button loginBtn;
    @FXML
    Button signupBtn;
    LoginController loginController;
    public LoginScreenHandler(String screenPath, Stage stage) throws IOException {
        super(screenPath, stage);
        loginController = new LoginController();
        // set login button action
        loginBtn.setOnAction(e->{
            // send message
            try {
                loginController.sendMessage(username.getText(), password.getText());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }

            // manage UI
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            String result = loginController.getResult();
            while (result.equals("pending")) result = loginController.getResult();

            if ( result.equals("true")){
                // show home screen
                try {
                    HomeScreenHandler homeScreenHandler = new HomeScreenHandler(Configs.HOME_SCREEN_PATH,this.stage);
                    homeScreenHandler.show();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            else {
                // show popup error
                try {
                    PopUpHandler popUpHandler = new PopUpHandler(Configs.POPUP_PATH,new Stage());
                    popUpHandler.Login(false);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        // set signup button action
        signupBtn.setOnAction((e->{
            try {
                SignUpScreenHandler signUpScreenHandler = new SignUpScreenHandler(Configs.SIGNUP_SCREEN_PATH, this.stage);
                signUpScreenHandler.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }));


    }
}
