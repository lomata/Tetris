package homework;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;

/**
 * Shape class define a shape, it can move down, left, right. also can rotate
 * Created by lomata on 17/11/7.
 */

public class Shape {
    enum ShapeType {
        I, J, L, O, Z, T, S
    }

    public static final int SHAPESIZE = 4;
    protected int[][] room;
    protected int curX = (BoardPanel.width - SHAPESIZE) / 2;
    protected int curY = -SHAPESIZE;
    protected Color color;
    protected ShapeType type;

    private Shape(int room[][], Color color, ShapeType shape) {
        this.color = color;
        this.room = room;
        this.type = shape;
    }

    public static Shape getRandomShape() {
        ShapeType[] shapes = ShapeType.values();
        Random r = new Random();
        return getShape(shapes[r.nextInt(shapes.length)]);
    }

    /**
     * Given a shape type and initialize a shape
     *
     * @return a shape decided by the shapeNum
     */
    public static Shape getShape(ShapeType shape) {
        int[][] room = new int[SHAPESIZE][SHAPESIZE];
        for (int i = 0; i < SHAPESIZE; i++) {
            for (int j = 0; j < SHAPESIZE; j++) {
                room[i][j] = 0;
            }
        }
        Color c = null;
        switch (shape)
        {
            case I:
                for (int i = 0; i < SHAPESIZE; i++) {
                    room[i][1] = 1;
                }
                c = Color.darkGray;
                break;
            case J:
                room[0][2] = 1;
                room[1][2] = 1;
                room[2][2] = 1;
                room[2][1] = 1;
                c = Color.green;
                break;
            case L:
                room[0][1] = 1;
                room[1][1] = 1;
                room[2][1] = 1;
                room[2][2] = 1;
                c = Color.blue;
                break;
            case O:
                room[1][1] = 1;
                room[1][2] = 1;
                room[2][1] = 1;
                room[2][2] = 1;
                c = Color.yellow;
                break;
            case Z:
                room[1][0] = 1;
                room[1][1] = 1;
                room[2][1] = 1;
                room[2][2] = 1;
                c = Color.cyan;
                break;
            case T:
                room[1][1] = 1;
                room[2][0] = 1;
                room[2][1] = 1;
                room[2][2] = 1;
                c = Color.red;
                break;
            case S:
                room[1][1] = 1;
                room[1][2] = 1;
                room[2][0] = 1;
                room[2][1] = 1;
                c = Color.pink;

        }
        return new Shape(room, c, shape);
    }

    public boolean rotateLeft() {

        int[][] newRoom = new int[SHAPESIZE][SHAPESIZE];

        for (int i = 0; i < SHAPESIZE; i++) {
            for (int j = 0; j < SHAPESIZE; j++) {
                newRoom[i][j] = this.room[j][SHAPESIZE - 1 - i];
            }
        }

        int[][] oldRoom = this.room;
        this.room = newRoom;

        //check if the block reaches the boarder
        if ((this.curY + this.getBottomBoarder() > BoardPanel.height - 1) || (this.curX + this.getleftBoarder() < 0)
                || ((this.curX + this.getRightBoarder()) > BoardPanel.width - 1)) {
            this.room = oldRoom;
            //if it reaches the boarder, it can not be rotated
            return false;
        }
        return true;
    }

    public boolean rotateRight() {
        int[][] newRoom = new int[SHAPESIZE][SHAPESIZE];
        for (int i = 0; i < SHAPESIZE; i++) {
            for (int j = 0; j < SHAPESIZE; j++) {
                newRoom[i][j] = this.room[SHAPESIZE - 1 - j][i];
            }
        }
        int[][] oldRoom = this.room;
        this.room = newRoom;

        //same as rotate left
        if ((this.curY + this.getBottomBoarder() > BoardPanel.height - 1) || (this.curX + this.getleftBoarder() < 0)
                || ((this.curX + this.getRightBoarder()) > BoardPanel.width - 1)) {
            this.room = oldRoom;
            return false;
        }
        return true;
    }

    public int getRightBoarder()
    {
        int i = SHAPESIZE - 1;
        for (; i >= 0; i--) {
            for (int j = 0; j < SHAPESIZE; j++)
            {
                if (this.room[j][i] == 1)
                {
                    return i;
                }
            }
        }
        return i;
    }

    public int getleftBoarder()
    {
        int i = 0;
        for (; i < SHAPESIZE; i++) {
            for (int j = 0; j < SHAPESIZE; j++) {
                if (this.room[j][i] == 1) {
                    return i;
                }
            }
        }
        return i;
    }

    public int getBottomBoarder() {
        int i = SHAPESIZE - 1;
        for (; i >= 0; i--) {
            for (int j = 0; j < SHAPESIZE; j++) {
                if (this.room[i][j] == 1) {
                    return i;
                }
            }
        }
        return i;
    }

    public int getUpBoarder() {
        int i = 0;
        for (; i < SHAPESIZE; i++) {
            for (int j = 0; j < SHAPESIZE; j++) {
                if (this.room[i][j] == 1) {
                    return i;
                }
            }
        }
        return i;
    }


    public boolean moveUp() {
        this.curY--;
        return true;
    }

    public boolean moveDown()
    {
        if ((this.curY + this.getBottomBoarder()) < (BoardPanel.height - 1)) {
            this.curY++;
            return true;
        }
        return false;
    }

    public boolean moveLeft()
    {
        if (this.curX + this.getleftBoarder() > 0)
        {
            this.curX--;
            return true;
        }
        return false;
    }

    public boolean moveRight()
    {
        if ((this.curX + this.getRightBoarder()) < BoardPanel.width - 1)
        {
            this.curX++;
            return true;
        }
        return false;
    }

    public int[][] getRoom()
    {
        return room;
    }

    public void setRoom(int[][] room)
    {
        this.room = room;
    }

    public int getCurX()
    {
        return curX;
    }

    public int getCurY()
    {
        return curY;
    }

    public void paint(Graphics g)
    {
        g.setColor(this.color);
        for (int i = 0; i < SHAPESIZE; i++)
        {
            for (int j = 0; j < SHAPESIZE; j++)
            {
                if (this.room[i][j] == 1)
                {
                    int x = this.curX + j;
                    int y = this.curY + i;
                    if (y >= 0) {
                        g.fillRect(x * BoardPanel.SIZE + 1, y * BoardPanel.SIZE + 1, BoardPanel.SIZE - 1,
                                BoardPanel.SIZE - 1);
                    }
                }
            }
        }
    }

    public Color getColor()
    {
        return this.color;
    }

}

