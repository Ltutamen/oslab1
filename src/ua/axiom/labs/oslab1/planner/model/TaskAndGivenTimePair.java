package ua.axiom.labs.oslab1.planner.model;

public class TaskAndGivenTimePair {
    public final Task task;
    public final long time;

    public TaskAndGivenTimePair(Task task, long time) {
        this.task = task;
        this.time = time;
    }
}
