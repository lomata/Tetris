package homework;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * draw the tetris frame
 * Created by lomata on 17/11/4.
 */

public class TetrisFrame extends JFrame implements ActionListener
{

    private GridBoard myGrid;
    private BoardPanel pMain;

    private JButton down = new JButton("D");
    private JButton downBottom = new JButton("bottom");

    //move to left or right
    private JButton left = new JButton("L");
    private JButton right = new JButton("R");

    //rotate to left or right
    private JButton rleft = new JButton("<");
    private JButton rright = new JButton(">");

    private JButton restart = new JButton("Restart");
    private JButton pause = new JButton("Pause");

    private User user;
    private long timeStart;
    private int timeSeconds = 0;

    private JLabel lblTime;

    //initial speed
    private int speed = 1;

    private NextShapePanel pNext;

    private JLabel lblScore;
    private JLabel lblHighest;
    private JLabel lblLevel;

    private boolean gameOver = false;
    private boolean paused = false;

    public TetrisFrame(User user)
    {
        setTitle("Tetris");
        setResizable(false);
        setSize(500, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                //update files when closing window
                Record.updateFile();
                System.exit(-1);
            }
        });

        this.user = user;

        getContentPane().setLayout(null);
        getContentPane().setLayout(null);

        pMain = new BoardPanel();

        pMain.setBorder(new LineBorder(new Color(0, 0, 0)));
        pMain.setBounds(10, 10, 302, 601);

        getContentPane().add(pMain);

        this.myGrid = pMain.getGridBoard();

        JLabel lblUserName = new JLabel("User: " + this.user.getName());
        lblUserName.setFont(new Font("Arial", Font.PLAIN, 20));
        lblUserName.setBounds(335, 28, 120, 27);

        getContentPane().add(lblUserName);

        lblTime = new JLabel("Time: 0");
        lblTime.setFont(new Font("Arial", Font.PLAIN, 20));
        lblTime.setBounds(335, 55, 120, 27);
        getContentPane().add(lblTime);


        pNext = new NextShapePanel(this.myGrid.getNextShape());
        pNext.setBorder(new LineBorder(new Color(0, 0, 0)));
        pNext.setBounds(335, 95, 120, 120);
        getContentPane().add(pNext);

        //showing current score
        lblScore = new JLabel("Score: " + this.myGrid.getScore());
        lblScore.setFont(new Font("Arial", Font.PLAIN, 20));
        lblScore.setBounds(335, 225, 120, 27);
        getContentPane().add(lblScore);

        //showing highest score
        lblHighest = new JLabel("Highest: " + this.user.getHighest());
        lblHighest.setFont(new Font("Arial", Font.PLAIN, 20));
        lblHighest.setBounds(335, 272, 120, 27);
        getContentPane().add(lblHighest);

        //showing current speed
        lblLevel = new JLabel("Level: " + this.speed);
        lblLevel.setFont(new Font("Arial", Font.PLAIN, 20));
        lblLevel.setBounds(335, 322, 120, 27);
        getContentPane().add(lblLevel);

        //rotate to left or right
        this.rleft.setBounds(350, 360, 60, 30);
        getContentPane().add(this.rleft);
        this.rleft.addActionListener(this);

        this.rright.setBounds(410, 360, 60, 30);
        getContentPane().add(this.rright);
        this.rright.addActionListener(this);

        //move to left, right, up, down and direct to bottom
        this.left.setBounds(330, 420, 50, 30);
        getContentPane().add(this.left);
        this.left.addActionListener(this);

        this.right.setBounds(430, 420, 50, 30);
        getContentPane().add(this.right);
        this.right.addActionListener(this);

        this.down.setBounds(380, 420, 50, 30);
        getContentPane().add(this.down);
        this.down.addActionListener(this);

        this.downBottom.setBounds(360, 450, 100, 30);
        getContentPane().add(this.downBottom);
        this.downBottom.addActionListener(this);

        this.restart.setBounds(360, 490, 100, 30);
        getContentPane().add(this.restart);
        this.restart.addActionListener(this);

        this.pause.setBounds(360, 560, 100, 30);
        getContentPane().add(this.pause);
        this.pause.addActionListener(this);

        //Add key listener
        this.pMain.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                keyMove(e.getKeyCode());
            }
        });

        setVisible(true);
        timeStart = System.currentTimeMillis();
        timeMover();
        this.pMain.requestFocus();
    }

    public void update(boolean next)
    {
        this.pMain.repaint();

        //current block fixed
        if (next)
        {
            lblScore.setText("Score:" + this.myGrid.getScore());
            this.pNext.setNextShape(this.myGrid.getNextShape());
            this.pNext.repaint();

            //update current highest score
            if (this.myGrid.getScore() > this.user.getHighest())
            {
                this.user.setHighest(this.myGrid.getScore());
                this.lblHighest.setText("Highest: " + this.user.getHighest());
            }

            //every 200 points
            //it will speed up
            speed = myGrid.getScore() / 200 + 1;
            this.lblLevel.setText("Level: " + this.speed);

        }
        long curTime = System.currentTimeMillis();

        //update every 1s
        if (curTime - this.timeStart >= 1000)
        {
            this.timeStart = curTime;
            this.timeSeconds++;
            lblTime.setText("Time: " + timeSeconds);
        }
        if (this.gameOver)
        {
            JOptionPane.showMessageDialog(this, "Game Over! Score: " + this.myGrid.getScore());
        }
    }

    //restart the game
    public void restart()
    {
        gameOver = false;
        timeStart = System.currentTimeMillis();
        timeSeconds = 0;
        lblTime.setText("Time: 0");

        //set initial speed to level 1
        speed = 1;
        this.lblLevel.setText("Level: " + this.speed);

        this.myGrid.reset();
        this.pMain.repaint();

        lblScore.setText("Score:" + this.myGrid.getScore());

        this.pNext.setNextShape(this.myGrid.getNextShape());

        this.pNext.repaint();
        this.paused = false;
        this.pause.setText("Pause");
    }


    private void keyMove(int i)
    {
        //space
        if (i == 32)
        {
            boolean res = this.myGrid.moveBottom();
            if (!res)
            {
                if (!this.myGrid.addCurShape())
                {
                    this.gameOver = true;
                }
            }
            update(!res);
        }
        else if (i == 37)
        {
            this.myGrid.moveLeft();
            update(false);
        }
        else if (i == 39)
        {
            this.myGrid.moveRight();
            update(false);
        }
        else if (i == 38)
        {
            this.myGrid.rRight();
            update(false);

        }
        else if (i == 40)
        {
            boolean res = this.myGrid.moveDown();
            if (!res)
            {
                if (!this.myGrid.addCurShape())
                {
                    this.gameOver = true;
                }
            }
            update(!res);
        }
    }

    /**
     *
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {

        if (this.gameOver && !e.getSource().equals(this.restart))
        {
            this.pMain.requestFocus();
            return;
        }

        if (e.getSource().equals(this.right))
        {
            this.myGrid.moveRight();
            update(false);
        }
        else if (e.getSource().equals(this.left))
        {
            this.myGrid.moveLeft();
            update(false);
        }
        if (e.getSource().equals(this.downBottom))
        {
            boolean res = this.myGrid.moveBottom();
            if (!res)
            {
                if (!this.myGrid.addCurShape())
                {
                    this.gameOver = true;
                }
            }
            update(!res);
        }
        if (e.getSource().equals(this.down))
        {
            boolean res = this.myGrid.moveDown();
            if (!res)
            {
                if (!this.myGrid.addCurShape())
                {
                    this.gameOver = true;
                }
            }
            update(!res);
        }
        if (e.getSource().equals(this.rright))
        {
            this.myGrid.rRight();
            update(false);
        }

        if (e.getSource().equals(this.rleft))
        {
            this.myGrid.rLeft();
            update(false);
        }

        if (e.getSource().equals(this.restart))
        {
            this.restart();
        }

        if (e.getSource().equals(this.pause))
        {
            if (this.paused)
            {
                this.paused = false;
                this.pause.setText("Pause");
            }
            else
            {
                this.paused = true;
                //change pause to resume
                this.pause.setText("Resume");
            }
        }
        this.pMain.requestFocus();
    }


    /**
     * Move down shape after sleep a while (depends by the speed)
     */
    public void timeMover()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    try
                    {
                        while (gameOver)
                        {
                            Thread.sleep(1000 / speed);
                        }
                        while (paused)
                        {
                            Thread.sleep(1000 / speed);
                        }
                        Thread.sleep(1000 / speed);
                        boolean res = myGrid.moveDown();
                        if (!res)
                        {
                            if (!myGrid.addCurShape())
                            {
                                gameOver = true;
                            }
                        }
                        update(!res);

                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        })
                .start();
    }
}
