package entity.message;

public class Receive {

    private String command;
    String type;
    String msg;
    public Receive() {

    }

    public Receive(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}