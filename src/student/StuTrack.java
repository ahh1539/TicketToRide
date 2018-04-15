package student;

import model.Baron;
import model.Orientation;
import model.Route;
import model.Space;

/**
 * Creates a track
 * @author Brett Farruggia & Alex Hurley
 */
public class StuTrack implements model.Track{

    private int col;
    private int row;
    private Baron owner;
    private model.Route route;

    /**
     * Creates a track object
     * @param route the route that the tracks go on
     * @param col the column of the track
     * @param row the row of the track
     */
    public StuTrack(model.Route route, int col, int row){
        this.route = route;
        this.col = col;
        owner = Baron.UNCLAIMED;
        this.row = row;
    }

    /**
     *
     * @return the orientation
     */
    @Override
    public Orientation getOrientation() {
        return route.getOrientation();
    }

    /**
     *
     * @return the baron
     */
    @Override
    public Baron getBaron() {
        return route.getBaron();
    }

    /**
     *
     * @return the route
     */
    @Override
    public Route getRoute() {
        return this.route;
    }

    /**
     *
     * @return the row number
     */
    @Override
    public int getRow() {
        return this.row;
    }

    /**
     *
     * @return the row column
     */
    @Override
    public int getCol() {
        return this.col;
    }

    /**
     *
     * @param other The other space to which this space is being compared for
     *              collocation.
     *
     * @return boolean if it is collocated
     */
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
