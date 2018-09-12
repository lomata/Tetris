package homework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;

/**
 * creating the game grid
 * Created by lomata on 17/11/4.
 */

public class GridBoard
{
    //girds board
    private int[][] girds = new int[BoardPanel.height][BoardPanel.width];

    //current and next following shape
    private Shape curShape;
    private Shape nextShape;

    private int score = 0;

    public GridBoard()
    {
        //
        for (int i = 0; i < BoardPanel.height; i++)
        {
            for (int j = 0; j < BoardPanel.width; j++)
            {
                this.girds[i][j] = 0;
            }
        }
        this.curShape = Shape.getRandomShape();
        this.nextShape = Shape.getRandomShape();
    }

    /**
     * Whether the gird intersect with the shape
     * @return
     */
    private boolean checkIntersection()
    {

        int startCol = this.curShape.getCurX();
        int startRow = this.curShape.getCurY();

        for (int i = 0; i < Shape.SHAPESIZE; i++)
        {
            for (int j = 0; j < Shape.SHAPESIZE; j++)
            {
                int r = i + startRow;
                int c = j + startCol;

                if (r < BoardPanel.height && r >= 0 && c >= 0 && c < BoardPanel.width)
                {
                    if (this.curShape.getRoom()[i][j] == 1 && this.girds[r][c] == 1)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Try to add current shape after down to bottom
     * @return false if current shape is set higher than the board
     */
    public boolean addCurShape()
    {
        int startCol = this.curShape.getCurX();
        int startRow = this.curShape.getCurY();

        for (int i = 0; i < Shape.SHAPESIZE; i++)
        {
            for (int j = 0; j < Shape.SHAPESIZE; j++)
            {
                int r = i + startRow;
                int c = j + startCol;
                if (r < BoardPanel.height && r >= 0 && c >= 0 && c < BoardPanel.width)
                {
                    if (this.curShape.getRoom()[i][j] == 1)
                        this.girds[r][c] = 1;
                }
            }
        }
        if ((this.curShape.getCurY() + this.curShape.getUpBoarder()) <= 0)
        {
            return false;
        }

        //check score
        checkScore();
        this.nextShape();
        return true;
    }

    private void checkScore()
    {
        int fullRow = -1;
        int fullRows = 0;
        while (true)
        {
            fullRow = -1;
            for (int i = BoardPanel.height - 1; i >= 0; i--)
            {
                int j;
                for (j = 0; j < BoardPanel.width; j++)
                {
                    if (this.girds[i][j] == 0)
                    {
                        break;
                    }
                }
                if (j >= BoardPanel.width)
                {
                    fullRow = i;
                    fullRows++;
                    break;
                }
            }
            if (fullRow == -1)
            {
                break;
            }
            for (int r = fullRow; r > 0; r--)
            {
                for (int j = 0; j < BoardPanel.width; j++)
                {
                    this.girds[r][j] = this.girds[r - 1][j];
                }
            }
        }
        int[] scores = new int[]
                { 0, 10, 30, 70, 150 };
        this.score += scores[fullRows];
    }

    public boolean moveDown()
    {
        boolean res = this.curShape.moveDown();
        if (!res)
        {
            return res;
        }
        if (checkIntersection())
        {
            this.curShape.moveUp();
            return false;
        }
        return true;
    }

    /**
     * move down to bottom
     * @return
     */
    public boolean moveBottom()
    {
        while (moveDown());
        return false;
    }

    public boolean moveLeft()
    {
        boolean res = this.curShape.moveLeft();
        if (!res)
        {
            return res;
        }
        if (checkIntersection())
        {
            this.curShape.moveRight();
            return false;
        }
        return true;
    }

    public boolean moveRight()
    {
        boolean res = this.curShape.moveRight();
        if (!res)
        {
            return res;
        }
        if (checkIntersection())
        {
            this.curShape.moveLeft();
            return false;
        }
        return true;
    }

    public boolean rLeft()
    {
        boolean res = this.curShape.rotateLeft();
        if (!res)
        {
            return res;
        }
        if (checkIntersection())
        {
            this.curShape.rotateRight();
            return false;
        }
        return true;
    }

    public boolean rRight()
    {
        boolean res = this.curShape.rotateRight();
        if (!res)
        {
            return res;
        }
        if (checkIntersection())
        {
            this.curShape.rotateLeft();
            return false;
        }
        return true;
    }

    public void nextShape()
    {
        this.curShape = this.nextShape;
        this.nextShape = Shape.getRandomShape();
    }

    public Shape getCurShape()
    {
        return curShape;
    }

    public Shape getNextShape()
    {
        return nextShape;
    }

    public int getScore()
    {
        return this.score;
    }

    public void paint(Graphics g)
    {
        for (int i = 0; i < BoardPanel.height; i++)
        {
            for (int j = 0; j < BoardPanel.width; j++)
            {
                if (this.girds[i][j] == 1)
                {
                    g.setColor(Color.orange);
                } else
                {
                    g.setColor(Color.lightGray);
                }
                g.fillRect(j * BoardPanel.SIZE + 1, i * BoardPanel.SIZE + 1,
                        BoardPanel.SIZE - 1, BoardPanel.SIZE - 1);
            }
        }
        this.curShape.paint(g);
    }

    //
    public void reset()
    {
        this.score = 0;
        for (int i = 0; i < BoardPanel.height; i++)
        {
            for (int j = 0; j < BoardPanel.width; j++)
            {
                this.girds[i][j] = 0;
            }
        }
        this.curShape = Shape.getRandomShape();
        this.nextShape = Shape.getRandomShape();
    }

}
