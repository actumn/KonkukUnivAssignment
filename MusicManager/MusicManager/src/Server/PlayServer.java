package Server;

import Model.Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Lee on 2015-06-19.
 */
public class PlayServer {
    /**
     * Created by Lee on 2015-06-07.
     */
    private static int uniqueId;

    ArrayList<PlayThread> al;

    public PlayServer(){
        al = new ArrayList<PlayThread>();
    }

    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(Main.portPlay);

            while (Server.keepGoing) {
                Socket socket = serverSocket.accept();

                if(!Server.keepGoing){
                    break;
                }
                PlayThread t = new PlayThread(socket);
                al.add(t);
                t.start();

            }

            //I was asked to stop
            serverSocket.close();;
            for(int i = 0; i < al.size(); ++i){
                PlayThread tc = al.get(i);
                tc.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public synchronized void remove(int id){
        //scan the array list until we found the Id
        for(int i = 0; i < al.size(); ++i){
            PlayThread ct = al.get(i);
            //found it
            if(ct.id == id){
                al.remove(i);
                return;
            }
        }
    }



    class PlayThread extends Thread {
        Socket socket;
        DataInputStream dis;
        DataOutputStream dos;
        InputStream is;
        BufferedReader br;
        int id;

        public PlayThread(Socket socket){
            this.socket = socket;
            id = ++uniqueId;

            try{
                is = socket.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                String filename = br.readLine();

                dis = new DataInputStream(new FileInputStream(new File(Main.fileRoot+"\\"+filename)));
                dos = new DataOutputStream(socket.getOutputStream());
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }


        @Override
        public void run() {
            try {
                int b = 0;
                while((b = dis.read()) != -1){
                    if(!socket.isConnected()) {
                        System.out.println("연결 끊김");
                        break;
                    }

                    dos.writeByte(b);
                    dos.flush();
                }

                System.out.println("Send 완료");
                close();

            } catch (IOException e) {
                System.out.println("연결 끊김");
                remove(id);
                close();
                //e.printStackTrace();
            }
            remove(id);
            close();
        }


        private void close(){
            try{
                if(dis != null)
                    dis.close();

                if(dos != null)
                    dos.close();

                if(is != null)
                    is.close();

                if(socket != null)
                    socket.close();
            }
            catch(Exception e){

            }
        }

    }
}
