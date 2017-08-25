package com.bottle.ui.components.player.sub;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
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

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.springframework.util.StringUtils;

import sun.swing.SwingUtilities2;  
   
public class MyPhoneNumberPanel extends JPanel {  
    private static final long serialVersionUID = 1L;  
    private static int _font_size_ = 48;
    private static int _key_size_height_ = 120;
    private static int _key_size_width_ = 120;
    private static int _backspace_key_width_ = 120;
    private static int _backspace_key_height_ = 120;
    private static int _keyboard_width_ = 500;
    private static int _keyboard_height_ = 500;
    private static PhoneNumberInputDlg fatherDlg;
    
    public static void main(String[] args) {  
    	final MyPhoneNumberPanel keyPopup = new MyPhoneNumberPanel();
    	
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
          
        //keyPopup.setVisible(false);
        frame.add(keyPopup);
        frame.setVisible(true);  
    }  
  
    private static Color transparentColor = new Color(255, 255, 255, 0);  
    // private static Dimension popupSize = new Dimension(360, 110); 
    private static Dimension popupSize = new Dimension(_keyboard_width_, _keyboard_height_);
    private static Color backColor = new Color(23, 127, 194);  
  
    protected SoftKeyBoardPanel softKeyBoardPanel;
    
    public MyPhoneNumberPanel() {  
        softKeyBoardPanel = new SoftKeyBoardPanel();  
        softKeyBoardPanel.setPreferredSize(popupSize);  
        softKeyBoardPanel.setBorder(BorderFactory.createEmptyBorder());  
  
        add(softKeyBoardPanel);  
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); 
        setOpaque(false); 
    }  
    
    public void setFatherDlg(PhoneNumberInputDlg fatherDlg) {
		this.fatherDlg = fatherDlg;
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
        popupSize = new Dimension(280, 110);  
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
  
            rows = new RowPanel[] { new RowPanel(RowType.first), new RowPanel(RowType.second), new RowPanel(RowType.third), new RowPanel(RowType.fourth)};  
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
  
                pressed = !pressed;  
                keyLable.setPressed(pressed);  
  
                notifyKeyLabel();  
            } else if (keyLable.isBackSpace()) {  
                clickBackSpace();  
            } else if (keyLable.isEnter()) {  
                clickEnter();  
            }
            else {
            	 String key;  
                 if (status == KeyStatus.normal) {  
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
        	status = KeyStatus.normal; 
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
        
        public void clickEnter() {
        	final String content = textField.getText();
        	if (true == StringUtils.isEmpty(content)) {
        		System.out.println("clickEnter: content is empty. content:" + content);
        		fatherDlg.showAlertDialog("请输入有效的手机号码!");
        	}
        	else {
        		if (content.length() != 11) {
        			System.out.println("clickEnter: content length is invalid. length:" + content.length());
        			fatherDlg.showAlertDialog("请输入有效的手机号码!");
        		}
        		else {
        			fatherDlg.setVisible(false);
        		}
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
                    keys = new KeyLable[]{new KeyLable("1", "1", SoftKeyBoardPanel.this),
					                	  new KeyLable("2", "2", SoftKeyBoardPanel.this),
					                	  new KeyLable("3", "3", SoftKeyBoardPanel.this)};
                } else if (rowType == RowType.second) {  
                	keys = new KeyLable[]{ new KeyLable("4", "4", SoftKeyBoardPanel.this),
      					  				   new KeyLable("5", "5", SoftKeyBoardPanel.this),
      					  				   new KeyLable("6", "6", SoftKeyBoardPanel.this)};
                } else if (rowType == RowType.third) {  
                	keys = new KeyLable[]{new KeyLable("7", "7", SoftKeyBoardPanel.this),
		                	  			   new KeyLable("8", "8", SoftKeyBoardPanel.this),
		                	  			   new KeyLable("9", "9", SoftKeyBoardPanel.this)};		                	  
                }else if (rowType == RowType.fourth) {
                	KeyLable backspaceKey = new KeyLable("BackSpace", "\u5220\u9664", true, SoftKeyBoardPanel.this); 
                    backspaceKey.setPreferredSize(new Dimension(_backspace_key_width_, _backspace_key_height_));
                                                            
                    KeyLable enterkey = new KeyLable("Enter", "\u786E\u8BA4",  true, SoftKeyBoardPanel.this); 
                    enterkey.setPreferredSize(new Dimension(_backspace_key_width_, _backspace_key_height_));
                	keys = new KeyLable[]{backspaceKey, new KeyLable("0", "0", SoftKeyBoardPanel.this), enterkey};                	 
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
		String displayName;
        String lowerLeftKey;  
        boolean isBackSpace;  
        boolean isCapsLock;  
        boolean isShift;  
        boolean isPressed;  
        boolean isEnter = false;
        KeyStatus status = KeyStatus.normal;  
        Dimension size = new Dimension(_key_size_width_, _key_size_height_);
        Color keyBorderColor = new Color(54, 112, 184);  
        Color keyBorderFocusColor = new Color(64, 194, 241);  
        Color keyBackColor = new Color(253, 255, 255);  
        Color keyBackFocusColor = new Color(28, 159, 228);  
        Font boldFont = new Font("Microsoft JhengHei Light", Font.PLAIN, _font_size_);
        Color boldColor = new Color(0, 0, 57);  
        Font plainFont = new Font("Microsoft JhengHei Light", Font.PLAIN, _font_size_);  
        Color plainColor = new Color(156, 157, 197);  
  
        public KeyLable(String centerKey, String displayName, ActionListener action) {  
            this(centerKey, displayName, null, action);  
        }  
  
        public KeyLable(String centerKey, String displayName, String lowerLeftKey, ActionListener action) {  
            this(centerKey, displayName, lowerLeftKey, false, action);  
        }  
  
        public KeyLable(String centerKey, String displayName, boolean isFunctionKey, ActionListener action) {  
            this(centerKey, displayName, null, isFunctionKey, action);  
        }  
  
        public KeyLable(String centerKey, String displayName, String lowerLeftKey, boolean isFunctionKey, final ActionListener action) {  
            this.centerKey = centerKey;
            this.displayName = displayName;
            this.lowerLeftKey = lowerLeftKey;  
            if (isFunctionKey) { 
                if (centerKey.indexOf("Shift") >= 0) {  
                    isShift = true;  
                } else if (centerKey.indexOf("Back") >= 0 || centerKey.indexOf("Space") >= 0) {  
                    isBackSpace = true;  
                } else if (centerKey.indexOf("Caps") >= 0 || centerKey.indexOf("Lock") >= 0) {  
                    isCapsLock = true;  
                } else if (centerKey.indexOf("Enter") >= 0) {
                	isEnter = true;
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
  
            if (status == KeyStatus.normal) {  
                if (lowerLeftKey == null) {  
                    g2d.setFont(boldFont);  
                    g2d.setPaint(boldColor);  
                    // g2d.drawString(centerKey, isCommKey() ? 8 : 4, 17);  
                    if ((false == this.isBackSpace) 
                    		&& (false == this.isEnter)){
                    	SwingUtilities2.drawStringUnderlineCharAt(this, g2d, displayName, -1,47, 78);
                    }
                    else {
                    	SwingUtilities2.drawStringUnderlineCharAt(this, g2d, displayName, -1, 15, 75);
                    }                    
                } else {  
                    g2d.setFont(plainFont);  
                    g2d.setPaint(plainColor);  
                    // g2d.drawString(centerKey, 12, 15);  
                    SwingUtilities2.drawStringUnderlineCharAt(this, g2d, displayName, -1, 25, 20);  
  
                    g2d.setFont(boldFont);  
                    g2d.setPaint(boldColor);  
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
        
        public boolean isEnter() {  
            return isEnter;  
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
   
        public void setStatus(KeyStatus status) {  
            if (this.status != status) {  
                if (status == KeyStatus.normal) {  
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
        normal  
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