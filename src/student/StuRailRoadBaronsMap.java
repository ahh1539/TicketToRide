package student;

import model.RailroadMap;
import model.RailroadMapObserver;
import model.Route;
import model.Space;

import java.util.Collection;

/*
Author @Alexander Hurley
 */

public class StuRailRoadBaronsMap implements RailroadMap{
    private int Rows;
    private int Cols;
    Space[][] spaces;

    @Override
    public void addObserver(RailroadMapObserver observer) {

    }

    @Override
    public void removeObserver(RailroadMapObserver observer) {

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
        return null;
    }

    @Override
    public void routeClaimed(Route route) {

    }

    @Override
    public int getLengthOfShortestUnclaimedRoute() {
        return 0;
    }

    @Override
    public Collection<Route> getRoutes() {
        return null;
    }
}
