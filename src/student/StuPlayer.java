package student;

import model.*;
import model.Pair;
import model.PlayerObserver;
import model.Route;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Observer;

public class StuPlayer implements model.Player {

    private Baron player;
    private int pieces;
    private int score;
    private HashMap<Card,Integer> player_cards;
    private ArrayList<PlayerObserver> observers;
    private ArrayList<Route> routes;
    public boolean canClaim = true;



    public StuPlayer(int score, int pieces, Baron player){
        this.pieces = pieces;
        this.score = score;
        this.player = player;


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
        observers.add(observer);

    }

    @Override
    public void removePlayerObserver(PlayerObserver observer) {
        observers.remove(observer);
    }

    @Override
    public Baron getBaron() {
        return player;
    }

    @Override
    public void startTurn(Pair dealt) {
        canClaim = true;

    }

    @Override
    public Pair getLastTwoCards() {
        return null;
    }

    @Override
    public int countCardsInHand(Card card) {
        //need to count the cards

    }

    @Override
    public int getNumberOfPieces() {
        return pieces;
    }

    @Override
    public boolean canClaimRoute(Route route) {
        int max_cards = 1000000000;
        for (Integer num: player_cards.values()) {
            if (num < max_cards){
                max_cards = num;
            }
        }
        if (route.getBaron() == Baron.UNCLAIMED && canClaim == true && route.getLength() <= max_cards){
            return true;
        }
        return false;
    }

    @Override
    public void claimRoute(Route route) throws RailroadBaronsException {
        canClaim = false;

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
