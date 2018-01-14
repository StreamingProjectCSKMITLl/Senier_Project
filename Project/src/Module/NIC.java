/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import static java.lang.System.out;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

/**
 *
 * @author NattapatN
 */
public class NIC {
    NetworkInterface [] a;
    public NIC(){
        
    }
    
    public NetworkInterface[] getNIC() throws SocketException{
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        int count = 1;
        for (NetworkInterface netint : Collections.list(nets)){
            count++;
//            displayInterfaceInformation(netint);
        }
        a=new NetworkInterface [count];
        count=0;
        for (NetworkInterface netint : Collections.list(nets)){
            a[count]=netint;
            count++;
        }
        return a;
    }
    
    static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        out.printf("Display name: %s\n", netint.getDisplayName());
        out.printf("Name: %s\n", netint.getName());
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            out.printf("InetAddress: %s\n", inetAddress);
        }
        out.printf("\n");
     }
    
}
