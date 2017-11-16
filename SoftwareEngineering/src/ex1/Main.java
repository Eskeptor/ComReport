package ex1;

public class Main {
    public static void main(String[] args) {
        Driver driver = new Driver();
        Movable car = new Car();
        driver.setMovable(car);
        driver.drive();

        Movable plane = new Plane();
        driver.setMovable(plane);
        driver.drive();
    }
}
