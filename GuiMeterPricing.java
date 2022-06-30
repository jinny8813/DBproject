package NewDB;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class GuiMeterPricing {

	
	private JFrame frame;
	GuiMeterPricing gui;

	public GuiMeterPricing() {
		gui = this;
	}

	void setVisible(boolean visiable) {
		frame.setVisible(visiable);
	}

	void initialize() {     
		frame = new JFrame();
		frame.setTitle("Meter pricing");
		frame.setBounds(0, 0, 1300, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		String[] nation_arr = {" ","國內","國外"};
		String[] time_arr = {" ","一般","快件"};
		String[] size_arr = {" ","平信","小包","大盒"};

		JLabel na = new JLabel("Nation:");    //nation
		na.setBounds(480,40,160,25);                       
		frame.getContentPane().add(na);
		JComboBox<String> nation = new JComboBox<>(nation_arr);
		nation.setBounds(550,40,254,25);
		frame.getContentPane().add(nation);

		JLabel t = new JLabel("Timeliness:");    //timeliness
		t.setBounds(480,70,160,25);                       
		frame.getContentPane().add(t);
		JComboBox<String> timeliness = new JComboBox<>(time_arr);
		timeliness.setBounds(550,70,254,25);
		frame.getContentPane().add(timeliness);


		JLabel s = new JLabel("Size:");    //size
		s.setBounds(480,100,160,25);                       
		frame.getContentPane().add(s);
		JComboBox<String> size = new JComboBox<>(size_arr);
		size.setBounds(550,100,254,25);
		frame.getContentPane().add(size);

		JLabel p = new JLabel("The price for this order is:");    //顯示價格
		p.setBounds(480,250,200,25);
		frame.getContentPane().add(p);
		
		JTextField Price = new JTextField("") ;
		Price.setEditable(false);
		Price.setBounds(650,250,60,25);
		frame.getContentPane().add(Price);

		JButton confirm = new JButton("Confirm");    //確認
		confirm.setBounds(600, 130, 100, 30);
		frame.getContentPane().add(confirm);
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected_nation = nation.getItemAt(nation.getSelectedIndex());
				String selected_timeliness = timeliness.getItemAt(timeliness.getSelectedIndex());
				String selected_size = size.getItemAt(size.getSelectedIndex());
				int prince = 0;
				if(selected_nation.equals("國內")) 
					prince += 30;
				else if(selected_nation.equals("國外"))
					prince += 100;
				if(selected_timeliness.equals("一般")) 
					prince += 20;
				else if(selected_timeliness.equals("快件"))
					prince += 70;
				if(selected_size.equals("平信")) 
					prince += 5;
				else if(selected_size.equals("小包"))
					prince += 20;
				else if(selected_size.equals("大盒"))
					prince += 40;
				
				Price.setText(Integer.toString(prince));       //顯示計算完的價格
			}
		});

		JButton Return = new JButton("return");    //上一頁
		Return.setBounds(20, 50, 100, 30);
		frame.getContentPane().add(Return);
		Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiClient gui = new GuiClient();
				gui.initialize();
				gui.setVisible(true);
				frame.dispose();
			}
		});

		JButton FrontPage = new JButton("FrontPage");    //回首頁
		FrontPage.setBounds(20, 15, 100, 30);
		frame.getContentPane().add(FrontPage);
		FrontPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiFrontPage gui = new GuiFrontPage();
				gui.initialize();
				gui.setVisible(true);
				frame.dispose();
			}
		});
	}
}
