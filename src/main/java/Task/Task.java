package Task;

import java.io.Serializable;

public abstract class Task implements Serializable {

    private final String task;

    private boolean done = false;

    /**
     * Initialises a task object.
     *
     * @param task description of the task
     */
    public Task(String task) {
        this.task = task;
    }

    /**
     * Gets the description of the task.
     *
     * @return the description of the tasl
     */
    public String getTask() {
        return this.task;
    }

    /**
     * Gets the completion status of the task.
     *
     * @return the completion status of the task.
     */
    public boolean getDone() {
        return this.done;
    }

    /**
     * Marks the task as complete.
     *
     * @return the String representation of the marking action.
     */
    public String markdone() {
        this.done = true;
        return "Yatta~ Congrats master, you've completed this task!\n" + this;
    }

     public boolean contains(String keyword) {
         return task.contains(keyword);
     }

    /**
     * Marks the task as incomplete.
     *
     * @return the String representation of the unmarking action.
     */
    public String unmarkdone() {
        this.done = false;
        return "All the best for this task, master!\n" + this;
    }
}