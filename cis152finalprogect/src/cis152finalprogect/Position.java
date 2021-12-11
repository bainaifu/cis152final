package cis152finalprogect;
//naifu Bai  nbai@dmacc.edu  12/10
public class Position {

    private int x, y;

    public Position(int x, int y, Position prev){
        this.x = x;
        this.y = y;
    }

    public Position(int x, int y){
        this(x, y, null);
    }

    public int getX(){ return x; }
    public int getY(){ return y; }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

}