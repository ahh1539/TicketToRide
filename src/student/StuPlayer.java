

package student;

import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

public class StuPlayer implements Player {
    private Baron baron;
    private ArrayList<Route> claimedRoutes;
    private TreeMap<Card, Integer> hand;
    private boolean claimedThisTurn;
    private int trainPieces;
    private int score;
    private Pair lastPair;
    private Card lastCard;
    private Card secondLastCard;
    private ArrayList<PlayerObserver> observers = new ArrayList<>();

    /**
     * Constructor for BoardPlayer.
     * @param baron
     */
    public StuPlayer(Baron baron) {
        claimedThisTurn = false;
        this.baron = baron;
        claimedRoutes = new ArrayList<>();
        hand = createHand();
        trainPieces = 45;
        score = 0;
        lastCard = Card.NONE;
        secondLastCard = Card.NONE;
    }

    /**
     * @return - Cards in the player's hand.
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
     * Resets the player's hand.
     * @param dealt The hand of {@link Card cards} dealt to the player at the
     */
    @Override
    public void reset(Card... dealt) {
        trainPieces = 45;
        score = 0;
        claimedRoutes.clear();
        lastCard = Card.NONE;
        secondLastCard = Card.NONE;
        for (int cards:hand.values()) {
            cards = 0;
        }
        for (int x=0;x<4;x++) {
            hand.put(dealt[x], hand.get(dealt[x]) + 1);
        }
        for (PlayerObserver p:observers) {
            p.playerChanged(this);
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
     * Removes a player observer.
     * @param observer The {@link PlayerObserver} to remove.
     */
    @Override
    public void removePlayerObserver(PlayerObserver observer) {
        observers.add(observer);
    }

    /**
     * @return - a player.
     */
    @Override
    public Baron getBaron() {
        return baron;
    }

    /**
     * Begins the player's turn.
     * @param dealt a {@linkplain Pair pair of cards} to the player. Note that
     */
    @Override
    public void startTurn(Pair dealt) {
        lastPair = dealt;
        for (Card c:hand.keySet()) {
            if (c==lastPair.getFirstCard()) {
                hand.put(c,hand.get(c)+1);
            }
            if (c==lastPair.getSecondCard()) {
                hand.put(c,hand.get(c)+1);
            }
        }
        claimedThisTurn = false;
        for (PlayerObserver p:observers) {
            p.playerChanged(this);
        }
    }

    /**
     * @return - Last pair of cards.
     */
    @Override
    public Pair getLastTwoCards() {
        return lastPair;
    }

    /**
     * @param card The {@link Card} of interest.
     * @return - Cards in the player's hand.
     */
    @Override
    public int countCardsInHand(Card card) {
        return hand.get(card);
    }

    /**
     * @return - the number of train pieces on
     * the board.
     */
    @Override
    public int getNumberOfPieces() {
        return trainPieces;
    }

    /**
     * Checks to see in a route is claimable.
     * @param route The {@link Route} being tested to determine whether or not
     *              the player is able to claim it.
     * @return - boolean.
     */
    @Override
    public boolean canClaimRoute(Route route) {
        if (route.getBaron()==Baron.UNCLAIMED&&
                getNumberOfPieces()>=route.getLength()&&
                claimedThisTurn==false&&checkCardAmounts(route.getLength())) {
            return true;
        }
        return false;
    }

    /**
     * A player claims a route.
     * @param route The {@link Route} to claim.
     *
     * @throws RailroadBaronsException
     */
    @Override
    public void claimRoute(Route route) throws RailroadBaronsException {
        if (canClaimRoute(route)) {
            route.claim(baron);
            playCards(route);
            claimedRoutes.add(route);
            score += route.getPointValue();
            trainPieces -= route.getLength();
            claimedThisTurn = true;
        }
        for (PlayerObserver p:observers) {
            p.playerChanged(this);
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
     * @return - the player's score.
     */
    @Override
    public int getScore() {
        /**
         int total = 0;
         for (Route r:claimedRoutes) {
         total += r.getPointValue();
         }
         score = total;
         **/
        return score;
    }

    /**
     * Checks to see if the game is over using routes.
     * @param shortestUnclaimedRoute The length of the shortest unclaimed
     *                               {@link Route} in the current game.
     *
     * @return - boolean
     */
    @Override
    public boolean canContinuePlaying(int shortestUnclaimedRoute) {
        if (getNumberOfPieces()>=shortestUnclaimedRoute&&checkCardAmounts(shortestUnclaimedRoute)) {
            return true;
        }
        return false;
    }

    /**
     * Checks to see if game is over using card
     * amounts.
     * @param routeLength
     * @return - boolean.
     */
    public boolean checkCardAmounts(int routeLength) {
        int mostCards = 0;
        int wildCards = 0;
        for (Card card: hand.keySet()) {
            if (card==Card.WILD) {
                wildCards = hand.get(card);
            }
            else {
                if (hand.get(card)>mostCards) {
                    mostCards = hand.get(card);
                }
            }
        }
        if (mostCards >= routeLength) {
            return true;
        }
        if (mostCards == routeLength-1 && wildCards > 0) {
            return true;
        }
        return false;
    }

    /**
     * Uses the player's cards to claim routes.
     * @param route
     */
    public void playCards(Route route) {
        boolean played = false;
        for (Card card : hand.keySet()) {
            if (card != Card.WILD) {
                if (hand.get(card) >= route.getLength()) {
                    hand.put(card, hand.get(card)-route.getLength());
                    played = true;
                    break;
                }
                /**
                 else if (hand.get(card) == route.getLength()-1 && hand.get(Card.WILD)>=1) {
                 hand.put(card,hand.get(card)-(route.getLength()-1));
                 hand.put(Card.WILD,hand.get(Card.WILD)-1);
                 break;
                 }
                 **/
            }
        }
        if (!played) {
            for (Card card : hand.keySet()) {
                if (card != Card.WILD) {
                    if (hand.get(card) == route.getLength() - 1 && hand.get(Card.WILD) >= 1) {
                        hand.put(card, hand.get(card) - (route.getLength() - 1));
                        hand.put(Card.WILD, hand.get(Card.WILD) - 1);
                        break;
                    }
                }
            }
        }
    }

    /**
     * @return - Name of Baron.
     */
    public String toString() {
        if (baron==Baron.RED) {return "RED Baron";}
        if (baron==Baron.YELLOW) {return "YELLOW Baron";}
        if (baron==Baron.GREEN) {return "GREEN Baron";}
        if (baron==Baron.BLUE) {return "BLUE Baron";}
        return "broke";
    }
}
