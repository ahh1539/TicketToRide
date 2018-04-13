package student;

import model.Deck;
import model.Player;
import model.RailroadBaronsException;
import model.RailroadBaronsObserver;
import model.RailroadMap;

import java.util.Collection;

public class StuRailroadBarons implements model.RailroadBarons  {
    @Override
    public void addRailroadBaronsObserver(RailroadBaronsObserver observer) {

    }

    @Override
    public void removeRailroadBaronsObserver(RailroadBaronsObserver observer) {

    }

    @Override
    public void startAGameWith(RailroadMap map) {

    }

    @Override
    public void startAGameWith(RailroadMap map, Deck deck) {

    }

    @Override
    public RailroadMap getRailroadMap() {
        return null;
    }

    @Override
    public int numberOfCardsRemaining() {
        return 0;
    }

    @Override
    public boolean canCurrentPlayerClaimRoute(int row, int col) {
        return false;
    }

    @Override
    public void claimRoute(int row, int col) throws RailroadBaronsException {

    }

    @Override
    public void endTurn() {

    }

    @Override
    public Player getCurrentPlayer() {
        return null;
    }

    @Override
    public Collection<Player> getPlayers() {
        return null;
    }

    @Override
    public boolean gameIsOver() {
        return false;
    }
}
