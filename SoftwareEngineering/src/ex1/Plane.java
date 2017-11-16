package ex1;

public class Plane implements Movable {
    @Override
    public void go() {
        System.out.println("Plane files...");
        System.out.println(".....");
        System.out.println(".....");
    }

    @Override
    public void engineOn() {
        System.out.println("Plane engine started");
    }

    @Override
    public void engineOff() {
        System.out.println("Plane engine stopped");
    }
}
