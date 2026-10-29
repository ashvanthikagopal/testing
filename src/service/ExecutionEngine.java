package service;

import model.Program;
import parser.CodeParser;

public class ExecutionEngine {

    private CodeParser parser;

    public ExecutionEngine() {

        parser = new CodeParser();

    }

    public void execute(Program program) {

        parser.parseVariables(program);

        parser.generateTimeline(program);

        System.out.println();

        System.out.println("Execution Completed.");

        System.out.println();

        System.out.println(program.getVariables().size()
                + " Variables Parsed.");

        System.out.println(program.getExecutionLog()
                .getSteps()
                .size()
                + " Timeline Steps Generated.");

    }

}