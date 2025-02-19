import java.util.*;

class Question {
    private String question;
    private String[] options;
    private char correctAnswer;

    public Question(String question, String[] options, char correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = Character.toUpperCase(correctAnswer);
    }

    public void displayQuestion() {
        System.out.println(question);
        for (int i = 0; i < options.length; i++) {
            System.out.println((char) ('A' + i) + ". " + options[i]);
        }
    }

    public boolean checkAnswer(char answer) {
        return Character.toUpperCase(answer) == correctAnswer;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }
}

class QuizGame {
    private List<Question> questions;
    private int score = 0;
    private static final int TIMEOUT = 10; // 10 seconds

    public QuizGame() {
        questions = Arrays.asList(
            new Question("What is the capital of France?", new String[]{"Paris", "London", "Rome", "Berlin"}, 'A'),
            new Question("Which programming language is used for Android development?", new String[]{"Python", "Java", "C#", "Swift"}, 'B'),
            new Question("Who wrote 'Hamlet'?", new String[]{"Shakespeare", "Dickens", "Hemingway", "Austen"}, 'A'),
            new Question("What is the chemical symbol for water?", new String[]{"H2O", "CO2", "O2", "NaCl"}, 'A'),
            new Question("How many continents are there?", new String[]{"5", "6", "7", "8"}, 'C'),
            new Question("Which planet is known as the Red Planet?", new String[]{"Earth", "Venus", "Mars", "Jupiter"}, 'C'),
            new Question("What is the square root of 64?", new String[]{"6", "7", "8", "9"}, 'C'),
            new Question("Who painted the Mona Lisa?", new String[]{"Van Gogh", "Da Vinci", "Picasso", "Rembrandt"}, 'B'),
            new Question("What is the largest ocean on Earth?", new String[]{"Atlantic", "Indian", "Arctic", "Pacific"}, 'D'),
            new Question("Which gas do plants absorb from the atmosphere?", new String[]{"Oxygen", "Nitrogen", "Carbon Dioxide", "Helium"}, 'C'),
            new Question("What is the capital of Japan?", new String[]{"Seoul", "Beijing", "Tokyo", "Bangkok"}, 'C'),
            new Question("Which element has the atomic number 1?", new String[]{"Helium", "Oxygen", "Hydrogen", "Carbon"}, 'C'),
            new Question("What is the hardest natural substance on Earth?", new String[]{"Gold", "Iron", "Diamond", "Platinum"}, 'C'),
            new Question("Which planet is closest to the Sun?", new String[]{"Venus", "Earth", "Mercury", "Mars"}, 'C'),
            new Question("What is the national animal of India?", new String[]{"Tiger", "Elephant", "Lion", "Peacock"}, 'A'),
            new Question("Which country is famous for its tulips?", new String[]{"France", "Netherlands", "Italy", "Germany"}, 'B'),
            new Question("What is the longest river in the world?", new String[]{"Amazon", "Nile", "Yangtze", "Mississippi"}, 'B'),
            new Question("Who discovered gravity?", new String[]{"Einstein", "Newton", "Galileo", "Tesla"}, 'B'),
            new Question("Which is the smallest country in the world?", new String[]{"Vatican City", "Monaco", "Maldives", "Liechtenstein"}, 'A'),
            new Question("What is the chemical symbol for gold?", new String[]{"Au", "Ag", "Pb", "Fe"}, 'A'),
            new Question("Which sport is known as the 'king of sports'?", new String[]{"Cricket", "Tennis", "Soccer", "Basketball"}, 'C'),
            new Question("Which is the tallest mountain in the world?", new String[]{"K2", "Mount Everest", "Kangchenjunga", "Makalu"}, 'B')
        );
    }

    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Quiz Game!");
        System.out.println("----------------------------");
        System.out.println("You have " + TIMEOUT + " seconds to answer each question.\n");

        for (Question question : questions) {
            question.displayQuestion();
            char userAnswer = getUserAnswer(scanner);

            if (userAnswer == '\0') {
                System.out.println("\nTime's up! Moving to the next question.\n");
                continue; // Skip the question if time runs out
            }

            if (question.checkAnswer(userAnswer)) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("Wrong! The correct answer was " + question.getCorrectAnswer() + ".\n");
            }
        }

        System.out.println("Quiz Over! Your final score: " + score + "/" + questions.size());
        scanner.close();
    }

    private char getUserAnswer(Scanner scanner) {
        final char[] answer = {'\0'};
        Thread inputThread = new Thread(() -> {
            try {
                System.out.print("Your answer (A/B/C/D): ");
                String userInput = scanner.nextLine().trim().toUpperCase();
                if (!userInput.isEmpty()) {
                    answer[0] = userInput.charAt(0);
                }
            } catch (Exception e) {
                System.out.println("Input error.");
            }
        });

        inputThread.start();
        try {
            inputThread.join(TIMEOUT * 1000); // Wait for the thread to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (inputThread.isAlive()) {
            inputThread.interrupt();
            return '\0'; // Return null character if time runs out
        }
        return answer[0];
    }
}

public class Main {
    public static void main(String[] args) {
        new QuizGame().startQuiz();
    }
}