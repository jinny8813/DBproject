package NewDB;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class GuiOrderSummary {
	private JFrame frame;
	GuiOrderSummary gui;
	
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://140.127.74.186/410977033";
	static final String USER = "410977033";
	static final String PASS = "410977033";

	public String orderSummary(String number) {
		ArrayList alist = new ArrayList();
		alist.clear();
		int count=0;
		String str = "<html><body>";
		
		int n = Integer.parseInt(number);
		System.out.println(n);
		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			String sql = "";
			sql = "SELECT d.update_time, a.site, b.site FROM delivery_log as d\r\n"
					+ "inner join schedule as a on d.update_s_id=a.schedule_id\r\n"
					+ "inner join schedule as b on d.trans_s_id=b.schedule_id\r\n"
					+ "where d.package_id="+n;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				alist.add(rs.getString("d.update_time"));
				alist.add(rs.getString("a.site"));
				alist.add(rs.getString("b.site"));
				count++;
			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		} // end try
		System.out.println("Goodbye!"+count);
		
		if(alist.isEmpty()) {
			str=str+"查無此包裹資訊";
		}
		for(int i=0;i<count;i++) {
			str=str+"更新時間:"+alist.get(i*3).toString()+"\t更新地點:"+alist.get(i*3+1).toString()+"\t即將由"+alist.get(i*3+2).toString()+"運送 <br>";
			System.out.println(str);
		}
		str = str + "</body></html>";
		
		return str;
	}
	
	public GuiOrderSummary() {
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
		
		JLabel n = new JLabel("輸入訂單編號:");    //number
		n.setBounds(440,40,160,25);                       
		frame.getContentPane().add(n);	
		JTextField number = new JTextField(20);                 
		number.setBounds(550,40,250,25);
		frame.getContentPane().add(number);
		number.setColumns(20);
		

//		JLabel t = new JLabel("輸入現在時間:");    //time
//		t.setBounds(440,70,160,25);                       
//		frame.getContentPane().add(t);
//		JLabel t2 = new JLabel("yyyy-mm-dd hh:mm:ss");    //time
//		t2.setBounds(440,100,200,25);                       
//		frame.getContentPane().add(t2);
//		JTextField time = new JTextField(20);                 
//		time.setBounds(550,70,250,25);
//		frame.getContentPane().add(time);
		

		JLabel s = new JLabel("此包裹狀態為:");    //state
		s.setBounds(440,170,160,25);                       
		frame.getContentPane().add(s);
		JLabel state = new JLabel();  
		state.setBounds(440,170,500,200);
		frame.getContentPane().add(state);

		JButton confirm = new JButton("Confirm");    //確認
		confirm.setBounds(600, 120, 100, 30);
		frame.getContentPane().add(confirm);
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String numberstr = number.getText();
				if(numberstr.equals("")) {
					state.setText("請輸入單號");
				}else {
					String str=orderSummary(numberstr);
					state.setText(str);
				}
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
