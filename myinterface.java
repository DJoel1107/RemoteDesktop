import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class myinterface extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btn1,btn2,btn3,btn4,btn5;
	public myinterface() {
		super("YiDaoCaiԶ��������");
		btn1=new JButton("����");
		btn2=new JButton("����");
		btn3=new JButton("�����ļ�");
		btn4=new JButton("���̼��");
		btn5=new JButton("���ػ�");
		Container cp=getContentPane();
		cp.setLayout(null);
		
		
		//���ð�ť��С��߾�
		btn1.setBounds(50,50,100,50);
		btn2.setBounds(200,50,100,50);
		btn3.setBounds(350,50,140,50);
		btn4.setBounds(540,50,140,50);
		btn5.setBounds(730,50,120,50);
		
		//���ð�ť�������С
		btn1.setFont(new Font("����",Font.PLAIN,20));
		btn2.setFont(new Font("����",Font.PLAIN,20));
		btn3.setFont(new Font("����",Font.PLAIN,20));
		btn4.setFont(new Font("����",Font.PLAIN,20));
		btn5.setFont(new Font("����",Font.PLAIN,20));
		
		cp.add(btn1);
		cp.add(btn2);
		cp.add(btn3);
		cp.add(btn4);
		cp.add(btn5);
	}
	
	
	
	public static void main(String[] args)
	{
		myinterface frame=new myinterface();
		frame.setSize(1000,900);
		frame.setLocation(500,40);
		frame.setVisible(true);
		frame.setBackground(Color.yellow);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}


