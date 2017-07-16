package Client;

import Model.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Lee on 2015-05-12.
 */
public class UploadFrame extends JFrame{
    private JTextField musicName;
    private JTextField musicArtist;
    private JPanel rootPanel;
    private JTextField musicAlbum;
    private JTextField fileroot;
    private JTextField filename;
    private JButton uploadButton;
    private JButton playButton;
    private JButton cancelButton;
    private JButton fileSearchButton;

    MP3 temp = new MP3("");

    Connection con;
    String id;

    public UploadFrame(Connection con, String id){
        super("Upload Frame");
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();

        this.con = con;
        this.id = id;

        buttonSetting();
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

    private void buttonSetting() {
        fileSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileSearch();
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                upload();
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                temp.close();
                dispose();
            }
        });
    }
    private void fileSearch(){
        FileDialog fd = new FileDialog(new JFrame(), "파일 열기", FileDialog.LOAD);
        fd.setDirectory("E:");
        fd.setVisible(true);

        fileroot.setText(fd.getDirectory());
        filename.setText(fd.getFile());
    }

    private void upload(){
        if(!filename.getText().endsWith(".mp3")){
            JOptionPane.showMessageDialog(getContentPane(), "there can be only mp3 file");
            return;
        }

        fileSend();

        try {
             //연결에 사용할 Statement
            Statement stat = con.createStatement();

            stat.executeQuery("INSERT INTO music VALUES('" + musicName.getText() + "','" + musicArtist.getText() + "','"
                    + musicAlbum.getText() + "', '" + filename.getText()+"','"+id+"');");

            temp.close();
            dispose();

        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(getContentPane(),"There's a same name file");
            System.out.println("SQL Exception");
            e.printStackTrace();
        }
    }

    private void fileSend(){
        try {
            Socket s = new Socket(Main.ip, Main.portUpload);
            System.out.println("파일명 : " + filename.getText());

            //파일명
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            bw.write(filename.getText()+"\n");
            bw.flush();

            //선택한 파일로부터 스트림을 읽어들여서    얻어놓은 OutputStream에 연결
            String file = fileroot.getText() + filename.getText();
            DataInputStream dis = new DataInputStream(new FileInputStream(new File(file)));
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            // 바이트단위로 읽어서 스트림
            int b= 0;
            while((b = dis.read()) != -1){
                dos.writeByte(b);
                dos.flush();
            }

            // 자원정리
            dis.close();
            dos.close();
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(this, "전송 완료");
    }

    private void play(){
        if(!filename.getText().endsWith(".mp3")){
            JOptionPane.showMessageDialog(getContentPane(), "Just Mp3 file");
            return;
        }
        if(temp != null){
            temp.close();
        }
        temp = new MP3(fileroot.getText() + filename.getText());
        temp.play();
    }
}
