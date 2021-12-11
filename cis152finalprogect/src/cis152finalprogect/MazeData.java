package cis152finalprogect;
//naifu Bai  nbai@dmacc.edu  12/10
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class MazeData {

    public static final char ROAD = ' ';
    public static final char WALL = '#';

    private int N, M;   // Row, column
    char[][] maze;

    private int entranceX, entranceY;   // Entrance
    private int exitX, exitY;   // exit

    public boolean[][] visited; // Record whether a location has been visited during the pathfinding process
    public boolean[][] path;    // Store the solution of the maze
    public boolean showPath;    // Whether to print the solution 

    public Position player; // Player's location

    public MazeData(int N, int M){
    	 
        if( N%2 == 0 || M%2 == 0)
            throw new IllegalArgumentException("Our Maze Generalization Algorihtm requires the width and height of the maze are odd numbers");
 
        this.N = N;
        this.M = M;
 
        maze = new char[N][M];
        visited = new boolean[N][M];
        path = new boolean[N][M];
        for(int i = 0 ; i < N ; i ++)
            for(int j = 0 ; j < M ; j ++){
                if(i%2 == 1 && j%2 == 1)
                    maze[i][j] = ROAD;
                else
                    maze[i][j] = WALL;
 
                visited[i][j] = false;
                path[i][j] = false;
            }
        showPath = false;
 
        entranceX = 1;
        entranceY = 0;
        exitX = N - 2;
        exitY = M - 1;
 
        maze[entranceX][entranceY] = ROAD;
        maze[exitX][exitY] = ROAD;
    }
 
    public int N(){ return N; }
    public int M(){ return M; }
    public int getEntranceX(){ return entranceX; }
    public int getEntranceY(){ return entranceY; }
    public int getExitX(){ return exitX; }
    public int getExitY(){ return exitY; }
 
    public boolean inArea(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < M;
    }
 
    public char getMaze(int i, int j){
        if (!inArea(i, j))
            throw new IllegalArgumentException("i or j is out of index in getMaze!");
 
        return maze[i][j];
    }
}