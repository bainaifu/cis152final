package cis152finalprogect;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MazeVisualizer {

    private static int DELAY = 10;
    private static int blockSide = 8;

    private MazeData data;
    private MazeFrame frame;

    private static final int d[][] = {
   {-1,0},{0,1},{1,0},{0,-1}}; // Move in four directions

	public MazeVisualizer(int N, int M){//(String mazeFile)
	    // Initialization
        data = new MazeData(N, M);

        int sceneHeight = data.N() * blockSide;
        int sceneWidth = data.M() * blockSide;

        // Initialization
        EventQueue.invokeLater(() -> {
            frame = new MazeFrame("Maze Solver Visualization", sceneWidth, sceneHeight);
            frame.addKeyListener(new AlgoKeyListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }
    private void run(){
    	 
        setRoadData(-1, -1);
 
        if (initial())
            System.out.println("Initialization completed, GO!");
 
        while (true){
            frame.render(data);
            AlgoVisHelper.pause(DELAY);
        }
    }

	//bfs
	private boolean initial(){
        data.player = new Position(data.getEntranceX(), data.getEntranceY());
        RandomQueue<Position> queue = new RandomQueue<Position>();
        Position first = new Position(data.getEntranceX(), data.getEntranceY()+1);
        queue.add(first);
        data.visited[first.getX()][first.getY()] = true;
 
        while(queue.size() != 0){
            Position curPos = queue.remove();
 
            for(int i = 0 ; i < 4  ; i ++){
                int newX = curPos.getX() + d[i][0]*2;
                int newY = curPos.getY() + d[i][1]*2;
 
                if(data.inArea(newX, newY) && !data.visited[newX][newY]){
                    queue.add(new Position(newX, newY));
                    data.visited[newX][newY] = true;
                    setRoadData(curPos.getX() + d[i][0], curPos.getY() + d[i][1]);
                }
            }
        }
        for(int i = 0 ; i < data.N() ; i ++)
            for(int j = 0 ; j < data.M() ; j ++)
                data.visited[i][j] = false;
        new Thread(() -> {
        	autoGo(data.getEntranceX(), data.getEntranceY());
        }).start();
        return true;
    }

 
	
	/*
	public void run(){
	    setData(-1, -1, false);
 
	    data.player = new Position(data.getEntranceX(), data.getEntranceY());
 
        // Recur
        if(!autoGo(data.getEntranceX(), data.getEntranceY()))
            System.out.println("The maze has NO solution!");
 
        System.out.println("Initialization completed, GO!");
 
	    while (true){
            frame.render(data);
            AlgoVisHelper.pause(DELAY);
            setData(-1, -1, false);
 
            if (data.player.getX() == data.getExitX() && data.player.getY() == data.getExitY()){
                System.out.println("Succeeded!!");
                frame.render(data);
                AlgoVisHelper.pause(DELAY);
                break;
            }
        }
	    setData(-1, -1, false);
    }*/

    // whether the solution was successful
    private boolean autoGo(int x, int y){
        if(!data.inArea(x,y))
            throw new IllegalArgumentException("x,y are out of index in go function!");

        data.visited[x][y] = true;
        setData(x, y, true);

        if (x == data.getExitX() && y == data.getExitY())
            return true;

        for (int i = 0; i < 4; i ++){
            int newX = x + d[i][0];
            int newY = y + d[i][1];
            if (data.inArea(newX, newY) &&
                    data.getMaze(newX, newY) == MazeData.ROAD &&
                    !data.visited[newX][newY]){
                if (autoGo(newX, newY))
                    return true;
            }
        }
        setData(x, y, false);
        return false;
    }

    private void setRoadData(int x, int y){
        if(data.inArea(x, y))
        	data.maze[x][y] = MazeData.ROAD;
    }

    private void setData(int x, int y, boolean isPath){
	    if (data.inArea(x, y))
            data.path[x][y] = isPath;
    }

    private class AlgoKeyListener extends KeyAdapter{
	    @Override
        public void keyPressed(KeyEvent event){
	        if (event.getKeyCode() == KeyEvent.VK_LEFT){
	            System.out.println("go left");
                oneStep(data.player.getX(), data.player.getY(), 3);
            }
            else if (event.getKeyCode() == KeyEvent.VK_DOWN){
                System.out.println("go down");
                oneStep(data.player.getX(), data.player.getY(), 2);
            }
            else if (event.getKeyCode() == KeyEvent.VK_RIGHT){
                System.out.println("go right");
                oneStep(data.player.getX(), data.player.getY(), 1);
            }
            else if (event.getKeyCode() == KeyEvent.VK_UP){
                System.out.println("go up");
                oneStep(data.player.getX(), data.player.getY(), 0);
            }
            else if (event.getKeyChar() == ' '){
                System.out.println("Show hint");
                data.showPath = !data.showPath;
            }
        }
    }

    private void oneStep(int x, int y, int direction){
        int newX = x + d[direction][0];
        int newY = y + d[direction][1];
        if (data.inArea(newX, newY) &&
                data.getMaze(newX, newY) == MazeData.ROAD){
            data.player.setX(newX);
            data.player.setY(newY);
        }
    }

    public static void main(String[] args) {
    	 
        int N = 101;
        int M = 101;
 
        MazeVisualizer vis = new MazeVisualizer(N, M);
 
    }
}
