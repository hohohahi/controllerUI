package com.bottle.ui.components.player;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Test extends JFrame implements ActionListener {
	 
    private static final long serialVersionUID = 2793297815624831929L;
 
    public String getLabelValue() {
        return labelValue;
    }
 
    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }
 
    private String labelValue = null;
 
    private JLabel label = null;
 
    private JButton btn = null;
 
    public Test() {
        init();
    }
 
    private void init() {
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(getCenterPanel(), BorderLayout.CENTER);
        this.getContentPane().add(getSouthPanel(), BorderLayout.SOUTH);
        this.setSize(new Dimension(2000, 1000));
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setUndecorated(true);
		setBounds(0, 0, 1439, 773);
		
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
 
    private JPanel getSouthPanel() {
        JPanel panel = new JPanel();
        label = new JLabel();
        panel.add(label);
        return panel;
    }
 
    private JPanel getCenterPanel() {
        JPanel panel = new JPanel();
        btn = new JButton("ChildDialog");
        btn.addActionListener(this);
        panel.add(btn);
        return panel;
    }
 
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn)) {
            new ChildDialog(Test.this, true);
            label.setText(getLabelValue());
        }
    }
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        new Test();
    }
 
}
 
class ChildDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = -7633528897837030539L;
 
    private JTextField text = null;
 
    private JButton btn = null;
 
    private Test parent = null;
 
    public ChildDialog(Test _parent, boolean _modal) {
        super(_parent, _modal);
        this.parent = _parent;
        init();
    }
 
    private void init() {
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(getCenterPanel(), BorderLayout.CENTER);
        this.getContentPane().add(getSouthPanel(), BorderLayout.SOUTH);
        this.setSize(new Dimension(100, 100));
        this.setLocation(new Point(300,500));
        this.setVisible(true);
    }
 
    private JPanel getCenterPanel() {
        JPanel panel = new JPanel();
        text = new JTextField(5);
        panel.add(text);
        return panel;
    }
 
    private JPanel getSouthPanel() {
        JPanel panel = new JPanel();
        btn = new JButton("OK");
        btn.addActionListener(this);
        panel.add(btn);
        return panel;
    }
 
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn)) {
            parent.setLabelValue(text.getText());
            onExit();
        }
    }
 
    private void onExit() {
        this.dispose();
    }
}