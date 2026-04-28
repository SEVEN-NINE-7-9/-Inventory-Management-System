package src;

/**
 * Represents a product in the inventory system.
 */
public class Product {
    private int id;
    private String name;
    private int quantity;
    private double price;

    public Product(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public int getId()       { return id; }
    public String getName()  { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    // Setters
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price)    { this.price = price; }
    public void setName(String name)      { this.name = name; }

    /**
     * Add stock to this product.
     */
    public void restock(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Restock amount must be positive.");
        this.quantity += amount;
    }

    /**
     * Sell (deduct) stock from this product.
     * @return true if sold successfully, false if insufficient stock.
     */
    public boolean sell(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Sell amount must be positive.");
        if (amount > this.quantity) return false;
        this.quantity -= amount;
        return true;
    }

    public void display() {
        System.out.printf("  [%3d] %-20s | Stock: %4d | Price: Rs.%.2f%n",
                id, name, quantity, price);
    }

    @Override
    public String toString() {
        return String.format("Product{id=%d, name='%s', qty=%d, price=%.2f}", id, name, quantity, price);
    }
}
