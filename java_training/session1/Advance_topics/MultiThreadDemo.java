// Creating a thread by extending Thread class
class MyThread extends Thread {

    // run() method contains the task to be performed by the thread
    public void run() {

        // currentThread().getName() gives the name of the running thread
        System.out.println(Thread.currentThread().getName() + " is running");
    }
}

public class MultiThreadDemo {

    public static void main(String[] args) {

        // Creating first thread object
        MyThread t1 = new MyThread();

        // Creating second thread object
        MyThread t2 = new MyThread();

        // Starting thread t1
        // This will internally call run() method in a separate thread
        t1.start();

        // Starting thread t2
        // This will also run concurrently with t1
        t2.start();
    }
}