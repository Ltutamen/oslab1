package ua.axiom.labs.oslab1.planner.view;

import ua.axiom.labs.oslab1.planner.model.PlannerSimulationResultsModel;

import java.util.List;

public class ResultsView {
    private long aLong;

    public void displayResults(List<PlannerSimulationResultsModel.SingularTestResult> results) {
        displayHeader();
        for (PlannerSimulationResultsModel.SingularTestResult result : results) {
            System.out.format(
                    "|%5d,\t%7d,\t%10d,\t%16d\t%12d\t%9d|\n",
                    result.hashCode()%1000,
                    result.startTime,
                    result.endTime,
                    result.creationTime,
                    result.complexity,
                    result.endTime - result.creationTime - result.complexity
            );
        }
    }

    public void displayValue(String name, double value) {
        System.out.format("%s: %f\n", name, value);
    }

    private void displayHeader() {
        System.out.println("hash:\tstart time:\tfinish time:\t creation time:\t complexity:\twait time:");
    }
}
