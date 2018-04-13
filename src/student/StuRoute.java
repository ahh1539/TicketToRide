package student;

import model.Baron;
import model.Orientation;
import model.Space;
import model.Station;
import model.Track;

import java.util.ArrayList;
import java.util.List;


/**
 * Author: Brett Farruggia (brf3493)
 */
public class StuRoute implements model.Route {

    private ArrayList<Track> tracks;
    private model.Station start;
    private model.Station end;
    private Baron owner;
    private Orientation ori;

    public StuRoute(model.Station start, model.Station end, Baron baron){

        tracks = new ArrayList<>();
        this.start = start;
        this.end = end;
        owner = baron;
        if (start.getRow()==end.getRow()) {
            ori=Orientation.HORIZONTAL;
        } else {
            ori=Orientation.VERTICAL;
        }
    }

    @Override
    public Baron getBaron() {
        return owner;
    }

    @Override
    public Station getOrigin() {
        return start;
    }

    @Override
    public Station getDestination() {
        return end;
    }

    @Override
    public Orientation getOrientation() {
        return ori;
    }

    @Override
    public List<Track> getTracks() {
        return tracks;
    }

    @Override
    public int getLength() {
        return tracks.size();
    }

    @Override
    public int getPointValue() {
        return 0;
    }

    @Override
    public boolean includesCoordinate(Space space) {
        for (Track track: tracks) {
            if (track.collocated(space)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean claim(Baron claimant) {
        if (owner == Baron.UNCLAIMED) {
            owner = claimant;
            return true;
        }
        return false;
    }
}
