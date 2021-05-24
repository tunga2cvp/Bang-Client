package entity.message;

public class LoginSend extends Send{
    String type = "signup";
    String username;
    String password;
    public LoginSend(String msg, String username, String password) {
        super("login", msg);
        this.username = username;
        this.password = password;
    }
}
