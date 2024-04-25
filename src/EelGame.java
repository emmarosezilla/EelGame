import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EelGame implements Runnable, KeyListener {
    final int WIDTH = 1000;
    final int HEIGHT = 700;
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;
    // public Character eelie;
    public Character sharkie;
    public boolean eelAndShark;
    public Image backgroundPic;
    public Image sharkPic;
    public Image startPic;
    public Eel[] eelies;
    public int numEels;
    public Egg egg;
    public Egg[] eggies;
    public SoundFile eatEggSound;
    public SoundFile sharkEatSound;
    public SoundFile backgroundMusic;
    public boolean gamePlaying = false;
    public boolean gameOver = false;

    public static void main(String[] args) {
        EelGame ex = new EelGame();
        new Thread(ex).start();
    }

    public EelGame() {
        setUpGraphics();
        canvas.addKeyListener(this);
        sharkie = new Character("sharkie", 200, 300);
        backgroundPic = Toolkit.getDefaultToolkit().getImage("background.png");
        sharkPic = Toolkit.getDefaultToolkit().getImage("shark.png");
        startPic = Toolkit.getDefaultToolkit().getImage("start image.png");
        eelies = new Eel[200];
        egg = new Egg();
        eelies[0] = new Eel(300, 100);

        for (int x = 1; x < eelies.length; x++) {
            eelies[x] = new Eel(eelies[x-1].right.x, eelies[x-1].right.y);
            eelies[x].pic = Toolkit.getDefaultToolkit().getImage("eelbody.png");
            eelies[x].isAlive = false;
        }
        eelies[0].isAlive = true;
        eelies[0].pic = Toolkit.getDefaultToolkit().getImage("eelbody.png");
        numEels = 1;
        egg.pic = Toolkit.getDefaultToolkit().getImage("egg.png");
        eatEggSound = new SoundFile("Comical Pop and Swirl.wav");
        sharkEatSound = new SoundFile("Chomp Sound.wav");
        backgroundMusic = new SoundFile("Background Music.wav");

    }

    public void run() {
        backgroundMusic.play();

        while (true) {
            if (gamePlaying == true) {
                moveThings();
                collision();
            }
            render();
            pause(10);
        }
    }

    public void moveThings() {
        sharkie.move();
        eelies[0].movies();

        for (int x = eelies.length - 1; x > 0; x--) { // each eel added takes the place of the eel before it
            eelies[x].xpos = eelies[x - 1].xpos;
            eelies[x].ypos = eelies[x - 1].ypos;
            eelies[x].eelhit = new Rectangle(eelies[x].xpos,eelies[x].ypos,eelies[x].width,eelies[x].height);

        }

        egg.move();


    }

    public void collision() {

        for (int x = 0; x < eelies.length; x++) { // for loop for when eel collides with egg
            if (eelies[x].eelhit.intersects(egg.hitbox) && eelies[x].isAlive &&
                    egg.isAlive ==true &&egg.isCrashing == false) {
                egg.isCrashing = true;
                eelies[numEels].isAlive = true;
                numEels+=5; // adds to the array, hence making the eel length longer
                eatEggSound.play();
                egg.isAlive = false;
                egg = new Egg();
                egg.pic = Toolkit.getDefaultToolkit().getImage("egg.png");
            }

            if (eelies[x].eelhit.intersects(egg.hitbox) == false){
                egg.isCrashing = false;
                egg.isAlive = true;
            }

        }
        for (int y = 0; y < eelies.length; y++) {   //below if statement is for when the eel collides with the shark
            if (eelies[y].eelhit.intersects(sharkie.sharkhit) && eelies[y].isAlive && sharkie.isAlive == true && sharkie.isCollided == false) {
                eelies[numEels].isAlive = false;
                sharkie.isCollided = true;
                sharkEatSound.play();
                gameOver = true; // makes it so that the game is over, able to restart
            }

            if (eelies[y].eelhit.intersects(sharkie.sharkhit) == false){
                sharkie.isCollided = false;
            }
        }
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.drawImage(backgroundPic, 0, 0, 1100, 800, null);

            // shows the number of points the user has obtained
            g.setColor(new Color(40, 92, 132));
            g.setFont(new Font("Hey Comic", Font.PLAIN, 20));
            g.drawString("Points: " + numEels/5, 900, 30);

            // start screen code
            if (gamePlaying == false) {
                g.drawImage(backgroundPic, 0, 0, 1100, 800, null);
                g.setColor(new Color(40, 92, 132));
                g.setFont(new Font("Hey Comic", Font.PLAIN, 40));
                g.drawString("PRESS THE SPACEBAR TO BEGIN!", 220, 100);
                g.drawImage(startPic, 420, 140, 150, 160, null);

            }
            else if (gamePlaying == true && gameOver == false) {
                g.drawImage(sharkPic, sharkie.xpos, sharkie.ypos, sharkie.width, sharkie.height, null);
                for (int x = 0; x< eelies.length; x++) {
                    if (eelies[x].isAlive == true) {
                        g.drawImage(eelies[x].pic, eelies[x].xpos, eelies[x].ypos, eelies[x].width, eelies[x].height, null);
                    }
                }
                if (egg.isAlive == true) {
                    g.drawImage(egg.pic, egg.xpos, egg.ypos, egg.width, egg.height, null);
                }

                else if (gameOver == true){
                    gamePlaying = false;
                    g.drawImage(backgroundPic, 0, 0, 1100, 800, null);
                    g.setColor(new Color(40, 92, 132));
                    g.setFont(new Font("Hey Comic", Font.PLAIN, 40));
                    g.drawString("PRESS THE SPACEBAR TO BEGIN!", 220, 100);
                    g.drawImage(startPic, 420, 140, 150, 160, null);
                }

            }
            // screen that prints when the game is over
            else {
                g.drawImage(backgroundPic, 0, 0, 1100, 800, null);
                g.setColor(new Color(40, 92, 132));
                g.setFont(new Font("Hey Comic", Font.PLAIN, 40));
                g.drawString("YOU WERE EATEN! TRY AGAIN!", 350, 100);
                g.drawString("Press the space bar to replay!", 300, 200);

            }
//        }


        g.dispose();
        bufferStrategy.show();
    }

    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        int keyCode = e.getKeyCode();
        if (keyCode == 87){
            eelies[0].upIsPressed = true;
        }
        if (keyCode == 83){
            eelies[0].downIsPressed = true;
        }
        if (keyCode == 68){
            eelies[0].rightIsPressed = true;
        }
        if (keyCode == 65){
            eelies[0].leftIsPressed = true;
        }

        if (gameOver == false){
            if (keyCode == 32){
                gamePlaying = true;
            }
        }

        // stipulates that if the space bar is pressed ONLY IF the game is over, it will re-start
        else if (gameOver == true) {
            if(keyCode == 32) {
                gameOver = false;
                gamePlaying = true;
                sharkie = new Character("sharkie", 200, 300);
                backgroundPic = Toolkit.getDefaultToolkit().getImage("background.png");
                sharkPic = Toolkit.getDefaultToolkit().getImage("shark.png");
                startPic = Toolkit.getDefaultToolkit().getImage("start image.png");
                eelies = new Eel[200];
                egg = new Egg();
                eelies[0] = new Eel(300, 100);
                for (int x = 1; x < eelies.length; x++) {
                    eelies[x] = new Eel(eelies[x - 1].right.x, eelies[x - 1].right.y);
                    eelies[x].pic = Toolkit.getDefaultToolkit().getImage("eelbody.png");
                    eelies[x].isAlive = false;
                }
                eelies[0].isAlive = true;
                eelies[0].pic = Toolkit.getDefaultToolkit().getImage("eelbody.png");
                numEels = 1;
                egg.pic = Toolkit.getDefaultToolkit().getImage("egg.png");
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        char key = e.getKeyChar();
        int keyCode = e.getKeyCode();
        if (keyCode == 87) {
            eelies[0].upIsPressed = false;
        }
        if (keyCode == 83){
            eelies[0].downIsPressed = false;
        }
        if (keyCode == 68){
            eelies[0].rightIsPressed = false;
        }
        if (keyCode == 65){
            eelies[0].leftIsPressed = false;
        }
    }

}