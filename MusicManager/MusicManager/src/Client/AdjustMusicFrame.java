package Client;

import Model.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Lee on 2015-06-06.
 */
public class AdjustMusicFrame extends JFrame{
    private JPanel rootPanel;
    private JTextField musicName;
    private JTextField musicArtist;
    private JTextField musicAlbum;
    private JButton cancelButton;
    private JButton updateButton;
    private JLabel fileNameLable;
    private JButton deleteButton;

    private Connection con;
    public AdjustMusicFrame(Connection con, String name, String artist, String album, String filename){
        super("AdjustFrame");
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();

        musicName.setText(name);
        musicArtist.setText(artist);
        musicAlbum.setText(album);
        fileNameLable.setText(filename);

        this.con = con;

        componentSetting();
        screenSetting();
    }

    private void componentSetting(){
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    adjust();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    delete();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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

    private void adjust() throws SQLException {
        Statement stat = con.createStatement();
        String name = musicName.getText();
        String artist = musicArtist.getText();
        String album = musicAlbum.getText();
        String filename = fileNameLable.getText();

        stat.executeQuery("UPDATE music " +
             "set name = '" +name+ "', " +
                "artist = '" + artist+"', " +
                "album = '"+album+"' " +
                "WHERE filename = '" +filename+ "'");
        dispose();
    }

    private void delete() throws SQLException, IOException {
        Socket s = new Socket(Main.ip, Main.portDelete);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        bw.write(fileNameLable.getText()+"\n");
        bw.flush();

        bw.close(); s.close();
        bw = null; s = null;

        Statement stat= con.createStatement();
        stat.executeQuery("DELETE " +
                "FROM music " +
                "WHERE filename = '"+fileNameLable.getText()+"';");

        dispose();
    }
}
