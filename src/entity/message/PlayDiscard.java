package entity.message;

import java.util.ArrayList;

public class PlayDiscard {
    private ArrayList<String> cardName;
    String command;
    int id_player;
    public PlayDiscard(String card, int id_player){
        cardName = new ArrayList<String>();
        cardName.add(card);
        this.id_player = id_player;
        command = "play_discard";
    }
}
