package Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Lee on 2015-06-18.
 */
public class SearchFavorite extends JFrame{
    private JPanel rootPanel;
    private JTable musicTable;
    private JComboBox selectBox;
    private JTextField searchText;
    private JButton searchButton;
    private JButton selectButton;
    private JButton playButton;
    private JButton backButton;

    private Connection con;
    private UserInfoFrame frame;

    public SearchFavorite(Connection con, UserInfoFrame frame){
        super("Search Frame");
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();

        this.con = con;
        this.frame = frame;

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

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                select();
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
                frame.setFavorite("NULL");
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

    private void select(){
        frame.setFavorite((String) musicTable.getValueAt(musicTable.getSelectedRow(), 4));
        dispose();
    }
}
