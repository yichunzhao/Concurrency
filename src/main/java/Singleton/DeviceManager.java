package Singleton;

/**
 * @author YNZ
 */

interface DM {
    void doSomething();
}

public class DeviceManager implements DM {
    //lazy initialization
    private static DeviceManager ourInstance = null;

    private DeviceManager() {

    }

    //synchronized method; may prevent threads from concurrent access.
    synchronized public static DeviceManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DeviceManager();
        }

        return ourInstance;
    }

    public void doSomething() {
        System.out.println("doing something from the dm ... ");
    }
}

class UsingDeviceManager{

    public static void main(String... args){
        //new DeviceManager();
        DM dm = DeviceManager.getInstance();
        dm.doSomething();

        DM dm1 = DeviceManager.getInstance();
        DM dm2 = DeviceManager.getInstance();
        System.out.println(dm1 == dm2);

    }
}
