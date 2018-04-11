package student;

import model.Baron;
import model.Orientation;
import model.Route;
import model.Space;

/*
Author @Alexander Hurley
 */

public class Track implements model.Track{
    student.Route route = new student.Route();

    @Override
    public Orientation getOrientation() {
        return null;
    }

    @Override
    public Baron getBaron() {
        return null;
    }

    @Override
    public Route getRoute() {
        return null;
    }

    @Override
    public int getRow() {
        return 0;
    }

    @Override
    public int getCol() {
        return 0;
    }

    @Override
    public boolean collocated(Space other) {
        return false;
    }
}
