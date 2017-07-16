package Client; /**
 * Created by Lee on 2015-05-08.
 */
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;


public class MP3{
    private String filename;
    private Player player;
    private boolean keepGoing = true;

    // constructor that takes the name of an MP3 file
    public MP3(String filename) {
        this.filename = filename;
    }

    public void close(){
        if (player != null)
            player.close();
    }


    // play the MP3 file to the sound card
    public void play() {
        try {
            FileInputStream fis = new FileInputStream(filename);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
        }
        catch (Exception e) {
            System.out.println("Problem playing file " + filename);
            System.out.println(e);
        }

        // run in new thread to play in background
        new Thread(){
            public void run(){
                try{
                    player.play();
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }.start();

    }

    public void switching(){
        if (keepGoing)
            play();
        else
            close();
        keepGoing = !keepGoing;
    }

    public Player getPlayer(){
        return player;
    }

    // test client
}
