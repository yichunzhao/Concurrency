package Singleton;

/**
 * @author YNZ
 */
public enum Logger {
    INSTANCE;

    public void info(String text ){
        System.out.println(text);
    }

    public void warn(String text){
        System.out.println(text);
    }
}

class UsingEnumSingleton{

    public static void main(String... args){
        Logger.INSTANCE.info("information 1");
        Logger.INSTANCE.info("information 2");

        Logger ref1 = Logger.INSTANCE;
        Logger ref2 = Logger.INSTANCE;

        System.out.println(ref1==ref2);
    }
}
