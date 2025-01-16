import java.io.*;
import java.util.*;

class Expense {
    private String category;
    private String description;
    private double amount;
    private Date date;

    public Expense(String category, String description, double amount, Date date) {
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Category: " + category + ", Description: " + description + ", Amount: " + amount;
    }
}

class ExpenseManager {
    private List<Expense> expenses;
    private final String filePath = "expenses.txt";

    public ExpenseManager() {
        expenses = new ArrayList<>();
        loadExpenses();
    }

    public void addExpense(String category, String description, double amount) {
        Expense expense = new Expense(category, description, amount, new Date());
        expenses.add(expense);
        saveExpenseToFile(expense);
    }

    public double getTotalExpenses(Date fromDate, Date toDate) {
        return expenses.stream()
                .filter(exp -> !exp.getDate().before(fromDate) && !exp.getDate().after(toDate))
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public void displayExpenses(Date fromDate, Date toDate) {
        expenses.stream()
                .filter(exp -> !exp.getDate().before(fromDate) && !exp.getDate().after(toDate))
                .forEach(System.out::println);
    }

    private void saveExpenseToFile(Expense expense) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(expense.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving expense: " + e.getMessage());
        }
    }

    private void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parsing logic to reconstruct Expense objects can be added if needed.
                System.out.println("Loaded: " + line);
            }
        } catch (IOException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
    }
}

public class DailyExpenseTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Total Expenses");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    manager.addExpense(category, description, amount);
                    System.out.println("Expense added successfully.");
                    break;

                case 2:
                    System.out.println("Viewing all expenses:");
                    manager.displayExpenses(new Date(0), new Date());
                    break;

                case 3:
                    System.out.println("Total expenses: " + manager.getTotalExpenses(new Date(0), new Date()));
                    break;

                case 4:
                    System.out.println("Exiting. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}