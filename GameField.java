import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = false;  // Изменено, чтобы начать игру только после нажатия клавиши
    private int timerDelay = 250;

    public GameField() {
        setBackground(Color.black);
        loadImages();
        initGame();
        setFocusable(true);

        // Добавим фокус на компоненте при создании
        requestFocusInWindow();

        // Добавим слушатель клавиш на весь компонент, чтобы реагировать на нажатия вне зависимости от положения фокуса
        addKeyListener(new FieldKeyListener());

        timer = new Timer(timerDelay, this);
        timer.start();
        createApple();
    }



    public void initGame() {
        dots = 3;

        int startX = SIZE / 2;
        int startY = SIZE / 2;

        for (int i = 0; i < dots; i++) {
            x[i] = startX - i * DOT_SIZE;
            y[i] = startY;
        }

        timer = new Timer(timerDelay, this);
        createApple();

        // Установим фокус на компоненте
        requestFocusInWindow();
    }


    public void setTimerDelay(int delay) {
        timerDelay = delay;
        timer.setDelay(timerDelay);
    }

    public void createApple() {
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("C:\\Users\\ПК\\Downloads\\Курсач работающий\\snakee-main_mirror_h_v_ask_me\\snakee-main\\apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("C:\\Users\\ПК\\Downloads\\Курсач работающий\\snakee-main_mirror_h_v_ask_me\\snakee-main\\dot.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {
            String str = "Press any key to start";
            g.setColor(Color.white);
            g.drawString(str, 80, SIZE / 2);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }

        // Отражение от стенок
        if (x[0] >= SIZE) {
            x[0] = 0;
        }
        if (x[0] < 0) {
            x[0] = SIZE - DOT_SIZE;
        }
        if (y[0] >= SIZE) {
            y[0] = 0;
        }
        if (y[0] < 0) {
            y[0] = SIZE - DOT_SIZE;
        }

        // Изменение направления при отражении
        if (x[0] == 0 && left) {
            left = false;
            right = true;
        }
        if (x[0] == SIZE - DOT_SIZE && right) {
            left = true;
            right = false;
        }
        if (y[0] == 0 && up) {
            up = false;
            down = true;
        }
        if (y[0] == SIZE - DOT_SIZE && down) {
            up = true;
            down = false;
        }
    }



    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }

    public void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();

            // Начало игры при нажатии любой клавиши
            if (!inGame) {
                inGame = true;
                return;
            }

            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_UP && !down) {
                right = false;
                up = true;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                right = false;
                down = true;
                left = false;
            }
        }
    }
}
