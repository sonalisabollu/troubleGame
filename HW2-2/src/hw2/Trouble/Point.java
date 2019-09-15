package hw2.Trouble;

/*
This class is used to store (x,y) order pair.
It is used in BoardGraphics class
 */

public class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int xCoord) {
        x = xCoord;
    }

    public void setY(int yCoord) {
        y = yCoord;
    }
}
