import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    private String question;
    private String[] options;
    private char correctAnswer;

    public Question(String question, String[] options, char correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = Character.toUpperCase(correctAnswer);
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }
}

class QuizGameGUI extends JFrame {
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int timeLeft = 10; // 10 seconds per question
    private Timer timer;

    private JLabel questionLabel;
    private JButton[] optionButtons = new JButton[4];
    private JLabel timerLabel;
    private JLabel scoreLabel;

    public QuizGameGUI() {
        // Sample questions
        questions = Arrays.asList(
            new Question("What is the capital of France?", new String[]{"Paris", "London", "Rome", "Berlin"}, 'A'),
            new Question("Which programming language is used for Android development?", new String[]{"Python", "Java", "C#", "Swift"}, 'B'),
            new Question("Who wrote 'Hamlet'?", new String[]{"Shakespeare", "Dickens", "Hemingway", "Austen"}, 'A')
        );

        // GUI Setup
        setTitle("Quiz Game");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        // UI Components
        questionLabel = new JLabel("Question", JLabel.CENTER);
        add(questionLabel);

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            add(optionButtons[i]);
            final int index = i;
            optionButtons[i].addActionListener(e -> checkAnswer((char) ('A' + index)));
        }

        timerLabel = new JLabel("Time left: 10", JLabel.CENTER);
        add(timerLabel);

        scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
        add(scoreLabel);

        // Load First Question
        loadNextQuestion();
        setVisible(true);
    }

    private void loadNextQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            showFinalScore();
            return;
        }

        Question q = questions.get(currentQuestionIndex);
        questionLabel.setText(q.getQuestion());
        String[] options = q.getOptions();
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText((char) ('A' + i) + ". " + options[i]);
            optionButtons[i].setEnabled(true);
        }

        timeLeft = 10;
        startTimer();
    }

    private void startTimer() {
        if (timer != null) {
            timer.cancel();
        }

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (timeLeft > 0) {
                    timeLeft--;
                    timerLabel.setText("Time left: " + timeLeft);
                } else {
                    timer.cancel();
                    JOptionPane.showMessageDialog(null, "Time's up!");
                    nextQuestion();
                }
            }
        }, 1000, 1000);
    }

    private void checkAnswer(char selectedOption) {
        timer.cancel();
        Question q = questions.get(currentQuestionIndex);

        if (selectedOption == q.getCorrectAnswer()) {
            score++;
            JOptionPane.showMessageDialog(null, "Correct!");
        } else {
            JOptionPane.showMessageDialog(null, "Wrong! Correct answer: " + q.getCorrectAnswer());
        }

        nextQuestion();
    }

    private void nextQuestion() {
        currentQuestionIndex++;
        scoreLabel.setText("Score: " + score);
        loadNextQuestion();
    }

    private void showFinalScore() {
        JOptionPane.showMessageDialog(null, "Quiz Over! Your score: " + score + "/" + questions.size());
        System.exit(0);
    }
}

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizGameGUI::new);
    }
}
