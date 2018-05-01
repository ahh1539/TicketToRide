

package student;

import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

/**
 * creates an instance of player in the game
 *
 * @author Brett Farruggia & Alex Hurley
 */
public class StuPlayer implements Player {
    private Card lastCard;
    private Card secondLastCard;

    private Baron baron;
    private ArrayList<Route> claimedRoutes;
    private TreeMap<Card, Integer> cardHandPlayer;
    private boolean claimedThisTurn;

    private int trains;
    private int scoreTotal;
    private Pair lastPair;

    private boolean northSouthMultiplier;
    private boolean eastWestMultiplier;


    private ArrayList<PlayerObserver> observers = new ArrayList<>();

    /**
     * Constructor for StuPlayer.
     * @param baron
     */
    public StuPlayer(Baron baron) {
        claimedThisTurn = false;
        this.baron = baron;
        claimedRoutes = new ArrayList<>();
        cardHandPlayer = createHand();
        trains = 45;
        scoreTotal = 0;
        lastCard = Card.NONE;
        secondLastCard = Card.NONE;

        northSouthMultiplier = false;
        eastWestMultiplier = false;

    }

    /**
     * @return  number of cardHandPlayer.
     */
    public TreeMap<Card, Integer> createHand() {
        TreeMap<Card,Integer> temp = new TreeMap<>();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(Card.ORANGE);
        cards.add(Card.RED);
        cards.add(Card.BLACK);
        cards.add(Card.WHITE);
        cards.add(Card.BLUE);
        cards.add(Card.YELLOW);
        cards.add(Card.GREEN);
        cards.add(Card.PINK);
        cards.add(Card.WILD);
        for (Card card: cards) {
            temp.put(card,0);
        }
        return temp;
    }

    /**
     * Resets the player hand.
     * @param dealt
     */
    @Override
    public void reset(Card... dealt) {
        trains = 45;
        scoreTotal = 0;
        claimedRoutes.clear();
        lastCard = Card.NONE;
        secondLastCard = Card.NONE;
        for (int cards: cardHandPlayer.values()) {
            cards = 0;
        }
        for (int x = 0; x < 4; x++) {
            cardHandPlayer.put(dealt[x], cardHandPlayer.get(dealt[x]) + 1);
        }
        for (PlayerObserver player: observers) {

            player.playerChanged(this);
        }
    }

    /**
     * Adds a player observer.
     * @param observer The new {@link PlayerObserver}.
     */
    @Override
    public void addPlayerObserver(PlayerObserver observer) {

        observers.add(observer);
    }


    /**
     * A Player
     * @return
     */
    @Override
    public Baron getBaron() {

        return baron;
    }

    /**
     * Starts Turn.
     * @param dealt
     */
    @Override
    public void startTurn(Pair dealt) {
        lastPair = dealt;

        for (Card card:cardHandPlayer.keySet()) {
            if (card == lastPair.getFirstCard()) {
                cardHandPlayer.put(card, cardHandPlayer.get(card)+1);
            }
            if (card == lastPair.getSecondCard()) {
                cardHandPlayer.put(card, cardHandPlayer.get(card)+1);
            }
        }
        claimedThisTurn = false;
        for (PlayerObserver player: observers) {
            player.playerChanged(this);
        }
    }

    /**
     * @return  A Pair of cards
     */
    @Override
    public Pair getLastTwoCards() {

        return lastPair;
    }

    /**
     * @param card The card
     * @return - Cards in the players Hand
     */
    @Override
    public int countCardsInHand(Card card) {

        return cardHandPlayer.get(card);
    }

    /**
     * @return - the number of train pieces
     */
    @Override
    public int getNumberOfPieces() {

        return trains;
    }

    /**
     * Check if the route can be claimed
     * @param route a route
     * @return - boolean.
     */
    @Override
    public boolean canClaimRoute(Route route) {
        if (route.getBaron() == Baron.UNCLAIMED && getNumberOfPieces()>=route.getLength()&&
                claimedThisTurn==false&&numberOfCards(route.getLength())) {

            return true;
        }

        return false;
    }

    /**
     * claims a route.
     * @param route The {@link Route} to claim.
     *
     * @throws RailroadBaronsException
     */
    @Override
    public void claimRoute(Route route) throws RailroadBaronsException {
        if (canClaimRoute(route)) {

            route.claim(baron);
            cardDealing(route);
            claimedRoutes.add(route);
            scoreTotal += route.getPointValue();
            trains = trains - route.getLength();
            claimedThisTurn = true;

        }
        for (PlayerObserver player: observers) {
            player.playerChanged(this);
        }
    }

    /**
     * @return - routes claimed by a player.
     */
    @Override
    public Collection<Route> getClaimedRoutes() {

        return claimedRoutes;
    }


    /**
     * @return - the player scoreTotal.
     */
    @Override
    public int getScore() {

        return scoreTotal;
    }

    public void updateScoreGUI(int newScore) {
        scoreTotal = newScore;
        for (PlayerObserver player: observers) {
            player.playerChanged(this);
        }
    }



    /**
     * Checks to see if the game is over using routes.
     * @param shortestUnclaimedRoute the shortest claimable route
     *
     * @return - boolean
     */
    @Override
    public boolean canContinuePlaying(int shortestUnclaimedRoute) {
        if (getNumberOfPieces() >= shortestUnclaimedRoute&&numberOfCards(shortestUnclaimedRoute)) {

            return true;
        }

        return false;
    }

    /**
     * Checks to see if game is over based on cards
     * @param routeLength
     * @return - boolean.
     */
    public boolean numberOfCards(int routeLength) {
        int wildCards = 0;
        int MCards = 0;


        for (Card card: cardHandPlayer.keySet()) {
            if (card == Card.WILD) {
                wildCards = cardHandPlayer.get(card);
            }
            else {
                if (cardHandPlayer.get(card) > MCards) {
                    MCards = cardHandPlayer.get(card);
                }
            }
        }
        if (MCards >= routeLength) {
            return true;
        }
        if (MCards == routeLength-1 && wildCards > 0) {
            return true;
        }
        return false;
    }

    /**
     * Uses the player's cards to claim routes.
     * @param route
     */
    public void cardDealing(Route route) {
        boolean played = false;
        for (Card card : cardHandPlayer.keySet()) {
            if (card != Card.WILD) {
                if (cardHandPlayer.get(card) >= route.getLength()) {
                    cardHandPlayer.put(card, cardHandPlayer.get(card) - route.getLength());
                    played = true;
                    break;
                }
            }
        }
        if (!played) {
            for (Card card : cardHandPlayer.keySet()) {
                if (card != Card.WILD) {
                    if (cardHandPlayer.get(card) == route.getLength() - 1 && cardHandPlayer.get(Card.WILD) >= 1) {
                        cardHandPlayer.put(card, cardHandPlayer.get(card) - (route.getLength() - 1));

                        cardHandPlayer.put(Card.WILD, cardHandPlayer.get(Card.WILD) - 1);
                        break;
                    }
                }
            }
        }
    }

    /**
     * @return Baron name
     */
    public String toString() {
        if (baron == Baron.RED) {return "RED Baron";}
        if (baron == Baron.YELLOW) {return "YELLOW Baron";}
        if (baron == Baron.GREEN) {return "GREEN Baron";}
        if (baron == Baron.BLUE) {return "BLUE Baron";}
        return ("Failed");
    }

/*
multiplies the score of a player if they connect a route from the north to south or east to west
 */
    public Boolean[] getMultiplier(){
        Boolean multi[] = new Boolean[2];
        multi[0] = northSouthMultiplier;
        multi[1] = eastWestMultiplier;
        return multi;
    }

    /**
     * Removes an observer.
     * @param observer
     */
    @Override
    public void removePlayerObserver(PlayerObserver observer) {

        observers.add(observer);
    }

}
