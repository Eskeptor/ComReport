package ex6;

public class Dog implements Barkable, Walkable {
    @Override
    public void bark() {
        System.out.println("Dog can bark.");
    }

    @Override
    public void walk() {
        System.out.println("Dog can walk.");
    }
}
