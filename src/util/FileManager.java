package util;

import model.Program;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {

    public Program loadJavaFile(String path) {

        Program program = new Program();

        ArrayList<String> lines = new ArrayList<>();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(path));

            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            reader.close();

            program.setFileName(path);
            program.setSourceCode(lines);

            System.out.println("File loaded successfully.");

        }

        catch (IOException e) {

            System.out.println("Error reading file.");

        }

        return program;

    }

}