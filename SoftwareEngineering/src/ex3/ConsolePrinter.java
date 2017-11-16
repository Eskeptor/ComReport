package ex3;

public class ConsolePrinter {
    private AreaCalculator mAreaCalculator;

    public void print() {
        double sum = mAreaCalculator.getAreaResult();
        System.out.println(sum);
    }

    public void setAreaCalculator(final AreaCalculator _ac) {
        mAreaCalculator = _ac;
    }
}
