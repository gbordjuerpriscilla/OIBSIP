import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * Screen 2: lets the student change their display name and password
 * before starting the exam.
 */
public class ProfilePanel extends JPanel {

    private static final Color ERROR_COLOR = new Color(0xC62828);
    private static final Color SUCCESS_COLOR = new Color(0x2E7D32);

    private final ExamApp app;
    private final JLabel welcomeLabel = new JLabel(" ", JLabel.CENTER);
    private final JTextField nameField = new JTextField(18);
    private final JPasswordField newPasswordField = new JPasswordField(18);
    private final JPasswordField confirmField = new JPasswordField(18);
    private final JLabel messageLabel = new JLabel(" ", JLabel.CENTER);

    public ProfilePanel(ExamApp app) {
        this.app = app;
        setLayout(new GridBagLayout());

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(24, 36, 24, 36)));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        form.add(welcomeLabel, c);

        JLabel info = new JLabel("Update your profile before starting (optional).", JLabel.CENTER);
        c.gridy = 1;
        form.add(info, c);

        addRow(form, 2, "Display name:", nameField);
        addRow(form, 3, "New password:", newPasswordField);
        addRow(form, 4, "Confirm password:", confirmField);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        form.add(messageLabel, c);

        JButton saveButton = new JButton("Save Changes");
        JButton startButton = new JButton("Start Exam");
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttons.add(saveButton);
        buttons.add(startButton);
        c.gridy = 6;
        form.add(buttons, c);

        add(form);

        saveButton.addActionListener(e -> saveChanges());
        startButton.addActionListener(e -> app.startExam());
    }

    private void addRow(JPanel form, int row, String labelText, JComponent field) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = row;
        c.gridx = 0;
        form.add(new JLabel(labelText), c);
        c.gridx = 1;
        form.add(field, c);
    }

    /** Fills the form with the logged-in user's details. */
    public void load(User user) {
        welcomeLabel.setText("Welcome, " + user.getDisplayName() + "!");
        nameField.setText(user.getDisplayName());
        newPasswordField.setText("");
        confirmField.setText("");
        showMessage(" ", false);
    }

    private void saveChanges() {
        User user = app.getCurrentUser();
        String name = nameField.getText().trim();
        String newPassword = new String(newPasswordField.getPassword());
        String confirm = new String(confirmField.getPassword());

        // Validate everything first so nothing is half-saved
        if (name.isEmpty()) {
            showMessage("Display name cannot be empty.", true);
            return;
        }
        if (!newPassword.isEmpty()) {
            if (newPassword.length() < 4) {
                showMessage("Password must be at least 4 characters.", true);
                return;
            }
            if (!newPassword.equals(confirm)) {
                showMessage("Passwords do not match.", true);
                return;
            }
            user.setPassword(newPassword);
        }
        user.setDisplayName(name);

        welcomeLabel.setText("Welcome, " + user.getDisplayName() + "!");
        newPasswordField.setText("");
        confirmField.setText("");
        showMessage("Profile updated successfully.", false);
    }

    private void showMessage(String text, boolean isError) {
        messageLabel.setForeground(isError ? ERROR_COLOR : SUCCESS_COLOR);
        messageLabel.setText(text);
    }
}
