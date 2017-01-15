package com.bottle.ui.components.exit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bottle.business.data.vo.ConfigurationVO;
import com.bottle.ui.components.common.FontLabel;

public class ExitDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private JLabel messageLabel = new FontLabel("", 64);	
	private int expiredTimeInSeconds = 0;
	private ConfigurationVO configurationVO = new ConfigurationVO();
	
	public static void main(String[] args) {
		try {
			ExitDialog dialog = new ExitDialog(new ConfigurationVO());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ExitDialog(ConfigurationVO configurationVO) {
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setModal(true);
		setUndecorated(true);
		
		setTitle("\u786E\u5B9A\u8981\u9000\u51FA\u7A0B\u5E8F\u5417");
		setBounds(0, 100, 622, 96);
		setLocationRelativeTo(null);
		
		
		this.configurationVO = configurationVO;
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.CENTER);
			buttonPane.setLayout(null);
			
			buttonPane.setBackground(Color.DARK_GRAY);
			messageLabel.setBounds(10, 11, 585, 74);
			buttonPane.add(messageLabel);
		}
		
		initExpireTimer();
	}
	
	public ConfigurationVO getConfigurationVO() {
		return configurationVO;
	}

	public void setConfigurationVO(ConfigurationVO configurationVO) {
		this.configurationVO = configurationVO;
	}

	public void initExpireTimer() {
		
		Timer timer = new Timer();  
        timer.schedule(new TimerTask() {  
            public void run() {              	            	
            	showWarningLable(configurationVO.getExitWarningTime_InSeconds() - expiredTimeInSeconds);
            	if (configurationVO.getExitWarningTime_InSeconds() == expiredTimeInSeconds) {
            		timer.cancel();
            		hideDialog();
            		
            	}
            	expiredTimeInSeconds++;
            }  
        }, 0, 1000); 
	}
	
	public void hideDialog() {
		this.setVisible(false);
	}

	public void showWarningLable(int leftTime) {		
		final String name = "  " + leftTime + "\u79D2\u540E\u9000\u51FA\u4E3B\u7A0B\u5E8F";
		messageLabel.setText(name);
		messageLabel.setForeground(Color.RED);
	}
}
