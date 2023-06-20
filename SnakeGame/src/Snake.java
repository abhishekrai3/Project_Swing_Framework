import javax.swing.*;

public class Snake extends JFrame {
    Board board;
    Snake(){
        board= new Board();
        // board visible because it is behave as  JFrame
        add(board);
        // pack parent component to childern component resize by forcing of parent
        pack();
        // we have to use setresizable with pack();
        setResizable(false);
        setVisible(true);

    }

    public static void main(String[] args){
        // initialise sanke
        Snake SnakeGame= new Snake();
    }
}
