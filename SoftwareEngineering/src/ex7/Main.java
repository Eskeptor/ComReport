package ex7;

public class Main {
    public static void main(String[] args) {
        Pet pet = new Cat();
        Person person = new Person();
        person.setPet(pet);
        person.loves();
    }
}
