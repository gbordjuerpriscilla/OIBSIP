import java.util.List;

/**
 * The outcome of one exam attempt: the questions, the student's answers,
 * the time taken and whether the exam was submitted automatically.
 * An answer of -1 means the question was left unanswered.
 */
public class ExamResult {

    private final List<Question> questions;
    private final int[] answers;
    private final int timeTakenSeconds;
    private final boolean autoSubmitted;

    public ExamResult(List<Question> questions, int[] answers,
                      int timeTakenSeconds, boolean autoSubmitted) {
        this.questions = questions;
        this.answers = answers.clone();
        this.timeTakenSeconds = timeTakenSeconds;
        this.autoSubmitted = autoSubmitted;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getAnswer(int questionIndex) {
        return answers[questionIndex];
    }

    public int getTotal() {
        return questions.size();
    }

    public int getCorrectCount() {
        int count = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (answers[i] == questions.get(i).getCorrectIndex()) {
                count++;
            }
        }
        return count;
    }

    public int getUnansweredCount() {
        int count = 0;
        for (int answer : answers) {
            if (answer < 0) {
                count++;
            }
        }
        return count;
    }

    /** Questions that were answered, but wrongly. */
    public int getIncorrectCount() {
        return getTotal() - getCorrectCount() - getUnansweredCount();
    }

    public int getTimeTakenSeconds() {
        return timeTakenSeconds;
    }

    public boolean isAutoSubmitted() {
        return autoSubmitted;
    }
}
