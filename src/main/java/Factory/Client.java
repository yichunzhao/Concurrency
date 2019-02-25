package Factory;

/**
 * @author YNZ
 */

interface App {
    void open(String fileName);
}

class WordProcessor implements App {

    public void open(String fileName) {
        System.out.println("open by word");
    }
}

class TextProcessor implements App {

    @Override
    public void open(String fileName) {
        System.out.println("opne by text ");
    }
}

abstract class AppFactory {
    abstract public App getApp();

    public App getIntance(){
        return getApp();
    }
}

class TextProcessorFactory extends AppFactory{

    @Override
    public App getApp() {
        return new TextProcessor();
    }
}

class WordProcessorFactory extends AppFactory{

    @Override
    public App getApp() {
        return new WordProcessor();
    }
}

public class Client {

    public static void main(String... args) {

        AppFactory textFactory = new TextProcessorFactory();
        textFactory.getIntance().open("dd");



    }
}
