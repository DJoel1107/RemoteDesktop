import java.io.*;
import java.net.*;
public class Client {
	private final String SelfAddress;
	private final String HostName;
	private String ServerAddress;
	private int port;
	private Socket socket;
	public Client() {
		this.ServerAddress = "127.0.0.1";
		this.port = 1234;
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
	/**
	 * @return
	 * ��Socketͨ��
	 */
	public boolean openSocket() {
		try {
			this.socket = new Socket(this.ServerAddress, this.port);
			receive();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
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
	 * �����˷�����Ϣ
	 */
	public boolean send(Information info) {
		
		return false;
	}
	/**
	 * @return
	 * �������Է���˵���Ϣ
	 */
	public boolean receive() {
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
		 String savePath = "E:\\";
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
	
	/**
	 * @return
	 * �Ա��ؽ��м򵥲���
	 */
	public boolean operator(Operator oper) {
		
		return false;
	}
	public String toString() {
		return HostName + ":" + SelfAddress;
	}
	public Client(String ServerAddress, int port) {
		this.ServerAddress = ServerAddress;
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
	public String getServerAddress() {
		return ServerAddress;
	}
	public void setServerAddress(String serverAddress) {
		ServerAddress = serverAddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public static void main(String args[]) {
		Client test = new Client();
		test.openSocket();
		System.out.println("Done......");
		test.closeSocket();
		//System.out.println(test);
		/*
		try{
			Socket socket=new Socket("127.0.0.1",4700);
			//�򱾻���4700�˿ڷ����ͻ�����
			BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
			//��ϵͳ��׼�����豸����BufferedReader����
			PrintWriter os=new PrintWriter(socket.getOutputStream());
			//��Socket����õ��������������PrintWriter����
			BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//��Socket����õ�����������������Ӧ��BufferedReader����
			String readline;
			readline=sin.readLine(); //��ϵͳ��׼�������һ�ַ���
			while(!readline.equals("bye")){
				//���ӱ�׼���������ַ���Ϊ "bye"��ֹͣѭ��
				os.println(readline);
				//����ϵͳ��׼���������ַ��������Server
				os.flush();
				//ˢ���������ʹServer�����յ����ַ���
				System.out.println("Client:"+readline);
				//��ϵͳ��׼����ϴ�ӡ������ַ���
				System.out.println("Server:"+is.readLine());
				//��Server����һ�ַ���������ӡ����׼�����
				readline=sin.readLine(); //��ϵͳ��׼�������һ�ַ���
			} //����ѭ��
			os.close(); //�ر�Socket�����
			is.close(); //�ر�Socket������
			socket.close(); //�ر�Socket
		}catch(Exception e) {
			System.out.println("Error: "+ e);
			//�������ӡ������Ϣ
		}
		*/
	}
}
