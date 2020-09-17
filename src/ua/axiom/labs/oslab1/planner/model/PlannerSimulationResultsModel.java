package ua.axiom.labs.oslab1.planner.model;

import java.util.ArrayList;
import java.util.List;

public class PlannerSimulationResultsModel {
    public static class SingularTestResult {
        public final Task.TType taskType;
        public final long creationTime;
        public final long startTime;
        public final long endTime;
        public final long waitTime;
        public final long executionTime;
        public final long complexity;

        public SingularTestResult(Task.TType taskType, long startTime, long endTime, long waitTime, long executionTime, long creationTime, long complexity) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.executionTime = executionTime;
            this.creationTime = creationTime;
            this.complexity = complexity;
            this.taskType = taskType;
            this.waitTime = endTime - creationTime - complexity;
        }
    }

    private final List<SingularTestResult> results;

    public PlannerSimulationResultsModel() {
        results = new ArrayList<>();
    }

    public void addResult(SingularTestResult result) {
        this.results.add(result);
    }


}
