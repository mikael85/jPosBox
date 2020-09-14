/*
 *This Display class can use to show string(text) on a VFD Customer Display and etc.
 *This class accept only String inputs.
 *This source code implemented to use with a device which has SERIAL socket.
 *Before use this class you have to install serialport.dll and RXTXcomm.jar
 *For that you can download mfz-rxtx-2.2-20081207-win-x86.zip free on internet.
 *Unzip and follow instructions.
 *Visit http://velozit.blogspot.com for more details.
 *This demo is by gayan liyanaarachchi
 *Need to have a serial port on developing pc.
 *Design for EPSON ESC/POS command mode. 
 * 
 http://sangon.altervista.org/bin/mfz-rxtx-2.2-20081207-win-x64.rar
 */
package jposbox;

import gnu.io.*;                                      //import from RXTXcomm.jar
import java.io.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author All Open Source Developers
 * @version 1.0.0.0
 * @since 2014/12/22
 * @see instructions before start.
 */
public class CustomerDisplay {

    static Enumeration portList;
    static CommPortIdentifier portId;
    static SerialPort serialPort;
    static String serialCOM;
    static OutputStream outputStream;
    static boolean outputBufferEmptyFlag = false;

    void send_text_customer_display(String[] lines) {
        ClearDisplay();
        PrintFirstLine(lines[0]);
        PrintSecondLine(lines[1]);
    }

    @SuppressWarnings("SleepWhileInLoop")
    public void StartDisplay() {
        try {
            portId = CommPortIdentifier.getPortIdentifier(serialCOM);
        } catch (NoSuchPortException ex) {
            System.out.println("There is no such COM port.");
            Logger.getLogger(CustomerDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            serialPort = (SerialPort) portId.open("SimpleWrite", 2000);
        } catch (PortInUseException e) {
            System.out.println("Port is offline now.");
        }

        try {
            outputStream = serialPort.getOutputStream();

        } catch (IOException e) {

        }

        try {
            serialPort.setSerialPortParams(9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            System.out.println("Display is online now.");
        } catch (UnsupportedCommOperationException e) {

        }
        try {
            serialPort.notifyOnOutputEmpty(true);
        } catch (Exception e) {
            System.out.println("Error setting event notification");
            System.out.println(e.toString());
            System.exit(-1);
        }
    }

    public CustomerDisplay(String pSerialCOM) {
        serialCOM = pSerialCOM;
        StartDisplay();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                close();
            }            
        });
        init();//?
        ShowGreeting();
    }

    public void ClearDisplay() {
        try {
            outputStream.write(ESCPOS.SELECT_DISPLAY);
            outputStream.write(ESCPOS.VISOR_CLEAR);
            outputStream.write(ESCPOS.VISOR_HOME);
            outputStream.flush();
        } catch (IOException e) {

        }
    }

    public void PrintFirstLine(String text) {
        try {
            ClearDisplay();
            if (text.length() > 20) //Display can hold only 20 characters per line.Most of displays have 2 lines.
            {
                text = text.substring(0, 20);
            }
            outputStream.write(text.getBytes());
            outputStream.flush();

        } catch (IOException r) {
        }

    }

    public void PrintSecondLine(String text) {
        try {
            outputStream.write(ESCPOS.SELECT_DISPLAY);
            outputStream.write(ESCPOS.Down_Line);
            outputStream.write(ESCPOS.Left_Line);
            if (text.length() > 20) {
                text = text.substring(0, 20);
            }
            outputStream.write(text.getBytes());
            outputStream.flush();
        } catch (IOException y) {
            System.out.println("Failed to print second line because of :" + y);
        }
    }

    public void ShowGreeting() {
        String text1 = "Starting Customer   ";                              // 20 characters
        String text2 = "     Display -> OK! ";                              //20 characters
        ClearDisplay();
        PrintFirstLine(text1);
        PrintSecondLine(text2);
        try {
            Thread.sleep(5000);                                   //Greeting will dislpay 5 seconds.
        } catch (InterruptedException ex) {
            Logger.getLogger(CustomerDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
        ClearDisplay();
    }

    public void init() {
        try {
            outputStream.write(ESCPOS.Anim);
        } catch (IOException i) {
        }
    }

    public void close() {
        serialPort.close();
        System.exit(1);

    }

//    public static void main(String[] args) {
////////////////////////  THIS IS HOW USE THIS CLASS //////////////////////////
//        CustomerDisplay display = new CustomerDisplay();
//        display.StartDisplay();   //optimal choice is start when system start.
//        display.PrintFirstLine("  Java Open Source  ");
//        display.PrintSecondLine(" Say Hello TO World ");
//        try {
//            Thread.sleep(5000);     //wait 5 seconds.otherwise unable to see above outputs.   
//        } catch (InterruptedException ex) {
//            Logger.getLogger(CustomerDisplay.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        display.ShowGreeting();
//        display.close();     //optimal choice is close when exit from system.
////////////////////// JOIN TO SHARE KNOWLADGE ////////////////////////////////   
//    }
}
