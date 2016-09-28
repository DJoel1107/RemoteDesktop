import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	private final String SelfAddress;
	private final String HostName;
	private int port;
	private ServerSocket server;
	private Socket socket;
	public Server() {
		InetAddress ia=null;
        try {
            ia = InetAddress.getLocalHost();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.HostName = ia.getHostName();
        this.SelfAddress = ia.getHostAddress();
        port = 1234;
	}
	public boolean openServer() {
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * @return
	 * ��Socketͨ��
	 */
	public boolean openSocket() {
		try {
			socket = server.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("����socket����");
		return true;
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
	 * ��ͻ��˷�����Ϣ
	 */
	public boolean send(String filepath) {
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
	 * �������Կͻ��˵���Ϣ
	 */
	public boolean receive() {
		
		return false;
	}
	public String getHostName() {
		return HostName;
	}
	public String getSelfAddress() {
		return SelfAddress;
	}
}
