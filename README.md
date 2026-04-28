# Inventory Management System

## 📦 Overview
A console-based Inventory Management System built in Java demonstrating:
- Object-Oriented Programming (OOP)
- State management with ArrayList
- Business logic & validation
- Clean separation of concerns (Model / Service / UI)

---

## 🗂️ Project Structure
```
InventorySystem/
├── src/
│   ├── Product.java          → Data model (id, name, qty, price)
│   ├── Inventory.java        → Business logic & operations
│   └── InventorySystem.java  → Main class with menu UI
└── README.md
```

---

## ✅ Features

| # | Feature             | Description                                    |
|---|---------------------|------------------------------------------------|
| 1 | Add Product         | Add new product with ID, name, qty, price      |
| 2 | Update / Restock    | Add more stock to existing product             |
| 3 | Sell Product        | Deduct stock; **prevents negative stock**      |
| 4 | Remove Product      | Delete a product with confirmation prompt      |
| 5 | Search by Name      | Keyword search across all product names        |
| 6 | View Inventory      | Display full inventory table                   |
| 7 | Low Stock Report    | Filter products below a quantity threshold     |
| 8 | Exit                | End the session                                |

---

## 🚀 How to Run

### Requirements
- Java JDK 11 or higher installed
- Terminal / Command Prompt

### Windows
```
Double-click run.bat
```
OR in Command Prompt:
```cmd
javac -d out src\*.java
java -cp out src.InventorySystem
```

### Linux / Mac
```bash
chmod +x run.sh
./run.sh
```
OR manually:
```bash
javac -d out src/*.java
java -cp out src.InventorySystem
```

---

## 🧠 Learning Outcomes (Week 6)

- **OOP Design**: Separate `Product`, `Inventory`, and `InventorySystem` classes
- **Encapsulation**: Private fields with getters/setters
- **Business Validation**: Prevents negative stock, duplicate IDs, empty inputs
- **State Updates**: Object mutation tracked across menu operations
- **Enum Usage**: `SellResult` enum for clean return values
- **Input Handling**: Robust scanner with try-catch for invalid inputs
- **Java Collections**: `ArrayList` with stream-based search

---

## 📸 Sample Output

```
  ╔═══════════════════════════════════════════╗
  ║    INVENTORY MANAGEMENT SYSTEM  v1.0      ║
  ║    Java OOP — Week 6 Project              ║
  ╚═══════════════════════════════════════════╝

  ┌─────────────────────────────┐
  │          MAIN MENU          │
  ├─────────────────────────────┤
  │  1. Add Product             │
  │  2. Update / Restock        │
  │  3. Sell Product            │
  │  4. Remove Product          │
  │  5. Search by Name          │
  │  6. View All Inventory      │
  │  7. Low Stock Report        │
  │  8. Exit                    │
  └─────────────────────────────┘
```
