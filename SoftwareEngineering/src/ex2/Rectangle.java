package ex2;

public class Rectangle extends Shape {
    private double mWidth;
    private double mHeight;

    public Rectangle(final double _w, final double _h) {
        mWidth = _w;
        mHeight = _h;
    }

    @Override
    public double area() {
        return mWidth * mHeight;
    }
}
