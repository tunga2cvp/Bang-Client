package controller;

import com.google.gson.Gson;
import entity.message.SignUpSend;
import utils.Client;

import java.io.IOException;

public class LoginController {
    private static String result = "pending";

    public void setResult(String result){
        this.result = result;
    }
    public String getResult(){
        return result;
    }
    public void sendMessage(String username, String password) throws IOException, InterruptedException {
        SignUpSend signUpSend = new SignUpSend("login","",username, password);
        Gson gson = new Gson();
        // Serialization
        String json = gson.toJson(signUpSend);
        System.out.println(json);
        // send to server
        Client.sendMessage(json);
    }
}
