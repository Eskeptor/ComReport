package ex1;

public class Driver {
    private Movable mMovable;

    /*public Driver(final Movable _movable) {
        mMovable = _movable;
    }*/

    public void setMovable(final Movable _movable) {
        mMovable = _movable;
    }

    public Movable getMovable() {
        return mMovable;
    }

    public void drive() {
        mMovable.engineOn();
        mMovable.go();
        mMovable.engineOff();
    }
}
