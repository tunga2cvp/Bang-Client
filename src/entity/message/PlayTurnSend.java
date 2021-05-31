package entity.message;

public class PlayTurnSend {
    String command;
    private String cardName;
    private String targetType;
    private String target;
    protected int id_player;
    public PlayTurnSend( String cardName, String targetType, String target, int id_player){
        this.cardName = cardName;
        this.targetType = targetType;
        this.target = target;
        this.id_player = id_player;
        command = "play_turn";
    }
}
