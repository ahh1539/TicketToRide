package student;

import model.*;

import java.util.ArrayList;
import java.util.Collection;

/*
Author @Alexander Hurley
 */

public class StuRailRoadBaronsMap implements RailroadMap{

    public Space[][] spaces;
    public ArrayList observers;
    public StuRoute[] routes;
    private int rows;
    private int columns;


    public StuRailRoadBaronsMap(Integer rows, Integer columns, StuRoute[] routes){
        this.routes = routes;
        this.rows = rows;
        this.columns = columns;
        this.spaces = spaces;

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
        int num_rows = spaces.length;
        return num_rows;
    }

    @Override
    public int getCols() {
        //assumes at least one col
        int num_cols = spaces[0].length;
        return num_cols;

    }

    @Override
    public Space getSpace(int row, int col) {
        //assumes that space is a valid one
        return spaces[row][col];
    }

    @Override
    public Route getRoute(int row, int col) {
        routes[0].
        return null;
    }

    @Override
    public void routeClaimed(Route route) {

    }

    @Override
    public int getLengthOfShortestUnclaimedRoute() {
        int temp = 0;
        int shortest = 1000000000;
        for (int i = 0; i <= routes.length; i++){
            if (routes[i].getBaron() == Baron.UNCLAIMED){
                temp = routes[i].getLength();
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

    @Override
    public Collection<Route> getRoutes() {
        return null;
    }
}
