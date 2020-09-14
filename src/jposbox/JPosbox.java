/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jposbox;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;
import static jposbox.PosBoxFrame.web1;

/**
 *
 * @author Jove
 */
public class JPosbox {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
                if ("Windows".equals(info.getName())) {
                    try {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(JPosbox.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(JPosbox.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(JPosbox.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(JPosbox.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        PosBoxFrame frame = new PosBoxFrame();
        frame.LoadDB();
        frame.setLocationRelativeTo(null);
        if (frame.getconf("startminimized").equals("yes")) frame.tray();
        else frame.setVisible(true);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                frame.web1.StopServer();
                frame.web2.StopServer();
            }
        });
    }
    
}
