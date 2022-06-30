package NewDB;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.Charset;
import java.util.Random;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class GuiAddPackage {
	private JFrame frame;
	GuiAddPackage gui;

	public GuiAddPackage() {
		gui = this;
	}

	void setVisible(boolean visiable) {
		frame.setVisible(visiable);
	}
	static String getRandomString() {          //產生隨機字串
        int i=10;
        // bind the length 
		byte[] bytearray;
        bytearray = new byte[256];             
        String mystring;
        StringBuffer thebuffer;
        String theAlphaNumericS;

        new Random().nextBytes(bytearray); 

        mystring 
            = new String(bytearray, Charset.forName("UTF-8")); 
            
        thebuffer = new StringBuffer();
        
        //remove all spacial char 
        theAlphaNumericS 
            = mystring 
                .replaceAll("[^A-Z0-9]", ""); 
      //random selection
        for (int m = 0; m < theAlphaNumericS.length(); m++) { 

            if (Character.isLetter(theAlphaNumericS.charAt(m)) 
                    && (i > 0) 
                || Character.isDigit(theAlphaNumericS.charAt(m)) 
                    && (i > 0)) { 

                thebuffer.append(theAlphaNumericS.charAt(m)); 
                i--; 
            } 
        } 

        // the resulting string 
        return thebuffer.toString(); 
    } 


	void initialize() {     
		frame = new JFrame();
		frame.setTitle("Add Package");
		frame.setBounds(0, 0, 1300, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel c_name = new JLabel("Sender Name:");    //寄件人姓名
		c_name.setBounds(270,20,150,25);                       
		frame.getContentPane().add(c_name);

		JTextField name = new JTextField(20);                 
		name.setBounds(430,20,250,25);
		frame.getContentPane().add(name);
		name.setColumns(20);

		JLabel c_adress = new JLabel("Sender Adress:");    //寄件人地址
		c_adress.setBounds(270,50,150,25);                       
		frame.getContentPane().add(c_adress);

		JTextField client_adress = new JTextField(20);                 
		client_adress.setBounds(430,50,250,25);
		frame.getContentPane().add(client_adress);
		client_adress.setColumns(20);

		JLabel c_id = new JLabel("Sender Town ID:");    //town_id
		c_id.setBounds(270,80,150,25);                       
		frame.getContentPane().add(c_id);

		JTextField town_id = new JTextField(20);                 
		town_id.setBounds(430,80,250,25);
		frame.getContentPane().add(town_id);
		town_id.setColumns(20);

		JLabel c_phone = new JLabel("Sender Phone Number:");    //寄件人電話
		c_phone.setBounds(270,110,150,25);                       
		frame.getContentPane().add(c_phone);

		JTextField client_phone = new JTextField(20);                 
		client_phone.setBounds(430,110,250,25);
		frame.getContentPane().add(client_phone);
		client_phone.setColumns(20);


		JLabel reciever_name = new JLabel("Reciever Name:");    //收件人姓名
		reciever_name.setBounds(270,170,150,25);                       
		frame.getContentPane().add(reciever_name);

		JTextField reciever = new JTextField(20);                 
		reciever.setBounds(430,170,250,25);
		frame.getContentPane().add(reciever);
		name.setColumns(20);

		JLabel r_adress = new JLabel("Reciever Adress:");    //收件人地址
		r_adress.setBounds(270,200,150,25);                       
		frame.getContentPane().add(r_adress);

		JTextField reciever_adress = new JTextField(20);                 
		reciever_adress.setBounds(430,200,250,25);
		frame.getContentPane().add(reciever_adress);
		reciever_adress.setColumns(20);

		JLabel r_id = new JLabel("Reciever Town ID:");    //town_id
		r_id.setBounds(270,230,150,25);                       
		frame.getContentPane().add(r_id);

		JTextField reciever_town_id = new JTextField(20);                 
		reciever_town_id.setBounds(430,230,250,25);
		frame.getContentPane().add(reciever_town_id);
		reciever_town_id.setColumns(20);

		JLabel r_phone = new JLabel("Reciever Phone Number:");    //收件人電話
		r_phone.setBounds(270,260,160,25);                       
		frame.getContentPane().add(r_phone);

		JTextField reciever_phone = new JTextField(20);                 
		reciever_phone.setBounds(430,260,250,25);
		frame.getContentPane().add(reciever_phone);
		reciever_phone.setColumns(20);

		JLabel w = new JLabel("weight:");    //weight
		w.setBounds(270,310,160,25);                       
		frame.getContentPane().add(w);

		JTextField weight = new JTextField(20);                 
		weight.setBounds(430,310,250,25);
		frame.getContentPane().add(weight);
		weight.setColumns(20);

		String[] nation_arr = {" ","國內","國外"};
		String[] time_arr = {" ","一般","快件"};
		String[] size_arr = {" ","平信","小包","大盒"};
		String[] method_arr = {" ","現金","刷卡"};

		JLabel na = new JLabel("Nation:");    //nation
		na.setBounds(270,340,160,25);                       
		frame.getContentPane().add(na);
		JComboBox<String> nation = new JComboBox<>(nation_arr);
		nation.setBounds(430,340,254,25);
		frame.getContentPane().add(nation);

		JLabel t = new JLabel("Timeliness:");    //timeliness
		t.setBounds(270,370,160,25);                       
		frame.getContentPane().add(t);
		JComboBox<String> timeliness = new JComboBox<>(time_arr);
		timeliness.setBounds(430,370,254,25);
		frame.getContentPane().add(timeliness);


		JLabel s = new JLabel("Size:");    //size
		s.setBounds(270,400,160,25);                       
		frame.getContentPane().add(s);
		JComboBox<String> size = new JComboBox<>(size_arr);
		size.setBounds(430,400,254,25);
		frame.getContentPane().add(size);
		
		JLabel n = new JLabel("The order number is:");    //顯示訂單編號
		n.setBounds(800,80,200,25);
		frame.getContentPane().add(n);
		
		JTextField Num = new JTextField("") ;
		Num.setEditable(false);
		Num.setBounds(970,80,100,25);
		frame.getContentPane().add(Num);

		JLabel p = new JLabel("The price for this order is:");    //顯示價格
		p.setBounds(800,120,200,25);
		frame.getContentPane().add(p);
		
		JTextField Price = new JTextField("") ;
		Price.setEditable(false); 
		Price.setBounds(970,120,100,25);
		frame.getContentPane().add(Price);
		
		JLabel d = new JLabel("The date for this order is:");    //顯示時間
		d.setBounds(800,160,200,25);
		frame.getContentPane().add(d);
		
		JTextField Date = new JTextField("") ;
		Date.setEditable(false);
		Date.setBounds(970,160,100,25);
		frame.getContentPane().add(Date);
		
		JLabel met = new JLabel("Payment methods:");    //付費方式
		met.setBounds(800,200,160,25);                       
		frame.getContentPane().add(met);
		JComboBox<String> method = new JComboBox<>(method_arr);
		method.setBounds(927,200,105,25);
		frame.getContentPane().add(method);

		JButton confirm = new JButton("Confirm");    //確認
		confirm.setBounds(450, 500, 100, 30);
		frame.getContentPane().add(confirm);
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected_nation = nation.getItemAt(nation.getSelectedIndex());
				String selected_timeliness = timeliness.getItemAt(timeliness.getSelectedIndex());
				String selected_size = size.getItemAt(size.getSelectedIndex());
				int P = 0;
				if(selected_nation.equals("國內")) 
					P += 30;
				else if(selected_nation.equals("國外"))
					P += 100;
				if(selected_timeliness.equals("一般")) 
					P += 20;
				else if(selected_timeliness.equals("快件"))
					P += 70;
				if(selected_size.equals("平信")) 
					P += 5;
				else if(selected_size.equals("小包"))
					P += 20;
				else if(selected_size.equals("大盒"))
					P += 40;
				
				String num = getRandomString();         //隨機訂單編號
				String price = Integer.toString(P);     //將價格轉成字串
				Random r = new Random();
				int month = r.nextInt(2) + 1;
				int day = r.nextInt(28) + 1;
				String date = "2022/" + month + "/" + day;
				
				Date.setText(date);               //顯示訂單日期
				Num.setText(num);                 //顯示訂單編號
				Price.setText(price);             //顯示計算完的價格
				
//			    System.out.println(selected_nation);
//			    System.out.println(P);
			}
		});

		JButton next = new JButton("Finish");    //完成
		next.setBounds(930, 700, 100, 30);
		frame.getContentPane().add(next);
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiClient gui = new GuiClient();
				gui.initialize();
				gui.setVisible(true);
				frame.dispose();
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
