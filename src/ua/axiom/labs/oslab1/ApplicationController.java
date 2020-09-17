package ua.axiom.labs.oslab1;

import ua.axiom.labs.oslab1.planner.controller.PlannerController;
import ua.axiom.labs.oslab1.planner.controller.ResultsViewController;
import ua.axiom.labs.oslab1.planner.model.PlannerModel;
import ua.axiom.labs.oslab1.planner.model.PlannerSimulationResultsModel;
import ua.axiom.labs.oslab1.planner.services.TaskProviderService;

import java.util.List;

public class ApplicationController {

    public static void main(String[] args) {
        PlannerModel plannerModel = new PlannerModel();
        TaskProviderService taskProvider = new TaskProviderService(plannerModel, 10000,0.2F, 10000);
        PlannerController plannerController = new PlannerController(taskProvider, plannerModel);
        taskProvider.addSubscriber(plannerController);
        new Thread(taskProvider).start();

        List<PlannerSimulationResultsModel.SingularTestResult> results = plannerController.run();

        ResultsViewController viewController = new ResultsViewController(results);
        viewController.run();
    }
}
