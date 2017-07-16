package Client;

import Model.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Created by Lee on 2015-06-06.
 */
public class UserInfoFrame extends JFrame{
    private JPanel rootPanel;
    private JButton othersButton;
    private JButton adjustButton;
    private JButton backButton;
    private JTextField password;
    private JTextField name;
    private JTextField age;
    private JLabel idLabel;
    private JButton searchMusicButton;
    private JLabel favoriteLabel;

    private Connection con;
    public UserInfoFrame(Connection con, String id){
        super("UserInfo");
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();

        this.con = con;
        try {
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM user WHERE id = '"+id+"';");
            rs.next();

            idLabel.setText(rs.getString("id"));
            password.setText(rs.getString("password"));
            name.setText(rs.getString("name"));
            age.setText("" + rs.getInt("age"));
            favoriteLabel.setText(rs.getString("favorite"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        buttonSetting();
        screenSetting();
    }


    private void buttonSetting() {
        adjustButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adjust();
            }
        });

        othersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OthersFrame(con);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        searchMusicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMusic();
            }
        });
    }
    private void searchMusic(){
        new SearchFavorite(con, this);
    }

    private void screenSetting() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = this.getSize();

        int xpos = (int)(screen.getWidth()/2 - size.getWidth()/2);
        int ypos = (int)(screen.getHeight()/2 - size.getHeight()/2);
        setLocation(xpos, ypos);

        setResizable(false);
        setVisible(true);
    }


    private void adjust(){
        String passwordS = password.getText();
        String nameS = name.getText();
        String ageI = age.getText();
        String favoriteS = favoriteLabel.getText();
        try {
            Connection rootCon = DriverManager.getConnection(Main.subUrl, Main.rootId, Main.rootPassword);
            Statement stat = rootCon.createStatement();
            stat.executeQuery("UPDATE USER " +
                    "SET password = password('"+passwordS+"')" +
                    "where user ='"+idLabel.getText()+"'");
            stat.executeQuery("FLUSH PRIVILEGES;");
            stat = con.createStatement();
            stat.executeQuery("UPDATE USER " +
                    "set password = '"+passwordS+"', " +
                    "name = '" + nameS + "', " +
                    "age = " + ageI + "," +
                    "Favorite = '" +favoriteS+ "' " +
                    "WHERE ID = '" + idLabel.getText() + "'");
            dispose();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void setFavorite(String text) {
        favoriteLabel.setText(text);
    }
}
