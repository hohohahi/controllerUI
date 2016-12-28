package com.bottle.ui.components.player.sub;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.data.service.IProductionDataManager;
import com.bottle.business.data.vo.ProductionDataVO;
import com.bottle.business.data.vo.RealtimeStasticDataVO;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ILanguageConstants;
import com.bottle.ui.components.common.AbstractBasePanel;
import com.bottle.ui.components.common.FontLabel;
import com.bottle.ui.components.common.MyTableWrapper;

@Component
public class RealProductionInfoPanel extends AbstractBasePanel{
	private static final long serialVersionUID = 1L;
	
	JLabel TotalNumTitleLabel = new FontLabel("\u6295\u74F6\u603B\u6570", 48);
	JLabel totalNumLabel = new FontLabel("a", 48);
	JLabel ValidNumTitleLabel = new FontLabel("\u6709\u6548\u6295\u74F6\u6570", 48);
	JLabel validNumLabel = new FontLabel("b", 48);
	JLabel InvalidNumTitleLabel = new FontLabel("\u65E0\u6548\u6295\u74F6\u6570", 48);
	JLabel invalidNumLabel = new FontLabel("c", 48);
	JLabel MoneyTitleLabel = new FontLabel("\u8FD4\u5229\u91D1\u989D", 48);
	JLabel moneyLabel = new FontLabel("d", 48);
	
	private MyTableWrapper realCheckResultTableWrapper;
	private JTable realCheckResultTable;;
	
	@Autowired
	private IProductionDataManager productionDataManager;
	
	@Autowired
	public void initialize() {
		updateStatisticData();
	}
	
	
	@SuppressWarnings("serial")
	public MyTableWrapper createWrapper() {
		return new MyTableWrapper(new ArrayList<String>(){{add(ILanguageConstants._RealProductionInfoPanel_Order_);
														   add(ILanguageConstants._RealProductionInfoPanel_Timestamp_); 
														   add(ILanguageConstants._RealProductionInfoPanel_ModelName_); 
														   add(ILanguageConstants._RealProductionInfoPanel_BarCode_);
														   add(ILanguageConstants._RealProductionInfoPanel_Price_);}}, 
							              new ArrayList<Integer>(){{add(50); add(250); add(160); add(197); add(60);}}, new RealCheckResultListTableModel());
	}
	
	public RealProductionInfoPanel() {
		setLayout(null);
		
		realCheckResultTableWrapper = createWrapper();
	    this.realCheckResultTable = realCheckResultTableWrapper.getTable();
	    realCheckResultTable.setBounds(430, 48, 536, 388);
	    JScrollPane scrollPane_server=new JScrollPane(realCheckResultTable);
	    scrollPane_server.setSize(735, 424);
	    scrollPane_server.setLocation(486, 67);
	    this.add(scrollPane_server);
	    
		TotalNumTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TotalNumTitleLabel.setBounds(23, 41, 255, 69);
		add(TotalNumTitleLabel);
		totalNumLabel.setText("0");
				
		totalNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		totalNumLabel.setBounds(288, 41, 174, 69);
		add(totalNumLabel);
		
		ValidNumTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ValidNumTitleLabel.setBounds(23, 153, 255, 69);
		add(ValidNumTitleLabel);
		validNumLabel.setText("0");
		
		validNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		validNumLabel.setBounds(288, 153, 174, 69);
		add(validNumLabel);						
		
		InvalidNumTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		InvalidNumTitleLabel.setBounds(23, 268, 255, 69);
		add(InvalidNumTitleLabel);
		invalidNumLabel.setText("0");
				
		invalidNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		invalidNumLabel.setBounds(288, 268, 174, 69);
		add(invalidNumLabel);
		
		MoneyTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		MoneyTitleLabel.setBounds(23, 397, 255, 69);
		add(MoneyTitleLabel);
				
		moneyLabel.setText("0");
		moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		moneyLabel.setBounds(288, 397, 174, 69);
		add(moneyLabel);		
	    
	    FontLabel ResultDetailLabel = new FontLabel("\u65E0\u6548\u6295\u74F6\u6570", 36);
	    ResultDetailLabel.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 28));
	    ResultDetailLabel.setText("\u68C0\u6D4B\u7ED3\u679C\u8BE6\u60C5");
	    ResultDetailLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    ResultDetailLabel.setBounds(486, 7, 224, 49);
	    add(ResultDetailLabel);
	    
	    JButton button = new JButton("\u5220\u9664\u4E3B\u63A7\u4E0A\u6307\u5B9A\u6A21\u7248\u6210\u529F");
	    button.setBounds(55, 502, 89, 23);
	    add(button);
	}
	
	public void updateRealCheckResultTable() {
		realCheckResultTableWrapper.clear();
		realCheckResultTable.invalidate();

		//existedTemplateTableWrapper.setTableModel(new ModelListTableModel());
		
		final List<ProductionDataVO> historyList = productionDataManager.getHistoryRealtimeStasticData();
		long index = 0;
		for (final ProductionDataVO vo : historyList) {
			index++;
			realCheckResultTableWrapper.add(new RealCheckResultTableCandidate(index, vo.getTimestampStr(), vo.getTemplateName(), vo.getBarCode(), vo.getPrice()));
		}
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
		updateRealCheckResultTable();
	}

	@Override
	public void setParent(java.awt.Component parent) {
		// TODO Auto-generated method stub
		
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
}
