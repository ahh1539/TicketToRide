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
     */
    public StuPair(Card card1, Card card2){
        this.card_one = card1;
        this.card_two = card2;


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
