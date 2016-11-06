package com.bottle.hardware.rxtx;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.hardware.rxtx.command.IMachineCommandSender;
import com.bottle.hardware.rxtx.exception.NoSuchPort;
import com.bottle.hardware.rxtx.exception.NotASerialPort;
import com.bottle.hardware.rxtx.exception.PortInUse;
import com.bottle.hardware.rxtx.exception.ReadDataFromSerialPortFailure;
import com.bottle.hardware.rxtx.exception.SendDataToSerialPortFailure;
import com.bottle.hardware.rxtx.exception.SerialPortInputStreamCloseFailure;
import com.bottle.hardware.rxtx.exception.SerialPortOutputStreamCloseFailure;
import com.bottle.hardware.rxtx.exception.SerialPortParameterFailure;
import com.bottle.hardware.rxtx.exception.TooManyListeners;
import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

@Service
public class SerialCommConnector extends AbstractBaseBean implements ISerialCommConnector, SerialPortEventListener{
	private SerialPort serialPort;
	
	private List<IMachineCommandSender> commandSenderList = new ArrayList<IMachineCommandSender>();
	
	@Autowired
	private IByteDataParser byteParser;
	
	@Override
	public void initialize() {
		super.initialize();
		initSerialPort();
	}
	
	public void initSerialPort() {
		try {
			serialPort = openPort(ICommonConstants._SerialPort_Name_, ICommonConstants._SerialPort_BauDRate_);
			this.addListener();
		} catch (Throwable e) {
			e.printStackTrace();
		}
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
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
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
    
    public boolean isSenderAlreadyInListenerList(final IMachineCommandSender commandSender) {
    	if (null == commandSenderList) {
    		throw new NullPointerException("commandSenderList is null.");
    	}
    	
    	return commandSenderList.contains(commandSender);
    }
    
    @Override
    public void send(byte[] data, final IMachineCommandSender commandSender) throws SendDataToSerialPortFailure, SerialPortOutputStreamCloseFailure {
    	if (null == data || null == commandSender) {
    		throw new NullPointerException("data or command sender is null.");
    	}
    	
        if (true == isSenderAlreadyInListenerList(commandSender)) {
        	throw new RuntimeException("commandSender is already in listener status. commandType:" + commandSender.getCommandType());
        }
        
    	OutputStream out = null;
        
        try {
            out = serialPort.getOutputStream();
            out.write(data);
            out.flush();
            
            addCommandResponseListener(commandSender);
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
        byte[] rtnBytes = null;

        byte[] readBuffer = new byte[4096];
        
        try {
        	in = serialPort.getInputStream();
            int bufflenth = in.available();
            
            int totalNum = 0;
            while (bufflenth != 0) {                            
                byte [] bytes = new byte[bufflenth];   
                int num = in.read(bytes);
                System.arraycopy(bytes, 0, readBuffer, totalNum, num);
                totalNum += num;
                
                try {
					Thread.sleep(10L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
                bufflenth = in.available();
            }
            
            rtnBytes = new byte[totalNum];
            System.arraycopy(readBuffer, 0, rtnBytes, 0, totalNum);            
        } catch (IOException e) {
        	e.printStackTrace();
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

        return rtnBytes;

    }
    
    public void addListener() throws TooManyListeners {
        try {
        	serialPort.addEventListener(this);
        	serialPort.notifyOnDataAvailable(true);
        	serialPort.notifyOnOutputEmpty(true);
        	serialPort.notifyOnCTS(true);
        	serialPort.notifyOnDSR(true);
        	serialPort.notifyOnRingIndicator(true);
        	serialPort.notifyOnCarrierDetect(true);
        	serialPort.notifyOnOverrunError(true);
        	serialPort.notifyOnParityError(true);
        	serialPort.notifyOnFramingError(true);
        	serialPort.notifyOnBreakInterrupt(true);
        } catch (TooManyListenersException e) {
            throw new TooManyListeners();
        }
    }

	@Override
	public void addCommandResponseListener(IMachineCommandSender commandSender) {
		if (null == commandSender) {
			throw new NullPointerException("command sender is null.");
		}
		
		commandSenderList.add(commandSender);
	}
	
	public IMachineCommandSender getCommandListenerAndRemoveIt(ICommonConstants.MachineCommandEnum commandType) {
		if (null == commandSenderList) {
			throw new NullPointerException("commandSenderList is null.");
		}
		
		IMachineCommandSender toBeRemovedSender = null;
		for (IMachineCommandSender commandSender : commandSenderList) {
			final ICommonConstants.MachineCommandEnum currentCommandType = commandSender.getCommandType();
			if (true == currentCommandType.equals(commandType)) {
				toBeRemovedSender = commandSender;
				break;
			}
		}
		
		if (null == toBeRemovedSender) {
			if (false == ICommonConstants.MachineCommandEnum._MachineCommand_ReturnResult_.equals(commandType)) {
				//no listener for this "Return  Result" command.  So exclude it
				throw new RuntimeException("there is no listener avaliable. commandType:" + commandType);
			}
		}
		else {
			commandSenderList.remove(toBeRemovedSender);
		}
		
		return toBeRemovedSender;
	}

	@Override
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
            case SerialPortEvent.DATA_AVAILABLE:  {                  
                byte[] data = null;
                
                try {
                    if (serialPort == null) {
                        throw new NullPointerException("serialPort is null.");
                    }
                    else {
                        data = read();                               
                                               
                        if (data == null || data.length < 1) {        
                            throw new RuntimeException("no available data.");
                        }
                        
                        final RxTxResponseVO responseVO = byteParser.parse(data);
                        final IMachineCommandSender sender = getCommandListenerAndRemoveIt(responseVO.getCommandType());
                        sender.onReceive(responseVO);
                    }                        
                    
                } catch (Throwable e) {
                    e.printStackTrace();
                }    
                
                break;
            }
        }
    }
}