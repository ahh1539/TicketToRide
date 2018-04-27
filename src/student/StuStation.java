package student;

import model.Station;

import model.Space;

/**
 * @author Brett Farruggia & Alex Hurley
 */
public class StuStation implements Station{

    private int ident;
    private String name;
    private int col;
    private int row;

    /**
     * Creates a staiton
     * @param ident station identity number
     * @param row station on this row
     * @param col station on this column
     * @param name name of the station
     */
    public StuStation(String name, int ident, int row, int col){
        this.name=name;
        this.col=col;
        this.row=row;
        this.ident = ident;
    }

    /**
    returns the station name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
    returns the row of the station
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
    returns the column of the station
     */
    @Override
    public int getCol() {
        return col;
    }


    public int getId() {return ident; }

    /**
    returns whether or not the station occupies the same place in the board as the passed in space type
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
