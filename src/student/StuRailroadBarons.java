package student;

import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


/**
 * The game logic for railroad barons
 * @author Brett Farruggia & Alex Hurley
 */
public class StuRailroadBarons implements model.RailroadBarons  {

    private ArrayList<RailroadBaronsObserver> observers;
    private ArrayList<Player> mod_players;
    private RailroadMap my_map;
    private Deck my_deck;
    private int turn = 0;

    /**
     * Adds a Railroad observer
     * @param observer The Railroad observer
     */
    @Override
    public void addRailroadBaronsObserver(RailroadBaronsObserver observer) {
        mod_players = new ArrayList<>();
        mod_players.add(new StuPlayer(Baron.BLUE));
        mod_players.add(new StuPlayer(Baron.RED));
        mod_players.add(new StuPlayer(Baron.GREEN));
        mod_players.add(new StuPlayer(Baron.YELLOW));
        observers = new ArrayList<>();
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
        StuDeck deck = new StuDeck();
        deck.reset();
        StuPair pair = new StuPair(deck);
        for (Player player: mod_players) {
            player.reset();
            for (int i = 0; i < 4 ;i++ ){
                player.startTurn(pair);
            }
        }
    }

    /**
     *
     * @param map The map that will be used to place the game
     * @param deck A deck of cards that will be sued in the game
     */
    @Override
    public void startAGameWith(RailroadMap map, Deck deck) {
        map = my_map;
        deck.reset();
        StuPair pair = new StuPair(my_deck);
        for (Player player: mod_players) {
            player.reset();
            for (int i = 0; i < 4 ;i++ ){
                player.startTurn(pair);
            }
        }
    }

    /**
     * The Railroad map
     * @return the railroad map
     */
    @Override
    public RailroadMap getRailroadMap() {
        return my_map;
    }

    /**
     *
     * @return the number of cards in the deck remaining
     */
    @Override
    public int numberOfCardsRemaining() {
        return my_deck.numberOfCardsRemaining();
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
        if (getCurrentPlayer().canClaimRoute()){

        }

    }

    /**
     * Ends a players turn
     */
    @Override
    public void endTurn() {
        turn ++;
    }

    /**
     * @return the current player
     */
    @Override
    public Player getCurrentPlayer() {
        if (turn == 4){
            turn = 1;
        }
        return mod_players.get(turn);
    }

    /**
     * All of the players
     * @return a list of players
     */
    @Override
    public Collection<Player> getPlayers() {
        return mod_players;
    }


    /**
     * Ends the game
     * @return boolean
     */
    @Override
    public boolean gameIsOver() {
        int num_players = 0;
        for (Player player : mod_players) {
            if (player.getNumberOfPieces() <= 0 ||
                    player.canContinuePlaying(my_map.getLengthOfShortestUnclaimedRoute()) == false){
                num_players++;
            }
        }
        if (num_players == mod_players.size()){
            return true;
        }
        return false;
    }
}
