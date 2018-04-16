package student;

import model.Baron;
import model.Orientation;
import model.Space;
import model.Station;
import model.Track;

import java.util.ArrayList;
import java.util.List;


/**
 * Author: Brett Farruggia (brf3493) & Alex Hurley
 */
public class StuRoute implements model.Route {

    private ArrayList<Track> tracks = new ArrayList<>();
    private model.Station start;
    private model.Station end;
    private Baron owner;
    private Orientation ori;

    /**
     * The constructor that creates the route
     * @param start starting point of a route (a station)
     * @param end ending point of a route (a station)
     * @param baron a baron who can claim the route
     */
    public StuRoute(StuStation start, StuStation end, Baron baron){


        this.start = start;
        this.end = end;
        owner = baron;
        if (start.getRow()==end.getRow()) {
            ori=Orientation.HORIZONTAL;
            for (int i = start.getCol() + 1; i< end.getCol();i++){
                tracks.add(new StuTrack(this,i, start.getRow()));
            }
        } else {
            ori=Orientation.VERTICAL;
            for (int j = start.getRow() +1; j <end.getRow();j++){
                tracks.add(new StuTrack(this, start.getCol(),j));
            }
        }
    }

    //public ArrayList<Track> buildTracks() {
      //  ArrayList<Track> newTracks = new ArrayList<>();
        //if (ori==Orientation.VERTICAL) {
          //  for (int x = start.getRow()+1; x < end.getRow(); x++) {
            //    newTracks.add(new StuTrack(this, start.getCol(),x));
            //}
        //} else {
          //  /**
            //for (int x = start.getCol()+1; x < end.getCol(); x++) {
              //  newTracks.add(new StuTrack(this, x, start.getRow()));
            //}
             //**/
        //}
        //return newTracks;
   // }

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
        return this.tracks.size();
    }

    @Override
    public boolean includesCoordinate(Space space) {
        boolean tracker = false;
        for (Track track: tracks) {
            if (track.getCol() == space.getCol() && track.getRow() == space.getRow()) {
                tracker=true;
            }
        }
        if (start.getCol() == space.getCol() && space.getRow() == space.getRow()){
            tracker = true;
        }
        if (end.getCol() == space.getCol() && end.getRow() == space.getRow()){
            tracker = true;
        }
        return tracker;
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
