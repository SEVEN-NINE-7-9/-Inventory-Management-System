package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Manages the collection of products and all inventory operations.
 */
public class Inventory {

    private List<Product> products = new ArrayList<>();

    // ─── Core Operations ───────────────────────────────────────────────────────

    /**
     * Add a brand-new product to the inventory.
     * @return true if added, false if ID already exists.
     */
    public boolean addProduct(int id, String name, int quantity, double price) {
        if (findById(id).isPresent()) return false;
        products.add(new Product(id, name, quantity, price));
        return true;
    }

    /**
     * Restock an existing product.
     */
    public boolean updateStock(int id, int amount) {
        Optional<Product> opt = findById(id);
        if (opt.isEmpty()) return false;
        opt.get().restock(amount);
        return true;
    }

    /**
     * Sell units of a product.
     * @return SellResult enum indicating outcome.
     */
    public SellResult sellProduct(int id, int amount) {
        Optional<Product> opt = findById(id);
        if (opt.isEmpty()) return SellResult.NOT_FOUND;
        boolean success = opt.get().sell(amount);
        return success ? SellResult.SUCCESS : SellResult.INSUFFICIENT_STOCK;
    }

    /**
     * Remove a product entirely from the inventory.
     */
    public boolean removeProduct(int id) {
        return products.removeIf(p -> p.getId() == id);
    }

    // ─── Search ────────────────────────────────────────────────────────────────

    public Optional<Product> findById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst();
    }

    public List<Product> searchByName(String keyword) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(keyword.toLowerCase()))
                result.add(p);
        }
        return result;
    }

    // ─── Display ───────────────────────────────────────────────────────────────

    public void viewAll() {
        if (products.isEmpty()) {
            System.out.println("  (No products in inventory)");
            return;
        }
        System.out.printf("  %-5s %-20s %-10s %-12s%n", "ID", "Name", "Stock", "Price");
        System.out.println("  " + "─".repeat(52));
        for (Product p : products) p.display();
        System.out.println("  " + "─".repeat(52));
        System.out.printf("  Total products: %d%n", products.size());
    }

    public void viewLowStock(int threshold) {
        System.out.println("  Low-stock products (qty <= " + threshold + "):");
        boolean any = false;
        for (Product p : products) {
            if (p.getQuantity() <= threshold) {
                p.display();
                any = true;
            }
        }
        if (!any) System.out.println("  All products have sufficient stock.");
    }

    public int size() { return products.size(); }

    // ─── Inner Enum ────────────────────────────────────────────────────────────

    public enum SellResult {
        SUCCESS, NOT_FOUND, INSUFFICIENT_STOCK
    }
}
