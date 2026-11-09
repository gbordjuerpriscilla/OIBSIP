import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Entry point: starts the Online Examination System on the Swing event thread.
 *
 * Demo logins:
 *   priscilla / java123
 *   student   / 1234
 */
public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // fall back to the default look and feel
        }
        SwingUtilities.invokeLater(() -> new ExamApp().setVisible(true));
    }
}
