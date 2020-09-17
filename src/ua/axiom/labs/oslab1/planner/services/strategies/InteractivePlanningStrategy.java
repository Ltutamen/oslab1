package ua.axiom.labs.oslab1.planner.services.strategies;

import ua.axiom.labs.oslab1.planner.model.Task;
import ua.axiom.labs.oslab1.planner.controller.TaskListener;
import ua.axiom.labs.oslab1.planner.model.InteractiveProcessorsModel;
import ua.axiom.labs.oslab1.planner.model.TaskAndGivenTimePair;
import ua.axiom.labs.oslab1.planner.services.TaskProviderService;

import java.util.Optional;

public class InteractivePlanningStrategy implements PlanningStrategy, TaskListener {
    private final InteractiveProcessorsModel model;

    public InteractivePlanningStrategy(TaskProviderService taskProviderService) {
        this.model = new InteractiveProcessorsModel();
        taskProviderService.addSubscriber(this);
    }

    @Override
    public Optional<TaskAndGivenTimePair> selectTaskToRun() {
        while (!model.hasNext()) {
        }
        return Optional.of(new TaskAndGivenTimePair(model.getNext(), 100));
    }

    @Override
    public void putTaskBack(Task task) {
        model.add(task);
    }

    @Override
    public void notifyOnHewTask(Task task) {
        if(task.getType() == Task.TType.INTERACTIVE) {
            model.add(task);
        }
    }

    @Override
    public void notifyNoMoreTasks() {
    }
}
