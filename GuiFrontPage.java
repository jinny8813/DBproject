package NewDB;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class GuiFrontPage {
	private JFrame frame;
	GuiFrontPage gui;

	public GuiFrontPage() {
		gui = this;
	}

	void setVisible(boolean visiable) {
		frame.setVisible(visiable);
	}

	void initialize() {
		frame = new JFrame();
		frame.setTitle("Delivery Company");
		frame.setBounds(0, 0, 1300, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel Label = new JLabel("choose identity");
		Label.setBounds(600, 70, 200, 40);
		frame.getContentPane().add(Label);

		JButton btn_client = new JButton("Client");
		btn_client.setBounds(600, 120, 96, 45);
		frame.getContentPane().add(btn_client);
		btn_client.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiClient gui = new GuiClient();
				gui.initialize();
				gui.setVisible(true);
				frame.dispose();
			}
		});

		JButton btn_manager = new JButton("Manager");
		btn_manager.setBounds(600, 180, 96, 45);
		frame.getContentPane().add(btn_manager);
		btn_manager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiManager gui = new GuiManager();
				gui.initialize();
				gui.setVisible(true);
				frame.dispose();
			}
		});

	}

	//public void identity(JPanel panel_) {
//		JFrame frame = new JFrame("Delivery Company");
//		frame.setSize(1300, 800);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		JPanel panel = new JPanel();    // 添加面板
//		frame.add(panel);
//		frame.setVisible(true);
//		panel.setLayout(null);	

//		JLabel Label = new JLabel("choose identity");
//		Label.setBounds(600, 20, 200, 40);        //setBounds(x, y, width, height)
//		panel.add(Label);
		//JButton loginButton1 = new JButton("Client");       //客戶按鈕
		//		loginButton1.addActionListener(new ActionListener() {
		//			public void actionPerformed(ActionEvent e) {
		//				loginStudent A02 = new loginStudent();
		//				A02.loginStudent(panel);
		//				frame.dispose();
		//			}
		//		});
//		loginButton1.setBounds(135, 70, 96, 23);
//		panel.add(loginButton1);

		//JButton loginButton2 = new JButton("Manager");     //登入按鈕
		//		loginButton2.addActionListener(new ActionListener() {
		//			public void actionPerformed(ActionEvent e) {
		//				loginManager A02 = new loginManager();
		//				A02.loginManager(panel);
		//				frame.dispose();
		//			}
		//		});
//		loginButton2.setBounds(335, 70, 96, 23);
//		panel.add(loginButton2);	
//	}

}

