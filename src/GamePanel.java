import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;      // Increasing this values slows the game and vice-versa

    // Creating 2 arrays for locating the snake head and body
    static int[] x = new int[GAME_UNITS];
    static int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R'; //Initial direction of snake
    boolean running = false;
    Random random;
    Timer timer;    // this timer belongs to java Swing not the java Util;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        addApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if(running) {
//            // Drawing the matrix grid of game to show the unit pixels or grids
//            for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE; i++) {
//                g.drawLine(i*UNIT_SIZE,0, i*UNIT_SIZE,SCREEN_HEIGHT);
//                g.drawLine(0,i*UNIT_SIZE, SCREEN_WIDTH,i*UNIT_SIZE);
//            }

            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for(int i=0; i<bodyParts; i++) {
                if(i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else{
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            // For lining the text in the center of the screen, we use font metrics
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score : "+applesEaten, (SCREEN_WIDTH-metrics.stringWidth("Score : "+applesEaten))/2, g.getFont().getSize());
        }
        else {
            gameOver(g);
        }
    }

    public void addApple() {
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public void move() {
        for(int i=bodyParts; i>0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        if(x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            addApple();
        }
    }

    public void checkCollisions() {
        for(int i=bodyParts; i>0; i--) {
            if(x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }

        if(x[0] < 0)
            running = false;
        if(x[0] > SCREEN_WIDTH-UNIT_SIZE)
            running = false;
        if(y[0] < 0)
            running = false;
        if(y[0] > SCREEN_HEIGHT-UNIT_SIZE)
            running = false;

        if(!running)
            timer.stop();
    }

    public void gameOver(Graphics g) {
        // To display "SCORE" text
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.TRUETYPE_FONT, 40));
        // For lining the text in the center of the screen, we use font metrics
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score = "+applesEaten, (SCREEN_WIDTH-metrics1.stringWidth("Score = "+applesEaten))/2, SCREEN_WIDTH/2 + g.getFontMetrics().getHeight());

        // To display "GAME OVER" text
        g.setColor(Color.RED);
        g.setFont(new Font("Serif", Font.BOLD, 75));
        // For lining the text in the center of the screen, we use font metrics
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH-metrics2.stringWidth("GAME OVER"))/2, SCREEN_WIDTH/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
