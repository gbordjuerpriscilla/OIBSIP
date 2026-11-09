# OIBSIP: Oasis Infobyte Internship Projects

Java Development projects completed during the **Oasis Infobyte Internship Program (OIBSIP)**.

Each task lives in its own folder with its source code and a README that explains the features, class design and how to run it.

## Projects

| Task | Project | Type | Folder |
|------|---------|------|--------|
| 2 | **Number Guessing Game** | Console | [Java-Task2-NumberGuessingGame](Java-Task2-NumberGuessingGame) |
| 3 | **ATM Interface** | Console, OOP | [Java-Task3-ATMInterface](Java-Task3-ATMInterface) |
| 4 | **Online Examination System** | GUI (Swing) | [Java-Task4-OnlineExamSystem](Java-Task4-OnlineExamSystem) |

## Project Summaries

### Task 2: Number Guessing Game
The computer picks a random number and the player guesses it with "Too High!" / "Too Low!" hints.

- Attempt counter and maximum attempts limit
- Play again option and score tracking across rounds
- Three difficulty levels (Easy, Medium, Hard)
- **Concepts:** `Random`, `Scanner`, `while` loops, `if-else`, input validation

### Task 3: ATM Interface
A console ATM simulation with PIN login and standard banking operations.

- Login with User ID and PIN, locked out after 3 wrong attempts
- Transaction history, withdraw, deposit, transfer between accounts and quit
- "Insufficient Funds" check before withdrawals and transfers
- **Concepts:** object-oriented design with 5 classes (`ATM`, `Account`, `Transaction`, `Bank`, `Main`), encapsulation, `ArrayList`, `HashMap`

### Task 4: Online Examination System
A Swing application where students log in, answer timed multiple-choice questions and see their results.

- Login screen and profile update screen (display name and password)
- One question at a time with 4 radio buttons, Next/Previous navigation
- Visible countdown timer that **auto-submits** when it reaches zero
- Manual submit with confirmation, result screen with score, time taken and breakdown
- "Are you sure you want to quit?" dialog when closing during an exam, plus Logout
- **Concepts:** `JFrame` and `JPanel`, `CardLayout`, `javax.swing.Timer`, `ButtonGroup` and `JRadioButton`, event listeners

## Tech Stack

- **Language:** Java
- **GUI:** Swing (Task 4)
- **Tools:** VS Code, Git and GitHub

## How to Run

Make sure the JDK is installed (`javac -version` should work). Then open a task folder in a terminal and run:

```bash
cd Java-Task3-ATMInterface      # or Java-Task4-OnlineExamSystem
javac *.java
java Main
```

Task 2 is a single file, so use:

```bash
cd Java-Task2-NumberGuessingGame
javac NumberGuessingGame.java
java NumberGuessingGame
```

See each folder's own README for demo logins and extra details.

## Repository Structure

```
OIBSIP/
├── Java-Task2-NumberGuessingGame/
│   ├── NumberGuessingGame.java
│   └── README.md
├── Java-Task3-ATMInterface/
│   ├── Main.java, ATM.java, Bank.java, Account.java, Transaction.java
│   └── README.md
├── Java-Task4-OnlineExamSystem/
│   ├── Main.java, ExamApp.java, LoginPanel.java, ProfilePanel.java,
│   │   ExamPanel.java, ResultPanel.java, Question.java, User.java, ExamResult.java
│   └── README.md
├── .gitignore
└── README.md
```

## Author

**Priscilla Etornam Gbordjuer**
Computer Science student, KNUST, Ghana
Java Development Intern, Oasis Infobyte

`#oasisinfobyte` `#OIBSIP` `#java` `#internship`
