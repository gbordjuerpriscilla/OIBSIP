import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Screen 3: shows one question at a time with four radio buttons, a visible
 * countdown timer, Previous/Next navigation and a Submit button.
 */
public class ExamPanel extends JPanel {

    private static final Color TIMER_NORMAL = new Color(0x1B5E20);
    private static final Color TIMER_WARNING = new Color(0xC62828);
    private static final int WARNING_SECONDS = 60;

    private final ExamApp app;

    private final JLabel userLabel = new JLabel();
    private final JLabel progressLabel = new JLabel();
    private final JLabel timerLabel = new JLabel();
    private final JTextArea questionArea = new JTextArea();
    private final JRadioButton[] optionButtons = new JRadioButton[4];
    private final ButtonGroup optionGroup = new ButtonGroup();
    private final JButton previousButton = new JButton("Previous");
    private final JButton nextButton = new JButton("Next");
    private final JButton submitButton = new JButton("Submit Exam");
    private final javax.swing.Timer timer;

    private List<Question> questions = new ArrayList<>();
    private int[] answers = new int[0];   // -1 = not answered
    private int current;
    private int totalSeconds;
    private int remainingSeconds;
    private boolean running;

    public ExamPanel(ExamApp app) {
        this.app = app;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 28, 16, 28));

        // ----- Top bar: candidate, progress, countdown -----
        JPanel left = new JPanel(new GridLayout(2, 1));
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        progressLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        left.add(userLabel);
        left.add(progressLabel);

        timerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        timerLabel.setForeground(TIMER_NORMAL);

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(0, 0, 10, 0)));
        top.add(left, BorderLayout.WEST);
        top.add(timerLabel, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        // ----- Centre: question text + 4 radio buttons -----
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setOpaque(false);
        questionArea.setFont(new Font("SansSerif", Font.BOLD, 18));

        // GridLayout gives every option the full width, however long its text is
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        for (int i = 0; i < optionButtons.length; i++) {
            final int optionIndex = i;
            JRadioButton button = new JRadioButton();
            button.setFont(new Font("SansSerif", Font.PLAIN, 16));
            button.setFocusPainted(false);
            // Save the choice the moment the student clicks an option
            button.addActionListener(e -> answers[current] = optionIndex);
            optionButtons[i] = button;
            optionGroup.add(button);
            optionsPanel.add(button);
        }
        JPanel optionsHolder = new JPanel(new BorderLayout());
        optionsHolder.add(optionsPanel, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 20));
        center.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        center.add(questionArea, BorderLayout.NORTH);
        center.add(optionsHolder, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        // ----- Bottom: navigation + submit -----
        JPanel navigation = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        navigation.add(previousButton);
        navigation.add(nextButton);

        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        submitPanel.add(submitButton);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(navigation, BorderLayout.WEST);
        bottom.add(submitPanel, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);

        // ----- Actions -----
        previousButton.addActionListener(e -> goToQuestion(current - 1));
        nextButton.addActionListener(e -> goToQuestion(current + 1));
        submitButton.addActionListener(e -> confirmAndSubmit());

        // javax.swing.Timer fires once per second on the Swing event thread
        timer = new javax.swing.Timer(1000, e -> tick());
    }

    // ---------- Starting and stopping ----------

    public void start(User user, List<Question> examQuestions, int seconds) {
        questions = examQuestions;
        answers = new int[questions.size()];
        Arrays.fill(answers, -1);
        current = 0;
        totalSeconds = seconds;
        remainingSeconds = seconds;

        userLabel.setText("Candidate: " + user.getDisplayName());
        updateTimerLabel();
        showQuestion();

        running = true;
        timer.restart();
    }

    public boolean isRunning() {
        return running;
    }

    public void stopTimer() {
        timer.stop();
        running = false;
    }

    // ---------- Questions ----------

    private void goToQuestion(int index) {
        if (index < 0 || index >= questions.size()) {
            return;
        }
        current = index;
        showQuestion();
    }

    private void showQuestion() {
        Question question = questions.get(current);
        progressLabel.setText("Question " + (current + 1) + " of " + questions.size());
        questionArea.setText(question.getText());

        optionGroup.clearSelection();
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(question.getOption(i));
        }
        // Restore the answer if the student has already answered this question
        if (answers[current] >= 0) {
            optionButtons[answers[current]].setSelected(true);
        }

        previousButton.setEnabled(current > 0);
        nextButton.setEnabled(current < questions.size() - 1);
    }

    // ---------- Timer ----------

    private void tick() {
        if (!running) {
            return;
        }
        remainingSeconds--;
        updateTimerLabel();

        if (remainingSeconds <= 0) {
            timer.stop();
            JOptionPane.showMessageDialog(this,
                    "Time is up! Your exam will be submitted automatically.",
                    "Time Up", JOptionPane.INFORMATION_MESSAGE);
            finish(true);
        }
    }

    private void updateTimerLabel() {
        int seconds = Math.max(remainingSeconds, 0);
        timerLabel.setText(String.format("Time left: %02d:%02d", seconds / 60, seconds % 60));
        timerLabel.setForeground(seconds <= WARNING_SECONDS ? TIMER_WARNING : TIMER_NORMAL);
    }

    // ---------- Submitting ----------

    private void confirmAndSubmit() {
        int answered = 0;
        for (int answer : answers) {
            if (answer >= 0) {
                answered++;
            }
        }
        int unanswered = answers.length - answered;

        StringBuilder message = new StringBuilder();
        message.append("You have answered ").append(answered)
                .append(" of ").append(answers.length).append(" questions.");
        if (unanswered > 0) {
            message.append("\n").append(unanswered)
                    .append(" unanswered question(s) will be marked wrong.");
        }
        message.append("\n\nDo you want to submit your exam now?");

        int choice = JOptionPane.showConfirmDialog(this, message.toString(),
                "Confirm Submission", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            finish(false);
        }
    }

    /** Ends the exam (manual or automatic) and opens the result screen. */
    private void finish(boolean autoSubmitted) {
        if (!running) {
            return; // already finished, e.g. time ran out while a dialog was open
        }
        timer.stop();
        running = false;

        int timeTaken = totalSeconds - Math.max(remainingSeconds, 0);
        app.showResult(new ExamResult(questions, answers, timeTaken, autoSubmitted));
    }
}
