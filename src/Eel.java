import java.awt.*;
public class Eel {
    public Image pic;
    public int dx = 2;
    public int dy = 0;
    public int xpos ;
    public int ypos ;
    public int width = 35;
    public int height = 35;
    public boolean isAlive = true;
    public Rectangle eelhit;
    public boolean upIsPressed;
    public boolean downIsPressed;
    public boolean rightIsPressed;
    public boolean leftIsPressed;
    public Rectangle above;
    public Rectangle below;
    public Rectangle left;
    public Rectangle right;




    public Eel(int paramXpos, int paramYpos) {
        xpos = paramXpos;
        ypos = paramYpos;
        eelhit = new Rectangle(xpos, ypos, width, height);
        above = new Rectangle(xpos, ypos+10, width, height);
        below = new Rectangle(xpos, ypos-10, width, height);
        right = new Rectangle(xpos+10, ypos, width, height);
        left = new Rectangle(xpos-10, ypos, width, height);
        isAlive = true;


    }

    public void movies() {
        xpos = xpos + dx;
        ypos = ypos + dy;

        if (upIsPressed == true) {
            dy = -2;
            dx=0;
        } else if (downIsPressed) {
            dy = 2;
            dx=0;
        }
        if (leftIsPressed == true) {
            dx=-2;
            dy=0;
        } else if (rightIsPressed) {
            dx=2;
            dy=0;
        }


        // top of the frame
        if (ypos < 0) {
            dy = -dy;
        }

        //bottom of the frame
        if (ypos > 700 - height){
            dy = -dy;

        }
        // right of the frame
        if (xpos > 955 -width) {
            dx = -dx;
        }

        //left of the frame
        if (xpos < 0){
            dx = -dx;
        }
        eelhit = new Rectangle(xpos, ypos, width, height);
        above = new Rectangle(xpos, ypos+10, width, height);
        below = new Rectangle(xpos, ypos-10, width, height);
        right = new Rectangle(xpos+10, ypos, width, height);
        left = new Rectangle(xpos-10, ypos, width, height);

    }
    public void followerRectangleUpdate() {
        eelhit = new Rectangle(xpos, ypos, width, height);
        above = new Rectangle(xpos, ypos+10, width, height);
        below = new Rectangle(xpos, ypos-10, width, height);
        right = new Rectangle(xpos+10, ypos, width, height);
        left = new Rectangle(xpos-10, ypos, width, height);
    }

}


