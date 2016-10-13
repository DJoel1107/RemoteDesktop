package DesktopServerProcess;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


import util.Information;

import DesktopServerUI.ServerMainFrame;

public class ServerThread extends ServerSocketHandler {
    private static Map<String, ServerThread> UserList = new HashMap<String, ServerThread>();
    //private BufferedReader is;
	public ServerThread(Socket c) throws IOException {  
        super(c);
    }
    public void setClient(Socket c) {
    	this.socket = c;
    }
    public Socket getSocket() {
    	return this.socket;
    }

	public static int getUserList() {
		return UserList.size();
	}
	public static Iterator<Entry<String, ServerThread>> getUser() {
		return UserList.entrySet().iterator();
	}
	public static void addUserList(String intelAdd, ServerThread st) {
		UserList.put(intelAdd, st);
	}
	public static void removeUserList(String intelAdd) {
		UserList.remove(intelAdd);
	}
	/**
	 * @return
	 * ��ͻ��˷����ļ�
	 */
	public boolean sendFile(String filepath) {
		File file = new File(filepath);
		//int flen = (int)file.length();
		 //DataInputStream dis;
		try {
			//dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			//dis.readByte();
			DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(filepath)));
            DataOutputStream ps = new DataOutputStream(socket.getOutputStream());
            ps.writeUTF(file.getName());
            ps.flush();
            ps.writeLong((long) file.length());
            ps.flush();

            int bufferSize = 8192;
            byte[] buf = new byte[bufferSize];

            while (true) {
                int read = 0;
                if (fis != null) {
                    read = fis.read(buf);
                }

                if (read <= 0) {
                    break;
                }
                ps.write(buf, 0, read);
            }
            System.out.println("�ļ��������");
            ps.flush();
            ps.close();
            fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
		return false;
	}
	/**
	 * @return
	 * ��ͻ��˷�����Ϣ
	 */
	public void sendMessage(Information info) {
		writer.send(info.toString());
		//����ϵͳ��׼���������ַ��������Server
	}
}