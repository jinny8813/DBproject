package NewDB;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GuiClient {
	private JFrame frame;
	GuiClient gui;
	
	public GuiClient() {
	       gui = this;
	}
	
	void setVisible(boolean visiable) {
		frame.setVisible(visiable);
	}
	
	void initialize() {
		frame = new JFrame();
		frame.setTitle("Client");
		frame.setBounds(0, 0, 1300, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel Label = new JLabel("options:");
		Label.setBounds(600, 70, 200, 50);
		frame.getContentPane().add(Label);
		
		JButton Return = new JButton("return");    //上一頁
		Return.setBounds(20, 50, 100, 30);
		frame.getContentPane().add(Return);
		Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiFrontPage gui = new GuiFrontPage();
				gui.initialize();
				gui.setVisible(true);
				frame.dispose();
			}
		});
		
//		JButton btn1 = new JButton("Add a package");    //新增包裹
//		btn1.setBounds(600, 120, 120, 50);
//		frame.getContentPane().add(btn1);
		
		JButton btn2 = new JButton("Order summary");    //查詢訂單狀況
		btn2.setBounds(600, 180, 120, 50);
		frame.getContentPane().add(btn2);
		
		JButton btn3 = new JButton("Meter pricing");    //價格試算
		btn3.setBounds(600, 240, 120, 50);
		frame.getContentPane().add(btn3);
		
		
//		btn1.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				GuiAddPackage gui = new GuiAddPackage();
//				gui.initialize();
//				gui.setVisible(true);
//				frame.dispose();
//			}
//		});
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiOrderSummary gui = new GuiOrderSummary();
				gui.initialize();
				gui.setVisible(true);
				frame.dispose();
			}
		});
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiMeterPricing gui = new GuiMeterPricing();
				gui.initialize();
				gui.setVisible(true);
				frame.dispose();
			}
		});
	}
}
