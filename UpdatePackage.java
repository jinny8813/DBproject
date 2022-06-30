package NewDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UpdatePackage {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://140.127.74.186/410977033";
	static final String USER = "410977033";
	static final String PASS = "410977033";

	public void updatePackageDetails() {
		int counta = 0;
		ArrayList alist = new ArrayList();
		alist.clear();

		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			String sql = "";
			sql = "SELECT info_id,nation,timeliness,size\r\n"
					+ "FROM package_info\r\n"
					+ "where info_id>10000"
					+ " order by info_id";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				alist.add(rs.getInt("info_id"));
				alist.add(rs.getString("nation"));
				alist.add(rs.getString("timeliness"));
				alist.add(rs.getString("size"));
				counta += 1;
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
		System.out.println("Goodbye!");

		for (int i = 0; i < counta; i++) {
			ArrayList blist = new ArrayList();
			blist.clear();
			
			int price = 0;
			if(alist.get(i*4+1).equals("國內")) 
				price += 30;
			else if(alist.get(i*4+1).equals("國外"))
				price += 100;
			if(alist.get(i*4+2).equals("一般")) 
				price += 20;
			else if(alist.get(i*4+2).equals("快件"))
				price += 70;
			if(alist.get(i*4+3).equals("平信")) 
				price += 5;
			else if(alist.get(i*4+3).equals("小包"))
				price += 20;
			else if(alist.get(i*4+3).equals("大盒"))
				price += 40;
			
			conn = null;
			stmt = null;
			try {
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt = conn.createStatement();

				String sql = "";
				sql = "SELECT update_time\r\n"
						+ "FROM delivery_log\r\n"
						+ "where package_id="+alist.get(i*4)+" and update_s_id='System';";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					blist.add(rs.getString("update_time"));
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
			System.out.println("Goodbye!");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d1 = new Date();
			Date d2 = new Date();
			try {
				d1 = sdf.parse(blist.get(0).toString());
				d2 = sdf.parse(blist.get(1).toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			long ontime_time=0;
			if (alist.get(i*4+1).equals("國內")) {
				if(alist.get(i*4+2).equals("快件")) 
					ontime_time=42* 60 * 60 * 1000;
				else
					ontime_time=82* 60 * 60 * 1000;
			} else {
				if(alist.get(i*4+2).equals("快件")) 
					ontime_time=68* 60 * 60 * 1000;
				else
					ontime_time=133* 60 * 60 * 1000;
			}
			boolean ontime=true;
			if((d2.getTime()-d1.getTime())>ontime_time) {
				ontime=false;
			}
			
			conn = null;
			stmt = null;
			
			try {
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt = conn.createStatement();

				String sql = "";
				sql = "UPDATE package_overall\r\n"
						+ "SET price = '"+price+"', arrival_time = '"+blist.get(1)+"',ontime_ornot = '"+ontime+"'\r\n"
						+ "WHERE package_id="+alist.get(i*4)+";";
				stmt.executeUpdate(sql);
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

			System.out.println("------------------------------");
		}

	}
}