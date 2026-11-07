 import java.util.HashMap;
import java.util.Map;

/**
 * Holds all accounts, authenticates users and carries out transfers
 * between two accounts.
 */
public class Bank {

    public enum TransferResult {
        SUCCESS, RECIPIENT_NOT_FOUND, SAME_ACCOUNT, INSUFFICIENT_FUNDS
    }

    private final Map<String, Account> accounts = new HashMap<>();

    public void addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
    }

    /** Returns the account if the ID and PIN match, otherwise null. */
    public Account authenticate(String accountId, String pin) {
        Account account = accounts.get(accountId);
        if (account != null && account.verifyPin(pin)) {
            return account;
        }
        return null;
    }

    public Account findAccount(String accountId) {
        return accounts.get(accountId);
    }

    public TransferResult transfer(Account from, String toAccountId, double amount) {
        Account to = accounts.get(toAccountId);
        if (to == null) {
            return TransferResult.RECIPIENT_NOT_FOUND;
        }
        if (to == from) {
            return TransferResult.SAME_ACCOUNT;
        }
        if (!from.sendTransfer(amount, toAccountId)) {
            return TransferResult.INSUFFICIENT_FUNDS;
        }
        to.receiveTransfer(amount, from.getAccountId());
        return TransferResult.SUCCESS;
    }
} 
    

