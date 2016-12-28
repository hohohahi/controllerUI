package com.bottle.ui.components.common;

import java.awt.Font;

import javax.swing.JButton;

public class FontButton extends JButton {
	private static final long serialVersionUID = 1L;
	
	public FontButton(String name) {
		super(name);
		setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));
	}
	
	public FontButton(String name, final int fontSize) {
		super(name);
		setFont(new Font("Microsoft JhengHei Light", Font.BOLD, fontSize));
	}
}
