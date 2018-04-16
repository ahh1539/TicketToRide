package student;

import model.Card;

import java.util.ArrayList;
import java.util.Collections;


/**
 * @author Brett Farruggia & Alex Hurley
 * Creates a deck of 180 cards to be used in the game
 */
public class StuDeck implements model.Deck {

    public static ArrayList<Card> gameDeck;


    /**
     * Constructor for the deck, adds 20 of each card type to the deck.
     */
    public StuDeck(){

        gameDeck = new ArrayList<>();

        for(int i = 1; i <=20; i++) {   //number of cards in a deck?
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

    /**
     * Resets the game deck to a new game state.
     */
    @Override
    public void reset() {
        gameDeck.clear();

        for(int i = 1; i <=20; i++) {   //number of cards in a deck?
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

    /**
     * Used to draw a card
     * @return The top card from the deck
     */
    @Override
    public Card drawACard() {
        if (numberOfCardsRemaining() == 0){
            return Card.NONE;

        }else{
            return gameDeck.remove(0);
        }
    }

    /**
     * The size of the deck
     * @return The number of cards remaining
     */
    @Override
    public int numberOfCardsRemaining() {

        return gameDeck.size();
    }
}
