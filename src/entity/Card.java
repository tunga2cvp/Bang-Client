package entity;

import java.util.ArrayList;
import java.util.List;

public class Card {
    String name;
    String type;

    public Card(String name, String type) {
        this.name = name;
        this.type = type;
    }
    public Card(){

    }
    public List getCardList(){
        List<Card> cardList = new ArrayList<Card>();
        cardList.add(new Card("bang","offense"));
        cardList.add(new Card("miss","defense"));
        cardList.add(new Card("bang","offense"));
        cardList.add(new Card("miss","defense"));
        cardList.add(new Card("winchester","equip"));
        return cardList;
    }
    public String getImageURL(){
        //assets/images/book/book5.jpg
        String begin = "src/assets/Cards/";
        String name = this.name;
        String last = ".jpg";
        String url = begin.concat(name).concat(last);
        System.out.println(url);
        return url;
    }
    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
}
