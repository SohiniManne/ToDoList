import java.time.LocalDate;

public class Task {
    private String title;
    private boolean isCompleted;
    private LocalDate deadline;

    public Task(String title, LocalDate deadline) {
        this.title = title;
        this.deadline = deadline;
        this.isCompleted = false; // Default to pending
    }

    public Task(String title, LocalDate deadline, boolean isCompleted) {
        this.title = title;
        this.deadline = deadline;
        this.isCompleted = isCompleted;
    }

    public void markAsCompleted() {
        isCompleted = true;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return (isCompleted ? "[X] " : "[ ] ") + title + " (Due: " + deadline + ")";
    }

    public String toFileString() {
        return title + ";" + deadline + ";" + isCompleted;
    }

    public static Task fromFileString(String line) {
        String[] parts = line.split(";");
        String title = parts[0];
        LocalDate deadline = LocalDate.parse(parts[1]);
        boolean isCompleted = Boolean.parseBoolean(parts[2]);
        return new Task(title, deadline, isCompleted);
    }
}
