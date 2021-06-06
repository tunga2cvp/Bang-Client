package entity.message;

public class SignUpSend extends Send{
    String username;
    String password;
    public SignUpSend(String msg, String username, String password) {
        super("signup", msg);
        this.username = username;
        this.password = password;
    }
}
