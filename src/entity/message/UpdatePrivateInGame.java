package entity.message;

import java.util.ArrayList;

public class UpdatePrivateInGame {
    ArrayList<String> cardsHand;
    int id_player;
    String type;
    String command;

    public ArrayList<String> getCardsHand() {
        return cardsHand;
    }
}
