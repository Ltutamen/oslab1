package ua.axiom.labs.oslab1.planner.model;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class RoundRobinTaskModel {
    private List<Task> taskList = new ArrayList<>();
    private int pointer = 0;

    public boolean hasNext() {
        return taskList.size() > 0;
    }

    public Task getNext() {
        Task toRet = taskList.get(pointer++);

        if(pointer >= taskList.size()) {
            pointer = 0;
        }

        return toRet;
    }

    public void add(Task task) {
        taskList.add(task);
    }

    public void remove(Task task) {
        taskList.remove(task);
    }
}
