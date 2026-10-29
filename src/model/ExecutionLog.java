package model;

import java.util.ArrayList;

public class ExecutionLog {

    private ArrayList<ExecutionStep> steps;

    public ExecutionLog() {

        steps = new ArrayList<>();

    }

    public void addStep(ExecutionStep step) {

        steps.add(step);

    }

    public ArrayList<ExecutionStep> getSteps() {

        return steps;

    }

    public void clear() {

        steps.clear();

    }

}