package ex7;

public class Person {
    private Pet mPet;
    public void setPet(final Pet _pet) {
        mPet = _pet;
    }
    public void loves() {
        System.out.println("I love " + mPet.toString());
    }
}
