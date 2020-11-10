import java.io.*;
import java.util.logging.Level;

/**
 * Represents a job (command) to be run, and performs the execution. (You need to fill in the 
 * details!)
 */
public class Job implements Runnable
{
    private String command;
    private int delay;
    Logger logger;

    public Job (String command, int delay, Logger logger){
        this.logger = logger;
        this.delay = delay;
        this.command = command;
    }

    @Override
    public void run() {
        try {
            // Assume 'command' is a string containing the command the
            // execute. Then we initially run it as follows:
            Process proc = Runtime.getRuntime().exec(command);

            // Arrange to capture the command's output, line by line.
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(proc.getInputStream()));
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                output.append('\n');
                line = reader.readLine();
            }

            // We've now reached the end of the command's output, which
            // generally means the command has finished.
            // System.out.println(command + ": " + output.toString());
            try {
                logger.setMessage(command);

            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(Job.class.getName()).log(Level.SEVERE, null, ex);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
    // ...
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
