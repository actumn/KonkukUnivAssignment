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
public class ClientFrame extends JFrame{
    private JButton uploadButton;
    private JButton searchButton;
    private JButton exitButton;
    private JPanel rootPanel;
    private JLabel IDField;
    private JButton withdrawAccountButton;
    private JButton logoutButton;
    private JButton playButton;
    private JButton userInformationButton;

    private JFrame chatFrame;

    //연결된 id와 password
    private String id;

    private boolean isAdmin;            //root 계정인가???
    private Connection con;               //DB 연결을 위한 Connection 변수
    private java.sql.Statement stat;        //SQL 쿼리문 작성을 위한 Statement

    public ClientFrame(Connection con, String id){
        super("Music Manager");

        isAdmin = id.equals(Main.rootId);

        chatFrame = new ClientGUI(Main.ip, Main.portChat, id);

        this.con = con;
        this.id = id;

        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        this.con = con;

        buttonSetting();
        screenSetting();

        try {
            Class.forName(Main.driverName);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void buttonSetting(){
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UploadFrame(con, id);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchMusicFrame(con);
            }
        });


        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlayFrame(con);
            }
        });

        userInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserInfoFrame(con, id);
            }
        });

        withdrawAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isAdmin) {
                    try {
                        con = DriverManager.getConnection(Main.subUrl, Main.rootId, Main.rootPassword);
                        stat = con.createStatement();
                        stat.executeQuery("DROP USER " + id + ";");
                        stat.executeQuery("FLUSH PRIVILEGES;");
                        con = DriverManager.getConnection(Main.DBurl, Main.rootId, Main.rootPassword);
                        stat = con.createStatement();
                        stat.executeUpdate("UPDATE USER " +
                                "SET favorite = NULL " +
                                "WHERE id = '"+id+"';");
                        stat.executeQuery("DELETE FROM USER WHERE id = '" + id + "';");
                        dispose();
                        chatFrame.dispose();
                        new LoginFrame();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                chatFrame.dispose();
                new LoginFrame();
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
        IDField.setText("Your ID : "+ id);
        setSize(300, 400);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = this.getSize();

        int xpos = (int)(screen.getWidth()/2 - size.getWidth()/2);
        int ypos = (int)(screen.getHeight()/2 - size.getHeight()/2);
        setLocation(xpos, ypos);
        setVisible(true);
        setResizable(false);
    }

}