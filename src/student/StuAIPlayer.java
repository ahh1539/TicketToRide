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
    private TreeMap<Card, Integer> playerHand;
    private boolean claimedThisTurn;

    private LonelyRailroadBarons lonely;


    private int trains;
    private int scoreTotal;
    private Pair lastPair;

    private boolean northSouthMultiplier;
    private boolean eastWestMultiplier;

    private ArrayList<PlayerObserver> observers = new ArrayList<>();

    /**
     * Constructor for AI Player.
     * @param baron
     */
    public StuAIPlayer(Baron baron, LonelyRailroadBarons lonely) {
        claimedThisTurn = false;
        this.baron = baron;
        claimedRoutes = new ArrayList<>();
        playerHand = createHand();
        trains = 45;
        scoreTotal = 0;
        lastCard = Card.NONE;
        secondLastCard = Card.NONE;

        northSouthMultiplier = false;
        eastWestMultiplier = false;
        this.lonely = lonely;

    }

    /**
     * @return - Cards in the player's playerHand.
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

    /*
    constructor for the AI player
     */
    public StuAIPlayer(Baron baron) {
        claimedThisTurn = false;
        this.baron = baron;
        claimedRoutes = new ArrayList<>();
        playerHand = createHand();
        trains = 45;
        scoreTotal = 0;
        lastCard = Card.NONE;
        secondLastCard = Card.NONE;

        northSouthMultiplier = false;
        eastWestMultiplier = false;


    }

    /*
    this is basically the AI for lonely that choses the first available route
     */
    public void playAI() throws RailroadBaronsException {
        for (Route route: lonely.getRailroadMap().getRoutes()) {
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

    /**
     * Resets the player's playerHand.
     * @param dealt
     */
    @Override
    public void reset(Card... dealt) {
        trains = 45;
        scoreTotal = 0;
        claimedRoutes.clear();
        lastCard = Card.NONE;
        secondLastCard = Card.NONE;
        for (int cards:playerHand.values()) {
            cards = 0;
        }
        for (int x=0;x<4;x++) {
            playerHand.put(dealt[x], playerHand.get(dealt[x]) + 1);
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
     * Begins the player's turn.
     * @param dealt
     */
    @Override
    public void startTurn(Pair dealt) {
        lastPair = dealt;
        for (Card c:playerHand.keySet()) {
            if (c==lastPair.getFirstCard()) {
                playerHand.put(c,playerHand.get(c)+1);
            }
            if (c==lastPair.getSecondCard()) {
                playerHand.put(c,playerHand.get(c)+1);
            }
        }
        claimedThisTurn = false;
        for (PlayerObserver p:observers) {
            p.playerChanged(this);
        }
        try {
            playAI();
        } catch (RailroadBaronsException e) {
            e.printStackTrace();
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
     * @return - Cards in the player's playerHand.
     */
    @Override
    public int countCardsInHand(Card card) {
        return playerHand.get(card);
    }


    /**
     * @return - the number of train pieces on
     * the board.
     */
    @Override
    public int getNumberOfPieces() {
        return trains;
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
            scoreTotal += route.getPointValue();
            trains -= route.getLength();
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
     * @return - the player's scoreTotal.
     */
    @Override
    public int getScore() {
        return scoreTotal;
    }



    /**
     * Checks to see if the game is over using routes.
     * @param shortestUnclaimedRoute
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

        for (Card card: playerHand.keySet()) {
            if (card==Card.WILD) {
                wildCards = playerHand.get(card);
            }
            else {
                if (playerHand.get(card)>mostCards) {
                    mostCards = playerHand.get(card);
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
        for (Card card : playerHand.keySet()) {
            if (card != Card.WILD) {
                if (playerHand.get(card) >= route.getLength()) {
                    playerHand.put(card, playerHand.get(card)-route.getLength());
                    played = true;
                    break;
                }
            }
        }
        if (!played) {
            for (Card card : playerHand.keySet()) {
                if (card != Card.WILD) {
                    if (playerHand.get(card) == route.getLength() - 1 && playerHand.get(Card.WILD) >= 1) {
                        playerHand.put(card, playerHand.get(card) - (route.getLength() - 1));
                        playerHand.put(Card.WILD, playerHand.get(Card.WILD) - 1);
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
        return ("Failed");
    }


    public Boolean[] getMultiplier(){
        Boolean multi[] = new Boolean[2];
        multi[0] = northSouthMultiplier;
        multi[1] = eastWestMultiplier;
        return multi;
    }
}

