package managers;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class TaskManager {
    private PriorityQueue<Task> priorityQueue; // Manages tasks based on their priority

    public TaskManager() {
        this.priorityQueue = new PriorityQueue<>((t1, t2) -> t2.getPriority() - t1.getPriority());
    }

    // Add a task to the priority queue
    public void addTask(Task task) {
        priorityQueue.add(task);
    }

    // Retrieve the highest-priority task without removing it
    public Task peekHighestPriorityTask() {
        return priorityQueue.peek();
    }

    // Retrieve and remove the highest-priority task
    public Task pollHighestPriorityTask() {
        return priorityQueue.poll();
    }

    // Get a list of all tasks, sorted by priority
    public List<Task> getAllTasks() {
        return new ArrayList<>(priorityQueue);
    }

    // Remove a specific task from the queue
    public boolean removeTask(Task task) {
        return priorityQueue.remove(task);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Tasks (by priority):\n");
        for (Task task : priorityQueue) {
            sb.append(task).append("\n");
        }
        return sb.toString();
    }
}
