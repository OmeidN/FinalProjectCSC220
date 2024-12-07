package tasks;
import users.User;

import java.time.Period;

public class RecurringTask extends Task {
    private Period recurrencePeriod; // The recurrence interval (e.g., daily, weekly, monthly)

    // Constructor to handle Task ID, name, due date, priority, and recurrence period
    public RecurringTask(int id, String name, int dueDate, int priority, Period recurrencePeriod, User owner) {
        super(id, name, dueDate, priority, owner); // Pass the ID along with other task details
        this.recurrencePeriod = recurrencePeriod;
    }

    // Getter for the recurrence period
    public Period getRecurrencePeriod() {
        return recurrencePeriod;
    }

    // Calculates the next due date based on the recurrence period
    public int getNextDueDate() {
        int year = getDueDate() / 10000;
        int month = (getDueDate() % 10000) / 100;
        int day = getDueDate() % 100;

        java.time.LocalDate currentDueDate = java.time.LocalDate.of(year, month, day);
        java.time.LocalDate nextDueDate = currentDueDate.plus(recurrencePeriod);

        return nextDueDate.getYear() * 10000 + nextDueDate.getMonthValue() * 100 + nextDueDate.getDayOfMonth();
    }

    @Override
    public String toString() {
        return super.toString() + ", Recurrence Period: " + recurrencePeriod;
    }
}
