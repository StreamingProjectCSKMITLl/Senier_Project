/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testFile;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author NattapatN
 */
public class Server extends Thread {

    private ServerSocket ss;

    public Server(int port) {
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                Socket clientSock = ss.accept();
                saveFile(clientSock);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile(Socket clientSock) throws IOException {
        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
        FileOutputStream fos = new FileOutputStream("media/testOut.mp4");
        byte[] buffer = new byte[4096];

        int filesize = 1512312345; // Send file size in separate msg
        int read = 0;
        int totalRead = 0;
        int remaining = filesize;
        while ((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
            totalRead += read;
            remaining -= read;
            System.out.println("read " + totalRead + " bytes.");
            fos.write(buffer, 0, read);
        }

        fos.close();
        dis.close();
    }

    public static void main(String[] args) {
        Server fs = new Server(1988);
        fs.start();
    }
}