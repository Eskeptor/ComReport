package ex6;

public class Bird implements Flyable, Walkable {
    @Override
    public void fly() {
        System.out.println("Birds can fly.");
    }

    @Override
    public void walk() {
        System.out.println("Birds can walk.");
    }
}
