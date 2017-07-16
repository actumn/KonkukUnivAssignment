package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * Created by Lee on 2015-05-12.
 */
public class PlayFrame extends JFrame{
    private JButton backButton;
    private JButton playButton;
    private JButton nextButton;
    private JPanel JPanel;
    private JPanel rootPanel;
    private JLabel nameLable;
    private JLabel artistLable;
    private JLabel albumLable;
    private JButton stopButton;
    private JRadioButton sequenceRadioButton;
    private JRadioButton randomRadioButton;
    private JRadioButton repeatedRadioButton;

    private Statement stat;
    private ResultSet rs;

    String filename;

    int musicLength;
    public PlayFrame(Connection con){
        super("Play Frame");
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();

        try {
            stat = con.createStatement();
            rs = stat.executeQuery("SELECT * FROM music");

            rs.last();
            musicLength = rs.getRow();
            rs.beforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        next();

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


    private void componentSetting() {

        ButtonGroup bg = new ButtonGroup();
        bg.add(sequenceRadioButton);
        bg.add(randomRadioButton);
        bg.add(repeatedRadioButton);
        sequenceRadioButton.setSelected(true);



        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                next();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop(); dispose();
            }
        });
    }


    private void next(){
        if(sequenceRadioButton.isSelected()){
            nextSeq();
        }
        else if(randomRadioButton.isSelected()){
            Random random = new Random();
            int index = random.nextInt(musicLength) + 1;
            for(int i = 0 ; i < index; i++){
                nextSeq();
            }
        }
        else if(repeatedRadioButton.isSelected()){

        }
        else{
            nextSeq();
        }
    }

    private void nextSeq(){
        try {
            if(rs.next()){
                nameLable.setText(rs.getString("name"));
                artistLable.setText(rs.getString("artist"));
                albumLable.setText(rs.getString("album"));

                filename = rs.getString("filename");
            }
            else{
                rs = stat.executeQuery("SELECT * FROM music");
            }
        } catch (SQLException e) {
        }
    }



    MP3Player player = new MP3Player("");
    Thread musicThread;

    private void play(){

        musicThread = new Thread(){
            @Override
            public void run() {
                tryPlay();
                try {
                    while (true) {
                        if (player.isComplete()) {
                            next();
                            tryPlay();
                        }

                        sleep(500);
                    }
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        };

        musicThread.start();
    }
    private void tryPlay(){
        if(!player.isComplete())
            player.close();

        player = new MP3Player(filename);
        player.play();

    }

    private void stop(){
        if(musicThread == null)
            return;
        if(musicThread.isAlive())
            musicThread.interrupt();
        if(!player.isComplete())
            player.close();
    }
}
