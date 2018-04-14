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
    private Pair last;



    public StuPlayer(Baron player){
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
        last = dealt;
    }

    @Override
    public Pair getLastTwoCards() {
        return last;
    }

    @Override
    public int countCardsInHand(Card card) {
        //need to count the cards
        int num_cards = 0;
        for (Integer num: player_cards.values()) {
            num_cards = num_cards + num;
        }
        return num_cards;
    }

    @Override
    public int getNumberOfPieces() {
        return pieces;
    }

    @Override
    public boolean canClaimRoute(Route route) {
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


    @Override
    public void claimRoute(Route route) throws RailroadBaronsException {
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

    @Override
    public Collection<Route> getClaimedRoutes() {
        return routes;
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
