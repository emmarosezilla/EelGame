import java.awt.*;

public class Character {
    public String name;
    public int xpos;
    public int ypos;
    public int dx = 2;
    public int dy = 2;
    public boolean isAlive = true;
    public int height = 130;
    public int width = 130;
    public boolean upIsPressed;
    public boolean downIsPressed;
    public boolean rightIsPressed;
    public boolean leftIsPressed;
    public Rectangle sharkhit;
    public boolean isCollided;



    public Character (String paramName, int paramXpos, int paramYpos){
        name = paramName;
        xpos = paramXpos;
        ypos = paramYpos;
        sharkhit = new Rectangle(xpos, ypos, width, height);
    }

    public void move(){
        xpos = xpos + dx;
        ypos = ypos + dy;

        // top of the frame
        if (ypos < 0) {
            dy = -dy;
        }

        //bottom of the frame
        if (ypos > 700 - height){
            dy = -dy;

        }
        // right of the frame
        if (xpos > 1000 -width) {
            dx = -dx;
        }

        //left of the frame
        if (xpos < 0){
            dx = -dx;
        }

        sharkhit = new Rectangle(xpos, ypos, width, height);

    }



}
