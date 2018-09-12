package homework;

import java.io.*;
import java.util.*;

/**
 * Created by lomata on 17/12/8.
 */
public class Record {
    private final static String FILENAME = "users.txt";

    private static List<User> userRecords;

    public static void loadFile()
    {
        userRecords = new ArrayList<User>();
        Scanner in = null;
        try
        {
            in = new Scanner(new File(FILENAME));
            while (in.hasNextLine())
            {
                String line = in.nextLine();
                String[] strs = line.split("\\s+");
                String name = strs[0];
                String pwd = strs[1];
                int highest = Integer.parseInt(strs[2]);

                userRecords.add(new User(name, pwd, highest));
            }
            in.close();
        } catch (FileNotFoundException e)
        {
        }
    }

    public static List<User> getUserRecords()
    {
        return userRecords;
    }

    public static void updateFile()
    {
        String content = "";
        for (int i = 0; i < userRecords.size(); i++)
        {
            User u = userRecords.get(i);
            content += u.toString() + "\n";
        }
        File file = new File(FILENAME);
        FileWriter fw;
        try
        {
            fw = new FileWriter(file, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            fw.close();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
        }

    }


    public static int check(String name, String pwd)
    {
        for (int i = 0; i < userRecords.size(); i++)
        {
            User u = userRecords.get(i);
            if (u.getName().equals(name))
            {
                //check the password
                if (u.getPassWord().equals(pwd))
                {
                    return 1;
                } else
                {
                    return -1;
                }
            }
        }
        return 0;
    }

    public static User getUserByName(String name)
    {
        for (int i = 0; i < userRecords.size(); i++)
        {
            User u = userRecords.get(i);
            if (u.getName().equals(name))
            {
                return u;
            }
        }
        return null;
    }

    public static void addUser(User u)
    {
        userRecords.add(u);
    }

}
