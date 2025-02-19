import javax.swing.*;
import java.awt.*;
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
        // List of 30 questions
        questions = Arrays.asList(
                new Question("What is the capital of France?", new String[]{"Paris", "London", "Rome", "Berlin"}, 'A'),
                new Question("Which planet is known as the Red Planet?", new String[]{"Earth", "Mars", "Jupiter", "Venus"}, 'B'),
                new Question("What is the square root of 64?", new String[]{"6", "8", "10", "12"}, 'B'),
                new Question("Who developed the theory of relativity?", new String[]{"Newton", "Einstein", "Tesla", "Hawking"}, 'B'),
                new Question("What is the chemical symbol for gold?", new String[]{"Au", "Ag", "Pb", "Fe"}, 'A'),
                new Question("Which gas do plants absorb from the atmosphere?", new String[]{"Oxygen", "Carbon Dioxide", "Nitrogen", "Helium"}, 'B'),
                new Question("Who painted the Mona Lisa?", new String[]{"Van Gogh", "Picasso", "Da Vinci", "Michelangelo"}, 'C'),
                new Question("Which country is famous for the Great Wall?", new String[]{"India", "China", "Japan", "Russia"}, 'B'),
                new Question("What is the largest ocean on Earth?", new String[]{"Atlantic", "Pacific", "Indian", "Arctic"}, 'B'),
                new Question("Who discovered gravity?", new String[]{"Newton", "Einstein", "Galileo", "Copernicus"}, 'A'),
                new Question("What is the longest river in the world?", new String[]{"Amazon", "Nile", "Yangtze", "Mississippi"}, 'B'),
                new Question("What is the boiling point of water in Celsius?", new String[]{"90", "100", "110", "120"}, 'B'),
                new Question("Which element has the chemical symbol 'H'?", new String[]{"Helium", "Hydrogen", "Hassium", "Hafnium"}, 'B'),
                new Question("What is the national language of Japan?", new String[]{"Mandarin", "Korean", "Japanese", "Thai"}, 'C'),
                new Question("Who invented the telephone?", new String[]{"Edison", "Bell", "Tesla", "Morse"}, 'B'),
                new Question("Which continent has the most countries?", new String[]{"Africa", "Asia", "Europe", "South America"}, 'A'),
                new Question("Which bird is known as the king of birds?", new String[]{"Sparrow", "Peacock", "Eagle", "Ostrich"}, 'C'),
                new Question("What is the smallest unit of life?", new String[]{"Tissue", "Organ", "Cell", "Organism"}, 'C'),
                new Question("What is the chemical formula of water?", new String[]{"CO2", "O2", "H2O", "NaCl"}, 'C'),
                new Question("Who was the first man to step on the moon?", new String[]{"Buzz Aldrin", "Yuri Gagarin", "Neil Armstrong", "Michael Collins"}, 'C'),
                new Question("What is the fastest land animal?", new String[]{"Cheetah", "Lion", "Tiger", "Horse"}, 'A'),
                new Question("Which country is known as the Land of the Rising Sun?", new String[]{"China", "Japan", "South Korea", "Vietnam"}, 'B'),
                new Question("What is the hardest natural substance on Earth?", new String[]{"Gold", "Iron", "Diamond", "Platinum"}, 'C'),
                new Question("Who wrote 'Hamlet'?", new String[]{"Shakespeare", "Dickens", "Hemingway", "Austen"}, 'A'),
                new Question("Which blood type is known as the universal donor?", new String[]{"O-", "A+", "B-", "AB+"}, 'A'),
                new Question("What is the main gas found in Earth's atmosphere?", new String[]{"Oxygen", "Hydrogen", "Nitrogen", "Carbon Dioxide"}, 'C'),
                new Question("Which is the longest bone in the human body?", new String[]{"Femur", "Tibia", "Fibula", "Humerus"}, 'A'),
                new Question("How many continents are there on Earth?", new String[]{"5", "6", "7", "8"}, 'C'),
                new Question("What is the currency of the United Kingdom?", new String[]{"Euro", "Pound", "Dollar", "Franc"}, 'B')
        );

        // GUI Setup
        setTitle("Quiz Game");
        setSize(500, 400);
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



    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(QuizGameGUI::new);
    }
}
