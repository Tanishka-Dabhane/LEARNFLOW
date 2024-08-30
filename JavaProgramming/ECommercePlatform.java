import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// E-commerce Platform
public class ECommercePlatform {

    // Product class
    static class Product {
        private final String productID;
        private final String name;
        private final double price;

        public Product(String productID, String name, double price) {
            this.productID = productID;
            this.name = name;
            this.price = price;
        }

        public String getProductID() { return productID; }
        public String getName() { return name; }
        public double getPrice() { return price; }

        @Override
        public String toString() {
            return productID + ": " + name + " ($" + price + ")";
        }
    }

    // User class
    static class User {
        private final String username;
        private final String password;
        private final List<Product> cart;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
            this.cart = new ArrayList<>();
        }

        public String getUsername() { return username; }
        public String getPassword() { return password; }
        public List<Product> getCart() { return cart; }

        public void addToCart(Product product) {
            cart.add(product);
        }

        public void clearCart() {
            cart.clear();
        }
    }

    // E-commerce system
    static class ECommerceSystem {
        private final List<Product> products;
        private final List<User> users;
        private User currentUser;

        public ECommerceSystem() {
            products = new ArrayList<>();
            users = new ArrayList<>();
            currentUser = null;

            // Adding some sample products
            products.add(new Product("P01", "Laptop", 800.00));
            products.add(new Product("P02", "Smartphone", 500.00));
            products.add(new Product("P03", "Headphones", 100.00));
        }

        // User registration
        public void registerUser(String username, String password) {
            users.add(new User(username, password));
            System.out.println("User registered successfully.");
        }

        // User login
        public boolean loginUser(String username, String password) {
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    currentUser = user;
                    System.out.println("Login successful. Welcome, " + username + "!");
                    return true;
                }
            }
            System.out.println("Invalid credentials.");
            return false;
        }

        // User logout
        public void logoutUser() {
            if (currentUser != null) {
                System.out.println("User " + currentUser.getUsername() + " logged out.");
                currentUser = null;
            }
        }

        // View products
        public void viewProducts() {
            System.out.println("\nProduct Listings:");
            for (Product product : products) {
                System.out.println(product);
            }
        }

        // Add product to cart
        public void addToCart(String productID) {
            if (currentUser != null) {
                for (Product product : products) {
                    if (product.getProductID().equals(productID)) {
                        currentUser.addToCart(product);
                        System.out.println(product.getName() + " added to your cart.");
                        return;
                    }
                }
                System.out.println("Product not found.");
            } else {
                System.out.println("You must be logged in to add products to your cart.");
            }
        }

        // View cart
        public void viewCart() {
            if (currentUser != null) {
                System.out.println("\nYour Shopping Cart:");
                double total = 0.0;
                for (Product product : currentUser.getCart()) {
                    System.out.println(product);
                    total += product.getPrice();
                }
                System.out.println("Total: $" + total);
            } else {
                System.out.println("You must be logged in to view your cart.");
            }
        }

        // Process payment
        public void processPayment() {
            if (currentUser != null) {
                if (!currentUser.getCart().isEmpty()) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Enter payment amount: ");
                    double amount = scanner.nextDouble();

                    double total = currentUser.getCart().stream().mapToDouble(Product::getPrice).sum();

                    if (amount >= total) {
                        System.out.println("Payment successful. Your order has been processed.");
                        currentUser.clearCart();
                    } else {
                        System.out.println("Insufficient payment. Please try again.");
                    }
                } else {
                    System.out.println("Your cart is empty.");
                }
            } else {
                System.out.println("You must be logged in to process payment.");
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        ECommerceSystem system = new ECommerceSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nE-Commerce Platform Menu:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. View Products");
            System.out.println("4. Add to Cart");
            System.out.println("5. View Cart");
            System.out.println("6. Process Payment");
            System.out.println("7. Logout");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    system.registerUser(username, password);
                }
                case 2 -> {
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    system.loginUser(loginUsername, loginPassword);
                }
                case 3 -> system.viewProducts();
                case 4 -> {
                    System.out.print("Enter Product ID to add to cart: ");
                    String productID = scanner.nextLine();
                    system.addToCart(productID);
                }
                case 5 -> system.viewCart();
                case 6 -> system.processPayment();
                case 7 -> system.logoutUser();
                case 8 -> {
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
