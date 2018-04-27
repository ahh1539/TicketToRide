package student;

import model.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Builds a railroad map
 * @author Brett Farruggia & Alex Hurley
 */

/*
creates game board
 */
public class StuRailRoadBaronsMap implements RailroadMap{

    private int Rows;
    private int Cols;
    Space[][] spaces;
    private ArrayList<Route> routes;
    private ArrayList<RailroadMapObserver> observers;



/*
constructor which initializes values for rows cols and routes, also makes the game board
 */
    public StuRailRoadBaronsMap(int rows, int cols, ArrayList<Route> routes) {

        spaces = new Space[rows][cols];
        this.Rows = rows;
        this.Cols = cols;
        this.routes = routes;
        observers = new ArrayList<>();
        createSpaces();
    }



        public void createSpaces(){
            for (Route route: routes) {
                spaces[route.getOrigin().getRow()][route.getOrigin().getCol()] = route.getOrigin();

                spaces[route.getDestination().getRow()][route.getDestination().getCol()] = route.getDestination();

                for (Track track: route.getTracks()) {
                    spaces[track.getRow()][track.getCol()] = track;
                }
            }
        }

    /**
     * Adds the specified {@linkplain RailroadMapObserver observer} to the
     * map. The observer will be notified of significant events involving this
     * map such as when a {@linkplain Route route} has been claimed by a
     * {@linkplain Baron}.
     *
     * @param observer The {@link RailroadMapObserver} being added to the map.
     */
    @Override
    public void addObserver(RailroadMapObserver observer) {
        observers.add(observer);

    }

    /**
     * Removes the specified {@linkplain RailroadMapObserver observer} from
     * the map. The observer will no longer be notified of significant events
     * involving this map.
     *
     * @param observer The observer to remove from the collection of
     *                 registered observers that will be notified of
     *                 significant events involving this map.
     */
    @Override
    public void removeObserver(RailroadMapObserver observer) {
        observers.remove(observer);

    }

    /*
    returns the rows for the railroad map
     */
    @Override
    public int getRows() {

        return Rows;
    }

    /*
    returns the columns for the railroad map
     */
    @Override
    public int getCols() {

        return Cols;

    }

    /*
    returns the space at a given row and col
     */
    @Override
    public Space getSpace(int row, int col) {

        return spaces[row][col];
    }

    /*
    returns the route in which a space is in
     */
    @Override
    public Route getRoute(int row, int col) {
        for(Route route: routes) {
            if(route.includesCoordinate(getSpace(row, col))) {
                return route;
            }
        }
        return null;
    }


    /**
     * Called to update the {@linkplain RailroadMap map} when a
     * {@linkplain Baron} has claimed a {@linkplain Route route}.
     *
     * @param route The {@link Route} that has been claimed.
     */
    @Override
    public void routeClaimed(Route route) {
        for (RailroadMapObserver obs:observers) {
            obs.routeClaimed(this,route);
        }
    }

    /*
    returns the length of the shortest unclaimed route
     */
    @Override
    public int getLengthOfShortestUnclaimedRoute() {
        int length = 10000;
        for(Route route: routes) {
            if(route.getBaron() == Baron.UNCLAIMED) {
                if(route.getLength() < length) {
                    length = route.getLength();
                }
            }
        }
        return length;
    }

    /*
    returns a collection/arraylist of routes
     */
    @Override
    public Collection<Route> getRoutes() {
        return routes;
    }

    /*
    returns an arraylist of players in game
     */
    public ArrayList<RailroadMapObserver> getObservers() {
        return observers;
    }
}
