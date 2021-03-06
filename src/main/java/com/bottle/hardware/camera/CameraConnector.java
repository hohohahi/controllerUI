package com.bottle.hardware.camera;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.login.ILoginManager;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.SubMessageTypeEnum;
import com.bottle.common.constants.IUserConstants;
import com.bottle.hardware.camera.constants.ICameraConstants;
import com.bottle.ui.components.verify.VideoBarCodeVerifyPanel;
import com.bottle.ui.components.verify.VideoPanel;  

@Service
public class CameraConnector extends AbstractBaseBean implements ICameraConnector {
	private OpenCVFrameGrabber grabber;
	private boolean isWorking = false;
	private VideoBarCodeVerifyPanel panel;

	@Autowired
	private ILoginManager loginManager;
	
	@Autowired
	private IMessageQueueManager queueManager;
	
	@Override
	public void initialize(){  
		super.initialize();
		initThread();
		initCamera();		
	}  

	public void initCamera(){
		grabber = new OpenCVFrameGrabber(0);  
	}

	public void setPanel(VideoBarCodeVerifyPanel panel) {
		this.panel = panel;
	}

	public void initThread() {
		ExecutorService pool = Executors.newSingleThreadExecutor();
		pool.submit(new Runnable(){
			@Override
			public void run() {
				long num = 0L;
				
				while (true){
					try {						

						if (false == isWorking) {
							Thread.sleep(ICameraConstants._Thread_Sleep_Milliseconds_);
							continue;
						}

						num++;

						Frame image = grabber.grab();

						if (image != null) {
							if (num%ICameraConstants._Counter_Divider_ != 0){
								continue;
							}

							Java2DFrameConverter converter = new Java2DFrameConverter();
							BufferedImage imageExt = converter.getBufferedImage(image, 1.0, false, null);

							notifyUI(imageExt);							
						}											        

					} catch (Exception | InterruptedException e) {
						e.printStackTrace();
					}
				}
			}		
		});
	}

	@Override
	public void start(){
		try {
			if (false == isWorking) {
				grabber.start();
				VideoPanel videoPanel = panel.getVideoPanel();
				int height = videoPanel.getHeight();
				int width = videoPanel.getWidth();
				grabber.setImageHeight(height);
				grabber.setImageWidth(width);

				System.out.println("CameraConnector::start: start successfully.");
				isWorking = true;
			}			
		} catch (Exception e) {
			super.logErrorAndStack(e, "error happens at start()");
		}
	}

	@Override
	public void stop() {
		try {
			if (true == isWorking) {
				isWorking = false;
				grabber.stop();
				System.out.println("CameraConnector::stop: stop successfully.");
			}			
		} catch (Exception e) {
			super.logErrorAndStack(e, "error happens at stop()");
			e.printStackTrace();
		}		
	}
	
	public void notifyUI(final BufferedImage image) {
		long role = loginManager.login(image);
		VideoPanel videoPanel = panel.getVideoPanel();
		
		videoPanel.setImage(image);
		videoPanel.repaint();
		
		if (IUserConstants._Role_None_ != role) {
			MessageVO vo = new MessageVO();
			vo.setMessageSource(ICommonConstants.MessageSourceEnum._MessageSource_MainFrame_);
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_MainFrame_Panel_);
			
			if (role == IUserConstants._Role_Admin_) {
				vo.setParam1(ICommonConstants.MainFrameActivePanelEnum._MainFrame_ActivePanel_Admin_.getId());
			}
			else if (role == IUserConstants._Role_SuperAdmin_) {
				vo.setParam1(ICommonConstants.MainFrameActivePanelEnum._MainFrame_ActivePanel_SuperAdmin_.getId());
			}
			
			queueManager.push(vo);
		}
	}
}  