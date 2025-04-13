import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.time.LocalDate;

public class FileManager {
    private static final String FILE_NAME = "tasks.txt";

    public static List<Task> loadTasks() {
        List<Task> taskList = new ArrayList<>();
        try {
            if (!Files.exists(Paths.get(FILE_NAME))) return taskList;

            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));
            for (String line : lines) {
                taskList.add(Task.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return taskList;
    }

    public static void saveTasks(List<Task> tasks) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_NAME))) {
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}

