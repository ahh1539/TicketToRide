package student;

import model.Card;

import java.util.ArrayList;
import java.util.Collections;

public class StuDeck implements model.Deck {

    public static ArrayList<Card> gameDeck;

    public StuDeck(){

        for(int i = 1; i <=16; i++) {   //number of cards in a deck?
            gameDeck.add(Card.BLACK);
            gameDeck.add(Card.BLUE);
            gameDeck.add(Card.ORANGE);
            gameDeck.add(Card.GREEN);
            gameDeck.add(Card.PINK);
            gameDeck.add(Card.WHITE);
            gameDeck.add(Card.RED);
            gameDeck.add(Card.YELLOW);
            gameDeck.add(Card.WILD);
        }
        Collections.shuffle(gameDeck);

    }
    @Override
    public void reset() {
        gameDeck.clear();

        for(int i = 1; i <=16; i++) {   //number of cards in a deck?
           gameDeck.add(Card.BLACK);
           gameDeck.add(Card.BLUE);
           gameDeck.add(Card.ORANGE);
           gameDeck.add(Card.GREEN);
           gameDeck.add(Card.PINK);
           gameDeck.add(Card.WHITE);
           gameDeck.add(Card.RED);
           gameDeck.add(Card.YELLOW);
           gameDeck.add(Card.WILD);
        }
        Collections.shuffle(gameDeck);


    }

    @Override
    public Card drawACard() {
        if (numberOfCardsRemaining() == 0){
            return Card.NONE;

        }else{
            return gameDeck.remove(0);
        }
    }

    @Override
    public int numberOfCardsRemaining() {

        return gameDeck.size();
    }
}
