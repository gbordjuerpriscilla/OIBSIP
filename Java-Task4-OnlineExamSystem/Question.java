/**
 * One multiple-choice question with four options and one correct answer.
 */
public class Question {

    private final String text;
    private final String[] options;
    private final int correctIndex;

    public Question(String text, String[] options, int correctIndex) {
        this.text = text;
        this.options = options.clone();
        this.correctIndex = correctIndex;
    }

    public String getText() {
        return text;
    }

    public String getOption(int index) {
        return options[index];
    }

    public int getOptionCount() {
        return options.length;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }
}
