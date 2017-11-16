package ex2;

public class Main {
    public static void main(String[] args) {
        Shape rec1 = new Rectangle(10, 20);
        Shape rec2 = new Rectangle(20, 40);
        Shape cir = new Circle(20);

        AreaCalculator areaCalculator = new AreaCalculator();
        areaCalculator.addShape(rec1);
        areaCalculator.addShape(rec2);
        areaCalculator.addShape(cir);

        areaCalculator.calculate();

        ConsolePrinter consolePrinter = new ConsolePrinter();
        consolePrinter.setAreaCalculator(areaCalculator);
        consolePrinter.print();
    }
}
