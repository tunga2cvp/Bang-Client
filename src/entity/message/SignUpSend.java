package entity.message;

public class SignUpSend extends Send{
    String type = "signup";
    String username;
    String password;
    public SignUpSend(String command, String msg, String username, String password) {
        super(command, msg);
        this.username = username;
        this.password = password;
    }
}
