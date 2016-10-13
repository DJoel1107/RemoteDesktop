package DesktopServerUI;

import java.awt.*;

import javax.swing.*;

public class DesktopServerRaiseHandFrame {
	private JFrame frm;
	private JLabel text;
	private int width, height;
	private Dimension screenSize;

	private static DesktopServerRaiseHandFrame raisehandframe;
	
	public static DesktopServerRaiseHandFrame getFrame() {
		if(raisehandframe == null) {
			try {
				raisehandframe = new DesktopServerRaiseHandFrame();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return raisehandframe;
	}
	
	private DesktopServerRaiseHandFrame() throws InterruptedException {
		width = 200;
		height = 80;
		frm = new JFrame();
		text = new JLabel();
		// ������һ��Ϳ�����ʾһ�����йرգ���С������󻯵İ�ť��Frame��
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// �ټ�����һ��Ϳ�����ʾһ�������Ͻǣ�ӵ��ָ����С��Frame��
		frm.setSize(width, height);
		frm.setLocation(screenSize.width - width, screenSize.height - height
				- 40);
		frm.setResizable(false);
		
		// ���û����һ�䣬�ڵ���ر�Frame��ʱ�������ʵ������ִ��״̬�еģ�������һ������������İ���Դ�ͷŵ���
		//frm.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		Container c = frm.getContentPane();

		JPanel panel = new JPanel();
		c.add(panel, BorderLayout.CENTER);
		// panel.setVisible(true);
		text.setFont(new Font("����", Font.BOLD, 24));
		panel.add(text);
	}

	public void setText(String value) throws InterruptedException {
		text.setText(value);
		width = value.length() * 30;
		reSize();
		frm.setVisible(true);
		Thread.sleep(3000);
		frm.setVisible(false);
	}

	private void reSize() {
		frm.setSize(width, height);
		frm.setLocation(screenSize.width - width, screenSize.height - height
				- 40);
	}

	/*public static void main(String[] args) {
		// ���ڴ�����һ�����󣬲���ʲô����ʾ������
		try {
			DesktopServerRaiseHandFrame d = new DesktopServerRaiseHandFrame();
			d.setText("��ͬѧ����");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/

}