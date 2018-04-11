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

    public Track(student.Route route){
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
