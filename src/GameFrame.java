import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame() {

        /* Creating a new instance of GamePanel directly without creating variable
           and adding that instance directly to JFrame using add method   */
        this.add(new GamePanel());
        this.setTitle("SNAKE_GAME");
        this.setVisible(true);
        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Always keep this statement last otherwise opens @unpredictable
        this.setLocationRelativeTo(null);
    }
}
