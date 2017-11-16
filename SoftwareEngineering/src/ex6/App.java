package ex6;

public class App {
    public static void testISP(final Barkable _thing) {
        _thing.bark();
    }

    public static void main(String[] args) {
        testISP(new Dog());
//        testISP(new Human());
//        testISP(new Bird());
    }
}
