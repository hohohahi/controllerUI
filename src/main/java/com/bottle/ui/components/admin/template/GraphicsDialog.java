package com.bottle.ui.components.admin.template;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.border.EmptyBorder;

import com.bottle.business.common.vo.PositionInfoVO;
import com.bottle.ui.components.common.GraphicsPanel;

public class GraphicsDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private final GraphicsPanel contentPanel = new GraphicsPanel();
	
	public static void main(String[] args) {
		try {
			GraphicsDialog dialog = new GraphicsDialog();
			List<PositionInfoVO> positionList = new ArrayList<PositionInfoVO>();
			positionList.add(new PositionInfoVO(0L, 0L));
			positionList.add(new PositionInfoVO(0L, 100L));
			positionList.add(new PositionInfoVO(100L, 100L));
			dialog.setPositionList(positionList);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GraphicsDialog() {
		setBounds(100, 100, 900, 700);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		this.setModal(true);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
	}

	public void setPositionList(List<PositionInfoVO> positionList) {
		contentPanel.setPositionList(positionList);
	}
}
