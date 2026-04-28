package src;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * ╔══════════════════════════════════════╗
 *  Inventory Management System — Week 6
 *  Java: OOP • State Management • Validation
 * ╚══════════════════════════════════════╝
 *
 * Features:
 *  1. Add product
 *  2. Update / restock product
 *  3. Sell product          (prevents negative stock)
 *  4. Remove product
 *  5. Search by name
 *  6. View all inventory
 *  7. View low-stock report
 *  8. Exit
 */
public class InventorySystem {

    private static final Inventory inventory = new Inventory();
    private static final Scanner sc = new Scanner(System.in);

    // ─── Entry Point ──────────────────────────────────────────────────────────

    public static void main(String[] args) {
        banner();
        seedDemoData();

        int choice;
        do {
            printMenu();
            choice = readInt("  Enter choice: ");
            System.out.println();
            handleChoice(choice);
        } while (choice != 8);

        System.out.println("\n  Goodbye! Inventory session ended.\n");
    }

    // ─── Menu Handling ────────────────────────────────────────────────────────

    private static void handleChoice(int choice) {
        switch (choice) {
            case 1 -> addProduct();
            case 2 -> updateStock();
            case 3 -> sellProduct();
            case 4 -> removeProduct();
            case 5 -> searchProduct();
            case 6 -> viewInventory();
            case 7 -> lowStockReport();
            case 8 -> {}  // exit handled in loop
            default -> System.out.println("  [!] Invalid option. Please choose 1-8.");
        }
    }

    // ─── Feature Methods ──────────────────────────────────────────────────────

    /** Case 1 — Add a new product */
    private static void addProduct() {
        section("ADD PRODUCT");
        int id = readInt("  Product ID   : ");
        String name = readString("  Product Name : ");
        int qty = readPositiveInt("  Initial Qty  : ");
        double price = readPositiveDouble("  Price (Rs.)  : ");

        boolean added = inventory.addProduct(id, name, qty, price);
        if (added)
            success("Product '" + name + "' added successfully!");
        else
            error("Product ID " + id + " already exists. Use Update to change stock.");
    }

    /** Case 2 — Restock an existing product */
    private static void updateStock() {
        section("UPDATE / RESTOCK");
        int id = readInt("  Product ID     : ");
        Optional<Product> opt = inventory.findById(id);
        if (opt.isEmpty()) { error("Product ID " + id + " not found."); return; }

        System.out.println("  Found: " + opt.get().getName() + " | Current stock: " + opt.get().getQuantity());
        int amount = readPositiveInt("  Add quantity   : ");

        inventory.updateStock(id, amount);
        success("Restocked! New stock: " + opt.get().getQuantity());
    }

    /** Case 3 — Sell a product (prevents negative stock) */
    private static void sellProduct() {
        section("SELL PRODUCT");
        int id = readInt("  Product ID     : ");
        Optional<Product> opt = inventory.findById(id);
        if (opt.isEmpty()) { error("Product ID " + id + " not found."); return; }

        Product p = opt.get();
        System.out.printf("  Found: %s | Stock: %d | Price: Rs.%.2f%n",
                p.getName(), p.getQuantity(), p.getPrice());

        int qty = readPositiveInt("  Sell quantity  : ");

        Inventory.SellResult result = inventory.sellProduct(id, qty);
        switch (result) {
            case SUCCESS -> {
                double total = qty * p.getPrice();
                success(String.format("Sold %d x %s. Total: Rs.%.2f | Remaining stock: %d",
                        qty, p.getName(), total, p.getQuantity()));
            }
            case INSUFFICIENT_STOCK ->
                error("Insufficient stock! Available: " + p.getQuantity() + ", Requested: " + qty);
            case NOT_FOUND ->
                error("Product not found.");
        }
    }

    /** Case 4 — Remove a product */
    private static void removeProduct() {
        section("REMOVE PRODUCT");
        int id = readInt("  Product ID : ");
        Optional<Product> opt = inventory.findById(id);
        if (opt.isEmpty()) { error("Product ID " + id + " not found."); return; }

        System.out.println("  Are you sure you want to remove '" + opt.get().getName() + "'? (y/n)");
        String confirm = readString("  Confirm     : ");
        if (confirm.equalsIgnoreCase("y")) {
            inventory.removeProduct(id);
            success("Product removed successfully.");
        } else {
            System.out.println("  [i] Removal cancelled.");
        }
    }

    /** Case 5 — Search by name keyword */
    private static void searchProduct() {
        section("SEARCH BY NAME");
        String keyword = readString("  Enter keyword : ");
        List<Product> results = inventory.searchByName(keyword);
        if (results.isEmpty()) {
            System.out.println("  No products matched '" + keyword + "'.");
        } else {
            System.out.println("  Results (" + results.size() + " found):");
            results.forEach(Product::display);
        }
    }

    /** Case 6 — View all inventory */
    private static void viewInventory() {
        section("INVENTORY — ALL PRODUCTS");
        inventory.viewAll();
    }

    /** Case 7 — Low stock report */
    private static void lowStockReport() {
        section("LOW STOCK REPORT");
        int threshold = readPositiveInt("  Alert threshold (qty <= ?): ");
        inventory.viewLowStock(threshold);
    }

    // ─── Demo Data ────────────────────────────────────────────────────────────

    private static void seedDemoData() {
        inventory.addProduct(101, "Laptop",      15, 55000.00);
        inventory.addProduct(102, "Wireless Mouse", 40, 799.00);
        inventory.addProduct(103, "USB-C Hub",    8,  1299.00);
        inventory.addProduct(104, "Monitor 24\"", 5,  18500.00);
        inventory.addProduct(105, "Keyboard",    12,  1599.00);
        System.out.println("  [i] Demo data loaded (5 products). Use option 6 to view.\n");
    }

    // ─── UI Helpers ───────────────────────────────────────────────────────────

    private static void banner() {
        System.out.println();
        System.out.println("  ╔═══════════════════════════════════════════╗");
        System.out.println("  ║    INVENTORY MANAGEMENT SYSTEM  v1.0      ║");
        System.out.println("  ║    Java OOP — Week 6 Project              ║");
        System.out.println("  ╚═══════════════════════════════════════════╝");
        System.out.println();
    }

    private static void printMenu() {
        System.out.println("  ┌─────────────────────────────┐");
        System.out.println("  │          MAIN MENU          │");
        System.out.println("  ├─────────────────────────────┤");
        System.out.println("  │  1. Add Product             │");
        System.out.println("  │  2. Update / Restock        │");
        System.out.println("  │  3. Sell Product            │");
        System.out.println("  │  4. Remove Product          │");
        System.out.println("  │  5. Search by Name          │");
        System.out.println("  │  6. View All Inventory      │");
        System.out.println("  │  7. Low Stock Report        │");
        System.out.println("  │  8. Exit                    │");
        System.out.println("  └─────────────────────────────┘");
    }

    private static void section(String title) {
        System.out.println("  ── " + title + " " + "─".repeat(Math.max(0, 36 - title.length())));
    }

    private static void success(String msg) { System.out.println("  [✓] " + msg); }
    private static void error(String msg)   { System.out.println("  [✗] " + msg); }

    // ─── Input Helpers ────────────────────────────────────────────────────────

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = sc.nextInt();
                sc.nextLine();
                return val;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("  [!] Please enter a valid integer.");
            }
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            int val = readInt(prompt);
            if (val > 0) return val;
            System.out.println("  [!] Value must be greater than 0.");
        }
    }

    private static double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = sc.nextDouble();
                sc.nextLine();
                if (val > 0) return val;
                System.out.println("  [!] Value must be greater than 0.");
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("  [!] Please enter a valid number.");
            }
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        String val = sc.nextLine().trim();
        while (val.isEmpty()) {
            System.out.println("  [!] Input cannot be empty.");
            System.out.print(prompt);
            val = sc.nextLine().trim();
        }
        return val;
    }
}
