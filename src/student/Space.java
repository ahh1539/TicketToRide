package student;


/**
 * A space on the gameboard.
 * Author: Brett Farruggia (brf3493)
 */
public class Space implements model.Space {


    private Integer row;

    private Integer col;

    /**
     * Creates a space.
     */
     public Space(){

         this.row = row;
         this.col = col;


    }

    @Override
    /**
     * Returns the row position of the space
     */
    public int getRow() {
        return row;
    }

    @Override
    /**
     * Returns the column position of the space.
     */
    public int getCol() {
        return col;
    }

    @Override
    public boolean collocated(model.Space other) {
        return false;
    }
}
