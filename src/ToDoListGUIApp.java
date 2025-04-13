import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ToDoListGUIApp extends JFrame {
    private static List<Task> tasks = FileManager.loadTasks();
    private static DefaultListModel<Task> taskListModel = new DefaultListModel<>();
    private JList<Task> taskList;
    private JTextField taskNameField;
    private JTextField taskDeadlineField;
    private JComboBox<String> statusComboBox;

    public ToDoListGUIApp() {
        setTitle("To-Do List");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tasks.forEach(taskListModel::addElement);
        taskList = new JList<>(taskListModel);
        JScrollPane scrollPane = new JScrollPane(taskList);

        taskNameField = new JTextField(15);
        taskDeadlineField = new JTextField(10);
        statusComboBox = new JComboBox<>(new String[]{"Pending", "Completed"});

        JButton addButton = new JButton("Add Task");
        JButton markCompletedButton = new JButton("Mark as Completed");
        JButton deleteButton = new JButton("Delete Task");
        JButton saveButton = new JButton("Save and Exit");

        addButton.addActionListener(e -> addTask());
        markCompletedButton.addActionListener(e -> markTaskAsCompleted());
        deleteButton.addActionListener(e -> deleteTask());
        saveButton.addActionListener(e -> saveAndExit());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Task Name:"));
        panel.add(taskNameField);
        panel.add(new JLabel("Deadline (YYYY-MM-DD):"));
        panel.add(taskDeadlineField);
        panel.add(new JLabel("Status:"));
        panel.add(statusComboBox);
        panel.add(addButton);
        panel.add(markCompletedButton);
        panel.add(deleteButton);
        panel.add(scrollPane);
        panel.add(saveButton);

        add(panel);
    }

    private void addTask() {
        String title = taskNameField.getText().trim();
        String deadlineText = taskDeadlineField.getText().trim();
        String status = (String) statusComboBox.getSelectedItem();

        if (title.isEmpty() || deadlineText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both task title and deadline.");
            return;
        }

        try {
            LocalDate deadline = LocalDate.parse(deadlineText);
            boolean isCompleted = status.equals("Completed");
            Task task = new Task(title, deadline, isCompleted);

            taskListModel.addElement(task);
            tasks.add(task);

            taskNameField.setText("");
            taskDeadlineField.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid deadline format. Use YYYY-MM-DD.");
        }
    }

    private void markTaskAsCompleted() {
        Task selectedTask = taskList.getSelectedValue();
        if (selectedTask != null) {
            selectedTask.markAsCompleted();
            taskList.repaint();
        }
    }

    private void deleteTask() {
        Task selectedTask = taskList.getSelectedValue();
        if (selectedTask != null) {
            taskListModel.removeElement(selectedTask);
            tasks.remove(selectedTask);
        }
    }

    private void saveAndExit() {
        FileManager.saveTasks(tasks);
        JOptionPane.showMessageDialog(this, "Tasks saved. Goodbye!");
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ToDoListGUIApp().setVisible(true));
    }
}
