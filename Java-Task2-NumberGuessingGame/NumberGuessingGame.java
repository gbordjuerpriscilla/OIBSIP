import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    // Difficulty settings: {max number, max attempts}
    private static final int[] EASY = {50, 10};
    private static final int[] MEDIUM = {100, 7};
    private static final int[] HARD = {200, 5};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        List<String> summaries = new ArrayList<>();
        int round = 0;
        boolean playAgain = true;

        System.out.println("=== NUMBER GUESSING GAME ===");

        while (playAgain) {
            round++;
            int[] level = chooseDifficulty(scanner);
            int maxNumber = level[0];
            int maxAttempts = level[1];

            // 1. System generates a random number at the start of each round
            int secret = random.nextInt(maxNumber) + 1;
            int attempts = 0;
            boolean won = false;

            System.out.println("\n--- Round " + round + " ---");
            System.out.println("I'm thinking of a number between 1 and " + maxNumber
                    + ". You have " + maxAttempts + " attempts.");

            while (attempts < maxAttempts) {
                // 4. Attempt counter visible throughout the game
                System.out.println("Attempt " + (attempts + 1) + " of " + maxAttempts);
                int guess = readGuess(scanner, maxNumber);
                attempts++;

                // 3. Three possible responses
                if (guess > secret) {
                    System.out.println("Too High!");
                } else if (guess < secret) {
                    System.out.println("Too Low!");
                } else {
                    System.out.println("Correct!");
                    won = true;
                    break;
                }
            }

            // 5. Max attempts reached: game over, reveal number
            if (won) {
                summaries.add("Round " + round + " — guessed in " + attempts + " attempts");
            } else {
                System.out.println("You Lost! The number was " + secret + ".");
                summaries.add("Round " + round + " — lost (number was " + secret + ")");
            }

            // 7. Score tracking across rounds
            System.out.println("\n--- Score Summary ---");
            for (String s : summaries) {
                System.out.println(s);
            }

            // 6. Play again option
            playAgain = askPlayAgain(scanner);
        }

        System.out.println("\nThanks for playing! Goodbye.");
        scanner.close();
    }

    // Bonus: difficulty levels
    private static int[] chooseDifficulty(Scanner scanner) {
        while (true) {
            System.out.println("\nChoose difficulty:");
            System.out.println("1. Easy   (1-50,  10 attempts)");
            System.out.println("2. Medium (1-100, 7 attempts)");
            System.out.println("3. Hard   (1-200, 5 attempts)");
            System.out.print("Enter 1, 2 or 3: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": return EASY;
                case "2": return MEDIUM;
                case "3": return HARD;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // 2. Prompt user for a guess, with input validation
    private static int readGuess(Scanner scanner, int maxNumber) {
        while (true) {
            System.out.print("Enter your guess (1-" + maxNumber + "): ");
            String input = scanner.nextLine().trim();
            try {
                int guess = Integer.parseInt(input);
                if (guess < 1 || guess > maxNumber) {
                    System.out.println("Please enter a number between 1 and " + maxNumber + ".");
                } else {
                    return guess;
                }
            } catch (NumberFormatException e) {
                System.out.println("That's not a valid number. Try again.");
            }
        }
    }

    private static boolean askPlayAgain(Scanner scanner) {
        while (true) {
            System.out.print("\nPlay again? (y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals("y") || answer.equals("yes")) return true;
            if (answer.equals("n") || answer.equals("no")) return false;
            System.out.println("Please type y or n.");
        }
    }
}