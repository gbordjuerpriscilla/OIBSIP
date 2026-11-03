# Number Guessing Game (Java)

**Oasis Infobyte Internship — Java Development, Task 2**

A console-based number guessing game written in Java. The computer picks a random number and the player tries to guess it, getting "Too High!" or "Too Low!" hints until they win or run out of attempts.

## Features

- Random number generated at the start of every round
- Input validation (rejects text and out-of-range numbers without wasting an attempt)
- Three responses after each guess: `Too High!`, `Too Low!` or `Correct!`
- Visible attempt counter throughout the round (e.g. `Attempt 3 of 7`)
- Maximum attempts limit: the game ends with `You Lost!` and reveals the number
- "Play Again" option after every round (y/n)
- Score tracking across rounds, e.g. `Round 1 — guessed in 4 attempts`
- **Bonus:** three difficulty levels

| Difficulty | Number range | Attempts |
|------------|--------------|----------|
| Easy       | 1 – 50       | 10       |
| Medium     | 1 – 100      | 7        |
| Hard       | 1 – 200      | 5        |

## Tech Stack

- Java (console application)
- `java.util.Random` for number generation
- `java.util.Scanner` for user input
- `ArrayList` for storing round summaries

## How to Run

Make sure the JDK is installed (`java -version` and `javac -version` should both work).

```bash
javac NumberGuessingGame.java
java NumberGuessingGame
```

## Sample Output

```
=== NUMBER GUESSING GAME ===

Choose difficulty:
1. Easy   (1-50,  10 attempts)
2. Medium (1-100, 7 attempts)
3. Hard   (1-200, 5 attempts)
Enter 1, 2 or 3: 2

--- Round 1 ---
I'm thinking of a number between 1 and 100. You have 7 attempts.
Attempt 1 of 7
Enter your guess (1-100): 50
Too Low!
Attempt 2 of 7
Enter your guess (1-100): 75
Too High!
Attempt 3 of 7
Enter your guess (1-100): 63
Correct!

--- Score Summary ---
Round 1 — guessed in 3 attempts

Play again? (y/n): n

Thanks for playing! Goodbye.
```

## How It Works

1. The player chooses a difficulty, which sets the number range and attempt limit.
2. The program generates a secret number with `random.nextInt(max) + 1`.
3. A `while` loop runs until the player guesses correctly or uses all attempts.
4. Each guess is compared to the secret number using `if-else` statements.
5. After the round, the result is saved to a list and the full score summary is printed.
6. The player can start a new round or exit.

## Project Structure

```
Java-Task2-NumberGuessingGame/
├── NumberGuessingGame.java
└── README.md
```

## Author

**Priscilla Etornam Gbordjuer**
Java Development Intern, Oasis Infobyte (#oasisinfobyte)
