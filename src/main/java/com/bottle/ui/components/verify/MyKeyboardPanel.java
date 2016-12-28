package com.bottle.ui.components.verify;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import sun.swing.SwingUtilities2;  
   
public class MyKeyboardPanel extends JPanel {  
    private static final long serialVersionUID = 1L;  
    private static int _font_size_ = 18;
    private static int _key_size_ = 48;
    private static int _backspace_key_width_ = 120;
    private static int _backspace_key_height_ = 48;
    private static int _shift_key_width_ = 90;
    private static int _shift_key_height_ = 48;
    private static int _capsLock_key_width_ = 90;
    private static int _capsLock_key_height_ = 48;
    private static int _keyboard_width_ = 700;
    private static int _keyboard_height_ = 220;
    
    public static void main(String[] args) {  
    	final MyKeyboardPanel keyPopup = new MyKeyboardPanel();
    	
        final JFrame frame = new JFrame();  
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setSize(1000, 600);  
        frame.setLocationRelativeTo(null);  
  
        final JPanel usernamePanel = new JPanel(new BorderLayout());  
        usernamePanel.setBackground(Color.WHITE);  
        usernamePanel.setPreferredSize(new Dimension(202, 30));  
        usernamePanel.setLayout(new BorderLayout());  
        usernamePanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        final JTextField username = new JTextField();  
        username.setSelectedTextColor(Color.BLACK); 
        username.setSelectionColor(Color.WHITE); 
        username.setForeground(Color.BLACK);  
        username.setFont(username.getFont().deriveFont(22f));  
        username.setBorder(new EmptyBorder(5, 3, 0, 3)); 
        username.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				keyPopup.setTextField(username);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}});
        usernamePanel.add(username, BorderLayout.CENTER);
        frame.add(usernamePanel);  
  
        final JPanel passwordPanel = new JPanel(new BorderLayout());  
        passwordPanel.setBackground(Color.WHITE);  
        passwordPanel.setPreferredSize(new Dimension(202, 30));  
        passwordPanel.setLayout(new BorderLayout());  
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        final JTextField passowrdText = new JTextField();  
        passowrdText.setSelectedTextColor(Color.BLACK); 
        passowrdText.setSelectionColor(Color.WHITE); 
        passowrdText.setForeground(Color.BLACK);  
        passowrdText.setFont(passowrdText.getFont().deriveFont(22f));  
        passowrdText.setBorder(new EmptyBorder(5, 3, 0, 3));
        passowrdText.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				keyPopup.setTextField(passowrdText);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}});
        passwordPanel.add(passowrdText, BorderLayout.CENTER);
        frame.add(passwordPanel);
          
  
        
        //keyPopup.setVisible(false);
        frame.add(keyPopup);
        frame.setVisible(true);  
    }  
  
    private static Color transparentColor = new Color(255, 255, 255, 0);  
    // private static Dimension popupSize = new Dimension(360, 110); 
    private static Dimension popupSize = new Dimension(_keyboard_width_, _keyboard_height_);
    private static Color backColor = new Color(23, 127, 194);  
  
    protected SoftKeyBoardPanel softKeyBoardPanel;
    
    public MyKeyboardPanel() {  
        softKeyBoardPanel = new SoftKeyBoardPanel();  
        softKeyBoardPanel.setPreferredSize(popupSize);  
        softKeyBoardPanel.setBorder(BorderFactory.createEmptyBorder());  
  
        add(softKeyBoardPanel);  
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); 
        setOpaque(false); 
    }  
  
    
    public void setTextField(JTextField textField) {
		this.softKeyBoardPanel.setTextField(textField);
	}


	public static void gc() {  
        popupSize = null;  
        backColor = null;   
        System.gc();  
    }  
  
    public static void resetValue() {  
        popupSize = new Dimension(380, 110);  
        backColor = new Color(23, 127, 194);  
    }  
  
    public SoftKeyBoardPanel getSoftKeyBoardPanel() {  
        return softKeyBoardPanel;  
    }  
   
    public static class SoftKeyBoardPanel extends JPanel implements ActionListener {  
		private static final long serialVersionUID = 1L;
		JTextField textField;  
        RowPanel[] rows;  
        KeyStatus status = KeyStatus.normal;  
        Paint[] paints = new Paint[] { new Color(70, 67, 114), new Color(62, 192, 238), new Color(138, 180, 231) };  
  
        public SoftKeyBoardPanel() {  
            initSoftKeyBoardPanel();  
        }  
   
        public void setTextField(JTextField textField) {
			this.textField = textField;
		}

		private void initSoftKeyBoardPanel() {  
            setLayout(null);  
            setBackground(backColor);  
  
            JPanel proxyPanel = new JPanel(new GridLayout(4, 1, 0, 1)); 
            proxyPanel.setBackground(backColor);  
            proxyPanel.setLocation(3, 4);  
            proxyPanel.setSize(popupSize.width - 6, popupSize.height - 7);  
            add(proxyPanel);  
  
            rows = new RowPanel[] { new RowPanel(RowType.first), new RowPanel(RowType.second), new RowPanel(RowType.third), new RowPanel(RowType.fourth) };  
            for (int i = 0; i < rows.length; i++) {  
                proxyPanel.add(rows[i]);  
            }  
        }  
   
        @Override  
        public void paint(Graphics g) {  
            super.paint(g);  
            Graphics2D g2d = (Graphics2D) g;  
            ImageTool.setAntiAliasing(g2d);  
            ImageTool.drawRoundRect(g2d, getWidth(), getHeight(), 0, null, paints); 
        }  
   
        @Override  
        public void actionPerformed(ActionEvent e) {  
            KeyLable keyLable = (KeyLable) e.getSource();  
            if (keyLable.isShift() || keyLable.isCapsLock()) {  
                boolean pressed = keyLable.isPressed();  
  
                if (keyLable.isShift()) {  
                    clickShift();  
                } else if (keyLable.isCapsLock()) {  
                    clickCapsLock();  
                }  
                pressed = !pressed;  
                keyLable.setPressed(pressed);  
  
                notifyKeyLabel();  
            } else if (keyLable.isBackSpace()) {  
                clickBackSpace();  
            } else if (keyLable.isCommKey()) {  
                String key;  
                if (status == KeyStatus.shift || status == KeyStatus.shiftAndCapsLock) {  
                    key = keyLable.getCenterKey();  
                } else if (status == KeyStatus.normal || status == KeyStatus.capsLock) {  
                    key = keyLable.getLowerLeftKey() == null ? keyLable.getCenterKey() : keyLable.getLowerLeftKey();  
                } else {  
                    key = "";  
                }  
                clickCommKey(key);  
            }  
        }  
   
        public void notifyKeyLabel() {  
            for (RowPanel rowPanel : rows) {  
                for (KeyLable keyLable : rowPanel.getKeys()) {  
                    keyLable.setStatus(status);  
                }  
            }  
        }  
   
        public void clickShift() {  
            if (status == KeyStatus.capsLock) {  
                status = KeyStatus.shiftAndCapsLock;  
            } else if (status == KeyStatus.shiftAndCapsLock) {  
                status = KeyStatus.capsLock;  
            } else if (status == KeyStatus.shift) {  
                status = KeyStatus.normal;  
            } else if (status == KeyStatus.normal) {  
                status = KeyStatus.shift;  
            } else {  
                status = KeyStatus.normal;  
            }  
        }  
   
        public void clickCapsLock() {  
            if (status == KeyStatus.capsLock) {  
                status = KeyStatus.normal;  
            } else if (status == KeyStatus.shiftAndCapsLock) {  
                status = KeyStatus.shift;  
            } else if (status == KeyStatus.shift) {  
                status = KeyStatus.shiftAndCapsLock;  
            } else if (status == KeyStatus.normal) {  
                status = KeyStatus.capsLock;  
            } else {  
                status = KeyStatus.normal;  
            }  
        }  
   
        public void clickBackSpace() {
        	if (null == textField) {
            	return;
            }
        	
            final String text = textField.getText();  
            if (text != null && text.length() > 0) {  
            	int curPos = textField.getCaret().getDot();
            	String prefixString = "";
            	if (0 == curPos) {
            		prefixString = "";
            	}
            	else {
            		prefixString = text.substring(0, curPos-1);
            	}
            	
            	final String endfixString = text.substring(curPos, text.length());
            	
                textField.setText(prefixString + endfixString);
                textField.getCaret().setDot((prefixString).length());
            }  
        }  
   
        public void clickCommKey(String key) {  
            if (key != null) {  
                if (key.length() > 1) { 
                    key = key.substring(0, key.length() - 1);  
                }
                
                if (null == textField) {
                	return;
                }
                
                int curPos = textField.getCaret().getDot();
                String text = textField.getText();
                String prefixString = "";
                if (0 == curPos) {
                	prefixString = "";
                }
                else {
                	prefixString = text.substring(0, curPos);
                }
                
                final String endfixString = text.substring(curPos, text.length());
 
                textField.setText(prefixString + key + endfixString);
                textField.getCaret().setDot((prefixString + key).length());
            }  
        }  
  
        public RowPanel[] getRows() {  
            return rows;  
        }  
   
        public Image createCloseImage(Color fontColor, boolean isFocus) {  
            int width = 12;  
            BufferedImage bi = new BufferedImage(width, width, BufferedImage.TYPE_4BYTE_ABGR);  
            Graphics2D g2d = bi.createGraphics();  
  
            ImageTool.setAntiAliasing(g2d);  
   
            g2d.setPaint(transparentColor);  
            g2d.fillRect(0, 0, width, width);  
  
            int[] xpoints_1 = { 2, 4, 8, 10 };  
            int[] ypoints_1 = { 2, 2, 10, 10 };  
            int npoints_1 = 4;  
            Polygon p_left = new Polygon(xpoints_1, ypoints_1, npoints_1); 
  
            int[] xpoints_2 = xpoints_1;  
            int[] ypoints_2 = { 10, 10, 2, 2 };  
            int npoints_2 = 4;  
            Polygon p_right = new Polygon(xpoints_2, ypoints_2, npoints_2); 
  
            if (isFocus) {  
                g2d.setPaint(new GradientPaint(0, 0, fontColor, 0, width, new Color(fontColor.getRed(), fontColor.getGreen(), fontColor.getBlue(), 50)));  
            } else {  
                g2d.setPaint(fontColor);  
            }  

            g2d.fillPolygon(p_left);  
            g2d.fillPolygon(p_right);  
  
            return bi;  
        }  
  
        public class RowPanel extends JPanel {  
			private static final long serialVersionUID = 1L;
			RowType rowType;  
            KeyLable[] keys;  
  
            public RowPanel(RowType rowType) {  
                this.rowType = rowType;  
                initRowPanel();  
            }  
  
            private void initRowPanel() {  
                setOpaque(true);  
                setLayout(new FlowLayout(FlowLayout.CENTER, 1, 0));
                setBackground(backColor);  
                if (rowType == RowType.first) {          	                           
                    KeyLable backspaceKey = new KeyLable("BackSpace", true, SoftKeyBoardPanel.this); 
                    backspaceKey.setPreferredSize(new Dimension(_backspace_key_width_, _backspace_key_height_));
                    
                    keys = new KeyLable[]{new KeyLable("!", "1", SoftKeyBoardPanel.this),
					                	 new KeyLable("@", "2", SoftKeyBoardPanel.this),
					                	 new KeyLable("#", "3", SoftKeyBoardPanel.this),
					                	 new KeyLable("$", "4", SoftKeyBoardPanel.this),
					                	 new KeyLable("%", "5", SoftKeyBoardPanel.this),
					                	 new KeyLable("^", "6", SoftKeyBoardPanel.this),
					                	 new KeyLable("&", "7", SoftKeyBoardPanel.this),
					                	 new KeyLable("*", "8", SoftKeyBoardPanel.this),
					                	 new KeyLable("(", "9", SoftKeyBoardPanel.this),
					                	 new KeyLable(")", "0", SoftKeyBoardPanel.this),
					                	 new KeyLable("~", "`", SoftKeyBoardPanel.this),
					                	 backspaceKey};
                } else if (rowType == RowType.second) {  
                	KeyLable shiftKey = new KeyLable("Shift", true, SoftKeyBoardPanel.this);  
                    shiftKey.setPreferredSize(new Dimension(_shift_key_width_, _shift_key_height_));

                    KeyLable capsLockKey = new KeyLable("CapsLock", true, SoftKeyBoardPanel.this);  
                    capsLockKey.setPreferredSize(new Dimension(_capsLock_key_width_, _capsLock_key_height_));
                    
                    keys = new KeyLable[]{shiftKey,
                    					 new KeyLable("+", "=", SoftKeyBoardPanel.this),
                    					 new KeyLable("|", "\\", SoftKeyBoardPanel.this),
					                	 new KeyLable("{", "[", SoftKeyBoardPanel.this),
					                	 new KeyLable("}", "]", SoftKeyBoardPanel.this),
					                	 new KeyLable(":", ";", SoftKeyBoardPanel.this),
					                	 new KeyLable("\"", "'", SoftKeyBoardPanel.this),
					                	 new KeyLable("<", ",", SoftKeyBoardPanel.this),
					                	 new KeyLable(">", ".", SoftKeyBoardPanel.this),
					                	 new KeyLable("?", "/", SoftKeyBoardPanel.this),
					                	 new KeyLable("_", "-", SoftKeyBoardPanel.this),
					                	 capsLockKey
                    };
                } else if (rowType == RowType.third) {                   	
                	keys = new KeyLable[]{new KeyLable("a", SoftKeyBoardPanel.this),  
			                        	 new KeyLable("b", SoftKeyBoardPanel.this),
			                        	 new KeyLable("c", SoftKeyBoardPanel.this),  
			                        	 new KeyLable("d", SoftKeyBoardPanel.this),  
			                        	 new KeyLable("e", SoftKeyBoardPanel.this),  
			                        	 new KeyLable("f", SoftKeyBoardPanel.this),  
			                        	 new KeyLable("g", SoftKeyBoardPanel.this),  
			                        	 new KeyLable("h", SoftKeyBoardPanel.this),  
			                        	 new KeyLable("i", SoftKeyBoardPanel.this), 
			                        	 new KeyLable("j", SoftKeyBoardPanel.this),  
			                        	 new KeyLable("k", SoftKeyBoardPanel.this),  
			                        	 new KeyLable("l", SoftKeyBoardPanel.this),  
			                        	 new KeyLable("m", SoftKeyBoardPanel.this)     
                	};
                } else if (rowType == RowType.fourth) {   
                	keys = new KeyLable[]{new KeyLable("n", SoftKeyBoardPanel.this),
						                  new KeyLable("o", SoftKeyBoardPanel.this),
						                  new KeyLable("p", SoftKeyBoardPanel.this),  
						                  new KeyLable("q", SoftKeyBoardPanel.this),  
						                  new KeyLable("r", SoftKeyBoardPanel.this),  
						                  new KeyLable("s", SoftKeyBoardPanel.this),  
						                  new KeyLable("t", SoftKeyBoardPanel.this),  
						                  new KeyLable("u", SoftKeyBoardPanel.this),  
						                  new KeyLable("v", SoftKeyBoardPanel.this),  
						                  new KeyLable("w", SoftKeyBoardPanel.this),  
						                  new KeyLable("x", SoftKeyBoardPanel.this),  
						                  new KeyLable("y", SoftKeyBoardPanel.this),  
						                  new KeyLable("z", SoftKeyBoardPanel.this)                			
                	};                	 
                }
                
                for (KeyLable key : keys){
                	add(key);
                }
            }  
  
            public KeyLable[] getKeys() {  
                return keys;  
            }  
        }  
    }  
   
    public static class KeyLable extends JLabel {   
		private static final long serialVersionUID = 1L;
		String centerKey;  
        String lowerLeftKey;  
        boolean isBackSpace;  
        boolean isCapsLock;  
        boolean isShift;  
        boolean isPressed;  
        KeyStatus status = KeyStatus.normal;  
        Dimension size = new Dimension(_key_size_, _key_size_);
        Color keyBorderColor = new Color(54, 112, 184);  
        Color keyBorderFocusColor = new Color(64, 194, 241);  
        Color keyBackColor = new Color(253, 255, 255);  
        Color keyBackFocusColor = new Color(28, 159, 228);  
        Font boldFont = new Font("Microsoft JhengHei Light", Font.PLAIN, _font_size_);
        Color boldColor = new Color(0, 0, 57);  
        Font plainFont = new Font("Microsoft JhengHei Light", Font.PLAIN, _font_size_);  
        Color plainColor = new Color(156, 157, 197);  
  
        public KeyLable(String centerKey, ActionListener action) {  
            this(centerKey, null, action);  
        }  
  
        public KeyLable(String centerKey, String lowerLeftKey, ActionListener action) {  
            this(centerKey, lowerLeftKey, false, action);  
        }  
  
        public KeyLable(String centerKey, boolean isFunctionKey, ActionListener action) {  
            this(centerKey, null, isFunctionKey, action);  
        }  
  
        public KeyLable(String centerKey, String lowerLeftKey, boolean isFunctionKey, final ActionListener action) {  
            this.centerKey = centerKey;  
            this.lowerLeftKey = lowerLeftKey;  
            if (isFunctionKey) { 
                if (centerKey.indexOf("Shift") >= 0) {  
                    isShift = true;  
                } else if (centerKey.indexOf("Back") >= 0 || centerKey.indexOf("Space") >= 0) {  
                    isBackSpace = true;  
                } else if (centerKey.indexOf("Caps") >= 0 || centerKey.indexOf("Lock") >= 0) {  
                    isCapsLock = true;  
                }  
            }  
  
            setOpaque(true); 
            setBackground(keyBackColor);  
            setPreferredSize(size);  
            setBorder(BorderFactory.createLineBorder(keyBorderColor));  
            setFont(boldFont);  
  
            addMouseListener(new MouseAdapter() {  
                public void mouseEntered(MouseEvent e) {  
                    KeyLable.this.setBackground(keyBackFocusColor); 
                }  
  
                public void mouseExited(MouseEvent e) {   
                    if ((!KeyLable.this.isShift && !KeyLable.this.isCapsLock) || (!isPressed)) {  
                        KeyLable.this.setBackground(keyBackColor);  
                    }  
                }  
  
                public void mouseClicked(MouseEvent e) {  
                    action.actionPerformed(new ActionEvent(KeyLable.this, ActionEvent.ACTION_PERFORMED, e.getID() + ""));  
                }  
            });  
        }  
  
        @Override  
        protected void paintComponent(Graphics g) {  
            super.paintComponent(g); 
  
            Graphics2D g2d = (Graphics2D) g;  
            ImageTool.setAntiAliasing(g2d); 
            Container parent = getParent();  
            ImageTool.clearAngle(g2d, parent != null ? parent.getBackground() : this.getBackground(), this.getWidth(), this.getHeight(), 4); 
  
            if (getMousePosition() != null) { 
                g2d.setPaint(keyBorderFocusColor);  
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 4, 4);  
            }  
  
            if (status == KeyStatus.normal || status == KeyStatus.capsLock) {  
                if (lowerLeftKey == null) {  
                    g2d.setFont(boldFont);  
                    g2d.setPaint(boldColor);  
                    // g2d.drawString(centerKey, isCommKey() ? 8 : 4, 17);  
                    SwingUtilities2.drawStringUnderlineCharAt(this, g2d, centerKey, -1, isCommKey() ? 16 : 4, 30);  
  
                } else {  
                    g2d.setFont(plainFont);  
                    g2d.setPaint(plainColor);  
                    // g2d.drawString(centerKey, 12, 15);  
                    SwingUtilities2.drawStringUnderlineCharAt(this, g2d, centerKey, -1, 25, 20);  
  
                    g2d.setFont(boldFont);  
                    g2d.setPaint(boldColor);  
                    // g2d.drawString(lowerLeftKey, 3, 20);  
                    SwingUtilities2.drawStringUnderlineCharAt(this, g2d, lowerLeftKey, -1, 10, 35);  
                }  
            } else if (status == KeyStatus.shift || status == KeyStatus.shiftAndCapsLock) {  
                if (lowerLeftKey == null) {  
                    g2d.setFont(boldFont);  
                    g2d.setPaint(boldColor);  
                    // g2d.drawString(centerKey, isCommKey() ? 8 : 4, 17);  
                    SwingUtilities2.drawStringUnderlineCharAt(this, g2d, centerKey, -1, isCommKey() ? 16 : 4, 30);  
  
                } else {  
                    g2d.setFont(boldFont);  
                    g2d.setPaint(boldColor);  
                    // g2d.drawString(centerKey, 10, 15);  
                    SwingUtilities2.drawStringUnderlineCharAt(this, g2d, centerKey, -1, 25, 20);  
  
                    g2d.setFont(plainFont);  
                    g2d.setPaint(plainColor);  
                    // g2d.drawString(lowerLeftKey, 3, 20);  
                    SwingUtilities2.drawStringUnderlineCharAt(this, g2d, lowerLeftKey, -1, 10, 35);  
                }  
            }  
        }  
  
        public String getCenterKey() {  
            return centerKey;  
        }  
  
        public String getLowerLeftKey() {  
            return lowerLeftKey;  
        }  
  
        public boolean isBackSpace() {  
            return isBackSpace;  
        }  
  
        public boolean isCapsLock() {  
            return isCapsLock;  
        }  
  
        public boolean isShift() {  
            return isShift;  
        }  
  
        public void setPressed(boolean isPressed) {  
            this.isPressed = isPressed;  
        }  
  
        public boolean isPressed() {  
            return isPressed;  
        }  
  
        public boolean isCommKey() {  
            return !isBackSpace && !isCapsLock && !isShift;  
        }  
    
        public void reset() {  
            this.isPressed = false;  
            if (isShift || isCapsLock) {  
                KeyLable.this.setBackground(keyBackColor);  
            } else if (isCommKey()) {  
                if (lowerLeftKey == null) {  
                    centerKey = centerKey.toLowerCase();  
                }  
            }  
            status = KeyStatus.normal;  
            repaint();  
        }  
   
        public void setStatus(KeyStatus status) {  
            if (isCommKey() && this.status != status) {  
                if (status == KeyStatus.shift || status == KeyStatus.capsLock) {  
                    if (lowerLeftKey == null) {  
                        if (Character.isUpperCase(centerKey.charAt(0))) {  
                            centerKey = centerKey.toLowerCase();  
                        } else {  
                            centerKey = centerKey.toUpperCase();  
                        }  
                    }  
                } else if (status == KeyStatus.normal || status == KeyStatus.shiftAndCapsLock) {  
                    if (lowerLeftKey == null) {  
                        centerKey = centerKey.toLowerCase();  
                    }  
                }  
                this.status = status;  
                repaint();  
            }  
        }  
    }  
  
    public static enum RowType {  
        first, second, third, fourth  
    }  
  
    public static enum KeyStatus {  
        normal, shift, capsLock, shiftAndCapsLock  
    }  
}  
  
class ImageTool {  
    public static void setAntiAliasing(Graphics2D g2d) {  
        setRenderingHint(g2d, RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
    }  
  
    public static void setRenderingHint(Graphics2D g2d, Key key, Object value) {  
        if (g2d.getRenderingHints() == null) {  
            g2d.setRenderingHints(new RenderingHints(key, value));  
        } else {  
            g2d.setRenderingHint(key, value);  
        }  
    }  
   
    public static void drawRoundRect(Graphics2D g2d, int width, int height, int r, Paint anglePaint, Paint[] borderPaints) {  
        clearAngle(g2d, anglePaint, width, height, r); 
        drawMultiBorder(g2d, width, height, r, anglePaint, borderPaints); 
    }  
  
    public static void clearAngle(Graphics2D g2d, Paint anglePaint, int width, int height, int r) {  
        setAntiAliasing(g2d);  
        Composite oldComposite = g2d.getComposite();  
  
        if (anglePaint == null) {  
            g2d.setComposite(AlphaComposite.Clear);  
        } else {  
            g2d.setPaint(anglePaint);  
        }  
  
        int npoints = 5;  
        int[] xpoints1 = { r, 0, 0, r / 4, r / 2 };  
        int[] ypoints1 = { 0, 0, r, r / 2, r / 4 };  
        Polygon polygon = new Polygon(xpoints1, ypoints1, npoints);  
        g2d.fillPolygon(polygon);   
        int[] xpoints2 = { width - r, width, width, width - r / 4, width - (r / 2) };  
        int[] ypoints2 = ypoints1;  
        polygon = new Polygon(xpoints2, ypoints2, npoints);  
        g2d.fillPolygon(polygon);   
        int[] xpoints3 = xpoints2;  
        int[] ypoints3 = { height, height, height - r, height - (r / 2), height - r / 4 };  
        polygon = new Polygon(xpoints3, ypoints3, npoints);  
        g2d.fillPolygon(polygon);   
        int[] xpoints4 = xpoints1;  
        int[] ypoints4 = ypoints3;  
        polygon = new Polygon(xpoints4, ypoints4, npoints);  
        g2d.fillPolygon(polygon);  
        g2d.setComposite(oldComposite);  
    }  
   
    public static void drawMultiBorder(Graphics2D g2d, int width, int height, int r, Paint anglePaint, Paint[] borderPaints) {  
        setAntiAliasing(g2d);  
  
        int roundx = r * 2;  
        int roundy = roundx;  
        int grow = 2;  
        int x = 0;  
        int y = 0;  
        int w = width;  
        int h = height;  
   
        for (int i = 0; i < borderPaints.length; i++, x++, y++, w -= grow, h -= grow) {  
            g2d.setPaint(borderPaints[i]);  
            if (r > 0) {  
                g2d.drawRoundRect(x, y, w - 1, h - 1, roundx, roundy);  
            } else {  
                g2d.drawRect(x, y, w - 1, h - 1);  
            }  
        }  
    }  
}  