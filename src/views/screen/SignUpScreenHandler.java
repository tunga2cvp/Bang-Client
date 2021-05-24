package views.screen;

import controller.SignUpController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Configs;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SignUpScreenHandler extends FXMLScreenHandler{
    @FXML
    TextField fullname;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Button signupBtn;
    SignUpController signUpController;
    public SignUpScreenHandler(String screenPath, Stage stage) throws IOException {
        super(screenPath, stage);
        signUpController = new SignUpController();
        signupBtn.setOnAction(e->{

            // send signup message
            try {
                signUpController.sendMessage(username.getText(), password.getText());
            } catch (IOException | InterruptedException ioException) {
                ioException.printStackTrace();
            }

            // manage UI
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            System.out.println("result is " + signUpController.getResult());
            if (signUpController.getResult().equals("pending")){

            }
            else if ( signUpController.getResult().equals("true")) {
                try {
                    PopUpHandler popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                    popUpHandler.SignUp(true);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            else {
                try {
                    PopUpHandler popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                    popUpHandler.SignUp(false);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });
    }
}
