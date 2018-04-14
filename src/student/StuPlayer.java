package student;

import model.*;
import model.Pair;
import model.PlayerObserver;
import model.Route;

import java.util.Collection;

public class StuPlayer implements model.Player {

    private Baron baron;
    private int pieces;
    private int cardsInHand;
    private int score;


    @Override
    public void reset(Card... dealt) {

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
