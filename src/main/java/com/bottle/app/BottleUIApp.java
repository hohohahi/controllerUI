package com.bottle.app;

import java.awt.EventQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.bottle.common.AbstractBaseBean;
import com.bottle.ui.MainFrame;

@Controller
public class BottleUIApp extends AbstractBaseBean implements IBottleUIApp {
	@Autowired
	private MainFrame mainframe;
	
	public BottleUIApp() {
		
	}
	
	@Override
	public void initialize(){
		super.initialize();
	}
	
	public MainFrame getMainframe() {
		return mainframe;
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {			
			public void run() {
				try {
					final ApplicationContext ac = new ClassPathXmlApplicationContext("/applicationContext.xml");
					
					final IBottleUIApp app = ac.getBean(IBottleUIApp.class);
					final MainFrame frame = app.getMainframe();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
