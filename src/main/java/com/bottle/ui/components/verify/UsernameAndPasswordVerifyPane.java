package com.bottle.ui.components.verify;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.bottle.business.common.service.IHttpClientHelper;
import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.AdminVO;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.data.service.IConfigurationManager;
import com.bottle.business.data.service.IProductionDataManager;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.ui.components.common.FontButton;
import com.bottle.ui.components.common.FontLabel;
import com.bottle.ui.components.common.MyTextField;

@Component
public class UsernameAndPasswordVerifyPane extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField usernameTextField;
	private JTextField passwordTextField;
	private JDialog dlg;
	
	@Autowired
	private IHttpClientHelper httpHelper;
	
	@Autowired
	private IMessageQueueManager messageManager;
	
	@Autowired
	private IConfigurationManager configurationManager;
	
	@Autowired
	private IProductionDataManager productionDataManager;
	
	public UsernameAndPasswordVerifyPane() {
		setLayout(null);
		
		final MyKeyboardPanel keyboard = new MyKeyboardPanel();
		keyboard.setLocation(10, 307);
		keyboard.setSize(815, 244);
		this.add(keyboard);

		JLabel label = new FontLabel("\u7528\u6237\u540D", 24);
		label.setBounds(250, 79, 103, 56);
		add(label);
		
		usernameTextField = new MyTextField(24);
		usernameTextField.setBounds(387, 77, 219, 58);
		add(usernameTextField);
		usernameTextField.setColumns(10);
		usernameTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				keyboard.setTextField(usernameTextField);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}});
		
		JLabel label_1 = new FontLabel("\u5BC6\u7801", 24);
        label_1.setBounds(250, 148, 103, 48);
        add(label_1);
        
       
        
        passwordTextField = new MyTextField(24);
        passwordTextField.setColumns(10);
        passwordTextField.setBounds(387, 146, 219, 58);
        passwordTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				keyboard.setTextField(passwordTextField);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}});
        
        add(passwordTextField);
        
        JButton btnNewButton = new FontButton("\u767B       \u9646", 24);
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		adminLogin();
        	}
        });
        btnNewButton.setBounds(250, 227, 356, 56);
        add(btnNewButton);
	}
	
	public void setDlg(JDialog dlg) {
		this.dlg = dlg;
	}

	public void adminLogin() {
		final String url = getURL();
		JSONObject json = new JSONObject();
		json.put(ICommonConstants._UI_Name_Key_, usernameTextField.getText());
		json.put(ICommonConstants._UI_Password_Key_, passwordTextField.getText());

		boolean isLogined = true;

		try {
			JSONObject rtnJSON = httpHelper.postJSON(url, json);
			final Object errorCodeObj = rtnJSON.get("errorCode");
			if (null == errorCodeObj || (false == errorCodeObj instanceof Integer)) {
				isLogined = false;
			}
			else {
				final long errorCode = (Integer)errorCodeObj;
				if (errorCode == 0) {
					isLogined = true;
					
					JSONObject dataJSON = (JSONObject)rtnJSON.get("data");
					if (null != dataJSON) {
						AdminVO adminVO = dataJSON.toJavaObject(AdminVO.class);
						productionDataManager.setAdminVO(adminVO);
					}
				}
				else {
					isLogined = false;
				}
			}						
		} catch (Exception e) {
			isLogined = false;
			e.printStackTrace();
		}
		
		if (true == isLogined) {            
			final MessageVO vo = new MessageVO();
			vo.setMessageSource(MessageSourceEnum._MessageSource_MainFrame_);
			vo.setSubMessageType(ICommonConstants.SubMessageTypeEnum._SubMessageType_MainFrame_Panel_);
			vo.setParam1(ICommonConstants.MainFrameActivePanelEnum._MainFrame_ActivePanel_Admin_.getId());
			messageManager.push(vo);
			dlg.setVisible(false);
		}
	}
	
	public String getURL() {
		final StringBuilder buf = new StringBuilder();
		
		buf.append("http://")
		   .append(configurationManager.getConfigurationVO().getServerDomain())
		   .append(ICommonConstants._URL_Seperator_)
		   .append(ICommonConstants._Server_Name_)
		   .append(ICommonConstants._URL_Seperator_)
		   .append(ICommonConstants._URL_API_)
		   .append(ICommonConstants._URL_Seperator_)
		   .append(ICommonConstants._URL_UI_AdminLogin);
		
		return buf.toString();
	}
}
