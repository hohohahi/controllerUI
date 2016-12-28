package com.bottle.ui.components.admin;

import javax.annotation.PostConstruct;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.ui.components.admin.config.ParameterConfigPanel;
import com.bottle.ui.components.admin.serialdebug.SerialCommandDebugPanel;
import com.bottle.ui.components.admin.template.TemplateManagementPanel;
import com.bottle.ui.components.common.AbstractBasePanel;

@Component
public class AdminPanel extends AbstractBasePanel {
	private static final long serialVersionUID = 1L;

	@Autowired
	private JTabbedPane tabbedpane;
	
	@Autowired
	private SerialCommandDebugPanel serialCommandDebugPanel;
	
	@Autowired
	private ParameterConfigPanel parameterConfigPanel;
	
	@Autowired
	private TemplateManagementPanel templateManagementPanel;
	
	public AdminPanel() {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(null);
	}
	
	public void initTab() {
		tabbedpane.setBounds(2, 10, 1480, 900);
		
		tabbedpane.add("\u4E32\u53E3\u547D\u4EE4\u8C03\u8BD5", serialCommandDebugPanel); //serial command debug
		tabbedpane.add("\u6A21\u7248\u7BA1\u7406", templateManagementPanel);   //model management
		tabbedpane.add("\u64CD\u4F5C\u65E5\u5FD7", new JLabel(""));   //operation log
		tabbedpane.add("\u53C2\u6570\u8BBE\u7F6E", parameterConfigPanel);   //parameter setting
		tabbedpane.add("\u8FD4\u56DE\u4E3B\u754C\u9762", new JLabel(""));  //return to main UI
		tabbedpane.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				int index = tabbedpane.getSelectedIndex();
				if (index == (tabbedpane.getTabCount()-1)) {
					MessageVO vo = new MessageVO();
					vo.setParam1(ICommonConstants.MainFrameActivePanelEnum._MainFrame_ActivePanel_Welcome_.getId());
					vo.setMessageSource(ICommonConstants.MessageSourceEnum._MessageSource_MainFrame_);
					vo.setSubMessageType(ICommonConstants.SubMessageTypeEnum._SubMessageType_MainFrame_Panel_);
					messageManager.push(vo);
					
					tabbedpane.setSelectedIndex(0);
				}
				
				templateManagementPanel.flush();
			}
			
		});
		this.tabbedpane.setTabPlacement(2);
		add(tabbedpane);			
	}
	
	@PostConstruct
	public void initialize() {
		super.addMessageListener();
		initTab();
	}

	@Override
	public void process(MessageVO vo) {
		if (null == vo) {
			throw new NullPointerException("vo is null.");
		}
		
		final ICommonConstants.MessageSourceEnum messageSource =vo.getMessageSource();
		if (false == messageSource.equals(getMessageType())) {
			return;
		}
	}

	@Override
	public MessageSourceEnum getMessageType() {
		return MessageSourceEnum._MessageSource_AdminPanel_;
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
