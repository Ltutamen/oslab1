package ua.axiom.labs.oslab1.planner.services.strategies;

import ua.axiom.labs.oslab1.planner.model.Task;
import ua.axiom.labs.oslab1.planner.controller.TaskListener;
import ua.axiom.labs.oslab1.planner.model.BackgroundProcessorsModel;
import ua.axiom.labs.oslab1.planner.model.TaskAndGivenTimePair;
import ua.axiom.labs.oslab1.planner.services.TaskProviderService;

import java.util.Optional;

public class BackgroundPlanningStrategy implements PlanningStrategy, TaskListener {
    private final BackgroundProcessorsModel model;

    public BackgroundPlanningStrategy(TaskProviderService taskProviderService) {
        this.model = new BackgroundProcessorsModel();
        taskProviderService.addSubscriber(this);
    }

    @Override
    public Optional<TaskAndGivenTimePair> selectTaskToRun() {
        Optional<Task> task = model.getTask();
        return task.map(value -> new TaskAndGivenTimePair(value, 100));
    }

    @Override
    public void putTaskBack(Task task) {
        model.add(task);
    }

    @Override
    public void notifyOnHewTask(Task task) {
        model.add(task);
    }

    @Override
    public void notifyNoMoreTasks() {
    }
}
