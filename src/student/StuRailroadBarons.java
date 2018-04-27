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

    private ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private RailroadMap map;

    private Deck deck;
    private int playerRot;
    private ArrayList<RailroadBaronsObserver> observers = new ArrayList<>();


    public StuRailroadBarons() {
        players.add(new StuPlayer(Baron.RED));
        players.add(new StuPlayer(Baron.GREEN));
        players.add(new StuPlayer(Baron.YELLOW));
        players.add(new StuPlayer(Baron.BLUE));
        deck = new StuDeck();
        playerRot = 0;
    }

    @Override
    public void addRailroadBaronsObserver(RailroadBaronsObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeRailroadBaronsObserver(RailroadBaronsObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void startAGameWith(RailroadMap map) {
        this.map = map;
        deck = new StuDeck();
        playerRot = 0;
        for (Player p:players) {
            p.reset(deck.drawACard(),deck.drawACard(),
                    deck.drawACard(),deck.drawACard());
        }
        currentPlayer = players.get(playerRot);
        currentPlayer.startTurn(new StuPair(deck));
        for (RailroadBaronsObserver r:observers) {
            r.turnStarted(this,currentPlayer);
        }
    }

    @Override
    public void startAGameWith(RailroadMap map, Deck deck) {
        deck = new StuDeck();
        playerRot = 0;
        this.map = map;
        for (Player p:players) {
            p.reset(deck.drawACard(),deck.drawACard(),
                    deck.drawACard(),deck.drawACard());
        }
        currentPlayer = players.get(playerRot);
        currentPlayer.startTurn(new StuPair(deck));
        for (RailroadBaronsObserver r:observers) {
            r.turnStarted(this,currentPlayer);
        }
    }

    @Override
    public RailroadMap getRailroadMap() {
        return map;
    }

    @Override
    public int numberOfCardsRemaining() {
        return deck.numberOfCardsRemaining();
    }

    @Override
    public boolean canCurrentPlayerClaimRoute(int row, int col) {
        return (currentPlayer.canClaimRoute(map.getRoute(row,col)));
    }

    @Override
    public void claimRoute(int row, int col) throws RailroadBaronsException {
        if (canCurrentPlayerClaimRoute(row,col)) {
            currentPlayer.claimRoute(map.getRoute(row,col));
            map.routeClaimed(map.getRoute(row,col));
        }
    }

    @Override
    public void endTurn() {
        for (RailroadBaronsObserver r:observers) {
            r.turnEnded(this,currentPlayer);
        }
        if (!gameIsOver()) {
            playerRot += 1;
            if (playerRot == 4) {
                playerRot = 0;
            }
            currentPlayer = players.get(playerRot);
            currentPlayer.startTurn(new StuPair(deck));
            for (RailroadBaronsObserver r : observers) {
                r.turnStarted(this, currentPlayer);
            }
        }
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public Collection<Player> getPlayers() {
        return players;
    }

    @Override
    public boolean gameIsOver() {
        boolean gameOver1 = false;
        boolean gameOver2 = false;
        if (map.getLengthOfShortestUnclaimedRoute() == 999) {
            gameOver1 = true;
            gameOver2 = true;
        }
        if (deck.numberOfCardsRemaining() == 0) {
            gameOver1 = true;
        }
        int x = 0;
        for (Player p : players) {
            if (!p.canContinuePlaying(map.getLengthOfShortestUnclaimedRoute())) {
                x += 1;
            }
        }
        if (x == 4&&deck.numberOfCardsRemaining()==0) {
            gameOver1 = true;
            gameOver2 = true;
        }
        if (gameOver1&&gameOver2) {
            Player winner = null;
            int highScore = 0;
            for (Player p : players) {
                if (p.getScore() >= highScore) {
                    highScore = p.getScore();
                    winner = p;
                }
            }
            for (RailroadBaronsObserver r : observers) {
                r.gameOver(this, winner);
            }
            return true;
        }
        return false;
    }
}
