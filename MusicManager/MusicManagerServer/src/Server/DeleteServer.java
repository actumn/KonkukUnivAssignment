package Server;

import Model.Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Lee on 2015-06-19.
 */
public class DeleteServer {
    /**
     * Created by Lee on 2015-06-08.
     */
    private static int uniqueId;

    ArrayList<DeleteThread> al;

    public DeleteServer(){
        al = new ArrayList<DeleteThread>();
    }

    public void start(){
        try {
            System.out.println("DeleteServer start");
            ServerSocket serverSocket = new ServerSocket(Main.portDelete);

            while (Server.keepGoing) {
                Socket socket = serverSocket.accept();

                if(!Server.keepGoing){
                    break;
                }
                DeleteThread t = new DeleteThread(socket);
                al.add(t);
                t.start();
            }

            //I was asked to stop
            serverSocket.close();;
            for(int i = 0; i < al.size(); ++i){
                DeleteThread tc = al.get(i);
                tc.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void remove(int id){
        //scan the array list until we found the Id
        for(int i = 0; i < al.size(); ++i){
            DeleteThread ct = al.get(i);
            //found it
            if(ct.id == id){
                al.remove(i);
                return;
            }
        }
    }


    class DeleteThread extends Thread {
        Socket socket;
        InputStream is;
        BufferedReader br;
        File f;
        int id;

        DeleteThread(Socket socket) {
            this.socket = socket;
            id = ++uniqueId;
            try {
                is = socket.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));

                String filename = br.readLine();
                f = new File(Main.fileRoot, filename);
            } catch (IOException e) {
                remove(id);
                close();
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            f.delete();
            remove(id);
            close();
        }

        public void close(){
            try{
                if(br != null)
                    br.close();

                if(is != null)
                    is.close();

                if(socket != null)
                    socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
