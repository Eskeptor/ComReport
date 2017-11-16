package ex1;

public class Car implements Movable {
    @Override
    public void go() {
        System.out.println("Car goes...");
        System.out.println(".....");
        System.out.println(".....");
    }

    @Override
    public void engineOn() {
        System.out.println("Car engine started");
    }

    @Override
    public void engineOff() {
        System.out.println("Car engine stopped");
    }
}
