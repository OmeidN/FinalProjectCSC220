package managers;

import tasks.Task;

import java.util.*;

public class GraphManager {
    private Map<Task, List<Task>> adjacencyList; // Adjacency list to represent dependencies

    public GraphManager() {
        this.adjacencyList = new HashMap<>();
    }

    // Add a task to the graph
    public void addTask(Task task) {
        adjacencyList.putIfAbsent(task, new ArrayList<>());
    }

    // Add a dependency (task1 -> task2 means task2 depends on task1)
    public void addDependency(Task task1, Task task2) {
        adjacencyList.putIfAbsent(task1, new ArrayList<>());
        adjacencyList.get(task1).add(task2);
    }

    // Detect if there is a circular dependency in the graph
    public boolean hasCircularDependency() {
        Set<Task> visited = new HashSet<>();
        Set<Task> stack = new HashSet<>();

        for (Task task : adjacencyList.keySet()) {
            if (dfs(task, visited, stack)) {
                return true;
            }
        }
        return false;
    }

    // Depth First Search to detect cycles
    private boolean dfs(Task task, Set<Task> visited, Set<Task> stack) {
        if (stack.contains(task)) {
            return true; // Found a cycle
        }
        if (visited.contains(task)) {
            return false; // Task already processed
        }

        visited.add(task);
        stack.add(task);

        for (Task neighbor : adjacencyList.getOrDefault(task, new ArrayList<>())) {
            if (dfs(neighbor, visited, stack)) {
                return true;
            }
        }

        stack.remove(task);
        return false;
    }

    // Get tasks that a specific task depends on
    public List<Task> getDependencies(Task task) {
        return adjacencyList.getOrDefault(task, new ArrayList<>());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Task Dependencies:\n");
        for (Map.Entry<Task, List<Task>> entry : adjacencyList.entrySet()) {
            sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
