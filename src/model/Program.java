package model;

import java.util.ArrayList;

public class Program {

    private String fileName;
    private ExecutionLog executionLog;

    private ArrayList<String> sourceCode;

    private ArrayList<Variable> variables;

    public Program() {

        sourceCode = new ArrayList<>();

        variables = new ArrayList<>();

        executionLog = new ExecutionLog();

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<String> getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(ArrayList<String> sourceCode) {
        this.sourceCode = sourceCode;
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }
    public void addVariable(Variable variable) {

        variables.add(variable);

    }

    public void clearVariables() {

        variables.clear();

    }

    public ExecutionLog getExecutionLog() {

        return executionLog;

    }
    public void setExecutionLog(ExecutionLog executionLog) {

        this.executionLog = executionLog;

    }
}