import java.util.*;
import java.time.LocalDate;

public class ToDoListApp {
    private static List<Task> tasks = FileManager.loadTasks();  // Ensure 'tasks' is a List of Task objects
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== TO-DO LIST MENU ===");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Delete Task");
            System.out.println("5. Sort Tasks by Deadline");
            System.out.println("6. Sort Tasks by Status");
            System.out.println("7. Save and Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addTask();
                case 2 -> viewTasks();
                case 3 -> markTaskAsCompleted();
                case 4 -> deleteTask();
                case 5 -> sortByDeadline();  // Sort tasks by deadline
                case 6 -> sortByStatus();  // Sort tasks by status
                case 7 -> {
                    FileManager.saveTasks(tasks);
                    System.out.println("Tasks saved. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addTask() {
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();
        System.out.print("Enter deadline (YYYY-MM-DD): ");
        LocalDate deadline = LocalDate.parse(scanner.nextLine());

        tasks.add(new Task(title, deadline));  // Using the constructor that does not require isCompleted

        // After adding, we can sort by deadline
        tasks.sort(Comparator.comparing(Task::getDeadline));  // Sort tasks by deadline
        System.out.println("Task added!");
    }

    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks yet.");
            return;
        }
        System.out.println("\nYour Tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    private static void markTaskAsCompleted() {
        viewTasks();
        System.out.print("Enter task number to mark as completed: ");
        int index = scanner.nextInt() - 1;
        if (isValidIndex(index)) {
            tasks.get(index).markAsCompleted();
            System.out.println("Task marked as completed.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private static void deleteTask() {
        viewTasks();
        System.out.print("Enter task number to delete: ");
        int index = scanner.nextInt() - 1;
        if (isValidIndex(index)) {
            tasks.remove(index);
            System.out.println("Task deleted.");
        } else {
            System.out.println("Invalid task number.");
        }
    }

    private static void sortByDeadline() {
        tasks.sort(Comparator.comparing(Task::getDeadline));  // Sort tasks by deadline
        System.out.println("Tasks sorted by deadline.");
    }

    private static void sortByStatus() {
        tasks.sort(Comparator.comparing(Task::isCompleted));  // Sort tasks by completion status
        System.out.println("Tasks sorted by completion status.");
    }

    private static boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }
}
