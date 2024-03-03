import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
public class  FlappyBird implements ActionListener, KeyListener { //receives action and keyboard events
   public static final int FPS = 50, WIDTH = 640, HEIGHT = 500; //speed of the grinch+ size of game window
    private Bird bird;
    private JFrame frame;
    private JPanel panel;
    private ArrayList<Rectangle> rects; //panel enclosed game area
    private int distance, obstacleSpace;
    private Timer t; //increases the score for every event/distance flying

    private boolean paused;

    public void go() {
        frame = new JFrame("Welcome to Flappy Bird");
        bird = new Bird();
        rects = new ArrayList<Rectangle>();
        panel = new GamePanel(this, bird, rects);
        frame.add(panel);

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(this);

        paused = true; //initial state

        t = new Timer(1000/FPS, this);
        t.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.repaint();
        if(!paused) {
            bird.physics();
            if(obstacleSpace % 35 == 0) { //difference between obstacles horizontally
                //Below is the code for the difference between obstacles vertically => user's range of flying capacity
                Rectangle r = new Rectangle(WIDTH, 0, GamePanel.CANDY_W, (int) ((Math.random()*HEIGHT)/5f + (0.2f)*HEIGHT));
                //size and positioning of upper obstacles
                int h2 = (int) ((Math.random()*HEIGHT)/5f + (0.2f)*HEIGHT);
                //size and positioning of lower obstacles
                Rectangle r2 = new Rectangle(WIDTH, HEIGHT - h2, GamePanel.CANDY_W, h2);
                rects.add(r);
                rects.add(r2);
            }
            ArrayList<Rectangle> toRemove = new ArrayList<Rectangle>(); //game panel coordinate space
            boolean game = true;
            for(Rectangle r : rects) {
                r.x-=3; //difference between two objects next to each other
                if(r.x + r.width <= 0 ) {
                    /* less/equal to 0, so that the passed obstacles disappear after the width of Rectangle
                     and the X coordinate of the upper-left corner of the Rectangle */
                    toRemove.add(r);
                }
                if(r.contains(bird.x, bird.y)) { //test if coordinates are inside the class boundary
                    JOptionPane.showMessageDialog(frame, "Oops,Do Better Next Time!\n"+"Your score is: "+distance+" points.");
                    game = false;
                }
            }
            rects.removeAll(toRemove); //removes all previous elements to increment score and difference of subsequent obstacles
            distance++;
            obstacleSpace++;

            if(bird.y > HEIGHT || bird.y+bird.size < 0) {//grinch flies outside the rectangular borders
                game = false;
            }
              //game is lost and reset
            if(!game) {
                rects.clear();
                bird.reset();
                distance = 0;
                obstacleSpace = 0;
                paused = true;
            }
        }
        else {

        }
    }

    public int getScore() {

        return distance;
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP) { //grinch flies with the up arrow in the keyboard
            bird.jump();
        }
        else if(e.getKeyCode()==KeyEvent.VK_SPACE) { //game starts with the space key in the keyboard
            paused = false;
        }
    }
    public void keyReleased(KeyEvent e) {

    }
    public void keyTyped(KeyEvent e) {

    }

    public boolean paused() {
        return paused;
    }
    public static void main(String[] args) {

        new FlappyBird().go();

    }
}
