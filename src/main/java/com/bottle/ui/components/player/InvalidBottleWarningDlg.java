package com.bottle.ui.components.player;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ICommonConstants.SubMessageTypeEnum;
import com.bottle.ui.components.common.FontLabel;

@Component
public class InvalidBottleWarningDlg extends JDialog {
	private static final long serialVersionUID = 1L;

	@Autowired
	protected IMessageQueueManager messageManager;
	
	private long errorCode = 0L;
	private String errorMessage = "";
	private boolean isThoughtToBeShown = false;
	private FontLabel errorMessageLabel = new FontLabel("", 36); 
	public static void main(String[] args) {
		try {
			InvalidBottleWarningDlg dialog = new InvalidBottleWarningDlg();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public InvalidBottleWarningDlg() {
		this.setModal(true);
		setUndecorated(true);
		setBounds(100, 100, 600, 240);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		FontLabel lblNewLabel = new FontLabel("\u65E0\u6548\u74F6\uFF0C \u8BF7\u53CA\u65F6\u53D6\u51FA", 48);
		lblNewLabel.setBounds(53, 55, 493, 48);
		lblNewLabel.setForeground(Color.BLUE);
		getContentPane().add(lblNewLabel);
		
		FontLabel label = new FontLabel("\u9519\u8BEF\u539F\u56E0:", 36);
		label.setForeground(Color.RED);
		label.setBounds(20, 131, 181, 48);
		getContentPane().add(label);
		
		errorMessageLabel.setBounds(199, 131, 391, 48);
		errorMessageLabel.setForeground(Color.RED);
		getContentPane().add(errorMessageLabel);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		
		errorMessageLabel.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				setDlgVisible(false);
				final MessageVO message = new MessageVO();
				message.setMessageSource(MessageSourceEnum._MessageSource_PlayerPanel_);
				message.setSubMessageType(SubMessageTypeEnum._SubMessageType_PlayerPanel_InvalidBottleTakenAwayDetected_);
				messageManager.push(message);
				
				
				setDlgVisible(true);
				System.out.println("push InvalidBottleTakenAwayDetected message");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	public void setDlgVisible(boolean isVisible) {
		this.setVisible(isVisible);
	}
	
	public boolean isThoughtToBeShown() {
		return isThoughtToBeShown;
	}

	public void setThoughtToBeShown(boolean isThoughtToBeShown) {
		this.isThoughtToBeShown = isThoughtToBeShown;
	}

	public long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public void updateUI() {
		errorMessageLabel.setText(errorMessage);
		errorMessageLabel.invalidate();
		errorMessageLabel.repaint();
	}
}
