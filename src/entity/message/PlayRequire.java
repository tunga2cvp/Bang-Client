package entity.message;

public class PlayRequire {
    String command;
    String cardName;
    int id_player;
    public PlayRequire(String cardName, int id_player){
        command = "play_required";
        this.cardName = cardName;
        this.id_player = id_player;
    }
}
