package student;

import model.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The game logic for lonely version of railroad barons
 * @author Brett Farruggia & Alex Hurley
 */

public class LonelyRailroadBarons implements model.RailroadBarons{
    private ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;
    private RailroadMap map;

    private Deck deck;
    private int playerRot;
    private ArrayList<RailroadBaronsObserver> observers = new ArrayList<>();

    private ArrayList<Station> horizontalStations;
    private ArrayList<Station> verticalStations;
    private Integer[] mapBound;


    /**
     * Lonely Constructor that initializes three AI players and one human player.
     */
    public LonelyRailroadBarons() {
        players.add(new StuPlayer(Baron.BLUE));
        players.add(new StuAIPlayer(Baron.RED));
        players.add(new StuAIPlayer(Baron.GREEN));
        players.add(new StuAIPlayer(Baron.YELLOW));

        deck = new StuDeck();
        playerRot = 0;
    }

    /**
     * adds new observer observers list to be notified when the state of the game changes
     * @param observer The {@link RailroadBaronsObserver} to add to the
     */
    @Override
    public void addRailroadBaronsObserver(RailroadBaronsObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the list of observers
     * @param observer The {@link RailroadBaronsObserver} to remove.
     */
    @Override
    public void removeRailroadBaronsObserver(RailroadBaronsObserver observer) {
        observers.remove(observer);
    }

    /**
     * starts a new Railroad barons game and initializes all values to their start position and creates game
     * map using map file
     * @param map The {@link RailroadMap} on which the game will be played.
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

        if (currentPlayer instanceof StuAIPlayer) {
            try {
                ((StuAIPlayer) currentPlayer).findRoute(map.getRoutes());
            }
            catch (RailroadBaronsException x) {System.out.println(x.getMessage()); }

        }
        else {
            currentPlayer.startTurn(new StuPair(deck));
        }


        for (RailroadBaronsObserver r:observers) {
            r.turnStarted(this,currentPlayer);
        }

        mapBound = getMapBound();
        verticalStations = getVerticalStations();
        horizontalStations = getHorizontalStations();
    }

    /**
     * starts a new Railroad barons game and initializes all values to their start position using a given
     * a map and a deck
     * @param map The {@link RailroadMap} on which the game will be played.
     * @param deck The {@link Deck} of cards used to play the game. This may
     *             be ANY implementation of the {@link Deck} interface,
     *             meaning that a valid implementation of the
     *             {@link RailroadBarons} interface should use only the
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

        mapBound = getMapBound();
        verticalStations = getVerticalStations();
        horizontalStations = getHorizontalStations();
    }

    /**
     * The game board
     * @return map
     */
    @Override
    public RailroadMap getRailroadMap() {
        return map;
    }

    /**
     * returns the number of unassigned cards left in the deck
     * @return int
     */
    @Override
    public int numberOfCardsRemaining() {
        return deck.numberOfCardsRemaining();
    }

    /**
     * Returns a boolean stating if the player cna claim a route
     * @param row The row of a {@link Track} in the {@link Route} to check.
     * @param col The column of a {@link Track} in the {@link Route} to check.
     * @return
     */
    @Override
    public boolean canCurrentPlayerClaimRoute(int row, int col) {
        return (currentPlayer.canClaimRoute(map.getRoute(row,col)));
    }

    /**
     * Claims a route for the given route and player
     * @param row The row of a {@link Track} in the {@link Route} to claim.
     * @param col The column of a {@link Track} in the {@link Route} to claim.
     * @throws RailroadBaronsException
     */
    @Override
    public void claimRoute(int row, int col) throws RailroadBaronsException {
        if (currentPlayer.getBaron() == Baron.BLUE) {
            if (canCurrentPlayerClaimRoute(row, col)) {
                currentPlayer.claimRoute(map.getRoute(row, col));
                map.routeClaimed(map.getRoute(row, col));
            }
        }
        else{
            if (canCurrentPlayerClaimRoute(row, col)) {
                currentPlayer.claimRoute(map.getRoute(row, col));
                map.routeClaimed(map.getRoute(row, col));
            }
        }

    }


    /**
     * Ends a turn, changing the vlaue, thus updating which player takes their turn next
     */
    @Override
    public void endTurn() {
        for (RailroadBaronsObserver obs:observers) {
            obs.turnEnded(this, currentPlayer);
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

            if (currentPlayer instanceof StuAIPlayer) {
                try {
                    Route r = ((StuAIPlayer) currentPlayer).findRoute(map.getRoutes());
                    if (r!=null) {
                        map.routeClaimed(r);
                    }
                } catch (RailroadBaronsException e) {}
                endTurn();
            }
        }
    }

    /**
     *
     * @return the current player
     */
    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns all players in the game
     * @return the collection of players
     */
    @Override
    public Collection<Player> getPlayers() {
        return players;
    }

    /**
     * A function to test all cases of a winning condition
     * @return boolean- true if game is over
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
        
        int check = 0;
        for (Player p : players) {
            if (!p.canContinuePlaying(map.getLengthOfShortestUnclaimedRoute())) {
                check += 1;
            }
        }
        if (check == 4) {
            gameOver1 = true;
            gameOver2 = true;
        }
        if (gameOver1&&gameOver2) {
            int highestScore = 0;
            Player winPlayer = null;


            for (Player p : players) {

                boolean HorizontalMultiplier = false;
                boolean verticalMultiplier = false;
                
                for (Route r: p.getClaimedRoutes()) {
                    
                    if (r.getOrigin().getRow()==mapBound[2]) {
                        if (!verticalMultiplier) {
                            
                            if (crossCountryRoute(r.getOrigin(), verticalStations, p)) {
                                verticalMultiplier = true;
                            }
                        }
                    }
                    if (r.getOrigin().getCol()==mapBound[0]) {
                        if (!HorizontalMultiplier) {
                            
                            if (crossCountryRoute(r.getOrigin(), horizontalStations, p)) {
                                HorizontalMultiplier = true;
                            }
                        }
                    }
                }
                int playerScore = p.getScore();
                if (verticalMultiplier) {
                    playerScore += ((mapBound[3]-mapBound[2])+1) * 5;
                }
                if (HorizontalMultiplier) {
                    playerScore += ((mapBound[1]-mapBound[0])+1) * 5;
                }
                if (playerScore >= highestScore) {
                    highestScore = playerScore;
                    winPlayer = p;
                }
                p.startTurn(new StuPair(deck));

                if (p instanceof StuAIPlayer) {
                    ((StuAIPlayer) p).updateScoreGUI(playerScore);
                }
                else {
                    ((StuPlayer) p).updateScoreGUI(playerScore);
                }

            }
            for (RailroadBaronsObserver r : observers) {
                r.gameOver(this, winPlayer);
            }
            return true;
        }
        return false;
    }

    /**
     * Gets all vertical stations on the map eligible to be a start or finish node
     * @return a list of vertical stations that are eligible
     */
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

    /**
     * Returns all horizontal stations on the map eligible to be a start or finish node
     * @return A list of eligible nodes
     */
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

    /**
     * Depth First Search of the stations to find the paths
     * @param curr The current stations
     * @param wasVisited List of all visited stations
     * @param owner The player that it is currently checking
     */
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

    /**
     * Gets all the neighbors of the adjacent nodes (stations)
     * @param station
     * @return a list of neighbor stations
     */
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


    /**
     * Checks the bounds of the map and determines the top left corner
     * @return An array of integers
     */
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

    /**
     * Checks for corner stations, used in implementation of cross country route.
     * @param station A station
     * @return A boolean, true if the station is a corner
     */
    public boolean isACornerStation(Station station) {
        boolean cornerStat = false;

        if (station.getRow() == 0 && (station.getCol() == map.getCols() - 1 || station.getCol() == 0)) {
            cornerStat = true;
        } else if (station.getRow() == map.getRows() - 1 && (station.getCol() == 0 || station.getCol() == map.getCols() - 1)) {
            cornerStat = true;
        }

        return cornerStat;
    }


    /**
     * Checks for a cross country route that can be assigned to a player, a valid route
     * @param startNode The starting node of the route
     * @param finishNode The goal node of the route
     * @param play the player that the function is checking
     * @return A boolean, true if they have a cross country route
     */
    public boolean crossCountryRoute(Station startNode, ArrayList<Station> finishNode, Player play) {
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

