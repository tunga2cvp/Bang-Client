package views.screen;

import controller.SignUpController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Configs;

import java.io.IOException;

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
            if ( signUpController.getResult().equals("Success")) {
                System.out.println("fullname " + fullname.getText());
                System.out.println("username " + username.getText());
                System.out.println("password " + password.getText());
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
