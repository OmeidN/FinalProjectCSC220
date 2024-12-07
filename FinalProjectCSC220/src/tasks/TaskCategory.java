package tasks;

public class TaskCategory {
    private String name;
    private String description;

    public TaskCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Category: " + name + " - " + description;
    }
}
