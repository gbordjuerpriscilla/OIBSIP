import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single record in an account's transaction history.
 * Fields are private and final, so a transaction cannot be changed once created.
 */
public class Transaction {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String type;
    private final double amount;
    private final double balanceAfter;
    private final String details;
    private final LocalDateTime timestamp;

    public Transaction(String type, double amount, double balanceAfter, String details) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    @Override
    public String toString() {
        return String.format("%s | %-12s | GHS %10.2f | Balance: GHS %10.2f | %s",
                timestamp.format(FORMAT), type, amount, balanceAfter, details);
    }
} 
