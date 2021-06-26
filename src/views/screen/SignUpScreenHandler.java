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
    private static SignUpScreenHandler signupscreenhandler;

    public static void setSignupScreenHandler(String screenPath, Stage stage) throws IOException {
        signupscreenhandler = new SignUpScreenHandler(screenPath, stage);
    }
    public static SignUpScreenHandler getSignUpScreenHandler() throws IOException {
        return signupscreenhandler;
    }
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
        });
    }
    public void showSuccessPopUp(){
        // manage UI
            try {
                PopUpHandler popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                popUpHandler.SignUp(true, this.stage);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
    }
    public void showFailedPopUp(){
            try {
                PopUpHandler popUpHandler = new PopUpHandler(Configs.POPUP_PATH, new Stage());
                popUpHandler.SignUp(false, this.stage);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
    }
}
