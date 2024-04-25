import org.w3c.dom.css.Rect;

import java.awt.*;


public class Egg {
    public Image pic;
    public int dx;
    public int dy;
    public int xpos ;
    public int ypos;
    public int width = 30;
    public int height = 30;
    public boolean isAlive = true;
    public Rectangle hitbox;

    public boolean isCrashing;


    public Egg(){
        xpos = (int)(Math.random()*950);
        ypos = (int)(Math.random()*680);
        hitbox = new Rectangle(xpos, ypos, width, height);
        isCrashing = false;

    }

    public void move(){
        xpos = xpos + dx;
        ypos = ypos + dy;

        if (ypos < 0 || ypos + height > 700) {
            dy = -dy;
        }

        if (xpos < 0 || xpos + width > 1050){
            dx = -dx;
        }
        hitbox = new Rectangle(xpos, ypos, width, height);

    }
}
