package model;

public class ExecutionStep {

    private int stepNumber;
    private int lineNumber;
    private String code;
    private String description;

    public ExecutionStep() {
    }

    public ExecutionStep(int stepNumber,
                         int lineNumber,
                         String code,
                         String description) {

        this.stepNumber = stepNumber;
        this.lineNumber = lineNumber;
        this.code = code;
        this.description = description;

    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {

        return String.format(
                "Step %-3d Line %-3d %-30s %s",
                stepNumber,
                lineNumber,
                code,
                description);

    }

}