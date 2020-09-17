package ua.axiom.labs.oslab1.planner.controller;

import ua.axiom.labs.oslab1.planner.model.Task;

public interface TaskListener {
    void notifyOnHewTask(Task task);

    void notifyNoMoreTasks();
}
