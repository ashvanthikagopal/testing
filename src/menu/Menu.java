package menu;
import model.Program;
import util.FileManager;
import util.Validation;
import model.Variable;
import service.AuthService;
import service.ExecutionEngine;
import java.util.Scanner;
import service.VisualizationService;

public class Menu {
    private Program currentProgram;
    private FileManager fileManager;
    private Scanner scanner;
    private ExecutionEngine executionEngine;
    private VisualizationService visualizationService;
    private AuthService authService;
    private boolean authenticated;
    private String currentUsername;


    public Menu() {

        scanner = new Scanner(System.in);

        fileManager = new FileManager();

        executionEngine = new ExecutionEngine();
        visualizationService = new VisualizationService();
        authService = new AuthService("data/intellitrace-db");
        authenticated = false;
        currentUsername = null;

    }

    public void start() {

        while (!authenticated) {
            displayAuthMenu();

            int choice = readChoice(1, 3);

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("\nThank you for using IntelliTrace.");
                    return;
            }
        }

        System.out.println("\nWelcome, " + currentUsername + "!");

        int choice;

        do {
            displayMenu();
            choice = readChoice(1, 9);

            switch (choice) {
                case 1:
                    loadJavaFile();
                    break;
                case 2:
                    viewSourceCode();
                    break;
                case 3:
                    executeProgram();
                    break;
                case 4:
                    viewVariableTable();
                    break;
                case 5:
                    viewExecutionTimeline();
                    break;
                case 6:
                    saveExecutionLog();
                    break;
                case 7:
                    registerUser();
                    break;
                case 8:
                    loginUser();
                    break;
                case 9:
                    System.out.println("\nThank you for using IntelliTrace.");
                    break;
            }

            if (choice != 9) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }

        } while (choice != 9);

    }

    private void displayAuthMenu() {
        System.out.println();
        System.out.println("=========================================");
        System.out.println("        IntelliTrace Authentication");
        System.out.println("=========================================");
        System.out.println("1. Register User");
        System.out.println("2. Login User");
        System.out.println("3. Exit");
        System.out.println("=========================================");
    }

    private void displayMenu() {

        System.out.println();
        System.out.println("=========================================");
        System.out.println("          IntelliTrace Analyzer");
        System.out.println("=========================================");
        System.out.println("1. Load Java File");
        System.out.println("2. View Source Code");
        System.out.println("3. Execute Program");
        System.out.println("4. View Variable Table");
        System.out.println("5. View Execution Timeline");
        System.out.println("6. Save Execution Log");
        System.out.println("7. Register User");
        System.out.println("8. Login User");
        System.out.println("9. Exit");
        System.out.println("=========================================");

    }

    private void loadJavaFile() {

        System.out.print("Enter Java file path: ");

        String path = scanner.nextLine();

        if (!Validation.isValidJavaFile(path)) {
            return;
        }

        currentProgram = fileManager.loadJavaFile(path);

    }

    private void viewSourceCode() {

        if (currentProgram == null) {

            System.out.println("No file loaded.");

            return;

        }

        System.out.println();

        System.out.println("========== SOURCE CODE ==========");

        int lineNumber = 1;

        for (String line : currentProgram.getSourceCode()) {

            System.out.printf("%2d  %s%n", lineNumber++, line);

        }

    }

    private void executeProgram() {

        if (currentProgram == null) {

            System.out.println("Load a Java file first.");

            return;

        }

        executionEngine.execute(currentProgram);

    }

    private void viewVariableTable() {

        if (currentProgram == null) {

            System.out.println("No Java file loaded.");

            return;

        }

        if (currentProgram.getVariables().isEmpty()) {

            System.out.println("No variables available.");

            System.out.println("Execute the parser first.");

            return;

        }

        System.out.println();

        System.out.println("============== VARIABLE TABLE ==============");

        System.out.printf("%-15s %-10s %-15s%n",
                "Variable", "Type", "Value");

        System.out.println("--------------------------------------------");

        for (var variable : currentProgram.getVariables()) {

            System.out.println(variable);

        }

    }

    private void viewExecutionTimeline() {

        if (currentProgram == null) {

            System.out.println("Load a Java file first.");

            return;

        }

        if (currentProgram.getExecutionLog()
                .getSteps()
                .isEmpty()) {

            System.out.println("Execute the program first.");

            return;

        }

        visualizationService.displayTimeline(currentProgram);

    }

    private void saveExecutionLog() {
        System.out.println("\n[Save Execution Log]");
        System.out.println("Feature will be implemented in Phase 10.");
    }

    private void registerUser() {
        System.out.println("\n========== USER REGISTRATION ==========");
        
        System.out.print("Enter full name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter mobile number (10 digits): ");
        String mobileNo = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        System.out.print("Confirm password: ");
        String confirmPassword = scanner.nextLine();
        
        if (authService.registerUser(name, email, mobileNo, password, confirmPassword)) {
            authenticated = true;
            currentUsername = email.trim();
            System.out.println("========== REGISTRATION SUCCESSFUL ==========\n");
        }
    }

    private void loginUser() {
        System.out.println("\n========== USER LOGIN ==========");
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        if (authService.loginUser(email, password)) {
            authenticated = true;
            currentUsername = email.trim();
            System.out.println("========== LOGIN SUCCESSFUL ==========\n");
        }
    }

    private int readChoice(int min, int max) {
        while (true) {
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
                System.out.print("Enter your choice: ");
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice < min || choice > max) {
                System.out.println("\nInvalid choice.");
                continue;
            }

            return choice;
        }
    }

}