package Singleton;

/**
 * @author YNZ
 */
public class UsingSingleton {

    public static void main(String... args){
        // new DeviceManager(); //cannot new a DeviceManager

        DeviceManager.getInstance().doSomething();
    }
}
