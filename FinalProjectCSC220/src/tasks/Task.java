package tasks;
import users.User;

public class Task {
    private int id; // Task ID
    private String name;
    private int dueDate; // Due date stored as an integer in YYYYMMDD format
    private int priority; // Priority level between 1 (lowest) and 10 (highest)
    private User owner;

    public Task(int id, String name, int dueDate, int priority, User owner) {
        if (priority < 1 || priority > 10) {
            throw new IllegalArgumentException("Priority must be between 1 and 10.");
        }
        if (!isValidDate(dueDate)) {
            throw new IllegalArgumentException("Invalid date format. Use YYYYMMDD.");
        }
        this.id = id; // Use user-provided ID
        this.name = name;
        this.dueDate = dueDate;
        this.priority = priority;
        this.owner = owner;
    }

    // Validates the date format (basic check for YYYYMMDD)
    private boolean isValidDate(int date) {
        int year = date / 10000;
        int month = (date % 10000) / 100;
        int day = date % 100;
        return year >= 1000 && year <= 9999 &&
               month >= 1 && month <= 12 &&
               day >= 1 && day <= 31; // Basic validation
    }

    // Getter for task ID
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDueDate() {
        return dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public String getOwnerId(){
        return owner.getId();
    }

    @Override
    public String toString() {
        return "Task ID: " + id + ", Name: " + name + ", Due Date: " + dueDate + ", Priority: " + priority;
    }
}
