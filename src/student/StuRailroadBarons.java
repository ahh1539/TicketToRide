package student;

import model.Deck;
import model.Player;
import model.RailroadBaronsException;
import model.RailroadBaronsObserver;
import model.RailroadMap;

import java.util.ArrayList;
import java.util.Collection;


/**
 * The game logic for railroad barons
 * @author Brett Farruggia & Alex Hurley
 */
public class StuRailroadBarons implements model.RailroadBarons  {

    private ArrayList<RailroadBaronsObserver> observers;
    private ArrayList<Player> players;

    /**
     * Adds a Railroad observer
     * @param observer The Railroad observer
     */
    @Override
    public void addRailroadBaronsObserver(RailroadBaronsObserver observer) {
        observers.add(observer);

    }

    /**
     * Removes the observer
     * @param observer The Railroad observer
     */
    @Override
    public void removeRailroadBaronsObserver(RailroadBaronsObserver observer) {
        observers.remove(observer);

    }

    /**
     * Starts a game with a map
     * @param map The map which the game will be played on
     */
    @Override
    public void startAGameWith(RailroadMap map) {

    }

    /**
     *
     * @param map The map that will be used to place the game
     * @param deck A deck of cards that will be sued in the game
     */
    @Override
    public void startAGameWith(RailroadMap map, Deck deck) {

    }

    /**
     * The Railroad map
     * @return the railroad map
     */
    @Override
    public RailroadMap getRailroadMap() {
        return null;
    }

    /**
     *
     * @return the number of cards in the deck remaining
     */
    @Override
    public int numberOfCardsRemaining() {
        return 0;
    }

    /**
     * Can it be claimed by a person
     * @param row The row
     * @param col The column
     * @return a boolean
     */
    @Override
    public boolean canCurrentPlayerClaimRoute(int row, int col) {
        return false;
    }

    /**
     * Claims a route
     * @param row The row
      * @param col The column
     * @throws RailroadBaronsException
     */
    @Override
    public void claimRoute(int row, int col) throws RailroadBaronsException {

    }

    /**
     * Ends a players turn
     */
    @Override
    public void endTurn() {

    }

    /**
     * @return the current player
     */
    @Override
    public Player getCurrentPlayer() {
        return null;
    }

    /**
     * All of the players
     * @return a list of players
     */
    @Override
    public Collection<Player> getPlayers() {
        return null;
    }

    /**
     * Ends the game
     * @return boolean
     */
    @Override
    public boolean gameIsOver() {
        return false;
    }
}
