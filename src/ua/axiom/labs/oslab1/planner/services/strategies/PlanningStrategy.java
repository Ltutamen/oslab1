package ua.axiom.labs.oslab1.planner.services.strategies;

import ua.axiom.labs.oslab1.planner.model.Task;
import ua.axiom.labs.oslab1.planner.model.TaskAndGivenTimePair;

import java.util.Optional;

/**
 * Selects some Task to run from given set of tasks
 */
public interface PlanningStrategy {
    Optional<TaskAndGivenTimePair> selectTaskToRun();


    void putTaskBack(Task task);
}
