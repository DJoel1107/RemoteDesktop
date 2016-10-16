package DesktopClientProcess;

import java.io.*;
import java.net.*;


import util.DesktopRemoteType;
import util.Information;
import util.XMLUtil;

public class Client {
	private final String SelfAddress;
	private final String HostName;
	private static String ServerAddress;
	private int port;
	private Socket socket;
	
	public Socket getSocket() {
		return socket;
	}
	private ClientSocketHandler handler;
	public Client(DesktopRemoteType type) throws IOException {
		//Client.ServerAddress = XMLUtil.getBean("server");
		this.port = type.getPort();
		InetAddress ia=null;
        try {
            ia=InetAddress.getLocalHost();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.HostName = ia.getHostName();
        this.SelfAddress = ia.getHostAddress();
        openSocket();
        handler = new ClientSocketHandler(this);
        handler.listen(true);
        new ClientShot().start();
        System.out.println("shot start...");
	}
	/**
	 * @return
	 * ��Socketͨ��
	 */
	public void openSocket() {
		boolean ok = false;
		String ip = null;
		if(!XMLUtil.exist()) {
			ip = javax.swing.JOptionPane.showInputDialog(null, " �����������ַ");
			XMLUtil.createXML(ip);
		}
		while(!ok) {
			try {
				Client.ServerAddress = XMLUtil.getBean("server");
				socket = new Socket(Client.ServerAddress, this.port);
				socket.setKeepAlive(true);
				socket.setTcpNoDelay(true);
				ok = true;
			} catch (UnknownHostException e) {
				ip = javax.swing.JOptionPane.showInputDialog(null, "��������ַ���������������������ַ");
				XMLUtil.createXML(ip);
			} catch (ConnectException ex){
				ip = javax.swing.JOptionPane.showInputDialog(null, "���ӷ�������ʱ�������������������ַ");
				XMLUtil.createXML(ip);
			} catch (Exception e) {
				ip = javax.swing.JOptionPane.showInputDialog(null, "���ӷ�����ʧ�ܣ������������������ַ");
				XMLUtil.createXML(ip);
			}
		}
	}
	/**
	 * @return
	 * �ر�Socketͨ��
	 */
	public boolean closeSocket() {
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * @return
	 * �����˷�����Ϣ
	 */
	public void send(Information info) {
		handler.sendMessage(info.toString());
	}
	/**
	 * @return
	 * ���շ���˵���Ϣ
	 */
	public Information receive() {
		return null;
	}
	/**
	 * @return
	 * �������Է���˵��ļ�
	 */
	public boolean receiveFile() {
		if(socket == null) return false;
		 DataInputStream inputStream = null;
		 DataInputStream getMessageStream = null;
		try {
			getMessageStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 inputStream = getMessageStream;
		 String savePath = "D:\\receive\\";
		 int bufferSize = 8192;
		 byte[] buf = new byte[bufferSize];
		 int passedlen = 0;
		 long len=0;
		 try {
			savePath += inputStream.readUTF();
			DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new BufferedOutputStream(new FileOutputStream(savePath))));
			len = inputStream.readLong();
			System.out.println("�ļ��ĳ���Ϊ:" + len + "\n");
			System.out.println("��ʼ�����ļ�!" + "\n");
			while (true) {
                int read = 0;
                if (inputStream != null) {
                    read = inputStream.read(buf);
                }
                passedlen += read;
                if (read <= 0) {
                    break;
                }
                //�����������Ϊͼ�ν����prograssBar���ģ���������Ǵ��ļ������ܻ��ظ���ӡ��һЩ��ͬ�İٷֱ�
                System.out.println("�ļ�������" +  (passedlen * 100/ len) + "%\n");
                fileOut.write(buf, 0, read);
            }
            System.out.println("������ɣ��ļ���Ϊ" + savePath + "\n");
            fileOut.close();
            inputStream.close();
            getMessageStream.close();
			
		 } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 //inputStream = socket.getMessageStream();
		return true;
	}
	
	public String toString() {
		return HostName + ":" + SelfAddress;
	}
	public Client(String ServerAddress, int port) {
		Client.ServerAddress = ServerAddress;
		this.port = port;
		InetAddress ia=null;
        try {
            ia=InetAddress.getLocalHost();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.HostName = ia.getHostName();
        this.SelfAddress = ia.getHostAddress();
	}
	public static String getServerAddress() {
		return ServerAddress;
	}
	public void setServerAddress(String serverAddress) {
		ServerAddress = serverAddress;
	}
	public String getSelfAddress() {
		return SelfAddress;
	}
	public int getPort() {
		return port;
	}
	public static void main(String args[]) throws IOException {
		//String ip = javax.swing.JOptionPane.showInputDialog(null, "��������ַ���������������������ַ");
		new Client(DesktopRemoteType.OtherType);
	}
	
}