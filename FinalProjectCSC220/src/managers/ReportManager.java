package managers;

import tasks.Task;

import java.util.List;

public class ReportManager {
    // Generate a report of all tasks
    public String generateTaskReport(List<Task> tasks) {
        StringBuilder sb = new StringBuilder("Task Report:\n");
        for (Task task : tasks) {
            sb.append(task).append("\n");
        }
        return sb.toString();
    }

    // Export the report to a file
    public void exportReport(String filePath, String reportContent) {
        try (java.io.FileWriter writer = new java.io.FileWriter(filePath)) {
            writer.write(reportContent);
            System.out.println("Report exported successfully to " + filePath);
        } catch (Exception e) {
            System.out.println("Error exporting report: " + e.getMessage());
        }
    }

    // Generate a summary report
    public String generateSummaryReport(List<Task> tasks) {
        int totalTasks = tasks.size();
        int highPriority = (int) tasks.stream().filter(t -> t.getPriority() >= 8).count();
        int mediumPriority = (int) tasks.stream().filter(t -> t.getPriority() >= 4 && t.getPriority() < 8).count();
        int lowPriority = (int) tasks.stream().filter(t -> t.getPriority() < 4).count();

        return String.format(
            "Task Summary Report:\n" +
            "Total Tasks: %d\n" +
            "High Priority (8-10): %d\n" +
            "Medium Priority (4-7): %d\n" +
            "Low Priority (1-3): %d\n",
            totalTasks, highPriority, mediumPriority, lowPriority
        );
    }
}
