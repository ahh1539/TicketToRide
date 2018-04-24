package student;

import model.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Builds a railroad map
 * @author Brett Farruggia & Alex Hurley
 */

public class StuRailRoadBaronsMap implements RailroadMap{

    private int Rows;
    private int Cols;
    Space[][] spaces;
    private ArrayList<Route> routes;
    private ArrayList<RailroadMapObserver> observers;


    /**
     * Constructs a railroad baron map
     * //@param rows the number of rows
     * //@param columns the number of columns
     * @param routes all of the routes on the map
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
                spaces[route.getOrigin().getRow()][route.getOrigin().getCol()]
                        = route.getOrigin();

                spaces[route.getDestination().getRow()][route.getDestination().getCol()]
                        = route.getDestination();

                for (Track tr: route.getTracks()) {
                    spaces[tr.getRow()][tr.getCol()] = tr;
                }
            }
        }

    /**
     * Adds the observer to the map
     * @param observer The observer being added to the map.
     */
    @Override
    public void addObserver(RailroadMapObserver observer) {
        observers.add(observer);

    }

    /**
     * Removes the observer form the map
     * @param observer The observer to remove from the collection of observers
     */
    @Override
    public void removeObserver(RailroadMapObserver observer) {
        observers.remove(observer);

    }

    /**
     *
     * @return the number of rows
     */
    @Override
    public int getRows() {
        //assumes at least one row
        return Rows;
    }

    /**
     *
     * @return the number of columns
     */
    @Override
    public int getCols() {
        //assumes at least one col
        return Cols;

    }

    /**
     * Gets a space on the game board.
     * @param row The row of the desired space
     * @param col The column of the desired space
     *
     * @return 2d array containing row and column
     */
    @Override
    public Space getSpace(int row, int col) {
        //assumes that space is a valid one
        return spaces[row][col];
    }

    /**
     *
     * @param row The row location of the tracks
     * @param col The column of the location of the tracks
     *
     * @return a route
     */
    @Override
    public Route getRoute(int row, int col) {
        for(Route r: routes) {
            if(r.includesCoordinate(getSpace(row, col))) {
                return r;
            }
        }
        return null;
    }


    /**
     * claims a route on the mpa
     * @param route The route that was claimed
     */
    @Override
    public void routeClaimed(Route route) {
        for (RailroadMapObserver r:observers) {
            r.routeClaimed(this,route);
        }
    }

    /**
     *
     * @return an integer that returns the shortest unclaimed route
     */
    @Override
    public int getLengthOfShortestUnclaimedRoute() {
        int n = 100000;
        for(Route r: routes) {
            if(r.getBaron() == Baron.UNCLAIMED) {
                if(r.getLength() < n) {
                    n = r.getLength();
                }
            }
        }
        return n;
    }

    /**
     *
     * @return a list tof routes
     */
    @Override
    public Collection<Route> getRoutes() {
        return routes;
    }

    public ArrayList<RailroadMapObserver> getObservers() {
        return observers;
    }
}
