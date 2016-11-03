package com.bottle.hardware.rxtx;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import javax.swing.JOptionPane;

import org.springframework.stereotype.Service;

import com.bottle.common.AbstractBaseBean;
import com.bottle.hardware.rxtx.exception.NoSuchPort;
import com.bottle.hardware.rxtx.exception.NotASerialPort;
import com.bottle.hardware.rxtx.exception.PortInUse;
import com.bottle.hardware.rxtx.exception.ReadDataFromSerialPortFailure;
import com.bottle.hardware.rxtx.exception.SendDataToSerialPortFailure;
import com.bottle.hardware.rxtx.exception.SerialPortInputStreamCloseFailure;
import com.bottle.hardware.rxtx.exception.SerialPortOutputStreamCloseFailure;
import com.bottle.hardware.rxtx.exception.SerialPortParameterFailure;
import com.bottle.hardware.rxtx.exception.TooManyListeners;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

@Service
public class SerialCommConnector extends AbstractBaseBean implements ISerialCommConnector {
	private SerialPort serialPort;
	
	@Override
	public void initialize() {
		super.initialize();
		initSerialPort();
	}
	
	public void initSerialPort() {
		//List<String> portList = findPort();
		//serialPort = openPort();
		//addListener
	}
	
    @SuppressWarnings("unchecked")
	public  final ArrayList<String> findPort() {

        //get all available serial port
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();    
        
        ArrayList<String> portNameList = new ArrayList<>();

        while (portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();
            portNameList.add(portName);
        }

        return portNameList;
    }
    
    public final SerialPort openPort(String portName, int baudrate) throws SerialPortParameterFailure, NotASerialPort, NoSuchPort, PortInUse {
        try {
            //get portby name
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);

            //open a port with name, and set a timeout 
            CommPort commPort = portIdentifier.open(portName, 2000);

            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                
                try {                        
                    serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);                              
                } catch (UnsupportedCommOperationException e) {  
                    throw new SerialPortParameterFailure();
                }
                
                return serialPort;     
            }        
            else {
                throw new NotASerialPort();
            }
        } catch (NoSuchPortException e1) {
          throw new NoSuchPort();
        } catch (PortInUseException e2) {
            throw new PortInUse();
        }
    }

    public  void closePort(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.close();
            serialPort = null;
        }
    }
    
    @Override
    public void send(byte[] order) throws SendDataToSerialPortFailure, SerialPortOutputStreamCloseFailure {
        OutputStream out = null;
        
        try {
            
            out = serialPort.getOutputStream();
            out.write(order);
            out.flush();
            
        } catch (IOException e) {
            throw new SendDataToSerialPortFailure();
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }                
            } catch (IOException e) {
                throw new SerialPortOutputStreamCloseFailure();
            }
        }
        
    }
    
    @Override
    public byte[] read() throws ReadDataFromSerialPortFailure, SerialPortInputStreamCloseFailure {
        InputStream in = null;
        byte[] bytes = null;

        try {
            
            in = serialPort.getInputStream();
            int bufflenth = in.available();        
            
            while (bufflenth != 0) {                             
                bytes = new byte[bufflenth];   
                in.read(bytes);
                bufflenth = in.available();
            } 
        } catch (IOException e) {
            throw new ReadDataFromSerialPortFailure();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch(IOException e) {
                throw new SerialPortInputStreamCloseFailure();
            }

        }

        return bytes;

    }
    
    public void addListener(SerialPortEventListener listener) throws TooManyListeners {
        try {
        	serialPort.addEventListener(listener);
        	serialPort.notifyOnDataAvailable(true);
        	serialPort.notifyOnBreakInterrupt(true);

        } catch (TooManyListenersException e) {
            throw new TooManyListeners();
        }
    }
    
    private class SerialListener implements SerialPortEventListener {
    	public void serialEvent(SerialPortEvent serialPortEvent) {
            
            switch (serialPortEvent.getEventType()) {
                case SerialPortEvent.BI: 
                    JOptionPane.showMessageDialog(null, "communication with serial port is interruptted", "Error", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case SerialPortEvent.OE: 
                case SerialPortEvent.FE:
                case SerialPortEvent.PE: 
                case SerialPortEvent.CD:
                case SerialPortEvent.CTS:
                case SerialPortEvent.DSR:
                case SerialPortEvent.RI:
                case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                    break;            
                case SerialPortEvent.DATA_AVAILABLE:
                    
                    //System.out.println("found data");
                    byte[] data = null;
                    
                    try {
                        if (serialPort == null) {
                            throw new NullPointerException("serialPort is null.");
                        }
                        else {
                            data = read();    
                            //System.out.println(new String(data));
                                                   
                            if (data == null || data.length < 1) {        
                                throw new RuntimeException("no available data.");
                            }
                            else {
                                String dataOriginal = new String(data);
                                String dataValid = "";  
                                String[] elements = null;    
                                
                                if (dataOriginal.charAt(0) == '*') {                                
                                    dataValid = dataOriginal.substring(1);
                                    elements = dataValid.split(" ");
                                    if (elements == null || elements.length < 1) {
                                    	throw new RuntimeException("error during parsing.");
                                    }
                                    else {
                                        try {
                                           
                                        } catch (ArrayIndexOutOfBoundsException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }    
                                }
                            }
                            
                        }                        
                        
                    } catch (ReadDataFromSerialPortFailure | SerialPortInputStreamCloseFailure e) {
                        JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0); 
                    }    
                    
                    break;

            }

        }
    }
}