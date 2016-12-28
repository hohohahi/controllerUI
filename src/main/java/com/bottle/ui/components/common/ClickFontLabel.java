package com.bottle.ui.components.common;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import com.bottle.business.verify.ISecretTriggerManager;

public class ClickFontLabel extends JLabel{
	private static final long serialVersionUID = 1L;
	private int pos = 0;
	private ISecretTriggerManager triggerManager;
	
	public ClickFontLabel(int pos) {
		super();
		setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));
		this.pos = pos;
		
		addMouseListener();
	}
	
	public void setTriggerManager(final ISecretTriggerManager triggerManager) {
		this.triggerManager = triggerManager;
	}
	
	public void addMouseListener() {
		this.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				triggerManager.push(pos);
				System.out.println(pos);
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
}
