import java.io.*;

/**
 * The logger is in charge of writing output to 'cron.log'. It does this in its own thread, but 
 * assumes that other threads will call the setMessage() in order to provide messages to log. (You 
 * need to fill in the details!)
 */
public class Logger {
    private String nextMessage;
    private Thread thread;
    private final Object monitor = new Object();
    private Thread currentThread;
    private final Object mutex = new Object();
    private volatile boolean exit = false;


    public Logger() {

    }

    public void setMessage(String newMessage) throws InterruptedException
    {
        synchronized(monitor) {
            monitor.wait();
            this.nextMessage = newMessage;
            System.out.println(newMessage);
//            monitor.notify();
        }
    }

    public void start()
    {
        Runnable logTask = () -> {
            while(true) {
                synchronized(monitor) {
                    try {
                        try (PrintWriter writer =
                                     new PrintWriter(new FileWriter("cron.log", true)))
                        {
                            writer.println(nextMessage);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        Thread.sleep(1000L);
                        monitor.notify();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        thread = new Thread(logTask, "logTask-thread");
        System.out.println("thread created = " + thread);
        thread.start();
    }

    public void stop()
    {
        synchronized(mutex) {
            currentThread.interrupt();
            System.out.println("The logger thread has stopped now");
            exit = true;
        }
    }

}