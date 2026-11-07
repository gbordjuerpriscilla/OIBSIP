import java.util.List;
import java.util.Scanner;

/**
 * The user interface of the ATM: login, main menu and the five options.
 */
public class ATM {

    private static final int MAX_LOGIN_ATTEMPTS = 3;

    private final Bank bank;
    private final Scanner scanner = new Scanner(System.in);

    public ATM(Bank bank) {
        this.bank = bank;
    }

    public void start() {
        System.out.println("=== WELCOME TO THE ATM ===");

        Account account = login();
        if (account == null) {
            System.out.println("\nToo many incorrect attempts. Access denied.");
        } else {
            System.out.println("\nLogin successful. Welcome, " + account.getHolderName() + "!");
            runMenu(account);
        }
        scanner.close();
    }

    // ---------- Login ----------

    private Account login() {
        for (int attempt = 1; attempt <= MAX_LOGIN_ATTEMPTS; attempt++) {
            System.out.print("\nEnter User ID: ");
            String id = scanner.nextLine().trim();
            System.out.print("Enter your PIN: ");
            String pin = scanner.nextLine().trim();

            Account account = bank.authenticate(id, pin);
            if (account != null) {
                return account;
            }
            int left = MAX_LOGIN_ATTEMPTS - attempt;
            if (left > 0) {
                System.out.println("Invalid User ID or PIN. Attempts left: " + left);
            }
        }
        return null;
    }

    // ---------- Main menu ----------

    private void runMenu(Account account) {
        boolean running = true;
        while (running) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option (1-5): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    showHistory(account);
                    break;
                case "2":
                    withdraw(account);
                    break;
                case "3":
                    deposit(account);
                    break;
                case "4":
                    transfer(account);
                    break;
                case "5":
                    System.out.println("\nThank you for banking with us, "
                            + account.getHolderName() + ". Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please enter a number from 1 to 5.");
            }
        }
    }

    // ---------- Menu actions ----------

    private void showHistory(Account account) {
        List<Transaction> history = account.getTransactions();
        System.out.println("\n--- Transaction History (current session) ---");
        if (history.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (int i = 0; i < history.size(); i++) {
                System.out.println((i + 1) + ". " + history.get(i));
            }
        }
        System.out.printf("Current balance: GHS %.2f%n", account.getBalance());
    }

    private void withdraw(Account account) {
        System.out.printf("%nCurrent balance: GHS %.2f%n", account.getBalance());
        double amount = readAmount("Enter amount to withdraw: ");

        // Balance check before the withdrawal
        if (!account.hasSufficientFunds(amount)) {
            System.out.println("Insufficient Funds");
            return;
        }
        account.withdraw(amount);
        System.out.printf("Withdrawal successful. New balance: GHS %.2f%n", account.getBalance());
    }

    private void deposit(Account account) {
        double amount = readAmount("\nEnter amount to deposit: ");
        account.deposit(amount);
        System.out.printf("Deposit successful. New balance: GHS %.2f%n", account.getBalance());
    }

    private void transfer(Account account) {
        System.out.print("\nEnter recipient account ID: ");
        String recipientId = scanner.nextLine().trim();

        // Check the recipient first so the user isn't asked for an amount needlessly
        Account recipient = bank.findAccount(recipientId);
        if (recipient == null) {
            System.out.println("Recipient account not found.");
            return;
        }
        if (recipient == account) {
            System.out.println("You cannot transfer money to your own account.");
            return;
        }

        double amount = readAmount("Enter amount to transfer: ");

        // Balance check before the transfer
        if (!account.hasSufficientFunds(amount)) {
            System.out.println("Insufficient Funds");
            return;
        }

        Bank.TransferResult result = bank.transfer(account, recipientId, amount);
        if (result == Bank.TransferResult.SUCCESS) {
            System.out.printf("Transferred GHS %.2f to %s (account %s). New balance: GHS %.2f%n",
                    amount, recipient.getHolderName(), recipientId, account.getBalance());
        } else {
            System.out.println("Transfer failed. Please try again.");
        }
    }

    // ---------- Helpers ----------

    /** Keeps asking until the user enters a positive, finite number. */
    private double readAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double amount = Double.parseDouble(input);
                if (Double.isFinite(amount) && amount > 0) {
                    return amount;
                }
                System.out.println("Amount must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("That's not a valid amount. Try again.");
            }
        }
    }
} 
    

