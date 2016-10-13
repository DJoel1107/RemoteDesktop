package Client;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.sun.image.codec.jpeg.JPEGCodec;

public class OutputPhoto extends Thread {
	private Dimension screenSize;
	private Rectangle rectangle;
	private Robot robot;

	public OutputPhoto() {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		rectangle = new Rectangle(screenSize);// ����ָ��������Ļ����
		try {
			robot = new Robot();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	public void run() {
		ZipOutputStream os = null;
		Socket socket = null;
		
		
		while (true) {
			System.out.println("���Ժͷ�������������");
			try {
				socket = new Socket("localhost", 8888);// ����Զ��IP
				System.out.println("�������ӳɹ�");
			} catch (UnknownHostException e) {
	            System.out.println("����ʧ�ܣ���������");
	        } catch(IOException e) {
	            System.out.println("����ʧ�ܣ����������׽����쳣");
	        } catch (Exception e) {
				e.printStackTrace();
	        }
			
			BufferedImage image = robot.createScreenCapture(rectangle);// �����ƶ���Ļ��������

			try {
				os = new ZipOutputStream(socket.getOutputStream());// ����ѹ����
				// os = new ZipOutputStream(new FileOutputStream("C:/1.zip"));
				os.putNextEntry(new ZipEntry("test.jpg"));
				os.setLevel(9);
				JPEGCodec.createJPEGEncoder(os).encode(image);// ͼ������JPEG
				os.close();
				Thread.sleep(50);// ÿ��20֡
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(InterruptedException e1){
				e1.printStackTrace();
			} finally {
				if (os != null) {
					try {
						os.close();
					} catch (Exception ioe) {
					}
				}
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	
	public static void main(String[] args) {
		new OutputPhoto().start();
	}
}

