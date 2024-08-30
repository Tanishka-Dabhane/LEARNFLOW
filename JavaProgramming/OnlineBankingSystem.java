import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class User {
    private final String username;
    private final  String password; // In a real system, ensure this is hashed and securely stored
    private final  Account account;

    public User(String username, String password, Account account) {
       this.username = username;
        this.password = password;
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public Account getAccount() {
        return account;
    }
}

class Account {
    private final String accountNumber;
    private double balance;
    private final List<String> transactionHistory;

    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created with balance: " + initialBalance);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: " + amount + ", New Balance: " + balance);
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            transactionHistory.add("Withdrawal attempt failed: " + amount + ", Insufficient funds.");
            return false;
        }
        balance -= amount;
        transactionHistory.add("Withdrew: " + amount + ", New Balance: " + balance);
        return true;
    }

    public boolean transfer(Account targetAccount, double amount) {
        if (withdraw(amount)) {
            targetAccount.deposit(amount);
            transactionHistory.add("Transferred: " + amount + " to Account: " + targetAccount.getAccountNumber());
            return true;
        }
        return false;
    }
}

class BankSystem {
    Map<String, User> users;

    public BankSystem() {
        users = new HashMap<>();
    }

    public void addUser(String username, String password, String accountNumber, double initialBalance) {
        Account newAccount = new Account(accountNumber, initialBalance);
        User newUser = new User(username, password, newAccount);
        users.put(username, newUser);
    }

    public User authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            return user;
        }
        return null;
    }
}

public class OnlineBankingSystem {
    public static void main(String[] args) {
        BankSystem bankSystem = new BankSystem();
        // Adding some users
        try (Scanner scanner = new Scanner(System.in)) {
            // Adding some users
            bankSystem.addUser("john_doe", "password123", "123456789", 1000.00);
            bankSystem.addUser("jane_doe", "password456", "987654321", 2000.00);
            
            // User login
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
            User user = bankSystem.authenticateUser(username, password);
            if (user != null) {
                System.out.println("Login successful!");
                
                boolean exit = false;
                while (!exit) {
                    System.out.println("1. Check Balance");
                    System.out.println("2. Deposit Funds");
                    System.out.println("3. Withdraw Funds");
                    System.out.println("4. Transfer Funds");
                    System.out.println("5. View Transaction History");
                    System.out.println("6. Exit");
                    System.out.print("Choose an option: ");
                    int choice = scanner.nextInt();
                    
                    switch (choice) {
                        case 1 -> System.out.println("Current Balance: " + user.getAccount().getBalance());
                        case 2 -> {
                            System.out.print("Enter amount to deposit: ");
                            double depositAmount = scanner.nextDouble();
                            user.getAccount().deposit(depositAmount);
                            System.out.println("Deposit successful.");
                        }
                        case 3 -> {
                            System.out.print("Enter amount to withdraw: ");
                            double withdrawAmount = scanner.nextDouble();
                            if (user.getAccount().withdraw(withdrawAmount)) {
                                System.out.println("Withdrawal successful.");
                            } else {
                                System.out.println("Insufficient funds.");
                            }
                        }
                        case 4 -> {
                            System.out.print("Enter target account number: ");
                            scanner.nextLine();  // Consume newline
                            String targetAccountNumber = scanner.nextLine();
                            System.out.print("Enter amount to transfer: ");
                            double transferAmount = scanner.nextDouble();
                            User targetUser = null;
                            for (User u : bankSystem.users.values()) {
                                if (u.getAccount().getAccountNumber().equals(targetAccountNumber)) {
                                    targetUser = u;
                                    break;
                                }
                            }
                            if (targetUser != null && user.getAccount().transfer(targetUser.getAccount(), transferAmount)) {
                                System.out.println("Transfer successful.");
                            } else {
                                System.out.println("Transfer failed.");
                            }
                        }
                        case 5 -> {
                            System.out.println("Transaction History:");
                            for (String transaction : user.getAccount().getTransactionHistory()) {
                                System.out.println(transaction);
                            }
                        }
                        case 6 -> {
                            exit = true;
                            System.out.println("Exiting...");
                        }
                        default -> System.out.println("Invalid option. Please try again.");
                    }
                }
            } else {
                System.out.println("Login failed. Invalid username or password.");
            }
        }
    }
}
