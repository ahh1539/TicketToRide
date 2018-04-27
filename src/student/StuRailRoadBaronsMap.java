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
                spaces[route.getOrigin().getRow()][route.getOrigin().getCol()]
                        = route.getOrigin();

                spaces[route.getDestination().getRow()][route.getDestination().getCol()]
                        = route.getDestination();

                for (Track tr: route.getTracks()) {
                    spaces[tr.getRow()][tr.getCol()] = tr;
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
        //assumes at least one row
        return Rows;
    }


    @Override
    public int getCols() {
        //assumes at least one col
        return Cols;

    }


    @Override
    public Space getSpace(int row, int col) {
        //assumes that space is a valid one
        return spaces[row][col];
    }


    @Override
    public Route getRoute(int row, int col) {
        for(Route r: routes) {
            if(r.includesCoordinate(getSpace(row, col))) {
                return r;
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

    @Override
    public Collection<Route> getRoutes() {
        return routes;
    }

    public ArrayList<RailroadMapObserver> getObservers() {
        return observers;
    }
}
