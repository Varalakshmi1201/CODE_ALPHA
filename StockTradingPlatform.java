import java.io.*;
import java.util.*;

// Class to represent a Stock
class Stock {
    String symbol;
    double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
}

// Class for user's stock holdings
class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();
    double balance;

    public Portfolio(double balance) {
        this.balance = balance;
    }

    // Buy operation
    public void buyStock(Stock stock, int quantity) {
        double cost = stock.price * quantity;
        if (cost > balance) {
            System.out.println("❌ Insufficient funds to buy " + quantity + " shares of " + stock.symbol);
            return;
        }
        balance -= cost;
        holdings.put(stock.symbol, holdings.getOrDefault(stock.symbol, 0) + quantity);
        System.out.println("✅ Bought " + quantity + " shares of " + stock.symbol);
    }

    // Sell operation
    public void sellStock(Stock stock, int quantity) {
        if (!holdings.containsKey(stock.symbol) || holdings.get(stock.symbol) < quantity) {
            System.out.println("❌ Not enough shares to sell!");
            return;
        }
        balance += stock.price * quantity;
        holdings.put(stock.symbol, holdings.get(stock.symbol) - quantity);
        if (holdings.get(stock.symbol) == 0) holdings.remove(stock.symbol);
        System.out.println("✅ Sold " + quantity + " shares of " + stock.symbol);
    }

    // Display portfolio
    public void showPortfolio() {
        System.out.println("\n📊 Your Portfolio:");
        for (String symbol : holdings.keySet()) {
            System.out.println(symbol + " - " + holdings.get(symbol) + " shares");
        }
        System.out.println("💰 Balance: $" + String.format("%.2f", balance));
    }
}

// Main trading platform class
public class StockTradingPlatform {
    static List<Stock> marketStocks = new ArrayList<>();
    static Portfolio portfolio;

    public static void main(String[] args) throws IOException {
        // Initialize data
        marketStocks.add(new Stock("AAPL", 175.50));
        marketStocks.add(new Stock("GOOG", 2800.75));
        marketStocks.add(new Stock("TSLA", 255.30));
        marketStocks.add(new Stock("AMZN", 133.40));

        portfolio = new Portfolio(5000.00);

        Scanner sc = new Scanner(System.in);
        int choice;

        System.out.println("=== 🏦 Welcome to Java Stock Trading Platform ===");

        do {
            System.out.println("\n1️⃣ View Market Data");
            System.out.println("2️⃣ Buy Stock");
            System.out.println("3️⃣ Sell Stock");
            System.out.println("4️⃣ View Portfolio");
            System.out.println("5️⃣ Save Portfolio");
            System.out.println("6️⃣ Load Portfolio");
            System.out.println("7️⃣ Exit");
            System.out.print("👉 Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> showMarketData();
                case 2 -> buyOperation(sc);
                case 3 -> sellOperation(sc);
                case 4 -> portfolio.showPortfolio();
                case 5 -> savePortfolio();
                case 6 -> loadPortfolio();
                case 7 -> System.out.println("👋 Exiting... Thank you!");
                default -> System.out.println("❌ Invalid choice!");
            }
        } while (choice != 7);

        sc.close();
    }

    // Display available stocks
    static void showMarketData() {
        System.out.println("\n📈 Market Data:");
        for (Stock s : marketStocks) {
            System.out.println(s.symbol + " - $" + s.price);
        }
    }

    // Handle buy operation
    static void buyOperation(Scanner sc) {
        showMarketData();
        System.out.print("Enter stock symbol to buy: ");
        String symbol = sc.next().toUpperCase();
        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();

        for (Stock s : marketStocks) {
            if (s.symbol.equals(symbol)) {
                portfolio.buyStock(s, qty);
                return;
            }
        }
        System.out.println("❌ Stock not found!");
    }

    // Handle sell operation
    static void sellOperation(Scanner sc) {
        portfolio.showPortfolio();
        System.out.print("Enter stock symbol to sell: ");
        String symbol = sc.next().toUpperCase();
        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();

        for (Stock s : marketStocks) {
            if (s.symbol.equals(symbol)) {
                portfolio.sellStock(s, qty);
                return;
            }
        }
        System.out.println("❌ Stock not found!");
    }

    // Save portfolio to file
    static void savePortfolio() throws IOException {
        FileWriter fw = new FileWriter("portfolio.txt");
        fw.write(portfolio.balance + "\n");
        for (Map.Entry<String, Integer> e : portfolio.holdings.entrySet()) {
            fw.write(e.getKey() + " " + e.getValue() + "\n");
        }
        fw.close();
        System.out.println("💾 Portfolio saved to portfolio.txt");
    }

    // Load portfolio from file
    static void loadPortfolio() throws IOException {
        File f = new File("portfolio.txt");
        if (!f.exists()) {
            System.out.println("⚠️ No saved portfolio found.");
            return;
        }
        Scanner file = new Scanner(f);
        double bal = Double.parseDouble(file.nextLine());
        Portfolio loaded = new Portfolio(bal);
        while (file.hasNext()) {
            String sym = file.next();
            int qty = file.nextInt();
            loaded.holdings.put(sym, qty);
        }
        file.close();
        portfolio = loaded;
        System.out.println("✅ Portfolio loaded successfully!");
    }
}

