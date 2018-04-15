package student;

import model.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Builds a railroad map
 * @author Brett Farruggia & Alex Hurley
 */

public class StuRailRoadBaronsMap implements RailroadMap{

    public Space[][] spaces;
    public ArrayList<RailroadMapObserver> observers;
    public ArrayList<model.Route> routes;
    private Integer rows;
    private Integer columns;
    private Baron baron;
    private ArrayList<Route> unclaimed;


    /**
     * Constructs a railroad baron map
     * @param rows the number of rows
     * @param columns the number of columns
     * @param routes all of the routes on the map
     */
    public StuRailRoadBaronsMap(int rows, int columns, ArrayList<Route> routes){
        this.routes = routes;
        this.rows = rows;
        this.columns = columns;
        for (Route route: routes){
            this.routes.add(route);
            this.unclaimed.add(route);
            spaces[route.getOrigin().getRow()][route.getOrigin().getCol()] = route.getOrigin();
            spaces[route.getDestination().getRow()][route.getDestination().getCol()] = route.getDestination();
            for (Track track: route.getTracks()) {
                spaces[track.getRow()][track.getCol()] = track;
            }
        }
        for (int row = 0; row <= rows; row++) {
            for (int col = 0; col <= columns; col++) {
                if (spaces[row][col] == null){
                    spaces[row][col] = (new StuSpace(row, col));
                }
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
        int num_rows = spaces.length;
        return num_rows;
    }

    /**
     *
     * @return the number of columns
     */
    @Override
    public int getCols() {
        //assumes at least one col
        int num_cols = spaces[0].length;
        return num_cols;

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
        for (Route route: routes) {
            ArrayList<model.Track> tracks = (ArrayList) route.getTracks();
            StuTrack track = new StuTrack(route,col,row);
            for (Track find: tracks) {
                if (find == track){
                    return route;
                }
            }
        }
        // look into changing the track constructor to implement space
        return null;
    }


    /**
     * claims a route on the mpa
     * @param route The route that was claimed
     */
    @Override
    public void routeClaimed(Route route) {
        for (RailroadMapObserver rail_obs: observers) {
            rail_obs.routeClaimed(this, route);
        }
        route.claim(route.getBaron());

    }

    /**
     *
     * @return an integer that returns the shortest unclaimed route
     */
    @Override
    public int getLengthOfShortestUnclaimedRoute() {
        int temp;
        int shortest = 1000000000;
        for (int i = 0; i <= routes.size(); i++){
            if (routes.get(i).getBaron() == Baron.UNCLAIMED){
                temp = routes.get(i).getLength();
                if (temp < shortest){
                    shortest = temp;
                }
                else {
                    i++;
                }
            }
            else{
                i++;
            }
        }
        return shortest;
    }

    /**
     *
     * @return a list tof routes
     */
    @Override
    public Collection<Route> getRoutes() {
        return routes;
    }
}
