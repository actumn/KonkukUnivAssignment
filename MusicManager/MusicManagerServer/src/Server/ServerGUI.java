package Server; /**
 * Created by Lee on 2015-05-06.
 */

import Model.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

// to execute that GUI type
// > java Server.ServerGUI
/*
    The server as a GUI
 */
public class ServerGUI extends JFrame implements ActionListener, WindowListener {

    private static final long serialVersionUID = 1L;

    //the stop and start buttons
    private JButton stopStart;
    //JText Area for the chat room and the events
    private JTextArea chat, event;
    //The port number
    private JTextField tPortNumber;
    //my Server.Server
    private Server server;

    // server constructor that receive the port to listen to for connection as parameter
    ServerGUI(){
        super("Server");
        server = null;
        // in the NorthPanel the PortNumber the Start and Stop buttons
        JPanel north = new JPanel();
        north.add(new JLabel("Port number: "));
        tPortNumber = new JTextField(" " + Main.portChat);
        north.add(tPortNumber);
        // to stop or start the server, we start with "Start"
        stopStart = new JButton("Start");
        stopStart.addActionListener(this);
        north.add(stopStart);
        add(north, BorderLayout.NORTH);

        //the event
        JPanel center = new JPanel(new GridLayout(2,1));
        chat = new JTextArea(80, 80);
        chat.setEditable(false);
        appendRoom("Chat room.\n");
        center.add(new JScrollPane(chat));
        event = new JTextArea(80,80);
        event.setEditable(false);
        appendEvent("Events log.\n");
        center.add(new JScrollPane(event));
        add(center);

        //need to be informed when the user click the close button on the frame
        addWindowListener(this);
        setSize(400,600);
        setVisible(true);
    }

    // append message to the two JTextArea
    // position at the end
    void appendRoom(String str){
        chat.append(str);
        chat.setCaretPosition(chat.getText().length() - 1);
    }
    void appendEvent(String str){
        event.append(str);
        event.setCaretPosition(chat.getText().length() - 1);
    }

    // start or stop where clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if(server != null){
            server.stop();
            server = null;
            tPortNumber.setEditable(true);
            stopStart.setText("Start");
            return;
        }
        // OK Start the server
        int port;
        try {
            port = Integer.parseInt(tPortNumber.getText().trim());
        }
        catch(Exception er){
            appendEvent("Invalied port number");
            return;
        }
        //create a new Server.Server
        server = new Server(port, this);
        //and start it as a thread
        new ServerRunning().start();
        stopStart.setText("Stop");
        tPortNumber.setEditable(false);
    }

    /*
    //entry point to start the Server.Server
    public static void main(String[] args){
        //start server default port 1500
        new Server.ServerGUI();
    }
    */


    /*
        IF the user click the X button to close the application
        I need to close the connection with the server to free the port
     */
    @Override
    public void windowClosing(WindowEvent e) {
        //if my Server.Server exist
        if(server != null){
            try{
                //ask the server to close the connection
                server.stop();
            }
            catch (Exception eClose){
            }
            server = null;
        }
        //dispose the frame;
        dispose();
        //System.exit(0);
    }

    // I can ignore the other WindowListener method
    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}

    /*
        A thread to run the Server.Server
     */
    class ServerRunning extends Thread{
        public void run(){
            //should execute until if fails
            server.start();

            //the server failed
            stopStart.setText("Start");
            tPortNumber.setEditable(true);
            appendEvent("Server.Server crashed\n");
            server = null;
        }
    }
}
