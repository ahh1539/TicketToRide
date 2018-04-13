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
     public Space(Integer row, Integer col){

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

    /**
     *
     * Checks if two spaces occupy the same row and column on the map.
     *
     * @param other The other space to which this space is being compared for
     *              collocation.
     *
     * @return True if the space is not collocated
     */
    @Override
    public boolean collocated(model.Space other) {

        int col = other.getCol();
        int row = other.getRow();


        if (row == this.row && col == this.col){
            return true;
        }
        return false;
    }
}
