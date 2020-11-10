import java.util.*;

/**
 * The scheduler keeps track of all the jobs, and runs each one at the appropriate time. (You need
 * to fill in the details!)
 */
public class Scheduler
{
    // ...
    private ArrayList<Job> jobs = new ArrayList<>();
    private Thread thread;
    private final Object mutex = new Object();
    private Thread currentThead;


    public void addJob(Job newJob)
    {
        synchronized (mutex) {
            System.out.println("Added new job!");
            jobs.add(newJob);
        }
    }
    
    public void start()
    {
        System.out.println("at start");
        currentThead = Thread.currentThread();
        // Define the task as an anonymous/
        // inner class.
        Runnable myTask = () -> {
            while (true) {
                synchronized (mutex) {
                    for (Job job : jobs) {

                        try {
                            Thread jobThread = new Thread(job, "job-thread");
                            jobThread.start();
                            Thread.sleep(job.getDelay() * 1000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        };

        thread = new Thread(myTask, "task");
        System.out.println("taskcreated = " + thread);
        thread.start();
    }

    public void stop()
    {
        synchronized (mutex){
            currentThead.interrupt();
        }
    }
}
