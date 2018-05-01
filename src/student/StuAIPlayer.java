package student;

import model.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

/**
 * ai player for lonely version
 * @author Brett Farruggia & Alex Hurley
 */
public class StuAIPlayer implements model.Player {
    private Card lastCard;
    private Card secondLastCard;

    private Baron baron;
    private ArrayList<Route> claimedRoutes;
    private TreeMap<Card, Integer> cardHandPlayer;
    private boolean claimedThisTurn;

    //private LonelyRailroadBarons lonely;


    private int trains;
    private int scoreTotal;
    private Pair lastPair;

    private boolean northSouthMultiplier;
    private boolean eastWestMultiplier;

    private ArrayList<PlayerObserver> observers = new ArrayList<>();


    /*
    public StuAIPlayer(Baron baron, LonelyRailroadBarons lonely) {
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
        this.lonely = lonely;

    }
     */

    /**
     * @return - Cards in the player's cardHandPlayer.
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
     *AI Constructor
     * @param baron
     */
    public StuAIPlayer(Baron baron) {
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
    public void playAI() throws RailroadBaronsException {
        for (Route route: lonely.getRailroadMap().getRoutes()) {
//            if (route.getLength() < 2 ){
//                continue;
//            }
            if (this.canClaimRoute(route)) {
                int row = route.getTracks().get(0).getRow();
                int col = route.getTracks().get(0).getCol();
                lonely.claimRoute(row,col);
                claimRoute(route);
            }
            else {
                continue;
            }
        }
        lonely.endTurn();
    }

     */

    /**
     * Resets the player's cardHandPlayer.
     * @param dealt
     */
    @Override
    public void reset(Card... dealt) {
        trains = 45;
        scoreTotal = 0;
        claimedRoutes.clear();
        lastCard = Card.NONE;
        secondLastCard = Card.NONE;
        for (int cards:cardHandPlayer.values()) {
            cards = 0;
        }
        for (int x=0;x<4;x++) {
            cardHandPlayer.put(dealt[x], cardHandPlayer.get(dealt[x]) + 1);
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
     * @param observer
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
    public void startTurn(Pair dealt) {
        lastPair = dealt;
        for (Card c:cardHandPlayer.keySet()) {
            if (c==lastPair.getFirstCard()) {
                cardHandPlayer.put(c,cardHandPlayer.get(c)+1);
            }
            if (c==lastPair.getSecondCard()) {
                cardHandPlayer.put(c,cardHandPlayer.get(c)+1);
            }
        }
        claimedThisTurn = false;
        for (PlayerObserver p:observers) {
            p.playerChanged(this);
        }
        if (getBaron() != Baron.BLUE){
            try {
                playAI();
            } catch (RailroadBaronsException e) {
                e.printStackTrace();
            }
        }

    }

     */


    /**
     * @return - Last pair of dealt cards.
     */
    public Pair getLastTwoCards() {
        return lastPair;
    }


    /**
     * @param card The card.
     * @return - Cards in the players Hand.
     */
    @Override
    public int countCardsInHand(Card card) {
        return cardHandPlayer.get(card);
    }




    /**
     * Check
     * @param route
     * @return - boolean.
     */
    @Override
    public boolean canClaimRoute(Route route) {
        if (route.getBaron()==Baron.UNCLAIMED&&
                getNumberOfPieces()>=route.getLength()&&
                claimedThisTurn==false&&numberOfCards(route.getLength())) {

            return true;
        }
        return false;
    }

    /**
     * Claims A Route
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
            trains -= route.getLength();
            claimedThisTurn = true;
        }

        for (PlayerObserver po:observers) {
            po.playerChanged(this);
        }
    }

    /**
     * @return routes claimed by a baron
     */
    @Override
    public Collection<Route> getClaimedRoutes() {
        return claimedRoutes;
    }

    /**
     * @return  the player scoreTotal.
     */
    @Override
    public int getScore() {
        return scoreTotal;
    }


    public void updateScoreGUI(int newScore) {
        scoreTotal = newScore;
        for (PlayerObserver p:observers) {
            p.playerChanged(this);
        }
    }



    /**
     * Checks to see if the game is over using the routst
     * @param shortestUnclaimedRoute
     *
     * @return - boolean
     */
    @Override
    public boolean canContinuePlaying(int shortestUnclaimedRoute) {
        if (getNumberOfPieces()>=shortestUnclaimedRoute&&numberOfCards(shortestUnclaimedRoute)) {
            return true;
        }
        return false;
    }

    /**
     * Checks to see if game is over from card amounts
     * @param routeLength length of the route
     * @return - boolean.
     */
    public boolean numberOfCards(int routeLength) {
        int MCards = 0;
        int wildCards = 0;

        for (Card card: cardHandPlayer.keySet()) {
            if (card==Card.WILD) {
                wildCards = cardHandPlayer.get(card);
            }
            else {
                if (cardHandPlayer.get(card)>MCards) {
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
     * Uses the player cards to claim route.
     * @param route a route
     */
    public void cardDealing(Route route) {
        boolean played = false;
        for (Card card : cardHandPlayer.keySet()) {
            if (card != Card.WILD) {
                if (cardHandPlayer.get(card) >= route.getLength()) {
                    cardHandPlayer.put(card, cardHandPlayer.get(card)-route.getLength());
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
     * @return baron.
     */
    public String toString() {
        if (baron==Baron.RED) {return "RED Baron";}
        if (baron==Baron.YELLOW) {return "YELLOW Baron";}
        if (baron==Baron.GREEN) {return "GREEN Baron";}
        if (baron==Baron.BLUE) {return "BLUE Baron";}
        return ("Failed");
    }


    public Boolean[] getMultiplier(){
        Boolean multi[] = new Boolean[2];
        multi[0] = northSouthMultiplier;
        multi[1] = eastWestMultiplier;
        return multi;
    }


    /**
     * Claims the route of the AI
     * @param routes routes available
     * @return route
     * @throws RailroadBaronsException
     */
    public Route getAIRoute(Collection<Route> routes) throws RailroadBaronsException{
        for (Route route: routes) {
            if (canClaimRoute(route)) {
                claimRoute(route);
                return route;
            }
        }
        return null;
    }


    /**
     * starts the turn by dealing cards, checks if player has claimed.
     * @param dealt a {@linkplain Pair pair of cards} to the player. Note that
     */
    public void startTurn(Pair dealt) {
        lastPair = dealt;
        for (Card c:cardHandPlayer.keySet()) {
            if (c==lastPair.getFirstCard()) {
                cardHandPlayer.put(c,cardHandPlayer.get(c)+1);
            }
            if (c==lastPair.getSecondCard()) {
                cardHandPlayer.put(c,cardHandPlayer.get(c)+1);
            }
        }
        claimedThisTurn = false;
        for (PlayerObserver p:observers) {
            p.playerChanged(this);
        }
    }

    /**
     * @return - the number of train pieces.
     */
    @Override
    public int getNumberOfPieces() {
        return trains;
    }




}

