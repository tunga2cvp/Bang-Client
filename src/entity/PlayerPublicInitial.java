package entity;

public class PlayerPublicInitial {
    private int id;
    private int health;
    private int numCards;
    String equipCard;
    public int getId() {
        return id;
    }

    public int getHealth() {
        return health;
    }

    public String getEquipCard() {
        return equipCard;
    }

    public int getNumCards() {
        return numCards;
    }

    public int getRange() {
        return range;
    }

    public boolean isBangLeft() {
        return bangLeft;
    }

    public String getName() {
        return name;
    }

    private int range;
    private boolean bangLeft;
    private String name;
}
