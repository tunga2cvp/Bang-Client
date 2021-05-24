package entity.message;
import com.google.gson.Gson;
import controller.LoginController;
import controller.SignUpController;
import utils.Client;
import utils.JsonHandler;

public class Listener {
    public static void readReceiveMessage(String message) throws InterruptedException {
        String command = JsonHandler.getStringAttribute(message, "command");
        switch (command){
            case "signup":
                SignUpController signUpController = new SignUpController();
                signUpController.setResult(JsonHandler.getStringAttribute(message, "result"));
                System.out.println("result = " + JsonHandler.getStringAttribute(message, "result"));
                break;
            case "login":
                LoginController loginController = new LoginController();
                loginController.setResult(JsonHandler.getStringAttribute(message, "result"));
                System.out.println("login result = " + JsonHandler.getStringAttribute(message, "result"));
                break;
            default:
                System.out.println("unknown command");
                System.out.println(command);
        }
    }
}
