import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

/**
 * Screen 4: score, time taken, correct/incorrect breakdown and a Logout button.
 */
public class ResultPanel extends JPanel {

    private final ExamApp app;
    private final JLabel titleLabel = new JLabel(" ");
    private final JLabel scoreLabel = new JLabel(" ");
    private final JLabel timeLabel = new JLabel(" ");
    private final JLabel breakdownLabel = new JLabel(" ");
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"#", "Question", "Your answer", "Correct answer", "Result"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public ResultPanel(ExamApp app) {
        this.app = app;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 28, 16, 28));

        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        scoreLabel.setForeground(new Color(0x1B5E20));
        timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        breakdownLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JPanel summary = new JPanel(new GridLayout(4, 1, 0, 4));
        summary.add(titleLabel);
        summary.add(scoreLabel);
        summary.add(timeLabel);
        summary.add(breakdownLabel);
        add(summary, BorderLayout.NORTH);

        JTable table = new JTable(tableModel);
        table.setRowHeight(24);
        table.getColumnModel().getColumn(0).setMaxWidth(36);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(4).setMinWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(120);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(logoutButton);
        add(bottom, BorderLayout.SOUTH);

        logoutButton.addActionListener(e -> app.logout());
    }

    /** Fills the screen with the result of the exam that just ended. */
    public void display(User user, ExamResult result) {
        int total = result.getTotal();
        int correct = result.getCorrectCount();
        long percent = Math.round(100.0 * correct / total);

        titleLabel.setText("Exam Result: " + user.getDisplayName());
        scoreLabel.setText("Score: " + correct + " out of " + total + " (" + percent + "%)");

        int seconds = result.getTimeTakenSeconds();
        String time = String.format("Time taken: %02d:%02d", seconds / 60, seconds % 60);
        if (result.isAutoSubmitted()) {
            time += "  (auto-submitted: time ran out)";
        }
        timeLabel.setText(time);

        breakdownLabel.setText("Correct: " + correct
                + "     Incorrect: " + result.getIncorrectCount()
                + "     Unanswered: " + result.getUnansweredCount());

        tableModel.setRowCount(0);
        List<Question> questions = result.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            int answer = result.getAnswer(i);

            String yourAnswer = answer < 0 ? "(not answered)" : question.getOption(answer);
            String outcome;
            if (answer < 0) {
                outcome = "Unanswered";
            } else if (answer == question.getCorrectIndex()) {
                outcome = "Correct";
            } else {
                outcome = "Wrong";
            }
            tableModel.addRow(new Object[]{
                    i + 1, question.getText(), yourAnswer,
                    question.getOption(question.getCorrectIndex()), outcome});
        }
    }
}
