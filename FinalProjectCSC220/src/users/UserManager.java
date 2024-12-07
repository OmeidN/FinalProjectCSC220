package users;

import tasks.Task;
import tasks.TaskGraph;

import java.util.*;

public class UserManager {
    private Map<String, User> users; // Maps user IDs to User objects
    private Map<Integer, Task> taskById; // Maps task IDs to Task objects
    private Stack<Action> undoStack; // Stack for undo actions
    private Stack<Action> redoStack; // Stack for redo actions

    public UserManager() {
        this.users = new HashMap<>();
        this.taskById = new HashMap<>();
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    // Adds a new user to the system
    public boolean addUser(String id, String name) {
        if (users.containsKey(id)) {
            return false; // User ID already exists
        }
        User newUser = new User(id, name);
        users.put(id, newUser);
        undoStack.push(new Action(ActionType.ADD_USER, id, null));
        redoStack.clear(); // Clear redo stack after a new action
        return true;
    }

    // Deletes a user and their associated tasks
    public boolean deleteUser(String id) {
        if (!users.containsKey(id)) {
            return false;
        }
        User removedUser = users.remove(id);
        // Remove all tasks associated with the user from the task map
        for (Task task : removedUser.getTaskList()) {
            taskById.remove(task.getId());
        }
        undoStack.push(new Action(ActionType.DELETE_USER, id, removedUser));
        redoStack.clear();
        return true;
    }

    public User getUser(String userID){
        return users.get(userID);
    }
    // Adds a task to a user
    public boolean addTaskToUser(String userId, Task task) {
        User user = users.get(userId);
        if (user == null) {
            return false; // User not found
        }
        user.addTask(task);
        taskById.put(task.getId(), task); // Add task to the global task map
        undoStack.push(new Action(ActionType.ADD_TASK, userId, task));
        redoStack.clear();
        return true;
    }

    // Removes a task by its ID
    public boolean removeTaskById(int taskId) {
        Task task = taskById.get(taskId);
        if (task == null) {
            return false; // Task not found
        }
        User user = null;
        // Find the user who owns the task
        for (User u : users.values()) {
            if (u.getTaskList().contains(task)) {
                user = u;
                break;
            }
        }
        if (user == null) {
            return false; // User not found
        }
        user.removeTask(taskId);
        taskById.remove(taskId); // Remove task from the global map
        undoStack.push(new Action(ActionType.REMOVE_TASK, user.getId(), task));
        redoStack.clear();
        return true;
    }

    // Retrieves a task by its ID
    public Task getTaskById(int taskId) {
        return taskById.get(taskId);
    }

    // Lists all users and their tasks
    public void listUsers() {
        for (User user : users.values()) {
            System.out.println(user);
        }
    }

    // Lists all tasks in the system
    public void listAllTasks() {
        System.out.println("All Tasks:");
        for (Task task : taskById.values()) {
            System.out.println(task);
        }
    }

    // Undo the last action
    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        Action action = undoStack.pop();
        redoStack.push(action); // Save action to redo stack

        switch (action.type) {
            case ADD_USER:
                users.remove(action.userId);
                break;
            case DELETE_USER:
                users.put(action.userId, (User) action.data);
                // Re-add tasks to the global task map
                User restoredUser = (User) action.data;
                for (Task task : restoredUser.getTaskList()) {
                    taskById.put(task.getId(), task);
                }
                break;
            case ADD_TASK:
                User user = users.get(action.userId);
                if (user != null) {
                    Task task = (Task) action.data;
                    user.removeTask(task.getId());
                    taskById.remove(task.getId());
                }
                break;
            case REMOVE_TASK:
                user = users.get(action.userId);
                if (user != null) {
                    Task task = (Task) action.data;
                    user.addTask(task);
                    taskById.put(task.getId(), task);
                }
                break;
        }

        System.out.println("Undo successful.");
    }

    // Redo the last undone action
    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo.");
            return;
        }

        Action action = redoStack.pop();
        undoStack.push(action); // Save action back to undo stack

        switch (action.type) {
            case ADD_USER:
                User newUser = (User) action.data;
                addUser(action.userId, newUser.getName());
                break;
            case DELETE_USER:
                deleteUser(action.userId);
                break;
            case ADD_TASK:
                addTaskToUser(action.userId, (Task) action.data);
                break;
            case REMOVE_TASK:
                removeTaskById(((Task) action.data).getId());
                break;
        }

        System.out.println("Redo successful.");
    }

    // Method to remove task and its dependencies
    public boolean removeTaskByIdAndDependencies(String userId, int taskId, TaskGraph taskGraph) {
        Task task = taskById.get(taskId);
        if (task == null) {
            return false; // Task not found
        }
        
        // Remove the task from the user
        User user = users.get(userId);
        if (user == null || !user.removeTask(taskId)) {
            return false; // User not found or task not associated with user
        }

        // Remove the task from the task map
        taskById.remove(taskId);

        // Remove the task from dependencies in TaskGraph
        taskGraph.removeDependencies(taskId); // Remove this task from any other task's dependency list

        undoStack.push(new Action(ActionType.REMOVE_TASK, userId, task));
        redoStack.clear();
        return true;
    }


    // Helper class for undo/redo actions
    private static class Action {
        ActionType type;
        String userId;
        Object data;

        public Action(ActionType type, String userId, Object data) {
            this.type = type;
            this.userId = userId;
            this.data = data;
        }
    }

    // Enum for action types
    private enum ActionType {
        ADD_USER,
        DELETE_USER,
        ADD_TASK,
        REMOVE_TASK
    }
}
