package ClassDesign;

/**
 * @author YNZ
 */
interface Eatable {
}

class Meat implements Eatable {
}

class Hay implements Eatable {
}

class Worm implements Eatable {
}

abstract class Animal {
    abstract void feed(Eatable eatable);
}

class Bird extends Animal {
    void feed(Eatable eatable) {
        System.out.println("bird feed.");
    }
}

class Lion extends Animal {
    void feed(Eatable eatable) {
        System.out.println("lion feed.");
    }
}

public class Animals {

    public static void main(String[] args) {

        Animal bird = new Bird();
        bird.feed(new Worm());

        Animal lion = new Lion();
        lion.feed(new Meat());
    }
}
