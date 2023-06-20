import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int B_Height=400;
    int B_Width=400;
    int Max_Dots=1400;
    int Dot_Size=10;
    int Dots;
    int x[]= new int[Max_Dots];
    int y[]= new int[Max_Dots];
    Board(){
        TAdapter tAdapter= new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(B_Width,B_Height));
        setBackground(Color.BLUE);
        Init();
        loadImages();
    }
    //
    Image body, head, apple;
    Timer timer;
    boolean leftDir=true;
    boolean rightDir= false;
    boolean upDir= false;
    boolean downDir= false;
    int DELAY=110;

    boolean inGame= true;
    // init game()
    public void Init(){
        Dots=3;
        // init snake pointer
        x[0]=250;
        y[0]=250;
        for(int i=0;i<Dots;i++){
            x[i]= x[0]+Dot_Size*i;
            y[i]=y[0];
        }
        locateApple();
        timer= new Timer(DELAY, this);
        timer.start();
    }
    // init apple position
    int apple_x;
    int  apple_y;
    // load images from resources folder
    public void loadImages(){
        ImageIcon dotIcon= new ImageIcon("src/resources/dot.png");
        body = dotIcon.getImage();
        ImageIcon headIcon= new ImageIcon("src/resources/head.png");
        head = headIcon.getImage();
        ImageIcon appleIcon= new ImageIcon("src/resources/apple.png");
        apple = appleIcon.getImage();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    //check collision
    public void checkCollision(){
        for(int i=1;i<Dots;i++){
            if(i>4&& x[0]==x[i]&& y[0]==y[i]){
                inGame=false;
            }
        }
        if(x[0]<0){
            inGame= false;
        }
        if(x[0]>=B_Height){
            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
        if(y[0]>=B_Width){
            inGame=false;
        }

    }
    public void gameOver(Graphics g){
        String str= "Game Over";
        int score= (Dots-3)*100;
        String scoreMsg= "Score :->"+ Integer.toString(score);
        Font small= new Font("Nelvetica", Font.BOLD, 14);
        FontMetrics fontMatrics= getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);

        g.drawString(str,(B_Width-fontMatrics.stringWidth(str))/2, B_Height/4);
        g.drawString(scoreMsg,(B_Width-fontMatrics.stringWidth(scoreMsg))/2,3*B_Height/4);
    }


    public void doDrawing(Graphics g){
        if(inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < Dots; i++) {
                if (i == 0) g.drawImage(head, x[0], y[0], this);
                else g.drawImage(body, x[i], y[i], this);
            }
        }
        else{
            gameOver(g);
            timer.stop();

        }
    }
    // randomize apples positon
    public void locateApple(){
        apple_x= (int)(Math.random()*39)*Dot_Size;
        apple_y= (int)(Math.random()*39)*Dot_Size;

    }

    public void actionPerformed(ActionEvent actionEvent){
        if(inGame) {
            checkFood();
            checkCollision();
            move();

        }
        repaint();
    }
    // make snake move
    public void move(){
        for(int i=Dots-1;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];

        }
        if(leftDir== true){
            x[0]=x[0]-Dot_Size;
        }
        if(rightDir){
            x[0]+=Dot_Size;

        }
        if(upDir){
            y[0]-=Dot_Size;
        }
        if(downDir){
            y[0]+=Dot_Size;
        }
    }
    // check food eaten
    public void checkFood(){
        if(apple_x==x[0]&&apple_y==y[0]){
            Dots++;
            locateApple();
        }
    }
    private  class TAdapter extends KeyAdapter{

        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if(key== keyEvent.VK_LEFT&&!rightDir){
                leftDir=true;
                upDir= false;
                downDir= false;
            }
            else if(key== keyEvent.VK_RIGHT&&!leftDir){
                rightDir= true;
                upDir= false;
                downDir= false;
            }
            else if(key== keyEvent.VK_UP&&!downDir){
                leftDir=false;
                rightDir= false;
                upDir= true;

            }
            else if(key== keyEvent.VK_DOWN&&!upDir){
                leftDir=false;
                rightDir= false;
                downDir= true;
            }

        }
    }

}