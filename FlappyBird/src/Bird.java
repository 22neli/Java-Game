import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Bird {
    public float x, y, vx, vy; //window and grinch movement coordinates
    public static final int size = 30; //size of the grinch
    private Image img;
    public Bird() {
        x = FlappyBird.WIDTH;
        y = FlappyBird.HEIGHT;
        try {
            img = ImageIO.read(new File("The-Grinch-PNG-File.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void physics() {
        x+=vx; //bird movements horizontally
        y+=vy; //bird movements vertically
        vy+=0.7f; //jumping distance(effects motion of the grinch)
    }
   public void update(Graphics g) { //adding graphic components for the movement of grinch later on

       g.drawImage(img, Math.round(x-size),Math.round(y-size),2*size,2*size, null);
       //method for coordinate space of the grinch inside rectangle (game window)
   }
    public void jump()
    {
        vy = -6; //update jumping time
    }
    public void reset() {
        x = 640/2; //starting position of grinch
        y = 640/2;
        vx = vy = 0; //start from beginning
    }
}