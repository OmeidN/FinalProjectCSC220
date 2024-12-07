package tasks;

import java.util.*;

public class TaskGraph {
    private Map<Integer, Task> taskMap; // Maps task ID to Task
    private Map<Integer, List<Integer>> adjacencyList; // Maps task ID to list of dependent task IDs
    private Set<Integer> visited;
    private Set<Integer> recursionStack; // For cycle detection

    public TaskGraph() {
        taskMap = new HashMap<>();
        adjacencyList = new HashMap<>();
        visited = new HashSet<>();
        recursionStack = new HashSet<>();
    }

    // Add a task to the graph
    public void addTask(Task task) {
        taskMap.put(task.getId(), task);
        adjacencyList.putIfAbsent(task.getId(), new ArrayList<>());
    }

    // Add a dependency (task1 -> task2 means task2 depends on task1)
    public boolean addDependency(int taskId, int dependentTaskId) {
        // Ensure both tasks exist
        if (!taskMap.containsKey(taskId) || !taskMap.containsKey(dependentTaskId)) {
            System.out.println("Error: One or both tasks do not exist.");
            return false;
        }

        // Add the dependency
        adjacencyList.get(taskId).add(dependentTaskId);
        System.out.println("Dependency added: Task " + taskId + " -> Task " + dependentTaskId);
        return true;
    }

    // Detect cycle using DFS
    public boolean hasCycle() {
        visited.clear();
        recursionStack.clear();
        for (Integer taskId : taskMap.keySet()) {
            if (!visited.contains(taskId)) {
                if (dfs(taskId)) {
                    return true; // Cycle detected
                }
            }
        }
        return false;
    }

    private boolean dfs(int taskId) {
        if (recursionStack.contains(taskId)) {
            return true; // Cycle found
        }
        if (visited.contains(taskId)) {
            return false; // Task already processed
        }

        visited.add(taskId);
        recursionStack.add(taskId);

        for (Integer dependentTaskId : adjacencyList.get(taskId)) {
            if (dfs(dependentTaskId)) {
                return true;
            }
        }

        recursionStack.remove(taskId);
        return false;
    }

    // Get all task dependencies (adjacency list)
    public Map<Integer, List<Integer>> getTaskDependencies() {
        return adjacencyList;
    }

    // Print the adjacency list for task dependencies
    public void printTaskDependencies() {
        System.out.println("Task Dependencies (Adjacency List):");
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            System.out.println("Task ID: " + entry.getKey() + " -> " + entry.getValue());
        }
    }

    // Removes all dependencies related to a task
    public void removeDependencies(int taskId) {
        adjacencyList.remove(taskId); // Remove the task's dependency list

        // Remove taskId from other tasks' dependency lists
        for (List<Integer> dependencies : adjacencyList.values()) {
            dependencies.remove(Integer.valueOf(taskId)); // Remove taskId from dependencies
        }
    }

    // Get the task by ID
    public Task getTaskById(int taskId) {
        return taskMap.get(taskId);
    }
}
