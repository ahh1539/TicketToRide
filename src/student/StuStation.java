package student;

import model.Space;

/*
Author @Alexander Hurley
 */
public class StuStation implements model.Station{

    private Integer ident;
    private String name;
    private Integer col;
    private Integer row;

    /*
    initializes values
     */
    public StuStation(Integer ident, Integer row, Integer col, String name){
        this.name=name;
        this.col=col;
        this.row=row;
        this.ident=ident;
    }

    /*
    returns the station name
     */
    @Override
    public String getName() {
        return name;
    }

    /*
    returns the row of the station
     */
    @Override
    public int getRow() {
        return row;
    }

    /*
    returns the column of the station
     */
    @Override
    public int getCol() {
        return col;
    }

    /*
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
