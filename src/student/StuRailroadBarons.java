package student;

import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


/**
 * The game logic for railroad barons
 * @author Brett Farruggia & Alex Hurley
 */
public class StuRailroadBarons implements model.RailroadBarons {

    private ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private RailroadMap map;

    private Deck deck;
    private int playerValue;
    private ArrayList<RailroadBaronsObserver> observers = new ArrayList<>();

    private ArrayList<Station> horizontalStations;
    private ArrayList<Station> verticalStations;
    private Integer[] mapBound;


    /*
    constructor of game that adds players to board and sets the player
    rotation to the beginning of the cycle
     */
    public StuRailroadBarons() {
        players.add(new StuPlayer(Baron.RED));
        players.add(new StuPlayer(Baron.GREEN));
        players.add(new StuPlayer(Baron.YELLOW));
        players.add(new StuPlayer(Baron.BLUE));
        deck = new StuDeck();
        playerValue = 0;
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
        playerValue = 0;

        for (Player p : players) {
            p.reset(deck.drawACard(), deck.drawACard(),
                    deck.drawACard(), deck.drawACard());
        }

        currentPlayer = players.get(playerValue);
        currentPlayer.startTurn(new StuPair(deck));

        for (RailroadBaronsObserver obs : observers) {
            obs.turnStarted(this, currentPlayer);
        }

        mapBound = getMapBound();
        verticalStations = getVerticalStations();
        horizontalStations = getHorizontalStations();
    }

    /*
    starts a new Railroad barons game and initializes all values to their start position using a given
    map and deck
     */
    @Override
    public void startAGameWith(RailroadMap map, Deck NewDeck) {
        NewDeck = new StuDeck();
        playerValue = 0;
        this.map = map;
        for (Player p : players) {

            p.reset(NewDeck.drawACard(), NewDeck.drawACard(),
                    NewDeck.drawACard(), NewDeck.drawACard());
        }
        currentPlayer = players.get(playerValue);
        currentPlayer.startTurn(new StuPair(deck));

        for (RailroadBaronsObserver obs : observers) {
            obs.turnStarted(this, currentPlayer);
        }

        mapBound = getMapBound();
        verticalStations = getVerticalStations();
        horizontalStations = getHorizontalStations();

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
        return (currentPlayer.canClaimRoute(map.getRoute(row, col)));
    }

    /*
    claims route fot player and updates the gameboard of that change
     */
    @Override
    public void claimRoute(int row, int col) throws RailroadBaronsException {
        if (canCurrentPlayerClaimRoute(row, col)) {
            currentPlayer.claimRoute(map.getRoute(row, col));
            map.routeClaimed(map.getRoute(row, col));
        }
    }

    /*
    ends a players turn chainging players rotation value to next player
     */
    @Override
    public void endTurn() {
        for (RailroadBaronsObserver obs : observers) {
            obs.turnEnded(this, currentPlayer);
        }
        if (!gameIsOver()) {
            playerValue += 1;
            if (playerValue == 4) {
                playerValue = 0;
            }
            currentPlayer = players.get(playerValue);
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
        if (x == 4) {
            gameOver1 = true;
            gameOver2 = true;
        }
        if (gameOver1&&gameOver2) {
            Player winner = null;
            int highScore = 0;
            for (Player p : players) {
                boolean vertBonus = false;
                boolean horiBonus = false;
                for (Route r: p.getClaimedRoutes()) {
                    if (r.getOrigin().getRow()==mapBound[2]) {
                        if (vertBonus==false) {
                            if (crossCountryRoute(r.getOrigin(), verticalStations, p)) {
                                vertBonus = true;
                            }
                        }
                    }
                    if (r.getOrigin().getCol()==mapBound[0]) {
                        if (horiBonus==false) {
                            if (crossCountryRoute(r.getOrigin(), horizontalStations, p)) {
                                horiBonus = true;
                            }
                        }
                    }
                }
                int playerScore = p.getScore();
                if (vertBonus) {
                    playerScore += ((mapBound[3]-mapBound[2])+1) * 5;
                }
                if (horiBonus) {
                    playerScore += ((mapBound[1]-mapBound[0])+1) * 5;
                }
                if (playerScore >= highScore) {
                    highScore = playerScore;
                    winner = p;
                }
                p.startTurn(new StuPair(deck));
                ((StuPlayer) p).updateScoreGUI(playerScore);
            }
            for (RailroadBaronsObserver r : observers) {
                r.gameOver(this, winner);
            }
            return true;
        }
        return false;
    }

    public ArrayList<Station> getVerticalStations() {
        ArrayList<Station> mapBorderStations = new ArrayList<>();

        for (Route route : map.getRoutes()) {
            if (route.getOrigin().getRow() == mapBound[2]) {

                if (!mapBorderStations.contains(route.getOrigin())) {
                    mapBorderStations.add(route.getOrigin());
                }
            }

            if (route.getDestination().getRow() == mapBound[2]) {

                if (!mapBorderStations.contains(route.getDestination())) {
                    mapBorderStations.add(route.getDestination());
                }
            }
            if (route.getDestination().getRow() == map.getRows() - 1) {

                if (!mapBorderStations.contains(route.getDestination())) {
                    mapBorderStations.add(route.getDestination());
                }
            }

            if (route.getOrigin().getRow() == map.getRows() - 1) {

                if (!mapBorderStations.contains(route.getOrigin())) {
                    mapBorderStations.add(route.getOrigin());
                }
            }
        }
        return mapBorderStations;
    }

    public ArrayList<Station> getHorizontalStations() {
        ArrayList<Station> mapBorderStations = new ArrayList<>();

        for (Route route : map.getRoutes()) {
            if (route.getOrigin().getCol() == mapBound[2]) {

                if (!mapBorderStations.contains(route.getOrigin())) {
                    mapBorderStations.add(route.getOrigin());
                }
            }

            if (route.getDestination().getCol() == mapBound[2]) {

                if (!mapBorderStations.contains(route.getDestination())) {
                    mapBorderStations.add(route.getDestination());
                }
            }
            if (route.getDestination().getCol() == map.getCols() - 1) {

                if (!mapBorderStations.contains(route.getDestination())) {
                    mapBorderStations.add(route.getDestination());
                }
            }

            if (route.getOrigin().getCol() == map.getCols() - 1) {

                if (!mapBorderStations.contains(route.getOrigin())) {
                    mapBorderStations.add(route.getOrigin());
                }
            }
        }
        return mapBorderStations;
    }

    public void DFSCrossCountry(Station curr, ArrayList<Station> wasVisited, Player owner) {
        for (Station neigh : getNeighbors(curr)) {

            if (!wasVisited.contains(neigh)) {

                for (Route route : map.getRoutes()) {

                    if (route.getOrigin() == neigh && route.getDestination() == curr
                            && route.getBaron() == owner.getBaron()) {

                        wasVisited.add(neigh);
                        DFSCrossCountry(neigh, wasVisited, owner);
                    }

                    if (route.getOrigin() == curr && route.getDestination() == neigh
                            && route.getBaron() == owner.getBaron()) {

                        wasVisited.add(neigh);
                        DFSCrossCountry(neigh, wasVisited, owner);
                    }
                }
            }
        }
    }

    public ArrayList<Station> getNeighbors(Station station) {
        ArrayList<Station> neighbor = new ArrayList<>();

        for (Route route : map.getRoutes()) {

            if (route.getOrigin() == station) {
                if (!neighbor.contains(route.getDestination())) {
                    neighbor.add(route.getDestination());
                }
            }

            if (route.getDestination() == station) {
                if (!neighbor.contains(route.getOrigin())) {
                    neighbor.add(route.getOrigin());
                }
            }
        }
        return neighbor;
    }


    public Integer[] getMapBound() {
        Integer[] mpBound = new Integer[4];

        int left = 500;  //top left
        int right = 0;

        int top = 500;  //top left
        int bottom = 0;

        for (Route r : map.getRoutes()) {
            if (r.getOrigin().getRow() < top) {
                top = r.getOrigin().getRow();
            }

            if (r.getOrigin().getRow() > bottom) {
                bottom = r.getOrigin().getRow();
            }

            if (r.getOrigin().getCol() > right) {
                right = r.getOrigin().getCol();
            }

            if (r.getDestination().getRow() > bottom) {
                bottom = r.getDestination().getRow();
            }

            if (r.getDestination().getCol() > right) {
                right = r.getDestination().getCol();
            }

            if (r.getDestination().getCol() < left) {
                left = r.getDestination().getCol();
            }

            if (r.getOrigin().getCol() < left) {
                left = r.getOrigin().getCol();
            }

            if (r.getDestination().getRow() < top) {
                top = r.getDestination().getRow();
            }
        }

        mpBound[0] = left;
        mpBound[1] = right;
        mpBound[2] = top;
        mpBound[3] = bottom;

        return mpBound;
    }

    public boolean isACornerStation(Station station) {
        boolean cornerStat = false;

        if (station.getRow() == 0 && (station.getCol() == map.getCols() - 1 || station.getCol() == 0)) {
            cornerStat = true;
        } else if (station.getRow() == map.getRows() - 1 && (station.getCol() == 0 || station.getCol() == map.getCols() - 1)) {
            cornerStat = true;
        }

        return cornerStat;
    }

    public boolean crossCountryRoute(Station startNode, ArrayList<Station> finishNode, Player play) {
        //Boolean[] multi = play.getMultiplier();
        boolean checkCornerStation = isACornerStation(startNode);
        ArrayList<Station> visitedStation = new ArrayList<>();

        visitedStation.add(startNode);
        DFSCrossCountry(startNode, visitedStation, play);

        if (visitedStation.size() < 3) {
            return false;
        }
        for (Station Stat: finishNode){
            if (visitedStation.contains(Stat)) {

                if (verticalStations.contains(startNode)) {
                    if (!checkCornerStation &&(startNode.getRow() == Stat.getRow())) {}
                    else if (startNode == Stat){}

                    else if ((checkCornerStation && ! isACornerStation(Stat))&& startNode.getRow()== Stat.getRow()){
                    }
                    else if ((checkCornerStation && ! isACornerStation(Stat))&& startNode.getCol()== Stat.getCol()){
                    }

                    else {
                        return true;
                    }
                }
                if (horizontalStations.contains(startNode)){
                    if (!checkCornerStation&&(startNode.getCol() == Stat.getCol())) {}
                    else if (startNode==Stat){}

                    else if ((checkCornerStation &&! isACornerStation(Stat))&& startNode.getRow()==Stat.getRow()){

                    }
                    else if ((checkCornerStation&&! isACornerStation(Stat))&& startNode.getCol()==Stat.getCol()){

                    }

                    else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
