package parser;

import model.Program;
import model.Variable;
import model.ExecutionStep;

public class CodeParser {

    public void parseVariables(Program program) {

        program.clearVariables();

        for (String line : program.getSourceCode()) {

            line = line.trim();

            if (line.startsWith("int ")
                    || line.startsWith("double ")
                    || line.startsWith("String ")
                    || line.startsWith("boolean ")
                    || line.startsWith("char ")) {

                parseVariable(line, program);

            }

        }

    }

    private void parseVariable(String line, Program program) {

        line = line.replace(";", "");

        String[] parts = line.split("=", 2);

        if (parts.length != 2) {

            return;

        }

        String left = parts[0].trim();

        String value = parts[1].trim();

        String[] leftParts = left.split("\\s+");

        if (leftParts.length != 2) {

            return;

        }

        String type = leftParts[0];

        String name = leftParts[1];

        Variable variable = new Variable(name, type, value);

        program.addVariable(variable);

    }
    public void generateTimeline(Program program) {

        program.getExecutionLog().clear();

        int step = 1;

        int lineNumber = 1;

        for (String line : program.getSourceCode()) {

            String code = line.trim();

            if (code.isEmpty()) {

                lineNumber++;

                continue;

            }

            String description = "";

            if (code.startsWith("int ")
                    || code.startsWith("double ")
                    || code.startsWith("String ")
                    || code.startsWith("boolean ")
                    || code.startsWith("char ")) {

                description = "Variable Declaration";

            }

            else if (code.contains("main(")) {

                description = "Method Declaration";

            }

            else if (code.startsWith("System.out")) {

                description = "Output Statement";

            }

            else {

                description = "Statement";

            }

            program.getExecutionLog().addStep(

                    new ExecutionStep(

                            step,

                            lineNumber,

                            code,

                            description

                    )

            );

            step++;

            lineNumber++;

        }

    }

}