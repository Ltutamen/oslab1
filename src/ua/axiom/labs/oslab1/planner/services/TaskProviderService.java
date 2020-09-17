package ua.axiom.labs.oslab1.planner.services;

import ua.axiom.labs.oslab1.planner.model.Task;
import ua.axiom.labs.oslab1.planner.controller.TaskListener;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TaskProviderService implements Runnable {
    private static final int MAX_TASK_COMPLEXITY = 260;
    private Set<TaskListener> listeners = new HashSet<>();
    private PlannerClock clock;

    private long tasksToCreate;
    private float percentOfBackgroundTasks;
    private long tasksPerMiliSecond;
    private Random random = new Random();

    public TaskProviderService(PlannerClock clock, long tasksToCrate, float percentOfBackgroundTasks, long tasksPerSecond) {
        this.tasksToCreate = tasksToCrate;
        this.percentOfBackgroundTasks = percentOfBackgroundTasks;
        this.tasksPerMiliSecond = tasksPerSecond / 1000;

        this.clock = clock;
    }

    @Override
    public void run() {
        //  todo make more sophisticated task creation
        while (tasksToCreate > 0) {
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < tasksPerMiliSecond; i++) {
                Task task = createTask();
                --tasksToCreate;
                notifyListenersOnNewTask(task);
            }
            long nowTime = System.currentTimeMillis();

            /*try {
                Thread.sleep(1000 - (nowTime - startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
        notifyListenersOnNoMoreTasks();
    }

    public void addSubscriber(TaskListener listener) {
        listeners.add(listener);
    }

    private void notifyListenersOnNewTask(Task task) {
        for (TaskListener listener : listeners) {
            listener.notifyOnHewTask(task);
        }
    }

    private void notifyListenersOnNoMoreTasks() {
        for (TaskListener listener : listeners) {
            listener.notifyNoMoreTasks();
        }
    }

    private Task createTask() {
        long taskComplexity = random.nextInt(MAX_TASK_COMPLEXITY);
        Task task = new Task(clock, taskComplexity);
        float randomFloat = random.nextFloat();
        task.setTime(taskComplexity);

        if(randomFloat > percentOfBackgroundTasks) {
             task.setType(Task.TType.INTERACTIVE);
        } else {
            task.setType(Task.TType.BACKGROUND);
        }

        return task;
    }


}
