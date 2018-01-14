/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import java.io.File;


/**
 *
 * @author NattapatN
 */
public class Split {
    
    File file;
    
    public Split(File f){
        file =f;
    }
    
    public void start(){
        String [][] nic = getNetworkInterface();
    }
    
    
    
}
