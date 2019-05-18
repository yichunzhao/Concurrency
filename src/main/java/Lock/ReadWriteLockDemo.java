package Lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    //one writer thread at a time and multiple reader threads at a time
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    //read and write lock are separate lock
    private static ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    public static void main(String[] args) {

        readResource();


        writeResource();

    }

    public static void readResource(){
        readLock.lock();
        //read resource

        readLock.unlock();
    }

    public static void writeResource(){
        writeLock.lock();
        //write resource

        writeLock.unlock();
    }
}
