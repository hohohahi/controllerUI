package com.bottle.ui.components.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.springframework.stereotype.Component;

@Component
public class MyTabbedPane extends JTabbedPane  implements ActionListener, ChangeListener{
	private static final long serialVersionUID = 1L;

	public MyTabbedPane() {
		super();
		setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 24));
		addChangeListener(this);
	}
	
	public MyTabbedPane(String text) {
		super();
		setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));
	}
	
	public MyTabbedPane(String text, boolean isBold, int fontSize) {
		super();
		if (true == isBold) {
			setFont(new Font("Microsoft JhengHei Light", Font.BOLD, fontSize));
		}		
		else {
			setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, fontSize));
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		final int tabCount = getTabCount();
		final int currentSel = this.getSelectedIndex();
		for (int index = 0; index < tabCount; index ++ ) {
			if (index == currentSel) {
				setForegroundAt(index, new Color(255,0,0));
				setBackgroundAt(index, new Color(0,0,255));
			}
			else {
				setForegroundAt(index, new Color(22,114,50));
				setBackgroundAt(index, new Color(125,125,125));
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
