import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Employee {
    private final int id;
    private final String name;
    private final double basicSalary;
    private int attendance;  // Number of days attended in a month
    private final double taxRate;  // Tax rate in percentage

    public Employee(int id, String name, double basicSalary, double taxRate) {
        this.id = id;
        this.name = name;
        this.basicSalary = basicSalary;
        this.taxRate = taxRate;
        this.attendance = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    @Override
    public String toString() {
        return "Employee ID: " + id + ", Name: " + name + ", Basic Salary: $" + basicSalary + ", Attendance: " + attendance + " days";
    }
}
 class Payroll {
    private final Employee employee;

    public Payroll(Employee employee) {
        this.employee = employee;
    }

    public double calculateGrossSalary() {
        // Assuming a 30-day month, calculating salary based on attendance
        return (employee.getBasicSalary() / 30) * employee.getAttendance();
    }

    public double calculateTax() {
        double grossSalary = calculateGrossSalary();
        return (grossSalary * employee.getTaxRate()) / 100;
    }

    public double calculateNetSalary() {
        double grossSalary = calculateGrossSalary();
        double tax = calculateTax();
        return grossSalary - tax;
    }

    public void generatePayslip() {
        System.out.println("\n--- Payslip ---");
        System.out.println("Employee ID: " + employee.getId());
        System.out.println("Employee Name: " + employee.getName());
        System.out.println("Basic Salary: $" + employee.getBasicSalary());
        System.out.println("Attendance: " + employee.getAttendance() + " days");
        System.out.println("Gross Salary: $" + calculateGrossSalary());
        System.out.println("Tax Deducted: $" + calculateTax());
        System.out.println("Net Salary: $" + calculateNetSalary());
        System.out.println("------------------\n");
    }
}

public class PayrollSystem {
    private final Map<Integer, Employee> employees;

    public PayrollSystem() {
        employees = new HashMap<>();
    }

    public void addEmployee(Employee employee) {
        employees.put(employee.getId(), employee);
    }

    public void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        for (Employee employee : employees.values()) {
            System.out.println(employee);
        }
    }

    public void recordAttendance(int id, int days) {
        Employee employee = employees.get(id);
        if (employee != null) {
            employee.setAttendance(days);
            System.out.println("Attendance recorded for " + employee.getName());
        } else {
            System.out.println("Employee not found.");
        }
    }

    public void processPayroll(int id) {
        Employee employee = employees.get(id);
        if (employee != null) {
            Payroll payroll = new Payroll(employee);
            payroll.generatePayslip();
        } else {
            System.out.println("Employee not found.");
        }
    }

    public static void main(String[] args) {
        PayrollSystem system = new PayrollSystem();

        // Adding some employees
        system.addEmployee(new Employee(1, "John Doe", 3000, 10));
        system.addEmployee(new Employee(2, "Jane Smith", 3500, 12));
        system.addEmployee(new Employee(3, "Alice Johnson", 4000, 15));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Employee Payroll System ---");
            System.out.println("1. Display Employees");
            System.out.println("2. Record Attendance");
            System.out.println("3. Process Payroll");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> system.displayEmployees();
                case 2 -> {
                    System.out.print("Enter Employee ID: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter attendance days: ");
                    int days = scanner.nextInt();
                    system.recordAttendance(id, days);
                }
                case 3 -> {
                    System.out.print("Enter Employee ID: ");
                    int id = scanner.nextInt();
                    system.processPayroll(id);
                }
                case 4 -> {
                    System.out.println("Exiting system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}