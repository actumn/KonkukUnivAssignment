package Client;

import Model.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Created by Lee on 2015-05-05.
 */
public class newIDFrame extends JFrame{
    private JPanel rootPanel;
    private JButton createIDButton;
    private JButton backButton;
    private JTextField ID;
    private JTextField password;
    private JTextField name;
    private JTextField age;

    public newIDFrame(){
        super("Create ID");
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();

        buttonSetting();
        screenSetting();
    }

    private void buttonSetting(){
        createIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName(Main.driverName);
                    //연결
                    Connection con = DriverManager.getConnection(Main.subUrl, Main.rootId, Main.rootPassword);
                    //연결에 사용할 Statement
                    Statement stat = con.createStatement();
                    //계정 생성
                    stat.executeQuery("CREATE USER "+ID.getText() +" identified by '"+password.getText()+"';");
                    stat.executeQuery("GRANT ALL PRIVILEGES on MusicManage.* to '"+ID.getText()+"'@'%'"+" identified by '"+password.getText()+"';");
                    stat.executeQuery("FLUSH PRIVILEGES;");
                    con = DriverManager.getConnection(Main.DBurl, Main.rootId, Main.rootPassword);
                    stat = con.createStatement();
                    stat.executeQuery("INSERT INTO user VALUES(" +
                            "'"+ID.getText()+"'," +
                            "'"+password.getText()+"'," +
                            "'"+name.getText()+"', " +
                            ""+age.getText()+", " +
                            "NULL);");
                    dispose();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    private void screenSetting(){
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = this.getSize();

        int xpos = (int)(screen.getWidth()/2 - size.getWidth()/2);
        int ypos = (int)(screen.getHeight()/2 - size.getHeight()/2);
        setLocation(xpos, ypos);

        setResizable(false);
        setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
