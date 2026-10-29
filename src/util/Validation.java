package util;

import java.io.File;
import java.util.regex.Pattern;

public class Validation {

    // Email regex pattern
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidJavaFile(String path) {

        File file = new File(path);

        if (!file.exists()) {
            System.out.println("File does not exist.");
            return false;
        }

        if (!file.isFile()) {
            System.out.println("Not a file.");
            return false;
        }

        if (!path.endsWith(".java")) {
            System.out.println("Only .java files are allowed.");
            return false;
        }

        return true;
    }

    /**
     * Validates email format
     * @param email the email address to validate
     * @return true if valid email format, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Email cannot be empty.");
            return false;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            System.out.println("Invalid email format.");
            return false;
        }

        return true;
    }

    /**
     * Validates password strength
     * Requirements:
     * - Minimum 8 characters
     * - At least one uppercase letter
     * - At least one lowercase letter
     * - At least one digit
     * - At least one special character (!@#$%^&*)
     * @param password the password to validate
     * @return true if password meets all requirements, false otherwise
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            System.out.println("Password cannot be empty.");
            return false;
        }

        if (password.length() < 8) {
            System.out.println("Password must be at least 8 characters long.");
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            System.out.println("Password must contain at least one uppercase letter.");
            return false;
        }

        if (!password.matches(".*[a-z].*")) {
            System.out.println("Password must contain at least one lowercase letter.");
            return false;
        }

        if (!password.matches(".*\\d.*")) {
            System.out.println("Password must contain at least one digit.");
            return false;
        }

        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};:',.<>?/`~].*")) {
            System.out.println("Password must contain at least one special character (!@#$%^&* etc).");
            return false;
        }

        return true;
    }

    /**
     * Validates user name
     * Requirements:
     * - Not empty
     * - Minimum 2 characters
     * - Only letters and spaces
     * @param name the name to validate
     * @return true if valid name, false otherwise
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return false;
        }

        if (name.trim().length() < 2) {
            System.out.println("Name must be at least 2 characters long.");
            return false;
        }

        if (!name.matches("^[a-zA-Z\\s]+$")) {
            System.out.println("Name can only contain letters and spaces.");
            return false;
        }

        return true;
    }

    /**
     * Validates mobile number
     * Requirements:
     * - Exactly 10 digits
     * - Only numbers (0-9)
     * @param mobileNo the mobile number to validate
     * @return true if valid mobile number, false otherwise
     */
    public static boolean isValidMobileNumber(String mobileNo) {
        if (mobileNo == null || mobileNo.trim().isEmpty()) {
            System.out.println("Mobile number cannot be empty.");
            return false;
        }

        if (!mobileNo.matches("^[0-9]{10}$")) {
            System.out.println("Mobile number must be exactly 10 digits (0-9).");
            return false;
        }

        return true;
    }

}