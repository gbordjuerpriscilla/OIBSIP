# Online Examination System (Java Swing)

**Oasis Infobyte Internship — Java Development, Task 4**

A GUI-based online examination system built with Java Swing. Students log in, optionally update their profile, answer multiple-choice questions against a countdown timer, and see a full result breakdown. The exam is submitted automatically when the time runs out.

## Features

- **Login screen:** username and password; on success the user moves on to the exam flow
- **Profile update screen:** change display name and password before starting (with validation)
- **Exam screen:** one MCQ at a time, 4 radio-button options
- **Navigation:** Next and Previous buttons; earlier answers are remembered and shown again
- **Countdown timer:** always visible, turns red in the last minute, **auto-submits at zero**
- **Manual submit:** Submit button with a confirmation dialog showing how many questions are answered
- **Result screen:** score (X out of Y), time taken, and a per-question breakdown (correct, wrong, unanswered)
- **Session management:** closing the window during an exam shows an "Are you sure you want to quit?" dialog
- **Logout button** on the result screen returns to the login screen

## Tech Stack

- Java, Swing (GUI)
- `javax.swing.Timer` for the countdown
- `ButtonGroup` + `JRadioButton` for the MCQ options
- `CardLayout` for switching between screens

## Class Design

| Class | Responsibility |
|-------|----------------|
| `Main` | Entry point; starts the app on the Swing event thread |
| `ExamApp` | Main window; owns all screens, user accounts and questions, switches screens with `CardLayout`, handles the window-close dialog |
| `LoginPanel` | Login screen |
| `ProfilePanel` | Profile update screen |
| `ExamPanel` | Exam screen: question display, answers, navigation, timer, submission |
| `ResultPanel` | Result screen with score, time and breakdown table |
| `Question` | One MCQ: text, four options, correct answer |
| `User` | Username, password and display name (password kept private) |
| `ExamResult` | Result of one attempt: answers, score counts, time taken, auto-submit flag |

## Demo Logins

| Username  | Password |
|-----------|----------|
| priscilla | java123  |
| student   | 1234     |

## How to Run

Make sure the JDK is installed (`javac -version` should work). From inside this folder:

```bash
javac *.java
java Main
```

## Changing the Exam Length

The exam lasts 10 minutes by default. To change it, edit this line in `ExamApp.java`:

```java
private static final int EXAM_DURATION_SECONDS = 10 * 60;
```

Use a small value such as `30` to quickly see the auto-submit behaviour.

## Screens

1. Login → 2. Profile update → 3. Exam (timer, 10 questions) → 4. Result → Logout back to Login

## Project Structure

```
Java-Task4-OnlineExamSystem/
├── Main.java
├── ExamApp.java
├── LoginPanel.java
├── ProfilePanel.java
├── ExamPanel.java
├── ResultPanel.java
├── Question.java
├── User.java
├── ExamResult.java
└── README.md
```

## Author

**Priscilla Etornam Gbordjuer**
Java Development Intern, Oasis Infobyte (#oasisinfobyte)
