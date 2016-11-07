package com.bottle.ui.components.player.sub;

import java.awt.Color;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.data.service.IProductionDataManager;
import com.bottle.business.data.vo.RealtimeStasticDataVO;
import com.bottle.ui.components.common.AbstractBasePanel;
import com.bottle.ui.components.common.FontLabel;

@Component
public class RealProductionInfoPanel extends AbstractBasePanel{
	private static final long serialVersionUID = 1L;
	
	private final JScrollPane scrollPane = new JScrollPane();
	JLabel TotalNumTitleLabel = new FontLabel("\u6295\u74F6\u603B\u6570", 36);
	JLabel totalNumLabel = new FontLabel("a", 36);
	JLabel ValidNumTitleLabel = new FontLabel("\u6709\u6548\u6295\u74F6\u6570", 36);
	JLabel validNumLabel = new FontLabel("b", 36);
	JLabel InvalidNumTitleLabel = new FontLabel("\u65E0\u6548\u6295\u74F6\u6570", 36);
	JLabel invalidNumLabel = new FontLabel("c", 36);
	JLabel MoneyTitleLabel = new FontLabel("\u8FD4\u5229\u91D1\u989D", 36);
	JLabel moneyLabel = new FontLabel("d", 36);
	JList<String> ResultDetailList = new JList<String>();
	
	@Autowired
	private IProductionDataManager productionDataManager;
	
	@Autowired
	public void initialize() {
		updateStatisticData();
	}
	
	public RealProductionInfoPanel() {
		setLayout(null);
		
		TotalNumTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TotalNumTitleLabel.setBounds(73, 43, 174, 69);
		add(TotalNumTitleLabel);
		totalNumLabel.setText("0");
				
		totalNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		totalNumLabel.setBounds(292, 43, 174, 69);
		add(totalNumLabel);
		
		ValidNumTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ValidNumTitleLabel.setBounds(73, 123, 214, 69);
		add(ValidNumTitleLabel);
		validNumLabel.setText("0");
		
		validNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		validNumLabel.setBounds(292, 123, 174, 69);
		add(validNumLabel);						
		
		InvalidNumTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		InvalidNumTitleLabel.setBounds(73, 203, 200, 69);
		add(InvalidNumTitleLabel);
		invalidNumLabel.setText("0");
				
		invalidNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		invalidNumLabel.setBounds(292, 203, 174, 69);
		add(invalidNumLabel);
		
		MoneyTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		MoneyTitleLabel.setBounds(73, 283, 174, 69);
		add(MoneyTitleLabel);
				
		moneyLabel.setText("0");
		moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		moneyLabel.setBounds(292, 283, 174, 69);
		add(moneyLabel);		
		
		ResultDetailList.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));
		ResultDetailList.setBackground(new Color(230, 230, 230));
		DefaultListModel<String> defaultListModel = new DefaultListModel<String>();  
		ResultDetailList.setModel(defaultListModel);
		scrollPane.setBounds(513, 103, 451, 243);	    
	    scrollPane.setViewportView(ResultDetailList);
	    add(scrollPane);
	    
	    FontLabel ResultDetailLabel = new FontLabel("\u65E0\u6548\u6295\u74F6\u6570", 36);
	    ResultDetailLabel.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 28));
	    ResultDetailLabel.setText("\u68C0\u6D4B\u7ED3\u679C\u8BE6\u60C5");
	    ResultDetailLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    ResultDetailLabel.setBounds(513, 56, 224, 49);
	    add(ResultDetailLabel);
	}

	public void updateStatisticData() {
		RealtimeStasticDataVO data = productionDataManager.getRealtimeStasticDataVO();
		totalNumLabel.setText(data.getTotalNum() + "");
		totalNumLabel.validate();
		validNumLabel.setText(data.getTotalValidNum() + "");
		validNumLabel.validate();
		invalidNumLabel.setText(data.getTotalInvalidNum() + "");
		invalidNumLabel.validate();
		moneyLabel.setText(data.getTotalMoney() + "");
		moneyLabel.validate();
	}
	
	@Override
	public void processChildMessage(MessageVO vo) {
		updateStatisticData();
	}

	@Override
	public void setParent(java.awt.Component parent) {
		// TODO Auto-generated method stub
		
	}
}
