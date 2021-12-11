package cis152finalprogect;
//naifu Bai  nbai@dmacc.edu  12/10
import java.awt.*;
import javax.swing.*;

public class MazeFrame extends JFrame{

    private int canvasWidth;
    private int canvasHeight;

    public MazeFrame(String title, int canvasWidth, int canvasHeight){

        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        MazeCanvas canvas = new MazeCanvas();
        setContentPane(canvas);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setVisible(true);
    }

    public MazeFrame(String title){

        this(title, 1024, 768);
    }

    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    private MazeData data;
    public void render(MazeData data){
        this.data = data;
        repaint();
    }

    private class MazeCanvas extends JPanel{

        public MazeCanvas(){
            // Cache
            super(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D)g;


            // drawing
            int w = canvasWidth / data.M(); // width
            int h = canvasHeight / data.N();// Height

            for (int i = 0; i < data.N(); i ++){
                for (int j = 0; j < data.M(); j ++){
                    if (data.getMaze(i,j) == MazeData.WALL)
                        AlgoVisHelper.setColor(g2d, AlgoVisHelper.LightBlue);
                    else
                        AlgoVisHelper.setColor(g2d, AlgoVisHelper.White);
 
                    if (data.path[i][j] && data.showPath == true)
                        AlgoVisHelper.setColor(g2d, AlgoVisHelper.Yellow);
 
                    if (data.player.getX() == i && data.player.getY() == j)
                        AlgoVisHelper.setColor(g2d, AlgoVisHelper.Red);
 
                    AlgoVisHelper.fillRectangle(g2d, j*w, i*h, w, h);
                }
            }
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}
