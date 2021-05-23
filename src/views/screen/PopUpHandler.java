package views.screen;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utils.Configs;

import java.io.IOException;

public class PopUpHandler extends FXMLScreenHandler{
    @FXML
    Label content;
    @FXML
    Label instruction;
    @FXML
    Button actionBtn;
    public PopUpHandler(String screenPath, Stage stage) throws IOException {
        super(screenPath, stage);
    }
    public void Login(Boolean result){
        if (result) {
            // show success
        }
        else{
            content.setText("Wrong user name or password!");
            instruction.setText("Please try again");
            actionBtn.setOnAction(e -> {
                stage.close();
            });
            actionBtn.setText("Close");
            show();
        }
    }
    public void SignUp(boolean result){
        if (result){
            // show success message
            content.setText("Welcome to BANG!");
            instruction.setText("");
            actionBtn.setText("Go to login");
            actionBtn.setOnAction(e -> {
                stage.close();
                try {
                    LoginScreenHandler loginScreenHandler = new LoginScreenHandler(Configs.LOGIN_SCREEN_PATH, this.stage);
                    loginScreenHandler.show();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            show();
        }
        else {
            content.setText("Sign Up Failed!");
            instruction.setText("Please try again");
            actionBtn.setOnAction(e -> {
                stage.close();
            });
            actionBtn.setText("Close");
            show();
        }
    }
}
