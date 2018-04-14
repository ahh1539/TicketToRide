package student;

import model.*;
import model.Pair;
import model.PlayerObserver;
import model.Route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Observer;

public class StuPlayer implements model.Player {

    private Baron baron;
    private int pieces;
    private int cardsInHand;
    private int score;
    private HashMap<Card,Integer> player_cards;
    private ArrayList<RailroadBaronsObserver> observers;
    private ArrayList<Route> routes;



    public StuPlayer(int score, int cardsInHand, int pieces){
        this.pieces = pieces;
        this.score = score;
        this.cardsInHand = cardsInHand;

        //back and none
    }

    @Override
    public void reset(Card... dealt) {
        player_cards.clear();
        for (Card card1: dealt) {
            player_cards.put(card1, 0);
        }

        pieces = 45;
        score = 0;
        for (Card card:dealt) {
            player_cards.put(card, player_cards.get(card)+1);
        }
        observers.clear();
        routes.clear();
    }

    @Override
    public void addPlayerObserver(PlayerObserver observer) {

    }

    @Override
    public void removePlayerObserver(PlayerObserver observer) {

    }

    @Override
    public Baron getBaron() {
        return baron;
    }

    @Override
    public void startTurn(Pair dealt) {


    }

    @Override
    public Pair getLastTwoCards() {
        return null;
    }

    @Override
    public int countCardsInHand(Card card) {
        //need to count the cards
        return cardsInHand;
    }

    @Override
    public int getNumberOfPieces() {

        return pieces;
    }

    @Override
    public boolean canClaimRoute(Route route) {
        return false;
    }

    @Override
    public void claimRoute(Route route) throws RailroadBaronsException {

    }

    @Override
    public Collection<Route> getClaimedRoutes() {
        return null;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public boolean canContinuePlaying(int shortestUnclaimedRoute) {
        return false;
    }
}
