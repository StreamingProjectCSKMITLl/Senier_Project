/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RTSP_test;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NattapatN
 */
public class Server {
    
    static String fileName;
    static String status;
    static BufferedReader RTSPBufferedReader;
    static BufferedWriter RTSPBufferedWriter;
    final static String CRLF = "\r\n";
    static int INIT;
    static InetAddress ClientIPAddr;
    static Socket RTSPsocket;
    static int state;
    
    public static void main(String [] args){
       int RTSPport = 8554;// Integer.parseInt(argv[0]);

        //Initiate TCP connection with the client for the RTSP session
        ServerSocket listenSocket;
        try {
            listenSocket = new ServerSocket(RTSPport);
            RTSPsocket = listenSocket.accept();
            listenSocket.close();
            //Get Client IP address
            ClientIPAddr = RTSPsocket.getInetAddress();
            //Initiate RTSPstate
            state = INIT;

            //Set input and output stream filters:
            RTSPBufferedReader = new BufferedReader(new InputStreamReader(RTSPsocket.getInputStream()));
            RTSPBufferedWriter = new BufferedWriter(new OutputStreamWriter(RTSPsocket.getOutputStream()));
            String request = parse_RTSP_request(); //blocking
            System.out.println(request);
            
        } catch (IOException ex) {
            Logger.getLogger(Server1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static private String parse_RTSP_request() {
        String request="";
        try {
            request = RTSPBufferedReader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Server1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (request);
    }

    private void send_RTSP_response() {
        try {
        } catch (Exception ex) {
            System.out.println("Exception caught: " + ex);
            System.exit(0);
        }
    }
    
}
