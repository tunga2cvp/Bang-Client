package entity.message;

public class ChatSend {
    String command;
    String content;
    public ChatSend(String content){
        command = "chat";
        this.content = content ;
    }
}
