package student;

import model.Space;

/**
 * @author Brett Farruggia & Alex Hurley
 */
public class StuStation implements model.Station{

    private Integer ident;
    private String name;
    private Integer col;
    private Integer row;

    /**
     * Creates a staiton
     * @param ident station identity number
     * @param row station on this row
     * @param col station on this column
     * @param name name of the station
     */
    public StuStation(int ident, int row, int col, String name){
        this.name=name;
        this.col=col;
        this.row=row;
        this.ident=ident;
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
