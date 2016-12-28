package com.bottle.ui.components.common;

import java.awt.Font;

import javax.swing.JTextField;

public class MyTextField extends JTextField {
	private static final long serialVersionUID = 1L;

	public MyTextField() {
		super();
		setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));
	}
	
	public MyTextField(final int fontSize) {
		super();
		setFont(new Font("Microsoft JhengHei Light", Font.BOLD, fontSize));
	}
	
	public MyTextField(String text) {
		super(text);
		setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));
	}
	
	public MyTextField(String text, int fontSize) {
		super(text);
		setFont(new Font("Microsoft JhengHei Light", Font.BOLD, fontSize));
	}
}
