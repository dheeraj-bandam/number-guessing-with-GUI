import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGuessingGameGUI extends JFrame {
    private int numberToGuess;
    private int attemptsLeft;
    private int maxAttempts;
    private int score;
    private int rangeMax;

    private JLabel messageLabel = new JLabel("Select difficulty and start the game!");
    private JTextField guessField = new JTextField(10);
    private JButton guessButton = new JButton("Guess");
    private JButton startButton = new JButton("Start Game");
    private JComboBox<String> levelSelector = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});

    public NumberGuessingGameGUI() {
        setTitle("🎯 Number Guessing Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 250);
        setLayout(new FlowLayout());

        add(levelSelector);
        add(startButton);
        add(new JLabel("Your Guess:"));
        add(guessField);
        add(guessButton);
        add(messageLabel);

        guessButton.setEnabled(false);

        startButton.addActionListener(e -> startGame());
        guessButton.addActionListener(e -> makeGuess());

        setLocationRelativeTo(null); // center window
        setVisible(true);
    }

    private void startGame() {
        String level = (String) levelSelector.getSelectedItem();
        switch (level) {
            case "Easy" -> { rangeMax = 50; maxAttempts = 10; }
            case "Medium" -> { rangeMax = 100; maxAttempts = 7; }
            case "Hard" -> { rangeMax = 200; maxAttempts = 5; }
        }
        numberToGuess = new Random().nextInt(rangeMax) + 1;
        attemptsLeft = maxAttempts;
        score = 100;
        messageLabel.setText("Guess a number between 1 and " + rangeMax);
        guessButton.setEnabled(true);
        guessField.setText("");
    }

    private void makeGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());
            attemptsLeft--;

            if (guess < numberToGuess) {
                score -= (100 / maxAttempts);
                messageLabel.setText("Too low! Attempts left: " + attemptsLeft);
            } else if (guess > numberToGuess) {
                score -= (100 / maxAttempts);
                messageLabel.setText("Too high! Attempts left: " + attemptsLeft);
            } else {
                score += (attemptsLeft >= maxAttempts / 2) ? 10 : 0;
                messageLabel.setText("🎉 Correct! Score: " + score);
                guessButton.setEnabled(false);
                return;
            }

            if (attemptsLeft == 0) {
                messageLabel.setText("💀 Out of attempts! Number was " + numberToGuess);
                guessButton.setEnabled(false);
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Enter a valid number!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NumberGuessingGameGUI::new);
    }
}
