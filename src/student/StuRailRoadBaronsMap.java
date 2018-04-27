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


    @Override
    public void addObserver(RailroadMapObserver observer) {
        observers.add(observer);

    }

    @Override
    public void removeObserver(RailroadMapObserver observer) {
        observers.remove(observer);

    }


    @Override
    public int getRows() {

        return Rows;
    }


    @Override
    public int getCols() {

        return Cols;

    }


    @Override
    public Space getSpace(int row, int col) {

        return spaces[row][col];
    }


    @Override
    public Route getRoute(int row, int col) {
        for(Route route: routes) {
            if(route.includesCoordinate(getSpace(row, col))) {
                return route;
            }
        }
        return null;
    }



    @Override
    public void routeClaimed(Route route) {
        for (RailroadMapObserver r:observers) {
            r.routeClaimed(this,route);
        }
    }


    @Override
    public int getLengthOfShortestUnclaimedRoute() {
        int var = 10000;
        for(Route route: routes) {
            if(route.getBaron() == Baron.UNCLAIMED) {
                if(route.getLength() < var) {
                    var = route.getLength();
                }
            }
        }
        return var;
    }

    @Override
    public Collection<Route> getRoutes() {
        return routes;
    }

    public ArrayList<RailroadMapObserver> getObservers() {
        return observers;
    }
}
