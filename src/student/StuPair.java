package student;

import model.Card;
import model.Deck;

/**
 * Author : Brett Farruggia
 */
public class StuPair implements model.Pair {

    private Card card_one;
    private Card card_two;


    /**
     * creates a pair of cards for the next turn
     * @param deck the deck which the cards are drawn form
     */
    public StuPair(Deck deck){
        this.card_one = deck.drawACard();
        this.card_two = deck.drawACard();


    }

    /**
     *
     * @return the first card
     */
    @Override
    public Card getFirstCard() {
        return card_one;
    }

    /**
     *
     * @return the second card
     */
    @Override
    public Card getSecondCard() {
        return
                card_two;
    }
}
