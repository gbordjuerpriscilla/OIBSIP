import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A bank account. Balance, PIN and history are private (encapsulation):
 * other classes can only change them through the methods below.
 */
public class Account {

    private final String accountId;
    private final String holderName;
    private final String pin;
    private double balance;
    private final ArrayList<Transaction> transactions = new ArrayList<>();

    public Account(String accountId, String holderName, String pin, double openingBalance) {
        this.accountId = accountId;
        this.holderName = holderName;
        this.pin = pin;
        this.balance = openingBalance;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public boolean verifyPin(String input) {
        return pin.equals(input);
    }

    public boolean hasSufficientFunds(double amount) {
        return amount <= balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("DEPOSIT", amount, balance, "Cash deposit"));
    }

    public boolean withdraw(double amount) {
        if (!hasSufficientFunds(amount)) {
            return false;
        }
        balance -= amount;
        transactions.add(new Transaction("WITHDRAWAL", amount, balance, "Cash withdrawal"));
        return true;
    }

    public boolean sendTransfer(double amount, String toAccountId) {
        if (!hasSufficientFunds(amount)) {
            return false;
        }
        balance -= amount;
        transactions.add(new Transaction("TRANSFER OUT", amount, balance,
                "Sent to account " + toAccountId));
        return true;
    }

    public void receiveTransfer(double amount, String fromAccountId) {
        balance += amount;
        transactions.add(new Transaction("TRANSFER IN", amount, balance,
                "Received from account " + fromAccountId));
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }
} 
