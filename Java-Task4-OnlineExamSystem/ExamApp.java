import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The main window. It owns the four screens (login, profile, exam, result)
 * and switches between them with a CardLayout.
 */
public class ExamApp extends JFrame {

    private static final String LOGIN_CARD = "login";
    private static final String PROFILE_CARD = "profile";
    private static final String EXAM_CARD = "exam";
    private static final String RESULT_CARD = "result";

    /** Exam length in seconds. 10 minutes by default; change to e.g. 30 to test auto-submit. */
    private static final int EXAM_DURATION_SECONDS = 10 * 60;

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);

    private final Map<String, User> users = new HashMap<>();
    private final List<Question> questions = buildQuestions();

    private final LoginPanel loginPanel;
    private final ProfilePanel profilePanel;
    private final ExamPanel examPanel;
    private final ResultPanel resultPanel;

    private User currentUser;

    public ExamApp() {
        super("Online Examination System");

        users.put("priscilla", new User("priscilla", "java123", "Priscilla"));
        users.put("student", new User("student", "1234", "Student"));

        loginPanel = new LoginPanel(this);
        profilePanel = new ProfilePanel(this);
        examPanel = new ExamPanel(this);
        resultPanel = new ResultPanel(this);

        cards.add(loginPanel, LOGIN_CARD);
        cards.add(profilePanel, PROFILE_CARD);
        cards.add(examPanel, EXAM_CARD);
        cards.add(resultPanel, RESULT_CARD);
        add(cards);

        // We handle the close button ourselves so we can ask for confirmation
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleClose();
            }
        });

        setSize(820, 580);
        setMinimumSize(new java.awt.Dimension(700, 500));
        setLocationRelativeTo(null);
        showLogin();
    }

    // ---------- Navigation ----------

    public void showLogin() {
        cardLayout.show(cards, LOGIN_CARD);
    }

    public void showProfile() {
        profilePanel.load(currentUser);
        cardLayout.show(cards, PROFILE_CARD);
    }

    public void startExam() {
        examPanel.start(currentUser, questions, EXAM_DURATION_SECONDS);
        cardLayout.show(cards, EXAM_CARD);
    }

    public void showResult(ExamResult result) {
        resultPanel.display(currentUser, result);
        cardLayout.show(cards, RESULT_CARD);
    }

    public void logout() {
        currentUser = null;
        loginPanel.reset();
        showLogin();
    }

    // ---------- Accounts ----------

    /** Returns true and remembers the user if the username and password match. */
    public boolean login(String username, String password) {
        User user = users.get(username.toLowerCase());
        if (user != null && user.checkPassword(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    // ---------- Window close ----------

    private void handleClose() {
        if (examPanel.isRunning()) {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to quit?\nYour exam will not be submitted.",
                    "Quit Exam", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (choice != JOptionPane.YES_OPTION) {
                return;
            }
            examPanel.stopTimer();
        }
        dispose();
        System.exit(0);
    }

    // ---------- Questions ----------

    private static List<Question> buildQuestions() {
        List<Question> list = new ArrayList<>();
        list.add(new Question("Which keyword is used to inherit from a class in Java?",
                new String[]{"implements", "inherits", "extends", "super"}, 2));
        list.add(new Question("Which of the following is NOT a primitive data type in Java?",
                new String[]{"int", "boolean", "char", "String"}, 3));
        list.add(new Question("What is the default value of an int instance variable?",
                new String[]{"0", "null", "1", "undefined"}, 0));
        list.add(new Question("Which collection does NOT allow duplicate elements?",
                new String[]{"ArrayList", "HashSet", "LinkedList", "Vector"}, 1));
        list.add(new Question("Which method is the entry point of a Java application?",
                new String[]{"run()", "main()", "start()", "init()"}, 1));
        list.add(new Question("Which keyword prevents a class from being extended?",
                new String[]{"static", "abstract", "final", "private"}, 2));
        list.add(new Question("Which Swing component lets the user type a single line of text?",
                new String[]{"JLabel", "JButton", "JPanel", "JTextField"}, 3));
        list.add(new Question("What does JVM stand for?",
                new String[]{"Java Variable Machine", "Java Virtual Machine",
                        "Java Verified Method", "Joint Virtual Machine"}, 1));
        list.add(new Question("Which exception is thrown when an integer is divided by zero?",
                new String[]{"NullPointerException", "NumberFormatException",
                        "ArithmeticException", "ArrayIndexOutOfBoundsException"}, 2));
        list.add(new Question("Which construct is used to handle exceptions in Java?",
                new String[]{"if-else", "switch", "for loop", "try-catch"}, 3));
        return list;
    }
}
