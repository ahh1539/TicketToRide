package student;

import model.Baron;
import model.Orientation;
import model.Space;
import model.Station;
import model.Track;


import model.*;

import static java.lang.Math.abs;


import java.util.ArrayList;
import java.util.List;


/**
 * Author: Brett Farruggia (brf3493) & Alex Hurley
 */
public class StuRoute implements model.Route {

    private ArrayList<Track> tracks;
    private StuStation start;
    private StuStation end;
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
        if (start.getRow()==end.getRow()) {
            ori=Orientation.HORIZONTAL;
        } else {
            ori=Orientation.VERTICAL;
        }
        tracks = buildTracks();
        owner = baron;
    }


    public ArrayList<Track> buildTracks() {
        ArrayList<Track> newTracks = new ArrayList<>();
        if (ori==Orientation.VERTICAL) {
            for (int x = start.getRow()+1; x < end.getRow(); x++) {
                newTracks.add(new StuTrack(x, start.getCol(),this));
            }
        } else {
            for (int x = start.getCol()+1; x < end.getCol(); x++) {
                newTracks.add(new StuTrack(start.getRow(),x,this));
            }
        }
        return newTracks;
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
        return this.tracks.size();
    }

    @Override
    public int getPointValue() {
        int size = getLength();
        if (size==1) {return 1;}
        else if (size==2) {return 2;}
        else if (size==3) {return 4;}
        else if (size==4) {return 7;}
        else if (size==5) {return 10;}
        else if (size==6) {return 15;}
        else {
            return 5*(size-3);
        }
    }

    @Override
    public boolean includesCoordinate(Space space) {
        if (ori == Orientation.VERTICAL) {
            if (space.getCol() == start.getCol()) {
                if ((space.getRow() > start.getRow() && space.getRow() < end.getRow()) ||
                        (space.getRow() < start.getRow() && space.getRow() > end.getRow())) {
                    return true;
                }
            }
        } else {
            if (space.getRow() == start.getRow()) {
                if ((space.getCol() > start.getCol() && space.getCol() < end.getCol()) ||
                        (space.getCol() < start.getCol() && space.getCol() > end.getCol())) {
                    return true;
                }
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
