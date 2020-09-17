package ua.axiom.labs.oslab1.planner.model;

import java.util.Comparator;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

public class BackgroundProcessorsModel {
    private final SortedSet<Task> tasks;

    public BackgroundProcessorsModel() {
        tasks = new TreeSet<>(Comparator.comparingLong(Task::getRemainingTime));
    }

    public synchronized void add(Task task) {
        tasks.add(task);
    }

    public synchronized  Optional<Task> getTask() {
        if(tasks.isEmpty()) {
            return Optional.empty();
        }

        Task task = tasks.first();
        tasks.remove(task);
        return Optional.of(task);
    }

    public synchronized void putTask(Task task) {
        tasks.add(task);
    }
}
