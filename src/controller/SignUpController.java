package controller;
import com.google.gson.Gson;
import entity.message.Listener;
import entity.message.SignUpSend;
import utils.Client;
import views.screen.FXMLScreenHandler;

import java.io.IOException;

public class SignUpController {
    private static String result = "pending";
    public void setResult(String result){
        this.result = result;
    }
    public String getResult(){
        return result;
    }
    public void sendMessage(String username, String password) throws IOException, InterruptedException {
        SignUpSend signUpSend = new SignUpSend("",username, password);
        Gson gson = new Gson();
        // Serialization
        String json = gson.toJson(signUpSend);
        System.out.println(json);
        // send to server
        Client.sendMessage(json);
    }
}
