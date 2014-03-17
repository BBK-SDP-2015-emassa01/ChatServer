/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatserver;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Esha
 */
public class ChatServer implements Runnable, ActionListener {
    private JFrame jfrm;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private JTextArea jta;
    private JScrollPane jscrip;
    private JTextField jtfInput;
    private JButton jbtnSend;
            
    public ChatServer(){
        jfrm = new JFrame("Chat Server");
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setLayout(new FlowLayout());
        jfrm.setSize(300,320);
        Thread myThread = new Thread(this);
        myThread.start();
        jta = new JTextArea(15,15);
        jta.setEditable(false);
        jta.setLineWrap(true);
        jscrip = new JScrollPane(jta);
        jtfInput = new JTextField(15);
        jtfInput.addActionListener(this);
        jbtnSend = new JButton("Send");
        jbtnSend.addActionListener(this);
        
        jfrm.getContentPane().add(jscrip);
        jfrm.getContentPane().add(jtfInput);
        jfrm.getContentPane().add(jbtnSend);
        jfrm.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("Send")|| ae.getSource() instanceof JTextField){
            try{
                oos.writeObject(jtfInput.getText() + "Server says" + jtfInput.getText() + "\n");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        
    }
    public void run(){
        try{
            serverSocket = new ServerSocket (4444);
            clientSocket = serverSocket.accept();
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());
            
            while(true){
                Object input = ois.readObject();
                jta.setText(jta.getText() + "Client says "+ (String) input+"\n");
                
                
            }
            
        }catch (IOException e){
            e.printStackTrace();
        
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        
            
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater (new Runnable(){
           public void run() {
                new ChatServer();
                }
        });
    }
}
