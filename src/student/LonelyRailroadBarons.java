package student;

import model.*;

import java.util.ArrayList;
import java.util.Collection;

public class LonelyRailroadBarons implements model.RailroadBarons{
    private ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private RailroadMap map;

    private Deck deck;
    private int playerRot;
    private ArrayList<RailroadBaronsObserver> observers = new ArrayList<>();

    /*
    constructor of game that adds players to board and sets the player
    rotation to the beginning of the cycle
     */
    public LonelyRailroadBarons() {
        players.add(new StuAIPlayer(Baron.RED, this));
        players.add(new StuAIPlayer(Baron.GREEN, this));
        players.add(new StuAIPlayer(Baron.YELLOW, this));
        players.add(new StuPlayer(Baron.BLUE));
        deck = new StuDeck();
        playerRot = 0;
    }

    /*
    adds new observer observers list to be notified when the state of the game changes
     */
    @Override
    public void addRailroadBaronsObserver(RailroadBaronsObserver observer) {
        observers.add(observer);
    }

    /*
    removes an observer from the list of observers so that they are not notified
    when the game state changes
     */
    @Override
    public void removeRailroadBaronsObserver(RailroadBaronsObserver observer) {
        observers.remove(observer);
    }

    /*
    starts a new Railroad barons game and initializes all values to their start position and creates game deck
    using a given map
     */
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

    /*
    starts a new Railroad barons game and initializes all values to their start position using a given
    map and deck
     */
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

    /*
    returns the gameboard map
     */
    @Override
    public RailroadMap getRailroadMap() {
        return map;
    }

    /*
    returns the number of unassigned cards left in the deck
     */
    @Override
    public int numberOfCardsRemaining() {
        return deck.numberOfCardsRemaining();
    }

    /*
    returns a boolean stating whether or not a play is eligible to claim a route
     */
    @Override
    public boolean canCurrentPlayerClaimRoute(int row, int col) {
        return (currentPlayer.canClaimRoute(map.getRoute(row,col)));
    }

    /*
    claims route fot player and updates the gameboard of that change
     */
    @Override
    public void claimRoute(int row, int col) throws RailroadBaronsException {
        if (currentPlayer.getBaron() == Baron.BLUE) {
            if (canCurrentPlayerClaimRoute(row, col)) {
                currentPlayer.claimRoute(map.getRoute(row, col));
                map.routeClaimed(map.getRoute(row, col));
            }
        }

    }




    /*
    ends a players turn chainging players rotation value to next player
     */
    @Override
    public void endTurn() {
        for (RailroadBaronsObserver obs:observers) {
            obs.turnEnded(this,currentPlayer);
        }
        if (!gameIsOver()) {
            playerRot += 1;
            if (playerRot == 4) {
                playerRot = 0;
            }
            currentPlayer = players.get(playerRot);
            currentPlayer.startTurn(new StuPair(deck));
            for (RailroadBaronsObserver obz : observers) {
                obz.turnStarted(this, currentPlayer);
            }
        }
    }

    /*
      returns the player whose turn it currently is
     */
    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /*
    returns all players currently in the game
     */
    @Override
    public Collection<Player> getPlayers() {
        return players;
    }

    /*
    checks game status to see if game has met end requirenments and declairs a winner
     */
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

