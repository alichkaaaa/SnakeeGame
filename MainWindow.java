import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(320, 345);
        setLocation(400, 400);

        GameField gameField = new GameField();

        // Добавьте следующие строки для ввода скорости с использованием диалогового окна
        String input = JOptionPane.showInputDialog("Введите скорость змейки (миллисекунды):");
        try {
            int speed = Integer.parseInt(input);
            gameField.setTimerDelay(speed);
        } catch (NumberFormatException e) {
            System.err.println("Ошибка ввода. Используется значение по умолчанию.");
        }

        add(gameField);

        // Убедимся, что фокус устанавливается после отображения окна
        setVisible(true);
        gameField.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow();
        });
    }
}




