package users;

import tasks.Task;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;
    private List<Task> taskList;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.taskList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public boolean removeTask(int taskId) {
        return taskList.removeIf(task -> task.getId() == taskId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User: ").append(name).append(" (ID: ").append(id).append(")\n");
        sb.append("Tasks:\n");
        for (Task task : taskList) {
            sb.append(task).append("\n");
        }
        return sb.toString();
    }
}
