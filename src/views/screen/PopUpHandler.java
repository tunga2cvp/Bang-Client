package views.screen;

import javafx.application.Platform;
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
    public void StartGameFailed(){
        Platform.runLater(()-> {
            content.setText("Start Game Failed!");
            instruction.setText("You are not the room host");
            actionBtn.setOnAction(e -> {
                stage.close();
            });
            actionBtn.setText("Close");
            show();
        });
    }
    public void ActionRequired(String attacker, String requiredCard){
        content.setText("You've been attacked by ".concat(attacker));
        instruction.setText("Play ".concat(requiredCard).concat(" or lose 1 health"));
        actionBtn.setOnAction(e -> {
            stage.close();
        });
        actionBtn.setText("Close");
        show();
    }
    public void NotYourTurn(){
        content.setText("Now is not your turn");
        instruction.setText("You idiot!");
        actionBtn.setOnAction(e -> {
            stage.close();
        });
        actionBtn.setText("Close");
        show();
    }
    public void NoCardSelected(){
        content.setText("No card was selected!");
        instruction.setText("Are you dumb?");
        actionBtn.setOnAction(e -> {
            stage.close();
        });
        actionBtn.setText("Close");
        show();
    }
    public void ActionDiscard(int numberOfCard){
        if ( numberOfCard <= 0){
            content.setText("You don't have to discard so many cards!");
            instruction.setText("You can end turn now");
        }
        else {
            content.setText("You have ".concat(String.valueOf(numberOfCard)).concat(" excess cards"));
            instruction.setText("Discard before you end turn!");

        }

        actionBtn.setOnAction(e -> {
            stage.close();
        });
        actionBtn.setText("Close");
        show();
    }
    public void CreateRoomFailed(){
        Platform.runLater(()-> {
            content.setText("Create Room failed!");
            instruction.setText("Please try again");
            actionBtn.setOnAction(e -> {
                stage.close();
            });
            actionBtn.setText("Close");
            show();
        });
    }
    public void JoinRoomFailed(){
        Platform.runLater(()-> {
            content.setText("Join Room failed!");
            instruction.setText("Please try again");
            actionBtn.setOnAction(e -> {
                stage.close();
            });
            actionBtn.setText("Close");
            show();
        });
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
    public void SignUp(boolean result, Stage bigstage){
        if (result){
            // show success message
            content.setText("Welcome to BANG!");
            instruction.setText("");
            actionBtn.setText("Go to login");
            actionBtn.setOnAction(e -> {
                stage.close();
                try {
                    LoginScreenHandler loginScreenHandler = new LoginScreenHandler(Configs.LOGIN_SCREEN_PATH, bigstage);
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
