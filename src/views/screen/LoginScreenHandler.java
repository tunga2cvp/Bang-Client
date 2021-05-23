package views.screen;

import controller.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Configs;

import java.io.IOException;

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
            System.out.println("user name" + username.getText());
            System.out.println("password" + password.getText());
            String result = loginController.getResult();
            if ( result.equals("Success")){
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
