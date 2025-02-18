import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuessTheNumberGame {
    private final JTextField inputField;
    private JLabel resultLabel;
    private JLabel messageLabel;
    private int numberToGuess;
    private int maxTries;
    private int numberOfTries;

    public GuessTheNumberGame() {
        JFrame frame = new JFrame("Угадай число");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Панель для ввода числа
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel promptLabel = new JLabel("Введите ваше число:");
        inputField = new JTextField(10);
        JButton guessButton = new JButton("Угадать");
        resultLabel = new JLabel("Попробуйте угадать число!");
        messageLabel = new JLabel();

        panel.add(promptLabel);
        panel.add(inputField);
        panel.add(guessButton);
        panel.add(resultLabel);
        panel.add(messageLabel);

        frame.add(panel, BorderLayout.CENTER);

        guessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guessNumber();
            }
        });

        // Создаем меню выбора уровня сложности
        String[] levels = {"Легкий", "Средний", "Сложный"};
        JComboBox<String> levelSelector = new JComboBox<>(levels);
        levelSelector.addActionListener(e -> setLevel(levelSelector.getSelectedIndex()));
        frame.add(levelSelector, BorderLayout.NORTH);

        frame.setVisible(true);

        // Начинаем игру
        setLevel(0);  // По умолчанию сложность "Легкий"
    }

    private void setLevel(int level) {
        switch (level) {
            case 0:
                maxTries = 10;
                numberToGuess = new Random().nextInt(50) + 1;
                break;
            case 1:
                maxTries = 8;
                numberToGuess = new Random().nextInt(100) + 1;
                break;
            case 2:
                maxTries = 5;
                numberToGuess = new Random().nextInt(200) + 1;
                break;
        }
        numberOfTries = 0;
        resultLabel.setText("Загадано число от 1 до " + (maxTries == 10 ? 50 : maxTries == 8 ? 100 : 200));
        messageLabel.setText("");
        inputField.setText("");
    }

    private void guessNumber() {
        try {
            int userGuess = Integer.parseInt(inputField.getText());
            numberOfTries++;

            if (userGuess < numberToGuess) {
                resultLabel.setText("Загаданное число больше!");
            } else if (userGuess > numberToGuess) {
                resultLabel.setText("Загаданное число меньше!");
            } else {
                resultLabel.setText("Поздравляю! Вы угадали число за " + numberOfTries + " попыток!");
                messageLabel.setText("Хотите сыграть снова?");
            }

            if (numberOfTries == maxTries && userGuess != numberToGuess) {
                resultLabel.setText("Вы не угадали. Загаданное число было " + numberToGuess);
                messageLabel.setText("Попробуйте снова!");
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Введите валидное число!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuessTheNumberGame::new);
    }
}
