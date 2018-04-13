package student;

import model.Baron;
import model.Orientation;
import model.Route;
import model.Space;

/*
Author @Alexander Hurley
 */

public class StuTrack implements model.Track{

    int col;
    int row;
    StuRoute route = new StuRoute();

    public StuTrack(StuRoute route, int col, int row){
        this.route = route;
        this.col = col;
        this.row = row;

    }

    @Override
    public Orientation getOrientation() {
        return route.getOrientation();
    }

    @Override
    public Baron getBaron() {
        return route.getBaron();
    }

    @Override
    public Route getRoute() {
        return this.route;
    }

    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public int getCol() {
        return this.col;
    }

    @Override
    public boolean collocated(Space other) {
        int row = other.getRow();
        int col = other.getCol();

        if (row == this.row && col == this.col){
            return true;
        }
        return false;
    }
}
