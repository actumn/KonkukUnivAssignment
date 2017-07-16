package Server; /**
 * Created by Lee on 2015-05-06.
 */

import Model.ChatMessage;
import Model.Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
    The server that can be run both as a console application or a GUI
 */
public class Server{
    // a unique ID for each connection
    private static int uniqueId;
    //an ArrayList to keep the list of the Client
    private ArrayList<ClientThread> al;
    //IF I am in a GUI
    private ServerGUI sg;
    // to display time
    private SimpleDateFormat sdf;
    //the port number to listen for connection
    private int port;
    //the boolean that will be turned of to stop the server
    public static boolean keepGoing;

    /*
        server constructor that receive the port to listen to for connection as parameter
        in console
     */
    public Server(int port){
        this(port, null);
    }
    public Server(int port, ServerGUI sg){
        // GUI or not
        this.sg = sg;
        // the port
        this.port = port;

        //to display hh:mm:ss
        sdf = new SimpleDateFormat("HH:mm:ss");
        // ArrayList for the Client list
        al = new ArrayList<ClientThread>();
    }


    public void start(){
        keepGoing = true;
        /*
            create socket server and wait for connection requests
         */
        new Thread(){
            @Override
            public void run() {
                DeleteServer ds = new DeleteServer();
                ds.start();
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                PlayServer ps = new PlayServer();
                ps.start();
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                UploadServer us = new UploadServer();
                us.start();

            }
        }.start();

        try{
            //the socket used by the server
            ServerSocket serverSocket = new ServerSocket(port);
            
            //infinite loop to wait for connection
            while(keepGoing){
                //format message saying we are waiting
                display("Server waiting for Clients on port " + port + ".");

                Socket socket = serverSocket.accept();          //accept connection
                //if I was asked to stop
                if(!keepGoing){
                    break;
                }
                ClientThread t = new ClientThread(socket);      //make a thread of it
                al.add(t);                                      //save it in the ArrayList
                t.start();
            }

            //I was asked to stop
            try{
                serverSocket.close();;
                for(int i = 0; i < al.size(); ++i){
                    ClientThread tc = al.get(i);
                    try {
                        tc.sInput.close();
                        tc.sOutput.close();
                        tc.socket.close();
                    }
                    catch(IOException ioE){
                        //not much I can do
                    }
                }
            }
            catch(Exception e){
                display("Exception closing the server and clients: " + e);
            }
        }
        //something went bed
        catch (IOException e) {
            String msg = sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
            display(msg);
            //e.printStackTrace();
        }
    }

    /*
        For the GUI to stop the Server
     */
    protected void stop(){
        keepGoing = false;
        //connect to myself as Client to exit Statement
        // Socket socket = serverSocket.accept();
        try{
            new Socket(Main.ip, Main.portUpload);
            new Socket(Main.ip, Main.portPlay);
            new Socket(Main.ip, Main.portDelete);
            new Socket(Main.ip, port);
        }
        catch(Exception e){
            //nothing I can really do
        }
    }
    /*
           Display an Event (not a message) to the console or the GUI
     */
    private void display(String msg) {
        String time = sdf.format(new Date()) + " " + msg;
        if(sg == null){
            System.out.println(time);
        }
        else{
            sg.appendEvent(time + "\n");
        }
    }
    /*
        to broadcast a message to all Clients
     */
    private synchronized void broadcast(String message){
        // add HH:mm:ss and \n to the message
        String time = sdf.format(new Date());
        String messageLF = time + " " + message + "\n";
        // display message on console or GUI
        if(sg == null)
            System.out.print(messageLF);
        else
            sg.appendRoom(messageLF);           //append in the room window



        //we loop in reverse order in case we would have to remove a Client
        //because it has disconnected
        for(int i = al.size(); --i>= 0;){
            ClientThread ct = al.get(i);
            //try to write to the Client if it fails remove it from the list
            if(!ct.writeMsg(messageLF)){
                al.remove(i);
                display("Disconnected Client " + ct.username + " removed from list.");
            }
        }
    }

    public synchronized void remove(int id){
        //scan the array list until we found the Id
        for(int i = 0; i < al.size(); ++i){
            ClientThread ct = al.get(i);
            //found it
            if(ct.id == id){
                al.remove(i);
                return;
            }
        }
    }


    /*

        To run as a console application just open a console window and
        java server
        java server portNumber

        If the port number is not spcified 5560 is used
      */
    public static void main(String[] args){
        //start server on port 1500 unless a PortNumber is specified
        int portNumber = Main.portChat;
        switch (args.length){
            case 1 :
                try {
                    portNumber = Integer.parseInt(args[0]);
                }
                catch(Exception e){
                    System.out.println("Invalid port number.");
                    System.out.println("Usage is: > java Server [portNumber]");
                    return;
                }
            case 0 :
                break;
            default :
                System.out.println("Usage is: > java Server[portNumber]");
                return;
        }

        //create a server object and start it
        final Server server = new Server(portNumber);
        new Thread(){
            @Override
            public void run() {
                server.start();
            }
        }.start();
        System.out.println("if you want stop server type anything");
        java.util.Scanner sc = new java.util.Scanner(System.in);
        sc.nextLine();
        System.exit(0);
    }

    /*
            One instance of this thread will run for each client
     */
    private class ClientThread extends Thread{
        //the socket where to listen/talk
        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;
        //my unique id (easier for deconnection)
        int id;
        // the username of the Client
        String username;
        // the only type of message a will receive
        ChatMessage cm;
        // the date I connect
        String date;

        // Constructore
        ClientThread(Socket socket) {
            // a unique id
            id = ++uniqueId;
            this.socket = socket;
            // Creating both Data Stream
            System.out.println("Thread trying to create Object Input/Output Streams");
            try {
                //create output first
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());
                //read the username
                username = (String) sInput.readObject();
                display(username + " just connected");
            } catch (IOException e) {
                display("Exception creating new Input/output Steams: " + e);
                return;
            }
            //have to catch ClassNotFoundException
            //but I read a String, I am sure it will work
            catch (ClassNotFoundException e) {
            }
            date = new Date().toString() + "\n";
        }

            //what will run forever
        public void run() {
            //to loop until Logout
            boolean keepGoing = true;
            while (keepGoing) {
                //read a String (which is an object)
                try {
                    cm = (ChatMessage) sInput.readObject();
                } catch (IOException e) {
                    display(username + " Exception reading Streams: " + e);
                    break;
                } catch (ClassNotFoundException e) {
                    break;
                }
                //the message part of the Model.ChatMessage
                String message = cm.getMessage();

                //Switch on the type of message receive
                switch (cm.getType()) {
                    case ChatMessage.MESSAGE:
                        broadcast(username + ": " + message);
                        break;
                    case ChatMessage.LOGOUT:
                        display(username + " disconnected with a LOGOUT message.");
                        keepGoing = false;
                        break;
                    case ChatMessage.WHOISIN:
                        writeMsg("List of the users connected at " + sdf.format(new Date()) + "\n");
                        //scan al the users connected
                        for (int i = 0; i < al.size(); ++i) {
                            ClientThread ct = al.get(i);
                            writeMsg((i + 1) + ") " + ct.username + " since " + ct.date);
                        }
                        break;
                }
            }
            //remove myself from the arrayList containing the list of the
            //connected Clients
            remove(id);
            close();
        }

        //try to close everything
        private void close(){
            //try to close the connection
            try {
                if(sOutput != null)
                    sOutput.close();
            }
            catch (Exception e) {
                //e.printStackTrace();
            }
            try {
                if(sInput != null)
                    sInput.close();
            }
            catch (Exception e){

            }
            try {
                if(socket != null){
                    socket.close();
                }
            }
            catch(Exception e){

            }
        }

        /*
            Write a String to the Client output Stream
         */
        private boolean writeMsg(String msg){
            //if Client is still connected send the message to it
            if(!socket.isConnected()){
                close();
                return false;
            }
            //write the message to the steam
            try{
                sOutput.writeObject(msg);
            }
            //if an error occurs, do not abort just inform the user
            catch(IOException e){
                display("Error sending message to " + username);
                display(e.toString());
            }
            return true;
        }
    }
}