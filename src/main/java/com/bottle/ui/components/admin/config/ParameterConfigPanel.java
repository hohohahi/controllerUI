package com.bottle.ui.components.admin.config;

import java.awt.Color;
import java.awt.Font;

import javax.annotation.PostConstruct;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.data.service.IConfigurationManager;
import com.bottle.business.data.vo.ConfigurationVO;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.ui.components.common.AbstractBasePanel;
import com.bottle.ui.components.common.FontLabel;

@Component
public class ParameterConfigPanel extends AbstractBasePanel {
	private static final long serialVersionUID = 1L;
	//serial communication
	JComboBox<?> serialPortNameComboBox = new JComboBox<Object>(new String[]{"COM1","COM2","COM3","COM4","COM5","COM6","COM7","COM8","COM9"});
	JComboBox<?> baudRateComboBox = new JComboBox<Object>(new String[]{"9600","19200","38400","57600","115200"});
	JComboBox<?> parityComboBox = new JComboBox<Object>(new String[]{"None","Even","Odd","Mark","Space"});
	JComboBox<?> dataBitsComboBox = new JComboBox<Object>(new String[]{"5","6","7","8"});
	JComboBox<?> stopBitsComboBox = new JComboBox<Object>(new String[]{"1","1.5","2"});
	
	//ping-pong
	JComboBox<?> networkPingSwitchCombox = new JComboBox<Object>(new Object[]{"ON", "OFF"});
	JComboBox<?> serialPortPingSwitchCombox = new JComboBox<Object>(new Object[]{"ON", "OFF"});
	JComboBox<?> pingPeriodCombox = new JComboBox<Object>(new Object[]{"15", "30", "60", "120"});
	
	//net communication
	JComboBox<?> serverIpAddressComboBox = new JComboBox<Object>(new Object[]{"109.205.93.209", "10.0.5.42"});
	JComboBox<?> serverPortComboBox = new JComboBox<Object>(new Object[]{"8080", "8585", "80"});
	JComboBox<?> serverDomaincomboBox = new JComboBox<Object>(new Object[]{"core-om-dev.everymatrix.com"});
	
	@Autowired
	private IConfigurationManager configurationManager;
	
	public ParameterConfigPanel() {
		setLayout(null);
		
		JPanel panel = new JPanel();
		
		panel.setBorder(new TitledBorder(null, "\u4E32\u53E3\u901A\u4FE1\u914D\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, 
						new Font("Microsoft JhengHei Light", Font.BOLD, 24), 
						new Color(0, 0, 255)));
		panel.setBounds(21, 22, 419, 202);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new FontLabel("\u7AEF\u53E3\u53F7");
		lblNewLabel.setBounds(22, 54, 64, 31);
		panel.add(lblNewLabel);
		
		serialPortNameComboBox.setBounds(117, 59, 94, 22);
		panel.add(serialPortNameComboBox);
		
		JLabel label = new FontLabel("\u6CE2\u7279\u7387");
		label.setBounds(22, 105, 64, 31);
		panel.add(label);

		baudRateComboBox.setBounds(117, 104, 94, 22);
		panel.add(baudRateComboBox);
		
		JLabel label_1 = new FontLabel("\u5947\u5076\u6821\u9A8C");
		label_1.setBounds(22, 147, 85, 31);
		panel.add(label_1);
		
		parityComboBox.setBounds(117, 151, 94, 22);
		panel.add(parityComboBox);
		
		JLabel label_2 = new FontLabel("\u6570\u636E\u4F4D");
		label_2.setBounds(221, 54, 64, 31);
		panel.add(label_2);
		
		dataBitsComboBox.setBounds(295, 59, 94, 22);
		panel.add(dataBitsComboBox);
		
		JLabel label_3 = new FontLabel("\u505C\u6B62\u4F4D");
		label_3.setBounds(221, 104, 64, 31);
		panel.add(label_3);
		
		stopBitsComboBox.setBounds(295, 103, 94, 22);
		panel.add(stopBitsComboBox);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "\u7F51\u7EDC\u901A\u4FE1\u914D\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, 
								new Font("Microsoft JhengHei Light", Font.BOLD, 24), 
								new Color(0, 0, 255)));
		panel_1.setBounds(21, 259, 419, 151);
		add(panel_1);
		
		FontLabel fntlblIp = new FontLabel("\u7AEF\u53E3\u53F7");
		fntlblIp.setHorizontalAlignment(SwingConstants.CENTER);
		fntlblIp.setText("IP");
		fntlblIp.setBounds(22, 46, 64, 31);
		panel_1.add(fntlblIp);
		
		
		serverIpAddressComboBox.setBounds(96, 54, 123, 22);
		panel_1.add(serverIpAddressComboBox);
		
		FontLabel fontLabel_1 = new FontLabel("\u6CE2\u7279\u7387");
		fontLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		fontLabel_1.setText("\u7AEF\u53E3");
		fontLabel_1.setBounds(240, 46, 64, 31);
		panel_1.add(fontLabel_1);
		
		serverPortComboBox.setBounds(314, 54, 69, 22);
		panel_1.add(serverPortComboBox);
		
		FontLabel fontLabel_5 = new FontLabel("\u7AEF\u53E3\u53F7");
		fontLabel_5.setText("\u57DF\u540D");
		fontLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		fontLabel_5.setBounds(22, 96, 64, 31);
		panel_1.add(fontLabel_5);
		
		
		serverDomaincomboBox.setBounds(96, 104, 287, 22);
		panel_1.add(serverDomaincomboBox);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(null, "\u5FC3\u8DF3\u68C0\u6D4B\u914D\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, 
								new Font("Microsoft JhengHei Light", Font.BOLD, 24), 
								new Color(0, 0, 255)));
		panel_2.setBounds(470, 22, 230, 202);
		add(panel_2);
		
		FontLabel fontLabel = new FontLabel("\u7AEF\u53E3\u53F7");
		fontLabel.setText("\u7F51\u7EDC\u5FC3\u8DF3");
		fontLabel.setBounds(22, 54, 85, 31);
		panel_2.add(fontLabel);
		
		networkPingSwitchCombox.setBounds(117, 59, 66, 22);
		panel_2.add(networkPingSwitchCombox);
		
		FontLabel fontLabel_2 = new FontLabel("\u6CE2\u7279\u7387");
		fontLabel_2.setText("\u4E32\u53E3\u5FC3\u8DF3");
		fontLabel_2.setBounds(22, 105, 85, 31);
		panel_2.add(fontLabel_2);
				
		serialPortPingSwitchCombox.setBounds(117, 104, 66, 22);
		panel_2.add(serialPortPingSwitchCombox);
		
		FontLabel fontLabel_3 = new FontLabel("\u5947\u5076\u6821\u9A8C");
		fontLabel_3.setText("\u5FC3\u8DF3\u5468\u671F");
		fontLabel_3.setBounds(22, 147, 85, 31);
		panel_2.add(fontLabel_3);
				
		pingPeriodCombox.setBounds(117, 151, 66, 22);
		panel_2.add(pingPeriodCombox);
		
		FontLabel fontLabel_4 = new FontLabel("\u5947\u5076\u6821\u9A8C");
		fontLabel_4.setText("\u79D2");
		fontLabel_4.setBounds(193, 147, 24, 31);
		panel_2.add(fontLabel_4);
	}

	@PostConstruct
	public void initUI() {
		initSerialConfiguration();
		initNetworkConfiguration();
		initPingConfiguration();
	}
	
	public void initPingConfiguration() {
		final ConfigurationVO configurationVO = configurationManager.getConfigurationVO();
		final boolean isNetworkPingOn = configurationVO.getIsNetworkPingOn();
		final boolean isSerialPingOn = configurationVO.getIsSerialPortPingOn();
		final long inteval = configurationVO.getPingInteval_InSecond();
		if (true == isNetworkPingOn) {
			networkPingSwitchCombox.setSelectedItem("ON");	
		}
		else {
			networkPingSwitchCombox.setSelectedItem("OFF");
		}
		
		if (true == isSerialPingOn) {
			serialPortPingSwitchCombox.setSelectedItem("ON");	
		}
		else {
			serialPortPingSwitchCombox.setSelectedItem("OFF");
		}
		
		pingPeriodCombox.setSelectedItem(String.valueOf(inteval));
	}
	
	public void initNetworkConfiguration() {
		final ConfigurationVO configurationVO = configurationManager.getConfigurationVO();
		final String ipAddress = configurationVO.getServerIP();
		final String domain = configurationVO.getServerDomain();
		final long port = configurationVO.getServerPort();
		serverIpAddressComboBox.setSelectedItem(ipAddress);
		serverPortComboBox.setSelectedItem(String.valueOf(port));
		serverDomaincomboBox.setSelectedItem(domain);
	}
	
	public void initSerialConfiguration() {
		final ConfigurationVO configurationVO = configurationManager.getConfigurationVO();
		final int baudRate = configurationVO.getSerialBaudRate();
		final int dataBits = configurationVO.getSerialDataBits();
		final int stopBits = configurationVO.getSerialStopBits();
		final int parity = configurationVO.getSerialParity();
		
		serialPortNameComboBox.setSelectedItem(configurationVO.getSerialPortName());
		baudRateComboBox.setSelectedItem(String.valueOf(baudRate));		
		dataBitsComboBox.setSelectedItem(String.valueOf(dataBits));
		stopBitsComboBox.setSelectedItem(String.valueOf(stopBits));
		
		String nameForParity = "";
		switch (parity) {
			case 0:{
				nameForParity = "None";
				break;
			}
			case 1:{
				nameForParity = "Even";
				break;
			}
			case 2:{
				nameForParity = "Odd";
				break;
			}
			case 3:{
				nameForParity = "Mark";
				break;
			}
			case 4:{
				nameForParity = "Space";
				break;
			}
		}
		
		parityComboBox.setSelectedItem(nameForParity);
	}
	
	@Override
	public void process(MessageVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MessageSourceEnum getMessageType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processChildMessage(MessageVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParent(java.awt.Component parent) {
		// TODO Auto-generated method stub
		
	}
}
