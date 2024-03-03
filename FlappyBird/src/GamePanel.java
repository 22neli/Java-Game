import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel {
    private Bird bird;
    private ArrayList<Rectangle> rects;
    private FlappyBird fb;
    private Font scoreFont, pauseFont;
  public static final Color bg = new Color(107, 73, 73, 255); //bg color

    public static final int CANDY_W = 50, CANDY_H = 56;
    private Image CandyHead, CandyLength;

    public GamePanel(FlappyBird fb, Bird bird, ArrayList<Rectangle> rects) {
        this.fb = fb; //var of the class
        this.bird = bird;
        this.rects = rects;
        scoreFont = new Font("SansSerif", Font.ITALIC, 20);
        pauseFont = new Font("Times New Roman", Font.BOLD, 50);

        try {
            CandyHead = ImageIO.read(new File("Reindeer.png"));
            CandyLength = ImageIO.read(new File("candy.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void paintComponent(Graphics g) {
     g.setColor(bg); //bg has color
     g.fillRect(0,0,FlappyBird.WIDTH,FlappyBird.HEIGHT);
     //fills rectangle coordinates -> grinch movements and obstacles are separated
      bird.update(g); //grinch shows up on screen

         for(Rectangle r : rects) {
            Graphics2D g2d = (Graphics2D) g;//class to gain more control over transformations
           AffineTransform old = g2d.getTransform();
           //linear mapping of coordinates after movement of the grinch -> returns copy of current transformation
            g2d.translate(r.x+CANDY_W/2, r.y+CANDY_H/2); //new coordinate corresponds to origin
           if (r.y < FlappyBird.HEIGHT/2) {  //y coordinate of window<height of the grinch
               g2d.translate(0, r.height);  //obstacles are set in their respective places
               g2d.rotate(Math.PI); //obstacles are facing each other
            }
          g2d.drawImage(CandyHead, -CANDY_W/2, -CANDY_H/2, GamePanel.CANDY_W, GamePanel.CANDY_H, null);
           //method with random changes to the obstacles and reindeer is in the middle of the candy
             g2d.drawImage(CandyLength, -CANDY_W/2, CANDY_H/2, GamePanel.CANDY_W, r.height, null);
             //position of the candy
           g2d.setTransform(old); //overwrites obstacle transformation
        }
        g.setFont(scoreFont);
        g.setColor(Color.BLACK);
        g.drawString("Your Score: "+fb.getScore(), 15, 20);

        if(fb.paused()) {
            g.setFont(pauseFont);
            g.setColor(new Color(0, 0, 0, 255));
            g.drawString("PAUSED", FlappyBird.WIDTH/3, FlappyBird.HEIGHT/3);
            //size and position of text so they don't overwrite each other
            g.drawString("Press SPACE to start", FlappyBird.WIDTH/2-198, FlappyBird.HEIGHT/2+45);
        }
    }
}