package Client;

import Model.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Lee on 2015-05-03.
 */
public class LoginFrame extends JFrame {
    private JButton loginButton;
    private JPanel rootPanel;
    private JTextField ID;
    private JTextField password;
    private JButton createIDButton;
    private JButton exitButton;


    public LoginFrame(){
        super("Login Frame");
        setContentPane(rootPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        buttonSetting();
        screenSetting();
    }

    private void buttonSetting() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    login();
                } catch (ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(getContentPane(), "No connection");
                    System.out.println("Class Not Found Exception");
                    //e1.printStackTrace();
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(getContentPane(), "ID or password error");
                    System.out.println("SQL Exception");
                    e1.printStackTrace();
                }
            }
        });

        createIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new newIDFrame();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }


    private void screenSetting(){

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = this.getSize();

        int xpos = (int)(screen.getWidth()/2 - size.getWidth()/2);
        int ypos = (int)(screen.getHeight()/2 - size.getHeight()/2);
        setLocation(xpos, ypos);

        setVisible(true);
    }

    private void login() throws ClassNotFoundException, SQLException {
        Class.forName(Main.driverName);
        //연결
        Connection con = DriverManager.getConnection(Main.DBurl, ID.getText(), password.getText());

        new ClientFrame(con, ID.getText());
        dispose();
    }


}
