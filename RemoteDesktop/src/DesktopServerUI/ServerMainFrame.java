package DesktopServerUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

import util.DesktopRemoteType;
import util.Information;

import DesktopServerProcess.Server;
import DesktopServerProcess.ServerShot;
import DesktopServerProcess.ServerThread;

public class ServerMainFrame extends JFrame {
	/**
	 * 
	 */
	private static final int total = 30;
	private static final long serialVersionUID = 1L;
	private static ServerMainFrame servermainframe;
	
	public static ServerMainFrame getFrame() {
		if(servermainframe == null) {
			servermainframe = new ServerMainFrame();
		}
		return servermainframe;
	}
	// NORTH
	private JButton setterBtn,prtScBtn,tranFileBtn,taskViewBtn,shutdown_upBtn;
	//private FileFrame fileframe;
	
	// EAST
	private JButton send;
	private JPanel row1, row2, row3;
	private JScrollPane sessionRoll, msgRoll, userRoll;
	private JTextArea session;
	private JTextArea msg;
	private JPanel onlineUserList;
	private JLabel onlineMember;
	
	// OTHER
	private FileFrame singleTranFileFrame ;
	public static JButton[] user = new JButton[total];
	
	protected DesktopServerRaiseHandFrame raisehandframe;
	private ServerMainFrame() {
		super("YiDaoCaiԶ��������");
		
		setSingleTranFileFrame(new FileFrame(null));
		raisehandframe = DesktopServerRaiseHandFrame.getFrame();
		
		
		
		JPanel chat = new JPanel(new BorderLayout());
		send = new JButton("����");
		row1 = new JPanel(new BorderLayout());
		row2 = new JPanel(new BorderLayout());
		//row3 = new JPanel(new GridLayout(1, 2));
		
		onlineMember = new JLabel("��ǰ����������0");
		msg = new JTextArea(5, 25);
		msg.setTabSize(4);
		msg.setFont(new Font("����", Font.BOLD, 16));
		msg.setLineWrap(true);
		msg.setWrapStyleWord(true);
		
		session = new JTextArea(20, 25);
		session.setLineWrap(true);
		session.setWrapStyleWord(true);
		session.setFont(new Font("����", Font.BOLD, 16));
		session.append("��ӭ����������\r\n");
		session.setEditable(false);
		sessionRoll = new JScrollPane(session);
		msgRoll = new JScrollPane(msg);
		
		chat.setSize(250, 560);
		row1.add(onlineMember, BorderLayout.NORTH);
		row1.add(sessionRoll, BorderLayout.CENTER);
		row2.add(msgRoll, BorderLayout.CENTER);
		row2.add(send, BorderLayout.SOUTH);
		//row3.add(send);
		chat.add(row1, BorderLayout.NORTH);
		chat.add(row2, BorderLayout.SOUTH);
		//chat.add(row3, BorderLayout.SOUTH);
		
		send.addActionListener(new Action());
		onlineUserList = new JPanel();
		onlineUserList.setLayout(new GridLayout(10,3));//new GridLayout(18, 3)
		for(int i = 0; i < total;i++){
			user[i]  = new JButton();
			user[i].setPreferredSize(new Dimension(200, 150));
			user[i].setVisible(false);
			user[i].setUI(new BasicButtonUI());
			user[i].setContentAreaFilled(false);
			user[i].setMargin(new Insets(0, 0, 0, 0));
			user[i].addActionListener(new Enlarge());
			onlineUserList.add(user[i]);
			
			//д��˫���Ŵ��
		}
		userRoll = new JScrollPane(onlineUserList);
		
		setterBtn = createBtn("�� ��", "./image/set.png");
		prtScBtn = createBtn("�� ��", "./image/prtSc.png");
		tranFileBtn = createBtn("�����ļ�", "./image/tranFile.png");
		taskViewBtn = createBtn("���̼��", "./image/taskView.png");
		shutdown_upBtn = createBtn("���ػ�", "./image/shutdown_up.png");
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JPanel p = new JPanel(new GridLayout(1, 5));
		//tranFileBtn.addActionListener(new Action());
		
		
		p.add(setterBtn);
		p.add(prtScBtn);
		p.add(tranFileBtn);
		p.add(taskViewBtn);
		p.add(shutdown_upBtn);
		
		cp.add(p, BorderLayout.NORTH);
		cp.add(chat, BorderLayout.EAST);
		cp.add(userRoll, BorderLayout.CENTER);
		
		this.setSize(900, 640);
		this.setLocation(500,40);
		this.setResizable(false);
		this.setVisible(true);
		this.setBackground(Color.yellow);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static JButton addBtn() {
		JButton jb = user[ServerShot.getTotal()];
		jb.setVisible(true);
		//servermainframe.onlineUserList.add(jb);
		return jb;
	}
	
	public class Action implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == tranFileBtn) {
				singleTranFileFrame.setVis(true);
			} else if(e.getSource() == send) {
				Iterator<Entry<InetAddress, ServerThread>> iter = ServerThread.getUser();
				while(iter.hasNext()) {
					Map.Entry<InetAddress, ServerThread> val = iter.next();
					System.out.println("interface : " + val);
					val.getValue().sendMessage(Information.createSession("Server", msg.getText()));
				}
				addSession("[Server]" + msg.getText());
				msg.setText(null);
			}
		}
		
	}
	private class Enlarge implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			watchFrame.getFrame().setVisible(true);
			watchFrame.getFrame().setTitle(watchFrame.getAddress(e.getSource()).toString());
			watchFrame.setStatu(true);
			watchFrame.setInetAddress(watchFrame.getAddress(e.getSource()));
		}
		
		
	}
	public void setOnlineMember(String value) {
		onlineMember.setText(value);
	}
	
	private JButton createBtn(String text, String icon) {
		ImageIcon ii = new ImageIcon(icon);
		Image image = ii.getImage(); 
		Image smallImage = image.getScaledInstance(64,64,Image.SCALE_FAST);
		JButton btn = new JButton(text, new ImageIcon(smallImage));
		
		btn.setUI(new BasicButtonUI());
		btn.setPreferredSize(new Dimension(64, 64));
		btn.setContentAreaFilled(false);
		btn.setFont(new Font("����", Font.PLAIN, 15));
		btn.setMargin(new Insets(0, 0, 0, 0));
		btn.addActionListener(new Action());
		//btn.addMouseListener(new MyMouseListener(this));
		return btn;
	}
	public void addSession(String content) {
		session.append(content + "\r\n");
	}

	public FileFrame getSingleTranFileFrame() {
		return singleTranFileFrame;
	}

	public void setSingleTranFileFrame(FileFrame singleTranFileFrame) {
		this.singleTranFileFrame = singleTranFileFrame;
	}
	public void showRaiseHand(String value) {
		try {
			raisehandframe.setText(value);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
