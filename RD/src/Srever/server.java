package Srever;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class server {
	Socket s = null;
	ServerSocket ss = null;
	HashMap<String,Integer> hash = new HashMap<String,Integer>();
	public server(){
		try {
			ss = new ServerSocket(8888);// ̽��8888�˿ڵ�����
			int ca = 0;
			while(true){
				try {
					System.out.println("��������");
					s = ss.accept();
					System.out.println("���ճɹ�");
					String ip = s.getInetAddress().getHostAddress();
					if(!hash.containsKey(ip)){
						if(ca >= 54) continue;
						hash.put(ip, ca++);
					}
					InputPhoto p = new InputPhoto(s,hash.get(ip));
					new Thread(p).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	} catch (Exception e) {
	} 
}

}
