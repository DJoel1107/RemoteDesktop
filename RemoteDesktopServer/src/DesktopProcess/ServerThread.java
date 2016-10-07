package DesktopProcess;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import DesktopUI.myinterface;

public class ServerThread extends Thread {
	private static myinterface mainframe;
	private Socket client;
    private static Map<String, ServerThread> UserList = new HashMap<String, ServerThread>();
    private BufferedReader is;
	private PrintWriter os;
    public static void setMainframe(myinterface mainframe) {
		ServerThread.mainframe = mainframe;
	}
	public ServerThread(Socket c) {  
        this.client = c;
		try {
			is = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
			os = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void setClient(Socket c) {
    	this.client = c;
    }
    public void run() {  
        try {  
            mainframe.addSession(client.getInetAddress()+":"+client.getPort()+"������");
            addUserList(client.getInetAddress()+":"+client.getPort(), this);
            System.out.println("run : " + this);
            //System.out.println("��ǰ��������Ϊ��" + getUserList());
            mainframe.addSession("��ǰ��������Ϊ��" + getUserList());
            while (true) {  
                String str = is.readLine();  
                //System.out.println(client.getInetAddress() + ":" + client.getPort() + "\n" + str);  
                Information reci = new Information(str);
                if(reci.getType().equals("session")) {
                	mainframe.addSession("[" + client.getInetAddress() + ":" + client.getPort() + "] �Ի� \n" + reci.getContent());
                } else if(reci.getType().equals("raisehand")) {
                	mainframe.addSession("[" + client.getInetAddress() + ":" + client.getPort() + "] ����");
                }
                
                if (str.equals("quit"))  
                    break;  
            }
            client.close();
            os.close();
            is.close();
        } catch (IOException ex) {  
        } finally {  
        	//System.out.println(client.getInetAddress()+":"+client.getPort()+"������");
            mainframe.addSession(client.getInetAddress()+":"+client.getPort()+"������");
        	removeUserList(client.getInetAddress()+":"+client.getPort());
            mainframe.addSession("��ǰ��������Ϊ��" + getUserList());
        	//System.out.println("��ǰ��������Ϊ��" + getUserList());
        }  
    }  

	public int getUserList() {
		return UserList.size();
	}
	public static Iterator<Entry<String, ServerThread>> getUser() {
		return UserList.entrySet().iterator();
	}
	public void addUserList(String intelAdd, ServerThread st) {
		UserList.put(intelAdd, st);
	}
	public void removeUserList(String intelAdd) {
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
            DataOutputStream ps = new DataOutputStream(client.getOutputStream());
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
	public boolean send(Information info) {
		os.println(info.toString());
		//����ϵͳ��׼���������ַ��������Server
		os.flush(); 
		return false;
	}
	/**
	 * @return
	 * �������Կͻ��˵���Ϣ
	 */
	public Information receive() {
		try {
			return new Information(is.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
}