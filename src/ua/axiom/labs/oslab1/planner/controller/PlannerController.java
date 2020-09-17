package ua.axiom.labs.oslab1.planner.controller;

import ua.axiom.labs.oslab1.planner.model.PlannerSimulationResultsModel;
import ua.axiom.labs.oslab1.planner.model.Task;
import ua.axiom.labs.oslab1.planner.model.PlannerModel;
import ua.axiom.labs.oslab1.planner.model.TaskAndGivenTimePair;
import ua.axiom.labs.oslab1.planner.services.TaskProviderService;
import ua.axiom.labs.oslab1.planner.services.strategies.BackgroundPlanningStrategy;
import ua.axiom.labs.oslab1.planner.services.strategies.InteractivePlanningStrategy;
import ua.axiom.labs.oslab1.planner.services.strategies.PlanningStrategy;

import javax.swing.plaf.OptionPaneUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class PlannerController implements TaskListener, PlanningStrategy {
    private static final long TIME_QWANT_MCSECS = 100;
    private final PlannerModel model;
    private boolean isRunning = true;

    private final PlanningStrategy backgroundPlanningStrategy;
    private final PlanningStrategy interactivePlanningStrategy;

    private final Random miscRandom;
    private float percentageOnBackGroundTask = 0.8F;

    public PlannerController(TaskProviderService taskProviderService, PlannerModel model) {
        this.model = model;
        this.miscRandom = new Random();

        backgroundPlanningStrategy = new BackgroundPlanningStrategy(taskProviderService);
        interactivePlanningStrategy = new InteractivePlanningStrategy(taskProviderService);
    }

    public List<PlannerSimulationResultsModel.SingularTestResult> run() {
        List<PlannerSimulationResultsModel.SingularTestResult> results = new ArrayList<>();
        while (isRunning) {
            Optional<TaskAndGivenTimePair> toExecute = this.selectTaskToRun();
            Optional<PlannerSimulationResultsModel.SingularTestResult> optionalResult = processTask(toExecute.get().task, toExecute.get().time);
            optionalResult.ifPresent(results::add);
        }

        return results;
    }

    @Override
    public Optional<TaskAndGivenTimePair> selectTaskToRun() {
        Optional<TaskAndGivenTimePair> toReturn = Optional.empty();
        float chance = miscRandom.nextFloat();

        long beforeSearchTime = System.currentTimeMillis();
        while (!toReturn.isPresent()) {
            if (chance > percentageOnBackGroundTask) {
                toReturn = interactivePlanningStrategy.selectTaskToRun();
            }
            if (!toReturn.isPresent() || chance < percentageOnBackGroundTask) {
                toReturn = backgroundPlanningStrategy.selectTaskToRun();
            }
        }
        model.addWaitTime(System.currentTimeMillis() - beforeSearchTime);

        return toReturn;
    }

    @Override
    public void notifyOnHewTask(Task task) {
    }

    @Override
    public void notifyNoMoreTasks() {
        this.isRunning = false;
    }

    private Optional<PlannerSimulationResultsModel.SingularTestResult> processTask(Task task, long timeInMcSecs) {
        if (task.getType() == Task.TType.INTERACTIVE) {
            model.addInteractivePassedTime(timeInMcSecs);
        } else if (task.getType() == Task.TType.BACKGROUND) {
            model.addBackgroundPassedTime(timeInMcSecs);
        }

        task.setStartTime(model.getClockTime());

        model.addClockTime(timeInMcSecs);
        task.removeTime(timeInMcSecs);

        if (task.getRemainingTime() <= 0) {
            return finishTask(task);
        } else {
            putTaskBack(task);
        }

        return Optional.empty();
    }

    private Optional<PlannerSimulationResultsModel.SingularTestResult> finishTask(Task task) {
        PlannerSimulationResultsModel.SingularTestResult testResult =
                new PlannerSimulationResultsModel.SingularTestResult(
                        task.getType(),
                        task.getStartTime(),
                        model.getClockTime(),
                        -1,
                        -1,
                        task.getCreationTime(),
                        task.getComplexity());
        //  task already not present in model

        return Optional.of(testResult);
    }

    @Override
    public void putTaskBack(Task task) {
        if (task.getType() == Task.TType.BACKGROUND) {
            backgroundPlanningStrategy.putTaskBack(task);
        } else if (task.getType() == Task.TType.INTERACTIVE) {
            interactivePlanningStrategy.putTaskBack(task);
        }
    }
}
