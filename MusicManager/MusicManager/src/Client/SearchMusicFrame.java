package Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * Created by Lee on 2015-05-12.
 */
public class SearchMusicFrame extends JFrame{
    private JPanel rootPanel;
    private JComboBox selectBox;
    private JTextField searchText;
    private JButton searchButton;
    private JButton adjustButton;
    private JButton playButton;
    private JButton backButton;
    private JTable musicTable;

    private Connection con;

    public SearchMusicFrame(Connection con){
        super("Search Frame");
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();

        this.con = con;

        componentSetting();
        screenSetting();
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

    DefaultTableModel tableModel;
    private void componentSetting() {
        selectBox.setModel(new DefaultComboBoxModel(new String[] {"MusicName", "Artist", "Album"}));
        tableModel = new DefaultTableModel(new String[] {"MusicName", " Artist", "Album", "Uploader", "FileName"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        musicTable.getTableHeader().setReorderingAllowed(false);
        musicTable.setModel(tableModel);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });

        adjustButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = (String) musicTable.getValueAt(musicTable.getSelectedRow(), 0);
                String artist = (String) musicTable.getValueAt(musicTable.getSelectedRow(), 1);
                String album = (String) musicTable.getValueAt(musicTable.getSelectedRow(), 2);
                String filename = (String) musicTable.getValueAt(musicTable.getSelectedRow(), 4);

                new AdjustMusicFrame(con, name, artist, album, filename);
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.close();
                dispose();
            }
        });
    }

    private void search(){
        if(selectBox.getSelectedItem().equals("MusicName")){
            tableModel.setRowCount(0);

            try {
                //연결에 사용할 Statement
                Statement stat = con.createStatement();
                ResultSet rs = null;
                //쿼리날리기
                if(searchText.getText().equals("")){
                    rs = stat.executeQuery("SELECT * FROM music");
                }
                else {
                    rs = stat.executeQuery("SELECT * FROM music WHERE name = '" + searchText.getText() + "';");
                }
                //결과
                while (rs.next()) {
                    String name = rs.getString("name");
                    String artist = rs.getString("artist");
                    String album = rs.getString("album");
                    String uploader = rs.getString("uploader");
                    String filename = rs.getString("filename");
                    tableModel.addRow(new String[] {name, artist, album, uploader, filename});
                }
            }
            catch(SQLException e){
                System.out.println("SQL Exception");
            }

        }
        else if(selectBox.getSelectedItem().equals("Artist")){
            tableModel.setRowCount(0);

            try {
                Statement stat = con.createStatement();
                ResultSet rs = null;
                if(searchText.getText().equals("")){
                    rs = stat.executeQuery("SELECT * FROM music");
                }
                else{
                    rs = stat.executeQuery("SELECT * FROM music WHERE artist = '" + searchText.getText() + "';");
                }

                while(rs.next()){
                    String name = rs.getString("name");
                    String artist = rs.getString("artist");
                    String album = rs.getString("album");
                    String uploader = rs.getString("uploader");
                    String filename = rs.getString("filename");
                    tableModel.addRow(new String[] {name, artist, album, uploader, filename});
                }
            } catch (SQLException e) {
                //e.printStackTrace();
            }

        }
        else if(selectBox.getSelectedItem().equals("Album")){
            tableModel.setRowCount(0);

            try{
                Statement stat = con.createStatement();
                ResultSet rs = null;
                if(searchText.getText().equals("")){
                    rs = stat.executeQuery("SELECT * FROM music");
                }
                else{
                    rs = stat.executeQuery("SELECT * FROM music WHERE album = '" + searchText.getText() + "';");
                }

                while(rs.next()){
                    String name = rs.getString("name");
                    String artist = rs.getString("artist");
                    String album = rs.getString("album");
                    String uploader = rs.getString("uploader");
                    String filename = rs.getString("filename");
                    tableModel.addRow(new String[] {name, artist, album, uploader, filename});
                }
            }catch(SQLException e){

            }
        }
    }

    MP3Player player = new MP3Player("");
    Thread musicThread;
    private void play(){
        musicThread = new Thread(){
            @Override
            public void run() {

                player.close();

                Object tempName = musicTable.getValueAt(musicTable.getSelectedRow(), 4);
                player = new MP3Player(tempName.toString());
                player.play();
            }
        };
        musicThread.start();
    }
}
