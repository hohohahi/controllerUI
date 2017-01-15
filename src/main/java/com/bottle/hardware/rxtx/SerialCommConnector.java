package com.bottle.hardware.rxtx;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.service.IOperationRecorder;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.common.vo.SerialCommandOperationVO;
import com.bottle.business.data.service.IProductionDataManager;
import com.bottle.business.data.vo.ConfigurationVO;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ICommonConstants.SubMessageTypeEnum;
import com.bottle.hardware.rxtx.command.IMachineCommandSender;
import com.bottle.hardware.rxtx.command.ISerialCommandSenderManager;
import com.bottle.hardware.rxtx.exception.NoSuchPort;
import com.bottle.hardware.rxtx.exception.NotASerialPort;
import com.bottle.hardware.rxtx.exception.PortInUse;
import com.bottle.hardware.rxtx.exception.ReadDataFromSerialPortFailure;
import com.bottle.hardware.rxtx.exception.SendDataToSerialPortFailure;
import com.bottle.hardware.rxtx.exception.SerialPortInputStreamCloseFailure;
import com.bottle.hardware.rxtx.exception.SerialPortOutputStreamCloseFailure;
import com.bottle.hardware.rxtx.exception.SerialPortParameterFailure;
import com.bottle.hardware.rxtx.exception.TooManyListeners;
import com.bottle.hardware.rxtx.vo.ByteArrayParseResultVO;
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
	private boolean isSerialPortReady = false;
	
	@Autowired
	private IOperationRecorder operationRecorder;
	
	@Autowired
	private IByteDataParser byteParser;
	
	@Autowired
	private ISerialCommandSenderManager senderManager;
	
	@Autowired
	private IProductionDataManager productionDataManager;
	
	@Autowired
	private IMessageQueueManager messageManager;
	
	@Override
	public void initialize() {
		super.initialize();
	}
	
	public void sendNotificationMessage(final boolean isSuccessful) {
		final MessageVO vo = new MessageVO();		
		vo.setMessageSource(MessageSourceEnum._MessageSource_MainFrame_);
		vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_MainFrame_SerialPortStatus_);
		
		long machineStatus = ICommonConstants._ConnectionStatus_Online_;
		if (true == isSuccessful) {
			machineStatus = ICommonConstants._ConnectionStatus_Online_;						
		}
		else {
			machineStatus = ICommonConstants._ConnectionStatus_Offline_;
		}
		
		vo.setParam1(machineStatus);	
		messageManager.push(vo);
	}
	
	public void initSerialPort() {
		try {
			final ConfigurationVO vo = super.configurationManager.getConfigurationVO();
			final String portName = vo.getSerialPortName();
			final int baudRate = vo.getSerialBaudRate();
			final int dataBits = vo.getSerialDataBits();
			final int stopBits = vo.getSerialStopBits();
			final int parity = vo.getSerialParity();
			serialPort = openPort("COM9", baudRate, dataBits, stopBits, parity);
			this.addListener();
			isSerialPortReady = true;			
		} catch (Throwable e) {
			isSerialPortReady = false;
			e.printStackTrace();
		} finally {
			productionDataManager.setIsSerialPortInitialized(isSerialPortReady);
			this.sendNotificationMessage(isSerialPortReady);
		}
	}
	
    public boolean getIsSerialPortReady() {
		return isSerialPortReady;
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
    
    public final SerialPort openPort(String portName, int baudrate, int dataBits, int stopBits, int parity) throws SerialPortParameterFailure, NotASerialPort, NoSuchPort, PortInUse {
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            CommPort commPort = portIdentifier.open(portName, 2000);

            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                
                try {                        
                    serialPort.setSerialPortParams(baudrate, dataBits, stopBits, parity);                              
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
    public void send(byte[] data, final IMachineCommandSender commandSender) throws SendDataToSerialPortFailure, SerialPortOutputStreamCloseFailure {
    	if (null == data || null == commandSender) {
    		throw new NullPointerException("data or command sender is null.");
    	}
        
    	OutputStream out = null;
        
        try {
            out = serialPort.getOutputStream();
            out.write(data);
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
        byte[] rtnBytes = null;

        byte[] readBuffer = new byte[4096*20];
        
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
                final SerialCommandOperationVO operationVO = new SerialCommandOperationVO();
                operationVO.setType(ICommonConstants.OperationTypeEnum._Operation_Type_SerialCommand_);
                operationVO.setDirection(ICommonConstants.SerialCommandOperationDirectionEnum._Operation_SerialCommand_Direction_Up_);
                operationVO.setTimestampStr(super.dateConverter.getCurrentTimestampInNineteenBitsInGMT());
                operationVO.setIsSuccess(false);
                final long startTime = System.currentTimeMillis();
                try {
                    if (serialPort == null) {
                        throw new NullPointerException("serialPort is null.");
                    }
                    else {
                    	byte[] data = read();                               
                        operationVO.setSrcInputArray(data);
                        
                        if (data == null || data.length < 1) {        
                            throw new RuntimeException("no available data.");
                        }
                        
                        final RxTxResponseVO responseVO = byteParser.parse(data);                        
                        final IMachineCommandSender sender = senderManager.getCommandListenerAndRemoveIt(responseVO.getCommandType());
                        if (null != sender) {
                        	sender.onReceive(responseVO);
                        }                        
                        else {
                        	//return money, or other status command.
                        }
                        ByteArrayParseResultVO parseResultVO = responseVO.getByteParseResultVO();
                        super.validateObject(parseResultVO);
                        operationVO.setPid(parseResultVO.getPid());
                        operationVO.setAid(parseResultVO.getAid());
                        operationVO.setData(parseResultVO.getContentArray());
                        operationVO.setDataLength(parseResultVO.getDataLength());
                        operationVO.setIsSuccess(true);                        
                    }                        
                    
                } catch (Throwable e) {
                	System.out.println("Error Message:" + e.getMessage());
                    e.printStackTrace();
                    operationVO.setIsSuccess(false);
                    operationVO.setErrorMessage(e.getMessage());
                } 
                finally {
                	operationVO.setSpentTime(System.currentTimeMillis() - startTime);
                	operationRecorder.log(operationVO);
                }
                
                break;
            }
        }
    }
}