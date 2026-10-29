package service;

import model.ExecutionStep;
import model.Program;

public class VisualizationService {

    public void displayTimeline(Program program) {

        System.out.println();

        System.out.println("============= EXECUTION TIMELINE =============");

        for (ExecutionStep step :

                program.getExecutionLog().getSteps()) {

            System.out.println(step);

        }

    }

}