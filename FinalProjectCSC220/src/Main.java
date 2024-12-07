import tasks.*;
import users.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize UserManager and TaskGraph
        UserManager userManager = new UserManager();
        TaskGraph taskGraph = new TaskGraph(); // Initialize TaskGraph here

        boolean running = true;

        while (running) {
            System.out.println("Main Menu:");
            System.out.println("1. Add User");
            System.out.println("2. Add Task to User");
            System.out.println("3. View Users and Tasks");
            System.out.println("4. Remove Task from User");
            System.out.println("5. Add Task Dependency");
            System.out.println("6. View Task Dependcies");
            System.out.println("7. Delete User");
            System.out.println("8. Undo");
            System.out.println("9. Redo");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline character

            switch (choice) {
                case 1:
                    // Add User
                    System.out.print("Enter User ID: ");
                    String userId = scanner.nextLine();
                    System.out.print("Enter User Name: ");
                    String userName = scanner.nextLine();
                    if (userManager.addUser(userId, userName)) {
                        System.out.println("User added successfully.");
                    } else {
                        System.out.println("User ID already exists.");
                    }
                    break;

                case 2:
                    // Add Task to User
                    System.out.print("Enter User ID: ");
                    userId = scanner.nextLine();
                    System.out.print("Enter Task ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter Task Name: ");
                    String taskName = scanner.nextLine();
                    System.out.print("Enter Task Due Date (YYYYMMDD): ");
                    int dueDate = scanner.nextInt();
                    System.out.print("Enter Task Priority (1-10): ");
                    int priority = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    try {
                        User user = userManager.getUser(userId); // Retrieve user by userId
                        if (user != null) {
                            Task task = new Task(id, taskName, dueDate, priority, user); // Assign user to the task
                            if (userManager.addTaskToUser(userId, task)) {
                                taskGraph.addTask(task); // Add task to task graph
                                System.out.println("Task added successfully.");
                            } else {
                                System.out.println("Task could not be added to the user.");
                            }
                        } else {
                            System.out.println("User not found.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid input: " + e.getMessage());
                    }
                    break;

                case 3:
                    // View Users and Tasks
                    userManager.listUsers();
                    break;

                case 4:
                    // Remove Task from User (also remove task from dependencies)
                    System.out.print("Enter User ID: ");
                    userId = scanner.nextLine();
                    System.out.print("Enter Task ID to remove: ");
                    int taskId = scanner.nextInt();
                    scanner.nextLine();

                    // Remove the task from the user and dependencies
                    if (userManager.removeTaskByIdAndDependencies(userId, taskId, taskGraph)) {
                        System.out.println("Task removed successfully.");
                    } else {
                        System.out.println("User or Task not found.");
                    }
                    break;

                case 5:
                    // Add Task Dependency
                    System.out.print("Enter Task ID 1: ");
                    int taskId1 = scanner.nextInt();
                    System.out.print("Enter Task ID 2 (dependent task): ");
                    int taskId2 = scanner.nextInt();

                    // Find the tasks by ID
                    Task task1ById = taskGraph.getTaskById(taskId1);
                    Task task2ById = taskGraph.getTaskById(taskId2);

                    // Check if tasks exist and belong to the same user by comparing userId
                    if (task1ById != null && task2ById != null && task1ById.getOwnerId().equals(task2ById.getOwnerId())) {
                        // Add the dependency if tasks belong to the same user
                        taskGraph.addDependency(taskId1, taskId2);
                        System.out.println("Dependency added successfully.");
                    } else {
                        System.out.println("Error: Tasks must belong to the same user or one of the tasks does not exist.");
                    }
                    break;

                case 6:
                    //View Task Dependencies
                    System.out.print("Enter User ID: ");
                    userId = scanner.nextLine();
                    User user = userManager.getUser(userId);
                    if (user != null) {
                        System.out.println("User: " + user.getName() + " (ID: " + user.getId() + ")");
                        System.out.println("Tasks and Dependencies:");

                        // Get all tasks for the user
                        for (Task task : user.getTaskList()) {
                            System.out.println("Task ID: " + task.getId() + " - " + task.getName());
                            // Display dependencies
                            List<Integer> dependencies = taskGraph.getTaskDependencies().get(task.getId());
                            if (dependencies != null && !dependencies.isEmpty()) {
                                System.out.println("  Dependencies: " + dependencies);
                            } else {
                                System.out.println("  No dependencies.");
                            }
                        }
                    } else {
                        System.out.println("User not found.");
                    }
                    break;

                case 7:
                    // Delete User
                    System.out.print("Enter User ID: ");
                    userId = scanner.nextLine();
                    if (userManager.deleteUser(userId)) {
                        System.out.println("User deleted successfully.");
                    } else {
                        System.out.println("User not found.");
                    }
                    break;

                case 8:
                    // Undo functionality
                    userManager.undo();
                    break;

                case 9:
                    // Redo functionality
                    userManager.redo();
                    break;

                case 10:
                    // Exit the system
                    running = false;
                    System.out.println("Exiting the Task Management System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        scanner.close();
    }
}
