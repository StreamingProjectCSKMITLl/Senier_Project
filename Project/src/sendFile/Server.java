/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sendFile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NattapatN
 */
public class Server {
    
    public static void main(String [] args){
        try {
            ServerSocket sSocket = new ServerSocket(9000);
            System.out.println("Server Start");
            
            while(true){
                Socket socket = sSocket.accept();
                System.out.println("Client connected!!");
                DataOutputStream out = null;
                
                DataInputStream in = new DataInputStream(socket.getInputStream());
                int bufferSize = in.readInt();
                long size = in.readLong();
                int length = in.readInt();
                byte[] data = new byte[length];
                in.readFully(data);
                String name = new String(data,"UTF-8");
                System.out.println("name : "+name );
                System.out.println("size : "+size);
                
                ServerSocket sSocket2 = new ServerSocket(0);
                out = new DataOutputStream(socket.getOutputStream());
                int port = sSocket2.getLocalPort();
                out.writeInt(port);
//                addRow(name,size+"","0%","","","Download");
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
