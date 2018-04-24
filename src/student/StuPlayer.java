package student;

import model.*;
import model.Pair;
import model.PlayerObserver;
import model.Card;
import model.Baron;
import model.Route;
import student.StuDeck;

import java.security.Key;
import java.util.*;

/**
 * @author Brett Farruggia & Alex Hurley
 */

public class StuPlayer implements model.Player {

    private model.Baron player;
    private int pieces;
    private int score;
    private HashMap<Card,Integer> player_cards;
    private ArrayList<PlayerObserver> observers;
    private ArrayList<Route> routes;
    public boolean canClaim = true;
    private Pair last;


    /**
     * The constructor, takes a player
     * @param player the player
     */
    public StuPlayer(model.Baron player){
        this.player = player;
        this.pieces = 45;
        this.score = 0;
        routes = new ArrayList<>();
        player_cards = createHand();

        //back and none
    }

    public void startgameplayer(){
        StuDeck deck = new StuDeck();
        deck.drawACard();
        deck.drawACard();
        deck.drawACard();
        deck.drawACard();

    }

    /**
     * Create hand
     * @return a new hand, cards set to 0
     */
    public HashMap<Card, Integer> createHand() {
        HashMap<Card,Integer> set = new HashMap<>();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(Card.BLACK);
        cards.add(Card.BLUE);
        cards.add(Card.WHITE);

        cards.add(Card.GREEN);
        cards.add(Card.ORANGE);
        cards.add(Card.PINK);


        cards.add(Card.RED);
        cards.add(Card.WILD);
        cards.add(Card.YELLOW);
        for (Card card: cards) {
            set.put(card,0);
        }
        return set;
    }

    /**
     * The players hand
     * @param dealt Resets the hand of the player
     */
    @Override
    public void reset(Card... dealt) {
        player_cards.clear();
        for (Card card1: dealt) {
            player_cards.put(card1, 0);
        }
        for (PlayerObserver playa: observers) {
            playa.playerChanged(this);
        }

        pieces = 45;
        score = 0;
        for (Card card:dealt) {
            player_cards.put(card, player_cards.get(card)+1);
        }
        observers.clear();
        routes.clear();

    }
    public void setTrainPieces(int n){
        pieces = n;
    }

    public HashMap getHand(){
        return player_cards;
    }

    public void setHasClaimedRoute(Boolean booollyy){
        canClaim = booollyy;
    }

    public Boolean isHasClaimedRoute(){
        return canClaim;
    }

    /**
     * Adds observer
     * @param observer The new PlayerObserver
     */
    @Override
    public void addPlayerObserver(PlayerObserver observer) {
        observers = new ArrayList<>();
        observers.add(observer);
    }

    /**
     * Removes observer
     * @param observer The PlayerObserver
     */
    @Override
    public void removePlayerObserver(PlayerObserver observer) {
        observers.remove(observer);
    }

    /**
     * gets The player
     * @return the player
     */
    @Override
    public Baron getBaron() {
        return player;
    }

    /**
     *
     * @param dealt A pair of cards to be dealt
     */
    @Override
    public void startTurn(Pair dealt) {
        canClaim = true;
        last = dealt;
        for (PlayerObserver playa: observers) {
            playa.playerChanged(this);
        }
    }

    /**
     *
     * @return the last tow cards in the players hand
     */
    @Override
    public Pair getLastTwoCards() {
        for (PlayerObserver playa: observers) {
            playa.playerChanged(this);
        }
        return last;
    }

    /**
     *
     * @param card a card
     * @return number of cards in the hand
     */
    @Override
    public int countCardsInHand(Card card) {
        //need to count the cards
        int num_cards = 0;
        for (int num: player_cards.values()) {
            num_cards = num_cards + num;
        }
        for (PlayerObserver playa: observers) {
            playa.playerChanged(this);
        }
        return num_cards;

    }

    public Baron getPlayer() {
        return player;
    }

    public int getPieces() {
        return pieces;
    }

    public HashMap<Card, Integer> getPlayer_cards() {
        return player_cards;
    }

    public ArrayList<PlayerObserver> getObservers() {
        return observers;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public boolean isCanClaim() {
        return canClaim;
    }

    public Pair getLast() {
        return last;
    }

    /**
     *
     * @return the number of pieces
     */
    @Override
    public int getNumberOfPieces() {
        for (PlayerObserver playa: observers) {
            playa.playerChanged(this);
        }
        return pieces;
    }

    /**
     *
     * @param route the route in question
     * @return a boolean, true if player cna claim route
     */
    @Override
    public boolean canClaimRoute(Route route) {
        for (PlayerObserver playa: observers) {
            playa.playerChanged(this);
        }
        int wild = 0;
        int max_cards = 0;
        for (Card carddd: player_cards.keySet()) {
            if (carddd == Card.WILD){
                if (player_cards.get(carddd)>=1){
                    wild =1;
                }
                else {
                    wild = 0;
                }
            }
            if (player_cards.get(carddd) > max_cards && carddd != Card.WILD){
                max_cards = player_cards.get(carddd);
            }
        }
        if (route.getBaron() == Baron.UNCLAIMED && canClaim == true &&
                route.getLength() <= max_cards+wild && pieces >= route.getLength()){
            return true;
        }
        return false;
    }


    /**
     * Claims a route
     * @param route The route being claimed
     *
     * @throws RailroadBaronsException
     */
    @Override
    public void claimRoute(Route route) throws RailroadBaronsException {
        for (PlayerObserver playa: observers) {
            playa.playerChanged(this);
        }
        if (canClaimRoute(route) == true) {

            canClaim = false;
            pieces = pieces - route.getLength();
            int max_cards = 0;
            Card color = Card.RED;
            Card wild = Card.WILD;
            for (Card carddd : player_cards.keySet()) {
                if (player_cards.get(carddd) > max_cards && carddd != Card.WILD) {
                    max_cards = player_cards.get(carddd);
                    color = carddd;
                }
            }
            if (route.getLength() == player_cards.get(color) + 1 && player_cards.get(wild) >= 1) {
                player_cards.put(color, player_cards.get(wild) - 1);
                player_cards.put(color, player_cards.get(color) - (route.getLength() + 1));
            } else {
                player_cards.put(color, player_cards.get(color) - route.getLength());
            }
            routes.add(route);
        }
    }

    /**
     *
     * @return all of the claimed routes
     */
    @Override
    public Collection<Route> getClaimedRoutes() {
        for (PlayerObserver playa: observers) {
            playa.playerChanged(this);
        }
        return routes;
    }

    /**
     * The score of the game
     * @return the score
     */
    @Override
    public int getScore() {
        for (PlayerObserver playa: observers) {
            playa.playerChanged(this);
        }
        return score;
    }

    /**
     *
     * @param shortestUnclaimedRoute The length of the shortest unclaimed
     *                               route in the current game.
     *
     * @return boolean
     */
    @Override
    public boolean canContinuePlaying(int shortestUnclaimedRoute) {
        for (PlayerObserver playa: observers) {
            playa.playerChanged(this);
        }
        int max_cards = 0;
        int wild = 0;
        for (Card carddd : player_cards.keySet()) {
            if (carddd == Card.WILD){
                if (player_cards.get(carddd)>=1){
                    wild =1;
                }
                else {
                    wild = 0;
                }
            }
            if (player_cards.get(carddd) > max_cards && carddd != Card.WILD) {
                max_cards = player_cards.get(carddd);
            }
        }
        if (pieces >= shortestUnclaimedRoute || shortestUnclaimedRoute <= max_cards+wild){
            return true;
        }
        return false;
    }
}
