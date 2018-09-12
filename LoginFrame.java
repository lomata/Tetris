package homework;
import java.awt.event.*;
import javax.swing.*;

/**
 * Created by lomata on 17/12/4.
 */


public class LoginFrame extends JFrame implements ActionListener {

    private static JButton login;
    private static JTextField namefiled;
    private static JTextField pwd;

    public LoginFrame() {
        this.setSize(350, 250);
        setTitle("Tetris Login");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel name = new JLabel("username");
        name.setBounds(90, 50, 100, 30);
        this.getContentPane().add(name);

        namefiled = new JTextField("");
        namefiled.setBounds(180, 50, 100, 30);
        this.getContentPane().add(namefiled);

        JLabel pass = new JLabel("password");
        pass.setBounds(90, 90, 100, 30);
        this.getContentPane().add(pass);

        pwd = new JTextField();
        pwd.setBounds(180, 90, 100, 30);
        this.getContentPane().add(pwd);

        login = new JButton("login");
        login.setBounds(90, 130, 150, 30);
        login.addActionListener(this);
        this.getContentPane().add(login);

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        String name = namefiled.getText().trim();
        String pwd = this.pwd.getText().trim();

        if(name.length() == 0 || pwd.length() == 0)
        {
            return;
        }

        //check if the user's name exists or not
        int res = Record.check(name, pwd);
        User u = null;

        //exists
        //correct password
        if (res == 1)
        {
            u = Record.getUserByName(name);
        }
        // wrong password
        else if (res == -1)
        {
            JOptionPane.showMessageDialog(this, "Wrong password, please try again!");
            return;
        }
        //do not exists
        //create new account
        else
            {
            u = new User(name, pwd, 0);
            JOptionPane.showMessageDialog(this, "New account Created: " + name);
                Record.addUser(u);
            }
            //game on
        new TetrisFrame(u);

        this.setVisible(false);
    }

}

