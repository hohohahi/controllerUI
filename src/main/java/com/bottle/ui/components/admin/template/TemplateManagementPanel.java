package com.bottle.ui.components.admin.template;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.common.vo.PositionInfoVO;
import com.bottle.business.data.service.IProductionDataManager;
import com.bottle.business.template.service.ITemplateService;
import com.bottle.business.template.vo.TemplateVO;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.hardware.rxtx.command.ICommandSelector;
import com.bottle.hardware.rxtx.command.IMachineCommandSender;
import com.bottle.ui.components.common.AbstractBasePanel;
import com.bottle.ui.components.common.FontButton;
import com.bottle.ui.components.common.FontLabel;
import com.bottle.ui.components.common.MyTableWrapper;
import javax.swing.JButton;

@Component
public class TemplateManagementPanel extends AbstractBasePanel {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ITemplateService templateService;
	
	@Autowired
	private ICommandSelector machineCommandSelector;
	
	@Autowired
	private IProductionDataManager productionDataManager;
	
	private JTable existedTemplateTable;
	private MyTableWrapper existedTemplateTableWrapper;
	List<TemplateVO> serverTemplateList = new ArrayList<TemplateVO>();
	private int currentSel_InServerTemplateTable = -1; 
	
	private JTable machineTemplateTable;
	private MyTableWrapper machineTemplateTableWrapper;
	List<TemplateVO> machineTemplateList = new ArrayList<TemplateVO>();
	private int currentSel_InMachineTemplateTable = -1; 
	
	private JLabel barCodeLabel = new FontLabel("", 14);
	private JLabel isMetalLabel = new FontLabel("", 14);
	private JLabel weightLabel = new FontLabel("", 14);
	private JLabel imageLabel = new FontLabel("", 14);
	private FontButton learnTemplateButton = new FontButton("\u5B66\u4E60\u6A21\u7248");
	private FontButton uploadTemplateButton = new FontButton("\u4E0A\u4F20\u6A21\u7248");
	private FontButton deleteTemplateFromServerButton = new FontButton("\u4ECE\u670D\u52A1\u5668\u5220\u9664\u6A21\u677F");
	private FontButton deleteTemplateFromMachineButton = new FontButton("\u4ECE\u4E3B\u63A7\u5220\u9664\u6A21\u677F");
	private FontButton flushServerTemplateButton = new FontButton("\u5237\u65B0");
	private FontButton downloadToMachineButton = new FontButton("\u4E0B\u8F7D\u5230\u4E3B\u63A7");
	private FontButton viewLearnTemplateImageButton = new FontButton("\u6D4B\u8BD5\u4E0A\u4F20\u6A21\u7248");
	private FontButton queryMachineTemplateListButton = new FontButton("\u66F4\u65B0");

	public TemplateManagementPanel() {
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u6A21\u677F\u5B66\u4E60\u533A\u57DF", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 1500, 761, 174);
		add(panel);
		panel.setLayout(null);
		

		learnTemplateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_LearnTemplate_);
				sender.send();
				learnTemplateButton.setEnabled(false);
			}
		});
		learnTemplateButton.setBounds(560, 43, 188, 33);
		panel.add(learnTemplateButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Content", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 23, 491, 129);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new FontLabel("\u4E8C\u7EF4\u7801", 14);
		lblNewLabel_1.setBounds(6, 16, 79, 37);
		panel_1.add(lblNewLabel_1);
		
		barCodeLabel.setBounds(90, 20, 121, 33);
		panel_1.add(barCodeLabel);
		
		JLabel label_1 = new FontLabel("\u662F\u5426\u91D1\u5C5E", 14);
		label_1.setBounds(200, 16, 79, 37);
		panel_1.add(label_1);
		isMetalLabel.setBounds(289, 16, 39, 33);
		panel_1.add(isMetalLabel);
		
		JLabel label_3 = new FontLabel("\u91CD\u91CF", 14);
		label_3.setBounds(335, 16, 79, 37);
		panel_1.add(label_3);
		
		weightLabel.setBounds(406, 20, 46, 33);
		panel_1.add(weightLabel);
		
		JLabel label_5 = new FontLabel("\u56FE\u5F62\u7279\u5F81\u7801", 14);
		label_5.setBounds(6, 64, 79, 14);
		panel_1.add(label_5);
		
		imageLabel.setBounds(115, 64, 473, 56);
		panel_1.add(imageLabel);
		
		
		uploadTemplateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				try {
					uploadTemplateButton.setEnabled(false);									
					final TemplateVO newestTemplate = productionDataManager.getNewestLearnedTemplate();
					System.out.println(newestTemplate);
					newestTemplate.setName("abc");
					templateService.uploadTemplate(newestTemplate);
					uploadTemplateButton.setEnabled(true);					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		uploadTemplateButton.setBounds(560, 103, 188, 33);
		panel.add(uploadTemplateButton);
		
		FontButton cmndbtnC = new FontButton("\u6D4B\u8BD5\u4E0A\u4F20\u6A21\u7248");
		cmndbtnC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final TemplateVO template = new TemplateVO();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Timestamp ts = null;
				template.setBarCode(generateRandomBarCode());		
				ts = Timestamp.valueOf(df.format(new Date()));				
				List<PositionInfoVO> positionList = new ArrayList<PositionInfoVO>();
				positionList.add(new PositionInfoVO(1L, 2L));
				positionList.add(new PositionInfoVO(3L, 4L));
				positionList.add(new PositionInfoVO(5L, 6L));
				positionList.add(new PositionInfoVO(7L, 8L));
				positionList.add(new PositionInfoVO(9L, 0L));
				template.setPosNum(positionList.size());
				template.setCreatedDate(ts);
				template.setCreatedBy(2L);
				template.setPositionInfoList(positionList);
				template.setIsMetal(1L);
				template.setDescription("test");
				template.setName("farmer water");
				template.setModifiedBy(3L);
				template.setModifiedDate(ts);
				template.setWeight(4L);
			
				System.out.println(template);
				templateService.uploadTemplate(template);
			}
		});

		cmndbtnC.setBounds(792, 43, 156, 33);
		panel.add(cmndbtnC);
		viewLearnTemplateImageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final TemplateVO newestTemplate = productionDataManager.getNewestLearnedTemplate();
				showTemplateGraphics(newestTemplate.getPositionInfoList());
			}
		});
		

		viewLearnTemplateImageButton.setText("\u67E5\u770B\u5B66\u4E60\u8F6E\u5ED3");
		viewLearnTemplateImageButton.setBounds(792, 103, 156, 33);
		panel.add(viewLearnTemplateImageButton);
				
		existedTemplateTableWrapper = createWrapper();
	    this.existedTemplateTable = existedTemplateTableWrapper.getTable();
	    existedTemplateTable.setBounds(430, 48, 536, 388);
	    
	    existedTemplateTable.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				currentSel_InServerTemplateTable = existedTemplateTable.getSelectedRow();
				updateButtonsStatus();
				
				if(e.getClickCount() == 2) {
					final TemplateVO template = getCurSelTemplateFromServerTable();
					showTemplateGraphics(template.getPositionInfoList());
				}
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
	    }
	    );
	    
	    JScrollPane scrollPane_server=new JScrollPane(existedTemplateTable);
	    scrollPane_server.setSize(562, 551);
	    scrollPane_server.setLocation(10, 51);
	    this.add(scrollPane_server);
	    
	    machineTemplateTableWrapper = createWrapper();
	    this.machineTemplateTable = machineTemplateTableWrapper.getTable();
	    machineTemplateTable.setBounds(630, 48, 536, 388);
	    
	    machineTemplateTable.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				currentSel_InMachineTemplateTable = machineTemplateTable.getSelectedRow();
				updateButtonsStatus();
				
				if(e.getClickCount() == 2) {
					final TemplateVO template = getCurSelTemplateFromBottleTemplateTable();
					showTemplateGraphics(template.getPositionInfoList());
				}
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
	    }
	    );
	    
	    JScrollPane scrollPane_machine=new JScrollPane(machineTemplateTable);
	    scrollPane_machine.setSize(562, 800);
	    scrollPane_machine.setLocation(10, 655);
	    this.add(scrollPane_machine);
	    
	    JLabel lblNewLabel = new FontLabel("\u670D\u52A1\u5668\u6A21\u677F\u7BA1\u7406");
	    lblNewLabel.setBounds(10, 8, 140, 32);
	    add(lblNewLabel);
	    
	    FontLabel fontLabel = new FontLabel("\u670D\u52A1\u5668\u6A21\u677F\u7BA1\u7406");
	    fontLabel.setText("\u4E3B\u63A7\u6A21\u677F\u7BA1\u7406");
	    fontLabel.setBounds(10, 613, 140, 32);
	    add(fontLabel);
	    
	    
	    queryMachineTemplateListButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {	
	    		queryMachineTemplateListButton.setEnabled(false);
	    		
	    		final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_QueryTemplateList_);
				sender.send();
	    	}
	    });
	    queryMachineTemplateListButton.setBounds(570, 728, 188, 32);

	    add(queryMachineTemplateListButton);
	    
	    
	    downloadToMachineButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		try {
					final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_DownloadTemplate_);
					TemplateTableCandidate templateCandidate = (TemplateTableCandidate)existedTemplateTableWrapper.getElementAtIndex(currentSel_InServerTemplateTable);
					TemplateVO template = getTemplateFromListById(templateCandidate.getId());
					sender.setTemplate(template);
					sender.send();
					downloadToMachineButton.setEnabled(false);
				} catch (Exception e1) {
					sendSystemInfo(e1.getMessage());
					downloadToMachineButton.setEnabled(true);
				}					    		
	    	}
	    });
	    
	    downloadToMachineButton.setBounds(582, 266, 188, 32);
	    add(downloadToMachineButton);
	    
	    deleteTemplateFromMachineButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		try {
					final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_DeleteTemplate_);
					TemplateTableCandidate templateCandidate = (TemplateTableCandidate)machineTemplateTableWrapper.getElementAtIndex(currentSel_InMachineTemplateTable);
					TemplateVO template = getTemplateFromListById(templateCandidate.getId());
					sender.setTemplate(template);
					sender.send();
					deleteTemplateFromMachineButton.setEnabled(false);
				} catch (Exception e1) {
					sendSystemInfo(e1.getMessage());
					deleteTemplateFromMachineButton.setEnabled(true);
				}
	    	}
	    });
	    deleteTemplateFromMachineButton.setBounds(585, 774, 188, 32);
	    add(deleteTemplateFromMachineButton);
	    
	    
	    deleteTemplateFromServerButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		deleteTemplateFromServerButton.setEnabled(false);	    			    		
	    		TemplateTableCandidate templateCandidate = (TemplateTableCandidate)existedTemplateTableWrapper.getElementAtIndex(currentSel_InServerTemplateTable);
	    		templateService.deleteTemplateById(templateCandidate.getId());
	    		deleteTemplateFromServerButton.setEnabled(true);
	    	}
	    });

	    deleteTemplateFromServerButton.setBounds(582, 320, 188, 32);
	    add(deleteTemplateFromServerButton);
	    
	    
	    flushServerTemplateButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		flushServerTemplateTable();
	    	}
	    });

	    flushServerTemplateButton.setBounds(582, 220, 188, 32);
	    add(flushServerTemplateButton);
	}
	
	public String generateRandomBarCode() {
		char [] arr = {'1','2','3','4','5','6','7','8','9','0',
				'a','b','c','d','e','f','g','h','i','j',
				'k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	
		StringBuilder buf = new StringBuilder();
		for (int i=0; i<13; i++) {
			int index=(int)(Math.random()*arr.length);
			char rand = arr[index];
			buf.append(rand);
		}
		
		return buf.toString();
	}
	
	public static void main(String [] args) {
		TemplateManagementPanel panel = new TemplateManagementPanel();
		System.out.println(panel.generateRandomBarCode());
		System.out.println(panel.generateRandomBarCode());
		System.out.println(panel.generateRandomBarCode());
		System.out.println(panel.generateRandomBarCode());
		System.out.println(panel.generateRandomBarCode());
		System.out.println(panel.generateRandomBarCode());
		System.out.println(panel.generateRandomBarCode());
	}
	public void showTemplateGraphics(final List<PositionInfoVO> positionList) {
		GraphicsDialog dlg = new GraphicsDialog();
		dlg.setPositionList(positionList);
		dlg.setVisible(true);
	}
	
	public TemplateVO getCurSelTemplateFromServerTable() {
		TemplateTableCandidate templateCandidate = (TemplateTableCandidate)existedTemplateTableWrapper.getElementAtIndex(currentSel_InServerTemplateTable);
		TemplateVO template = getTemplateFromListById(templateCandidate.getId());
		
		return template;
	}
	
	public TemplateVO getCurSelTemplateFromBottleTemplateTable() {
		TemplateTableCandidate templateCandidate = (TemplateTableCandidate)machineTemplateTableWrapper.getElementAtIndex(currentSel_InMachineTemplateTable);
		TemplateVO template = getTemplateFromListById(templateCandidate.getId());
		
		return template;
	}
	
	public TemplateVO getTemplateFromListById(final long id) {
		if (null == serverTemplateList) {
			throw new NullPointerException("serverTemplateList is null.");
		}
		
		TemplateVO rtnTemplate = null;
		for (TemplateVO template : serverTemplateList) {
			if (null == template) {
				throw new NullPointerException("template is null.");
			}
			
			if (id == template.getId()) {
				rtnTemplate = template;
			}
		}
		
		if (null == rtnTemplate) {
			throw new NullPointerException("rtnTemplate is null.");
		}
		
		return rtnTemplate;
	}
	
	@SuppressWarnings("serial")
	public MyTableWrapper createWrapper() {
		return new MyTableWrapper(new ArrayList<String>(){{add("ID"); add("Name"); add("Bar Code"); add("IsMetal"); add("Weight"); add("Price");}}, 
	              new ArrayList<Integer>(){{add(30); add(210); add(150); add(50); add(50);add(70);}}, new ModelListTableModel());
	}
	
	@PostConstruct
	public void initialize() {
		addMessageListener();
		initTableWrapper();
		updateButtonsStatus();
	}
	
	public void addBottleTemplateMap() {
		TemplateTableCandidate templateCandidate = (TemplateTableCandidate)existedTemplateTableWrapper.getElementAtIndex(currentSel_InServerTemplateTable);
		TemplateVO template = getTemplateFromListById(templateCandidate.getId());
		this.templateService.addBottleTemplateMap(template.getId());
		System.out.println("addBottleTemplateMap: add template to bottle. templateId:" + template.getId());
	}
	
	public void removeBottleTemplateMap() {
		TemplateTableCandidate templateCandidate = (TemplateTableCandidate)machineTemplateTableWrapper.getElementAtIndex(currentSel_InMachineTemplateTable);
		TemplateVO template = getTemplateFromListById(templateCandidate.getId());
		this.templateService.removeBottleTemplateMap(template.getId());
		System.out.println("removeBottleTemplateMap: remove template to bottle. templateId:" + template.getId());
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
		
		final ICommonConstants.SubMessageTypeEnum subMessageType = vo.getSubMessageType();
		if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_TemplateManagementPanel_LearnTemplateButton_.equals(subMessageType)) {
			learnTemplateButton.setEnabled(true);
			learnTemplateButton.validate();
		} 
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_TemplateManagementPanel_DownloadTemplateButton_.equals(subMessageType)) {			
			if (true == vo.getIsBooleanParam3()) {
				addBottleTemplateMap();
			}
			
			downloadToMachineButton.setEnabled(true);
			downloadToMachineButton.validate();
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_TemplateManagementPanel_DeleteTemplateButton_.equals(subMessageType)) {			
			if (true == vo.getIsBooleanParam3()) {
				removeBottleTemplateMap();
			}
			
			deleteTemplateFromMachineButton.setEnabled(true);
			deleteTemplateFromMachineButton.validate();
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_TemplateManagementPanel_QueryTemplateListButton_.equals(subMessageType)) {			
			if (true == vo.getIsBooleanParam3()) {
				final List<Long> templateMaskList = vo.getLongListParameter4();
				
				machineTemplateList.clear();
				machineTemplateTableWrapper.clear();
				machineTemplateTable.invalidate();
				currentSel_InMachineTemplateTable = -1;
				updateButtonsStatus();
				
				final long maxId = templateMaskList.size();
				machineTemplateList = templateService.getMachineTemplateList();
				for (final TemplateVO template : machineTemplateList) {
					final long templateId = template.getId();
					if (templateId > maxId) {
						System.out.println("currentId is greater than maxId. current Id:" + templateId + "--maxId:" + maxId);
						continue;
					}
					
					if (templateMaskList.get((int)(templateId-1)) == 1){
						machineTemplateTableWrapper.add(new TemplateTableCandidate(template.getId(), template.getName(), template.getBarCode(), template.getIsMetal(), template.getWeight(), template.getPrice()));
					}					
					//machineTemplateTableWrapper.add(new TemplateTableCandidate(vo.getId(), vo.getName(), vo.getBarCode(), vo.getIsMetal(), vo.getWeight(), vo.getPrice()));
				}
			}
			
			queryMachineTemplateListButton.setEnabled(true);
			queryMachineTemplateListButton.validate();
		}		
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_TemplateManagementPanel_UpdateLearnedTemplate_.equals(subMessageType)){
			final TemplateVO templateVO = productionDataManager.getNewestLearnedTemplate();
			final String barCode = templateVO.getBarCode();
			final long isMetal = templateVO.getIsMetal();
			final long weight = templateVO.getWeight();
			final List<PositionInfoVO> posistionInfoList = templateVO.getPositionInfoList();
			String image = "";
			for (PositionInfoVO position : posistionInfoList) {
				image += "{x=" + position.getX() + ", y=" + position.getY() + "}" + ", ";
			}
			
			System.out.println(image);
			barCodeLabel.setText(barCode);
			isMetalLabel.setText(isMetal + "");
			weightLabel.setText(weight + "");
			imageLabel.setText(image);
		}
		
		updateButtonsStatus();
	}
;
	@Override
	public MessageSourceEnum getMessageType() {
		return ICommonConstants.MessageSourceEnum._MessageSource_AdminPanel_Tab_TemplateManagementPanel_;
	}

	@Override
	public void processChildMessage(MessageVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParent(java.awt.Component parent) {
		// TODO Auto-generated method stub
		
	}
	
	public void flushServerTemplateTable() {
		try {
			serverTemplateList.clear();
			existedTemplateTableWrapper.clear();
			existedTemplateTable.invalidate();
			currentSel_InServerTemplateTable = -1;
			updateButtonsStatus();
			//existedTemplateTableWrapper.setTableModel(new ModelListTableModel());
			
			serverTemplateList = templateService.getTemplateList();
			for (final TemplateVO vo : serverTemplateList) {
				JSONObject obj = (JSONObject)JSONObject.toJSON(vo);
				existedTemplateTableWrapper.add(new TemplateTableCandidate(vo.getId(), vo.getName(), vo.getBarCode(), vo.getIsMetal(), vo.getWeight(), vo.getPrice()));
				//machineTemplateTableWrapper.add(new TemplateTableCandidate(vo.getId(), vo.getName(), vo.getBarCode(), vo.getIsMetal(), vo.getWeight(), vo.getPrice()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void flushMachineTemplateTable() {
		try {
			machineTemplateList.clear();
			machineTemplateTableWrapper.clear();
			machineTemplateTable.invalidate();
			currentSel_InMachineTemplateTable = -1;
			updateButtonsStatus();
			//existedTemplateTableWrapper.setTableModel(new ModelListTableModel());
			
			final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_QueryTemplateList_);
			sender.send();		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initTableWrapper() {
		
	}
	
	public void updateButtonsStatus() {
		final boolean isSerialPortInitailzed = productionDataManager.getIsSerialPortInitialized();
		final boolean isTemplateLearn = this.productionDataManager.isLearned();
		if (currentSel_InServerTemplateTable == -1) {
			deleteTemplateFromServerButton.setEnabled(false);
		}
		else {
			deleteTemplateFromServerButton.setEnabled(true);
		}
		
		if (false == isTemplateLearn) {
			uploadTemplateButton.setEnabled(false);
			viewLearnTemplateImageButton.setEnabled(false);
		}
		else {
			uploadTemplateButton.setEnabled(true);
			viewLearnTemplateImageButton.setEnabled(true);
		}
		
		if (false == isSerialPortInitailzed) {
			learnTemplateButton.setEnabled(false);
		}
		else {
			learnTemplateButton.setEnabled(true);
		}
		
		if (true == isSerialPortInitailzed && currentSel_InServerTemplateTable != -1) {
			downloadToMachineButton.setEnabled(true);
		}
		else {
			downloadToMachineButton.setEnabled(false);
		}
	}
	
	public void flush() {
		updateButtonsStatus();
	}
}
