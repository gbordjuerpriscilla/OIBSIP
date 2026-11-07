# ATM Interface (Java)

**Oasis Infobyte Internship — Java Development, Task 3**

A console-based simulation of an ATM machine, built with object-oriented design across multiple classes. Users log in with a User ID and PIN, then check their history, withdraw, deposit, transfer money or quit.

## Features

- Login with **User ID and PIN**; access is denied after **3 incorrect attempts**
- Main menu after a successful login:
  1. **Transaction History**: log of all past transactions in the current session
  2. **Withdraw**: validates the amount and balance, updates the balance, logs the transaction
  3. **Deposit**: updates the balance and logs the transaction
  4. **Transfer**: validates the recipient account and balance, updates **both** accounts, logs the transaction for both
  5. **Quit**: goodbye message and exit
- Balance is checked before every withdrawal or transfer; shows `Insufficient Funds` when the balance is too low
- All transactions are stored in an `ArrayList` and displayed clearly with date, type, amount and balance after
- Input validation: rejects text, zero, negative and invalid amounts

## Class Design

| Class | Responsibility |
|-------|----------------|
| `Main` | Entry point. Creates the bank with sample accounts and starts the ATM |
| `ATM` | User interface: login, menu and all user interaction (`Scanner`) |
| `Bank` | Stores accounts, authenticates users, performs transfers between accounts |
| `Account` | Holds ID, name, PIN, balance and the `ArrayList<Transaction>` history |
| `Transaction` | One immutable record: type, amount, balance after, details, timestamp |

**OOP concepts used:** encapsulation (private fields with controlled getters and methods), abstraction (the ATM talks to the Bank, not directly to internals), composition (an Account *has* Transactions, a Bank *has* Accounts) and single responsibility per class.

## Test Accounts

| User ID | PIN  | Name      | Starting balance |
|---------|------|-----------|------------------|
| 1001    | 1234 | Priscilla | GHS 5000.00      |
| 1002    | 4321 | Kwame     | GHS 2500.00      |
| 1003    | 1111 | Ama       | GHS 1000.00      |

## How to Run

Make sure the JDK is installed (`javac -version` should work). From inside this folder:

```bash
javac *.java
java Main
```

## Sample Output

```
=== WELCOME TO THE ATM ===

Enter User ID: 1001
Enter PIN: 9999
Invalid User ID or PIN. Attempts left: 2

Enter User ID: 1001
Enter PIN: 1234

Login successful. Welcome, Priscilla!

===== MAIN MENU =====
1. Transaction History
2. Withdraw
3. Deposit
4. Transfer
5. Quit
Choose an option (1-5): 3

Enter amount to deposit: 500
Deposit successful. New balance: GHS 5500.00

Choose an option (1-5): 2

Current balance: GHS 5500.00
Enter amount to withdraw: 99999
Insufficient Funds

Choose an option (1-5): 4

Enter recipient account ID: 1002
Enter amount to transfer: 300
Transferred GHS 300.00 to Kwame (account 1002). New balance: GHS 5200.00

Choose an option (1-5): 1

--- Transaction History (current session) ---
1. ... | DEPOSIT      | GHS     500.00 | Balance: GHS    5500.00 | Cash deposit
2. ... | TRANSFER OUT | GHS     300.00 | Balance: GHS    5200.00 | Sent to account 1002
Current balance: GHS 5200.00

Choose an option (1-5): 5

Thank you for banking with us, Priscilla. Goodbye!
```

## Project Structure

```
Java-Task3-ATMInterface/
├── Main.java
├── ATM.java
├── Bank.java
├── Account.java
├── Transaction.java
└── README.md
```

## Author

**Priscilla Etornam Gbordjuer**
Java Development Intern, Oasis Infobyte (#oasisinfobyte)