package homework;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;

/**
 * displaying the background
 * Created by lomata on 17/11/4.
 */
public class BoardPanel extends JPanel
{

    public static final int SIZE = 30;
    public static final int height = 20;
    public static final int width = 10;

    private GridBoard gb;

    public BoardPanel()
    {
        gb = new GridBoard();
        setBackground(Color.WHITE);
    }

    public GridBoard getGridBoard()
    {
        return this.gb;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.lightGray);
        gb.paint(g);
    }
}
