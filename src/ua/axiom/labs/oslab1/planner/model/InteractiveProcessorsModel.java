package ua.axiom.labs.oslab1.planner.model;

import java.util.ArrayList;
import java.util.List;

public class InteractiveProcessorsModel {
    private List<Task> taskList = new ArrayList<>();
    private int pointer = 0;

    public boolean hasNext() {
        return taskList.size() > 0;
    }

    public synchronized  Task getNext() {
        Task toRet = taskList.get(pointer);
        taskList.remove(pointer);
        pointer++;

        if(pointer >= taskList.size()) {
            pointer = 0;
        }

        return toRet;
    }

    public synchronized  void add(Task task) {
        taskList.add(pointer, task);
    }

    public synchronized  void remove(Task task) {
        taskList.remove(task);
    }

}
