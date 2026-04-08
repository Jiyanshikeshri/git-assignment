interface Animal {
    void sound();   // no body
}

class Dog implements Animal {
    public void sound() {
        System.out.println("Dog barks");
    }
}

public class Interface {
    public static void main(String[] args) {
        Dog d = new Dog();
        d.sound();
    }
}