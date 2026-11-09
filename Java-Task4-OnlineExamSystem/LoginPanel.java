import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * Screen 1: username and password. On success the profile screen opens.
 */
public class LoginPanel extends JPanel {

    private final ExamApp app;
    private final JTextField usernameField = new JTextField(16);
    private final JPasswordField passwordField = new JPasswordField(16);
    private final JLabel messageLabel = new JLabel(" ");

    public LoginPanel(ExamApp app) {
        this.app = app;
        setLayout(new GridBagLayout());

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(24, 36, 24, 36)));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Online Examination System", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        form.add(title, c);

        JLabel subtitle = new JLabel("Please log in to continue", JLabel.CENTER);
        c.gridy = 1;
        form.add(subtitle, c);

        c.gridwidth = 1;
        c.gridy = 2;
        c.gridx = 0;
        form.add(new JLabel("Username:"), c);
        c.gridx = 1;
        form.add(usernameField, c);

        c.gridy = 3;
        c.gridx = 0;
        form.add(new JLabel("Password:"), c);
        c.gridx = 1;
        form.add(passwordField, c);

        JButton loginButton = new JButton("Login");
        c.gridy = 4;
        c.gridx = 0;
        c.gridwidth = 2;
        form.add(loginButton, c);

        messageLabel.setForeground(new Color(0xC62828));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        c.gridy = 5;
        form.add(messageLabel, c);

        JLabel hint = new JLabel("Demo logins: priscilla / java123   or   student / 1234",
                JLabel.CENTER);
        hint.setForeground(Color.GRAY);
        hint.setFont(new Font("SansSerif", Font.ITALIC, 11));
        c.gridy = 6;
        form.add(hint, c);

        add(form);

        loginButton.addActionListener(e -> attemptLogin());
        passwordField.addActionListener(e -> attemptLogin());
        usernameField.addActionListener(e -> passwordField.requestFocusInWindow());
    }

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Enter both username and password.");
            return;
        }
        if (app.login(username, password)) {
            messageLabel.setText(" ");
            app.showProfile();
        } else {
            messageLabel.setText("Invalid username or password.");
            passwordField.setText("");
        }
    }

    /** Clears the form, used when a user logs out. */
    public void reset() {
        usernameField.setText("");
        passwordField.setText("");
        messageLabel.setText(" ");
    }
}
