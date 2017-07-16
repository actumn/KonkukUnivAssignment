package Server;

import Model.Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Lee on 2015-06-19.
 */
public class UploadServer {
    /**
     * Created by Lee on 2015-06-05.
     */
    private static int uniqueId;

    ArrayList<UploadThread> al;

    public UploadServer(){
        al = new ArrayList<UploadThread>();

    }

    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(Main.portUpload);

            while (Server.keepGoing) {
                Socket socket = serverSocket.accept();

                if(!Server.keepGoing){
                    break;
                }
                UploadThread t = new UploadThread(socket);
                al.add(t);
                t.start();

            }

            //I was asked to stop
            serverSocket.close();;
            for(int i = 0; i < al.size(); ++i){
                UploadThread tc = al.get(i);
                tc.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized void remove(int id){
        //scan the array list until we found the Id
        for(int i = 0; i < al.size(); ++i){
            UploadThread ct = al.get(i);
            //found it
            if(ct.id == id){
                al.remove(i);
                return;
            }
        }
    }

    class UploadThread extends Thread{
        Socket socket;
        InputStream is;
        BufferedReader br;
        FileOutputStream out;

        File f;
        int id;
        UploadThread(Socket socket){
            this.socket = socket;

            //소켓으로부터 스트림 받기
            try {
                is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                //파일 객체 생성
                String fileName = br.readLine();
                f = new File(Main.fileRoot, fileName);

                //기록할 파일 연결
                out = new FileOutputStream(f);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                //보내온 파일 읽기
                int i = 0;
                while((i = is.read()) != -1){
                    out.write((char)i);
                }

            } catch (Exception e) {
                remove(id);
                close();
                e.printStackTrace();
            }

            remove(id);
            close();
        }

        public void close(){
            try{
                if(br != null)
                    br.close();
                if(is != null)
                    is.close();
                if(out != null)
                    out.close();
            } catch(IOException e){

            }
        }

    }
}
