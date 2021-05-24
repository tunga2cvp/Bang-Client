package controller;

import com.google.gson.Gson;
import entity.message.LoginSend;
import entity.message.LogoutSend;
import utils.Client;

import java.io.IOException;

public class LogoutController {
    public void sendMessage(String message) throws IOException, InterruptedException {
        LogoutSend logoutSend = new LogoutSend("");
        Gson gson = new Gson();

        // Serialization
        String json = gson.toJson(logoutSend);
        System.out.println(json);

        // send to server
        Client.sendMessage(json);
    }
}
