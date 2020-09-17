package ua.axiom.labs.oslab1.planner.controller;

import ua.axiom.labs.oslab1.planner.model.PlannerSimulationResultsModel;
import ua.axiom.labs.oslab1.planner.model.Task;
import ua.axiom.labs.oslab1.planner.view.ResultsView;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ResultsViewController {
    private List<PlannerSimulationResultsModel.SingularTestResult> resultList;

    public ResultsViewController(List<PlannerSimulationResultsModel.SingularTestResult> resultList) {
        this.resultList = resultList;
    }

    public void run() {
        ResultsView view = new ResultsView();

        view.displayResults(resultList);

        //  wait & execute
        Set<PlannerSimulationResultsModel.SingularTestResult> interactiveResults = resultList.stream()
                .filter(singularTestResult -> singularTestResult.taskType == Task.TType.INTERACTIVE)
                .collect(Collectors.toSet());

        Set<PlannerSimulationResultsModel.SingularTestResult> backgroundResults = resultList.stream()
                .filter(singularTestResult -> singularTestResult.taskType == Task.TType.BACKGROUND)
                .collect(Collectors.toSet());

        double medWaitTimeBck = backgroundResults.stream()
                .mapToLong(result -> result.waitTime)
                .summaryStatistics()
                .getAverage();

        double medWaitTimeInt = interactiveResults.stream()
                .mapToLong(result -> result.waitTime)
                .summaryStatistics()
                .getAverage();


        double medianWaitToStartBck = backgroundResults.stream()
                .mapToLong(result -> result.startTime - result.creationTime)
                .summaryStatistics()
                .getAverage();

        double medianWaitToStartInt = interactiveResults.stream()
                .mapToLong(result -> result.startTime - result.creationTime)
                .summaryStatistics()
                .getAverage();


        view.displayValue("Average wait-to-start time for interactive task", medianWaitToStartInt);
        view.displayValue("Average wait-to-start time for background task", medianWaitToStartBck);

        view.displayValue("Average wait time for interactive task", medWaitTimeInt);
        view.displayValue("Average wait time for background task", medWaitTimeBck);
    }
}
