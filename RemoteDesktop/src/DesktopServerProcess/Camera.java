package DesktopServerProcess;


import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.Component;

import com.sun.image.codec.jpeg.*;

public class Camera extends Thread{
    //private String fileName; //�ļ���ǰ׺

    //int serialNum = 0;

    //private String imageFormat; //ͼ���ļ��ĸ�ʽ

    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

    //private ServerThread server;
   // Ĭ�ϵ��ļ�ǰ׺ΪGuiCamera���ļ���ʽΪPNG��ʽ
    public Camera() {
        //fileName = "Camera";
        //imageFormat = "jpg";
    }

    public Camera(String s, String format, ServerThread client){
    	//server = client;
        //fileName = s;
        //imageFormat = format;
    }

     // ����Ļ��������
    public void run(){
    	FileOutputStream fos = null;
    	//server.openSocket();
        try{
        	Robot robot = new Robot();
        	Rectangle screen_size = new Rectangle(0, 0,
        			(int) d.getWidth(), (int) d.getHeight());
        	while(true) {  //���ƴ���
	            //������Ļ��һ��BufferedImage����screenshot

	        	//Point p= MouseInfo.getPointerInfo().getLocation();
	            BufferedImage screenshot = robot
	                    .createScreenCapture(screen_size);
	            
	            
	            //�����ļ�ǰ׺�������ļ���ʽ�������Զ������ļ���
	            //String name = fileName + String.valueOf(serialNum) + "." + imageFormat;
	            //serialNum++;
	          //  File f = new File(name);
	            //System.out.println("Save File " + name);  //
	            
	            //������겢�ӵ�ͼƬ��
	           // BufferedImage cursor = ImageIO.read(new File("C:\\Windows\\Cursors\\aero_arrow.cur"));
	           // screenshot.createGraphics().drawImage(cursor, p.x, p.y, null);
	            
	            //��screenshot����д��ͼ���ļ�
	           // ImageIO.write(screenshot, imageFormat, f);
	            //fos = new FileOutputStream(name);
	            
	            JPEGCodec.createJPEGEncoder(fos).encode(screenshot);
	            fos.close();
	            //server.send(name);
	            Thread.sleep(40);//ÿ��25֡
        	}
            
           // System.out.println("..Finished! ");  //
            
        } catch (AWTException e) {
        	
            e.printStackTrace();  
        } catch (Exception ex){
        	
        	ex.printStackTrace();
        } finally {
        	try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    //public static void main(String[] args){
        //System.out.println("������");  //
        //new Camera("D:\\records\\Hello", "jpg").start();
        
        // String imagepath2 = ScreenSnapshot.class.getResource("/").getPath().toString();
        //
        //new WnetWScreenRecordPlayer();
    //}
}