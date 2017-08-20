package com.bottle.ui.components.verify;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.ui.components.common.FontLabel;
import com.bottle.ui.components.common.MyTabbedPane;
import com.bottle.ui.constants.IUIConstants;

@Component
public class VerifyDialog extends JDialog implements WindowListener{
	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedpane = new MyTabbedPane();
	private JLabel messageLabel = new FontLabel("", 24);
	private int expiredTimeInSeconds = 0;
	private Timer timer;
	
	@Autowired
	private UsernameAndPasswordVerifyPane verifyDialog_usernameAndPasswordVerifyPane;
	
	@Autowired
	private VideoBarCodeVerifyPanel verifyDialog_videoBarCodeVerifyPane;
	
	public VerifyDialog() {
		this.setModal(true);
		//setUndecorated(true);
		setBounds(100, 100, 1000, 647);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		tabbedpane.setBounds(0, 48, 980, 560);
		
		this.tabbedpane.setTabPlacement(2);
		getContentPane().add(tabbedpane);
		
		
		messageLabel.setBounds(125, 0, 717, 49);
		getContentPane().add(messageLabel);
		messageLabel.setOpaque(true);
		messageLabel.setForeground(new Color(255, 255, 255));
		messageLabel.setBackground(new Color(255, 0, 0));
		
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.addWindowListener(this);
		this.setIconImage(null);
		this.setTitle(ICommonConstants._version_);
	}
	
	@PostConstruct
	public void initialize() {
		tabbedpane.add("\u5BC6\u7801\u9A8C\u8BC1", verifyDialog_usernameAndPasswordVerifyPane); //serial command debug
		tabbedpane.add("\u4E8C\u7EF4\u7801\u9A8C\u8BC1", verifyDialog_videoBarCodeVerifyPane);   //model management
		
		verifyDialog_usernameAndPasswordVerifyPane.setDlg(this);
		verifyDialog_videoBarCodeVerifyPane.setDlg(this);
	}
	
	public void initExpireTimer() {
		expiredTimeInSeconds = 0;
		timer = new Timer();  
        timer.schedule(new TimerTask() {  
            public void run() {              	            	
            	showWarningLable(IUIConstants._VerifyPanel_ExpireTime - expiredTimeInSeconds);
            	if (IUIConstants._VerifyPanel_ExpireTime == expiredTimeInSeconds) {
            		timer.cancel();
            		exitVerify();
            	}
            	expiredTimeInSeconds++;
            }  
        }, 0, 1000); 
	}
	
	public void showWarningLable(int leftTime) {		
		final String name = "\u8BF7\u5728" + leftTime + "\u79D2\u5185\u5B8C\u6210\u9A8C\u8BC1";
		messageLabel.setText(name);
	}
	
	public void exitVerify() {
		MessageVO vo = new MessageVO();
		vo.setParam1(ICommonConstants.MainFrameActivePanelEnum._MainFrame_ActivePanel_Welcome_.getId());
		vo.setMessageSource(ICommonConstants.MessageSourceEnum._MessageSource_MainFrame_);
		vo.setSubMessageType(ICommonConstants.SubMessageTypeEnum._SubMessageType_MainFrame_Panel_);
		//messageManager.push(vo);
	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		expiredTimeInSeconds = 0;
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("window closed");
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		System.out.println("dialog active.");
		
		expiredTimeInSeconds = 0;
		initExpireTimer();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {	
		if (null != timer) {
			timer.cancel();
			timer = null;
		}
		
		System.out.println("dialog deactive.");
	}
}
