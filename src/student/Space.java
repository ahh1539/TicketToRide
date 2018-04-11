package student;

public class Space implements model.Space {
    @Override

    public int getRow() {
        return 0;
    }

    @Override
    public int getCol() {
        return 0;
    }

    @Override
    public boolean collocated(model.Space other) {
        return false;
    }
}
