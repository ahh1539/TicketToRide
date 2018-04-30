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
     * @param start
     * @param end
     * @param baron
     */
    public StuRoute(StuStation start, StuStation end, Baron baron){

        this.start = start;
        this.end = end;

        if (start.getRow()==end.getRow()) {
            ori=Orientation.HORIZONTAL;
        } else {
            ori=Orientation.VERTICAL;
        }
        tracks = makeTracks();
        owner = baron;
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
    public boolean includesCoordinate(Space spaceOnBoard) {
        if (ori == Orientation.VERTICAL) {
            if (spaceOnBoard.getCol() == start.getCol()) {

                if ((spaceOnBoard.getRow() > start.getRow() && spaceOnBoard.getRow() < end.getRow()) ||
                        (spaceOnBoard.getRow() < start.getRow() && spaceOnBoard.getRow() > end.getRow())) {
                    return true;
                }
            }
        } else {
            if (spaceOnBoard.getRow() == start.getRow()) {

                if ((spaceOnBoard.getCol() > start.getCol() && spaceOnBoard.getCol() < end.getCol()) ||
                        (spaceOnBoard.getCol() < start.getCol() && spaceOnBoard.getCol() > end.getCol())) {
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


    public ArrayList<Track> makeTracks() {
        ArrayList<Track> newT = new ArrayList<>();

        if (ori==Orientation.VERTICAL) {
            for (int x = start.getRow()+1; x < end.getRow(); x++) {

                newT.add(new StuTrack(x, start.getCol(),this));
            }
        } else {
            for (int x = start.getCol()+1; x < end.getCol(); x++) {

                newT.add(new StuTrack(start.getRow(),x,this));
            }
        }
        return newT;
    }
}
