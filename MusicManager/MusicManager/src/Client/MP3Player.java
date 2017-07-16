package Client;

import Model.Main;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by Lee on 2015-06-07.
 */
public class MP3Player {
    String filename;
    Player player;
    public MP3Player(String filename){
        this.filename = filename;
    }

    public void play(){
        try {
            Socket s = new Socket(Main.ip, Main.portPlay);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            System.out.println("파일명 : " + filename);
            bw.write(filename+"\n");
            bw.flush();

            InputStream is = s.getInputStream();
            player = new Player(is);
            player.play();

            System.out.println("Receive 완료");
            bw.close(); is.close(); s.close();
            bw = null; is = null; s= null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (player != null){
            player.close();
            player = null;
        }
    }

    public boolean isComplete(){
        return player!=null && player.isComplete();
    }
}
