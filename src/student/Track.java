package student;

import model.Baron;
import model.Orientation;
import model.Route;
import model.Space;

/*
Author @Alexander Hurley
 */

public class Track extends student.Space implements model.Track{

    int col;
    int row;
    student.Route route;

    public Track(student.Route route, int col, int row){
        super(row,col);
        this.route = route;

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
