package ex3;

public class Main {
    public static void main(String[] args) {
        Shape rectangle1 = new Rectangle(10, 20);
        Shape rectangle2 = new Rectangle(20, 40);
        Shape cir = new Circle(20);

        AreaCalculator areaCalculator = new MultiCalculator();
        areaCalculator.addShape(rectangle1);
        areaCalculator.addShape(rectangle2);
        areaCalculator.addShape(cir);

        areaCalculator.calculate();

        ConsolePrinter printer = new ConsolePrinter();
        printer.setAreaCalculator(areaCalculator);
        printer.print();
    }
}
