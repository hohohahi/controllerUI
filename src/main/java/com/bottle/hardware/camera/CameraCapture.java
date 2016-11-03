package com.bottle.hardware.camera;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameConverter;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import goja.QRCode;

import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

public class CameraCapture {  
    public static String savedImageFile = "d:\\my.jpg";  
    
    public static void main(String[] args) throws Exception {  
        //open camera source  
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);  
        grabber.start();  
        grabber.setFrameRate(0.5);
        //create a frame for real-time image display  
        CanvasFrame canvasFrame = new CanvasFrame("Camera");  
        Frame image = grabber.grab();  
        int width = image.imageWidth;  
        int height = image.imageHeight;  
        canvasFrame.setCanvasSize(width, height);  
          
        //onscreen buffer for image capture  
        final BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        Graphics2D bGraphics = bImage.createGraphics();       
          
        
          
         
        //real-time image display  
        int num =0;
        while(canvasFrame.isVisible() && (image=grabber.grab()) != null){  
                canvasFrame.showImage(image);
                num++;
                if (num%12 != 0){
                	continue;
                }
                
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage imageExt = converter.getBufferedImage(image, 1.0, false, null);
                try {
					String message = QRCode.from(imageExt);
					System.out.println(message + "--num:" + num);
				} catch (Exception e) {
					
				}
                
                //ImageIO.write(imageExt, "jpg", new File(savedImageFile)); 
                
                //cvSaveImage(savedImageFile, image.image);
                //draw the onscreen image simutaneously  
                //bGraphics.drawImage(image.image,null,0,0);    
        }  
          
        //release resources  
        
             
        grabber.stop();  
        canvasFrame.dispose();  
    }  
  
}  