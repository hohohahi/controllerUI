package com.bottle.ui.components.common;

import java.awt.Font;

import javax.swing.JLabel;

public class FontLabel extends JLabel{
	private static final long serialVersionUID = 1L;

	public FontLabel() {
		super();
		setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));
	}
	
	public FontLabel(int fontSize) {
		super();
		setFont(new Font("Microsoft JhengHei Light", Font.BOLD, fontSize));
	}
	
	public FontLabel(String text) {
		super(text);
		setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));
	}
	
	public FontLabel(String text, int fontSize) {
		super(text);
		setFont(new Font("Microsoft JhengHei Light", Font.BOLD, fontSize));
	}
}
