package views.screen;

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

    public LoginScreenHandler(String screenPath, Stage stage) throws IOException {
        super(screenPath, stage);
        loginBtn.setOnAction(e->{
            System.out.println("user name" + username.getText());
            System.out.println("password" + password.getText());
        });
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
