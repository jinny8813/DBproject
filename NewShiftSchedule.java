package NewDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewShiftSchedule {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://140.127.74.186/410977033";
	static final String USER = "410977033";
	static final String PASS = "410977033";

	public static Date getTime(String type) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			if (type.equals("trunk")) {
				date = sdf.parse("2021-12-31 17:00:00");
			} else if (type.equals("plane")) {
				date = sdf.parse("2021-12-31 13:00:00");
			} else if (type.equals("warehouse")) {
				date = sdf.parse("2021-12-31 17:00:00");
			} else {
				date = sdf.parse("2022-2-12 00:00:00");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public void shiftNationPlane() {
		Date start = getTime("plane");
		Date end = getTime("");
		long time = 24 * 60 * 60 * 1000;// 24小時
		while (start.before(end)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date end_time_d = new Date(start.getTime() + time - 1);//
			String start_time = sdf.format(start);
			String end_time = sdf.format(end_time_d);
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("MMddhh");
			String set_time = sdf2.format(start);
			System.out.println(set_time);

			int counta = 0;
			ArrayList alist = new ArrayList();
			alist.clear();
			ArrayList elist = new ArrayList();
			elist.clear();

			Connection conn = null;
			Statement stmt = null;

			try {
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt = conn.createStatement();

				String sql = "";
				sql = "SELECT location_id,country\r\n"
						+ "FROM list_location\r\n"
						+ "WHERE warehouse='全國大倉庫'\r\n"
						+ "group by country";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					alist.add(rs.getInt("location_id"));
					alist.add(rs.getString("country"));
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

			for (int p = 0; p < counta; p++) {
				ArrayList blist = new ArrayList();
				blist.clear();

				String countryl_id2;
				String countryl_id = alist.get(p * 2).toString();
				String country = alist.get(p * 2+ 1).toString();
				if(countryl_id.equals("1")) {
					countryl_id2 = "2";
				}else {
					countryl_id2 = "1";
				}

				conn = null;
				stmt = null;

				try {
					Class.forName(JDBC_DRIVER);
					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					stmt = conn.createStatement();

					String sql = "";
					sql = "SELECT employee_id\r\n" + "FROM employee\r\n" + "WHERE type='N飛機' \r\n" + "AND location_id="
							+ countryl_id + " ORDER BY RAND()\r\n";
					ResultSet rs = stmt.executeQuery(sql);
					while (rs.next()) {
						blist.add(rs.getInt("employee_id"));
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

				String[] tliness = { "快件", "一般" };
				for (int m = 0; m < tliness.length; m++) {
					ArrayList clist = new ArrayList();
					clist.clear();
					
					String s_num="";
					s_num=s_num+"NP";
					if(tliness[m].equals("快件")) {
						s_num=s_num+"E";
					}else {
						s_num=s_num+"G";
					}
					String tnum1=String.format("%03d", Integer.parseInt(countryl_id));
					String tnum2=String.format("%03d", Integer.parseInt(countryl_id2));
					String s_num1=s_num+tnum1+tnum2+set_time;
					String s_num2=s_num+tnum2+tnum1+set_time;
					System.out.println(s_num1);
					
					try {
						Class.forName(JDBC_DRIVER);
						conn = DriverManager.getConnection(DB_URL, USER, PASS);
						stmt = conn.createStatement();

						String sql = "";
						sql = "SELECT plane_id,name " + "FROM list_plane " + "WHERE type='N飛機' AND timeliness='"
								+ tliness[m] + "' AND location_id='" + countryl_id + "' " + "ORDER BY RAND()" ;
						ResultSet rs = stmt.executeQuery(sql);
						while (rs.next()) {
							clist.add(rs.getString("plane_id"));
							clist.add(rs.getString("name"));
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

					conn = null;
					stmt = null;

					System.out.println(blist);
					try {
						Class.forName(JDBC_DRIVER);
						conn = DriverManager.getConnection(DB_URL, USER, PASS);
						stmt = conn.createStatement();

						String sql = "";
						sql = "INSERT INTO schedule (schedule_num,site_id,site,place_l_id,goto_l_id,employee_id,start_time,end_time)"
								+ "VALUES ('" +s_num1 + "'," + "'"+ clist.get(0) + "','"
								+ clist.get(1) + "','" + countryl_id + "','" + countryl_id2 + "','" + blist.get(m * 2) + "','"
								+ start_time + "','" + end_time + "')";
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
					System.out.println("Goodbye!");
				}
			}

			start = new Date(start.getTime() + time);
		}
	}

	public void shiftDomesticPlane() {
		Date start = getTime("plane");
		Date end = getTime("");
		long time = 24 * 60 * 60 * 1000;// 24小時
		while (start.before(end)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date end_time_d = new Date(start.getTime() + time - 1);//
			String start_time = sdf.format(start);
			String end_time = sdf.format(end_time_d);
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("MMddhh");
			String set_time = sdf2.format(start);
			System.out.println(set_time);

			int counta = 0;
			ArrayList alist = new ArrayList();
			alist.clear();
			ArrayList elist = new ArrayList();
			elist.clear();

			Connection conn = null;
			Statement stmt = null;

			try {
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt = conn.createStatement();

				String sql = "";
				sql = "SELECT b.location_id,b.country,a.location_id,a.city\r\n"
						+ "FROM list_location as b\r\n"
						+ "inner join list_location as a ON b.country=a.country\r\n"
						+ "WHERE b.warehouse='全國大倉庫' AND (a.type='未知'OR a.type='都有'OR a.type='離島') AND a.city<>'0'\r\n"
						+ "group by a.city";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					alist.add(rs.getInt("b.location_id"));
					alist.add(rs.getString("b.country"));
					alist.add(rs.getInt("a.location_id"));
					alist.add(rs.getString("a.city"));
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

			for (int p = 0; p < counta; p++) {
				ArrayList blist = new ArrayList();
				blist.clear();

				String countryl_id = alist.get(p * 4).toString();
				String country = alist.get(p * 4 + 1).toString();
				String cityl_id = alist.get(p * 4+2).toString();
				String city = alist.get(p * 4 + 3).toString();

				conn = null;
				stmt = null;

				try {
					Class.forName(JDBC_DRIVER);
					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					stmt = conn.createStatement();

					String sql = "";
					sql = "SELECT employee_id\r\n" + "FROM employee\r\n" + "WHERE type='D飛機' \r\n" + "AND location_id="
							+ cityl_id + " ORDER BY RAND()\r\n";
					ResultSet rs = stmt.executeQuery(sql);
					while (rs.next()) {
						blist.add(rs.getInt("employee_id"));
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

				String[] tliness = { "快件", "一般" };
				for (int m = 0; m < tliness.length; m++) {
					ArrayList clist = new ArrayList();
					clist.clear();
					
					String s_num="";
					s_num=s_num+"DP";
					if(tliness[m].equals("快件")) {
						s_num=s_num+"E";
					}else {
						s_num=s_num+"G";
					}
					String tnum1=String.format("%03d", Integer.parseInt(countryl_id));
					String tnum2=String.format("%03d", Integer.parseInt(cityl_id));
					String s_num1=s_num+tnum1+tnum2+set_time;
					String s_num2=s_num+tnum2+tnum1+set_time;
					System.out.println(s_num1);
					
					try {
						Class.forName(JDBC_DRIVER);
						conn = DriverManager.getConnection(DB_URL, USER, PASS);
						stmt = conn.createStatement();

						String sql = "";
						sql = "SELECT plane_id,name " + "FROM list_plane " + "WHERE type='D飛機' AND timeliness='"
								+ tliness[m] + "' AND location_id='" + cityl_id + "' " + "ORDER BY RAND()" ;
						ResultSet rs = stmt.executeQuery(sql);
						while (rs.next()) {
							clist.add(rs.getString("plane_id"));
							clist.add(rs.getString("name"));
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

					conn = null;
					stmt = null;

					System.out.println(blist);
					try {
						Class.forName(JDBC_DRIVER);
						conn = DriverManager.getConnection(DB_URL, USER, PASS);
						stmt = conn.createStatement();

						String sql = "";
						sql = "INSERT INTO schedule (schedule_num,site_id,site,place_l_id,goto_l_id,employee_id,start_time,end_time)"
								+ "VALUES ('" +s_num1 + "'," + "'"+ clist.get(0) + "','"
								+ clist.get(1) + "','" + countryl_id + "','" + cityl_id + "','" + blist.get(m * 2) + "','"
								+ start_time + "','" + end_time + "')";
						stmt.executeUpdate(sql);	
						sql = "INSERT INTO schedule (schedule_num,site_id,site,place_l_id,goto_l_id,employee_id,start_time,end_time)"
								+ "VALUES ('" +s_num2 + "'," + "'"+ clist.get(2) + "','"
								+ clist.get(3) + "','" + cityl_id + "','" + countryl_id + "','" + blist.get(m * 2 + 1) + "','"
								+ start_time + "','" + end_time + "')";
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
					System.out.println("Goodbye!");
				}
			}

			start = new Date(start.getTime() + time);
		}
	}

	public void shiftDomesticTrunk() {
		Date start = getTime("trunk");
		Date end = getTime("");
		long time = 8 * 60 * 60 * 1000;// 8小時
		while (start.before(end)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date end_time_d = new Date(start.getTime() + time - 1);//
			String start_time = sdf.format(start);
			String end_time = sdf.format(end_time_d);
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("MMddhh");
			String set_time = sdf2.format(start);
			System.out.println(set_time);

			int counta = 0;
			ArrayList alist = new ArrayList();
			alist.clear();
			ArrayList elist = new ArrayList();
			elist.clear();

			Connection conn = null;
			Statement stmt = null;

			try {
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt = conn.createStatement();

				String sql = "";
				sql = "SELECT b.location_id,b.country,a.location_id,a.city\r\n"
						+ "FROM list_location as b\r\n"
						+ "inner join list_location as a ON b.country=a.country\r\n"
						+ "WHERE b.warehouse='全國大倉庫' AND (a.type='都有'OR a.type='本島') AND a.city<>'0'\r\n"
						+ "group by a.city";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					alist.add(rs.getInt("b.location_id"));
					alist.add(rs.getString("b.country"));
					alist.add(rs.getInt("a.location_id"));
					alist.add(rs.getString("a.city"));
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

			for (int p = 0; p < counta; p++) {
				ArrayList blist = new ArrayList();
				blist.clear();

				String countryl_id = alist.get(p * 4).toString();
				String country = alist.get(p * 4 + 1).toString();
				String cityl_id = alist.get(p * 4+2).toString();
				String city = alist.get(p * 4 + 3).toString();

				conn = null;
				stmt = null;

				try {
					Class.forName(JDBC_DRIVER);
					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					stmt = conn.createStatement();

					String sql = "";
					sql = "SELECT employee_id\r\n" + "FROM employee\r\n" + "WHERE type='D貨車' \r\n" + "AND location_id="
							+ cityl_id + " ORDER BY RAND()\r\n";
					ResultSet rs = stmt.executeQuery(sql);
					while (rs.next()) {
						blist.add(rs.getInt("employee_id"));
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

				String[] tliness = { "快件", "一般" };
				for (int m = 0; m < tliness.length; m++) {
					ArrayList clist = new ArrayList();
					clist.clear();
					
					String s_num="";
					s_num=s_num+"DT";
					if(tliness[m].equals("快件")) {
						s_num=s_num+"E";
					}else {
						s_num=s_num+"G";
					}
					String tnum1=String.format("%03d", Integer.parseInt(countryl_id));
					String tnum2=String.format("%03d", Integer.parseInt(cityl_id));
					String s_num1=s_num+tnum1+tnum2+set_time;
					String s_num2=s_num+tnum2+tnum1+set_time;
					System.out.println(s_num1);
					
					try {
						Class.forName(JDBC_DRIVER);
						conn = DriverManager.getConnection(DB_URL, USER, PASS);
						stmt = conn.createStatement();

						String sql = "";
						sql = "SELECT trunk_id,name " + "FROM list_trunk " + "WHERE type='D貨車' AND timeliness='"
								+ tliness[m] + "' AND location_id='" + cityl_id + "' " + "ORDER BY RAND()" ;
						ResultSet rs = stmt.executeQuery(sql);
						while (rs.next()) {
							clist.add(rs.getString("trunk_id"));
							clist.add(rs.getString("name"));
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

					conn = null;
					stmt = null;

					System.out.println(blist);
					try {
						Class.forName(JDBC_DRIVER);
						conn = DriverManager.getConnection(DB_URL, USER, PASS);
						stmt = conn.createStatement();

						String sql = "";
						sql = "INSERT INTO schedule (schedule_num,site_id,site,place_l_id,goto_l_id,employee_id,start_time,end_time)"
								+ "VALUES ('" +s_num1 + "'," + "'"+ clist.get(0) + "','"
								+ clist.get(1) + "','" + countryl_id + "','" + cityl_id + "','" + blist.get(m * 2) + "','"
								+ start_time + "','" + end_time + "')";
						stmt.executeUpdate(sql);	
						sql = "INSERT INTO schedule (schedule_num,site_id,site,place_l_id,goto_l_id,employee_id,start_time,end_time)"
								+ "VALUES ('" +s_num2 + "'," + "'"+ clist.get(2) + "','"
								+ clist.get(3) + "','" + cityl_id + "','" + countryl_id + "','" + blist.get(m * 2 + 1) + "','"
								+ start_time + "','" + end_time + "')";
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
					System.out.println("Goodbye!");
				}
			}

			start = new Date(start.getTime() + time);
		}
	}

	public void shiftProvinceTrunk() {
		Date start = getTime("trunk");
		Date end = getTime("");
		long time = 8 * 60 * 60 * 1000;// 8小時
		while (start.before(end)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date end_time_d = new Date(start.getTime() + time - 1);//
			String start_time = sdf.format(start);
			String end_time = sdf.format(end_time_d);
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("MMddhh");
			String set_time = sdf2.format(start);
			System.out.println(set_time);

			int counta = 0;
			ArrayList alist = new ArrayList();
			alist.clear();
			ArrayList elist = new ArrayList();
			elist.clear();

			Connection conn = null;
			Statement stmt = null;

			try {
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt = conn.createStatement();

				String sql = "";
				sql = "SELECT b.location_id,b.city,a.location_id,a.town\r\n"
						+ "FROM list_location as b \r\n"
						+ "inner join list_location as a ON b.city=a.city\r\n"
						+ "WHERE b.warehouse='物流中心' AND a.town<>'0'";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					alist.add(rs.getInt("b.location_id"));
					alist.add(rs.getString("b.city"));
					alist.add(rs.getInt("a.location_id"));
					alist.add(rs.getString("a.town"));
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

			for (int p = 0; p < counta; p++) {
				ArrayList blist = new ArrayList();
				blist.clear();

				String cityl_id = alist.get(p * 4).toString();
				String city = alist.get(p * 4 + 1).toString();
				String townl_id = alist.get(p * 4+2).toString();
				String town = alist.get(p * 4 + 3).toString();

				conn = null;
				stmt = null;

				try {
					Class.forName(JDBC_DRIVER);
					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					stmt = conn.createStatement();

					String sql = "";
					sql = "SELECT employee_id\r\n" + "FROM employee\r\n" + "WHERE type='P貨車' \r\n" + "AND location_id="
							+ townl_id + " ORDER BY RAND()\r\n";
					ResultSet rs = stmt.executeQuery(sql);
					while (rs.next()) {
						blist.add(rs.getInt("employee_id"));
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

				String[] tliness = { "快件", "一般" };
				for (int m = 0; m < tliness.length; m++) {
					ArrayList clist = new ArrayList();
					clist.clear();
					
					String s_num="";
					s_num=s_num+"PT";
					if(tliness[m].equals("快件")) {
						s_num=s_num+"E";
					}else {
						s_num=s_num+"G";
					}
					String tnum1=String.format("%03d", Integer.parseInt(townl_id));
					String tnum2=String.format("%03d", Integer.parseInt(cityl_id));
					String s_num1=s_num+tnum1+tnum2+set_time;
					String s_num2=s_num+tnum2+tnum1+set_time;
					System.out.println(s_num1);
					
					try {
						Class.forName(JDBC_DRIVER);
						conn = DriverManager.getConnection(DB_URL, USER, PASS);
						stmt = conn.createStatement();

						String sql = "";
						sql = "SELECT trunk_id,name " + "FROM list_trunk " + "WHERE type='P貨車' AND timeliness='"
								+ tliness[m] + "' AND location_id='" + townl_id + "' " + "ORDER BY RAND()" ;
						ResultSet rs = stmt.executeQuery(sql);
						while (rs.next()) {
							clist.add(rs.getString("trunk_id"));
							clist.add(rs.getString("name"));
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

					conn = null;
					stmt = null;

					System.out.println(blist);
					try {
						Class.forName(JDBC_DRIVER);
						conn = DriverManager.getConnection(DB_URL, USER, PASS);
						stmt = conn.createStatement();

						String sql = "";
						sql = "INSERT INTO schedule (schedule_num,site_id,site,place_l_id,goto_l_id,employee_id,start_time,end_time)"
								+ "VALUES ('" +s_num1 + "'," + "'"+ clist.get(0) + "','"
								+ clist.get(1) + "','" + townl_id + "','" + cityl_id + "','" + blist.get(m * 2) + "','"
								+ start_time + "','" + end_time + "')";
						stmt.executeUpdate(sql);	
						sql = "INSERT INTO schedule (schedule_num,site_id,site,place_l_id,goto_l_id,employee_id,start_time,end_time)"
								+ "VALUES ('" +s_num2 + "'," + "'"+ clist.get(2) + "','"
								+ clist.get(3) + "','" + cityl_id + "','" + townl_id + "','" + blist.get(m * 2 + 1) + "','"
								+ start_time + "','" + end_time + "')";
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
					System.out.println("Goodbye!");
				}
			}

			start = new Date(start.getTime() + time);
		}
	}


	public void shiftLocalTrunk() {
		Date start = getTime("trunk");
		Date end = getTime("");
		long time = 8 * 60 * 60 * 1000;// 8小時
		while (start.before(end)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date end_time_d = new Date(start.getTime() + time - 1);//
			String start_time = sdf.format(start);
			String end_time = sdf.format(end_time_d);
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("MMddhh");
			String set_time = sdf2.format(start);
			System.out.println(set_time);

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
				sql = "SELECT location_id,town " + "FROM list_location WHERE town<>'0'";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					alist.add(rs.getInt("location_id"));
					alist.add(rs.getString("town"));
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

			for (int p = 0; p < counta; p++) {
				ArrayList blist = new ArrayList();
				blist.clear();

				String location_id = alist.get(p * 2).toString();
				String town = alist.get(p * 2 + 1).toString();

				conn = null;
				stmt = null;

				try {
					Class.forName(JDBC_DRIVER);
					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					stmt = conn.createStatement();

					String sql = "";
					sql = "SELECT employee_id\r\n" + "FROM employee\r\n" + "WHERE type='L貨車' \r\n" + "AND location_id="
							+ location_id + " ORDER BY RAND()\r\n";
					ResultSet rs = stmt.executeQuery(sql);
					while (rs.next()) {
						blist.add(rs.getInt("employee_id"));
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

				String[] tliness = { "快件", "一般" };
				for (int m = 0; m < tliness.length; m++) {
					ArrayList clist = new ArrayList();
					clist.clear();
					
					String s_num="";
					s_num=s_num+"LT";
					if(tliness[m].equals("快件")) {
						s_num=s_num+"E";
					}else {
						s_num=s_num+"G";
					}
					String tnum=String.format("%03d", Integer.parseInt(location_id));
					String s_num1=s_num+"000"+tnum+set_time;
					String s_num2=s_num+tnum+"999"+set_time;
					System.out.println(s_num1);
					
					try {
						Class.forName(JDBC_DRIVER);
						conn = DriverManager.getConnection(DB_URL, USER, PASS);
						stmt = conn.createStatement();

						String sql = "";
						sql = "SELECT trunk_id,name " + "FROM list_trunk " + "WHERE type='L貨車' AND timeliness='"
								+ tliness[m] + "' AND location_id='" + location_id + "' " + "ORDER BY RAND()" ;
						ResultSet rs = stmt.executeQuery(sql);
						while (rs.next()) {
							clist.add(rs.getString("trunk_id"));
							clist.add(rs.getString("name"));
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

					conn = null;
					stmt = null;

					System.out.println(blist);
					try {
						Class.forName(JDBC_DRIVER);
						conn = DriverManager.getConnection(DB_URL, USER, PASS);
						stmt = conn.createStatement();

						String sql = "";
						sql = "INSERT INTO schedule (schedule_num,site_id,site,place_l_id,goto_l_id,employee_id,start_time,end_time)"
								+ "VALUES ('" +s_num1 + "'," + "'"+ clist.get(0) + "','"
								+ clist.get(1) + "','" + "000" + "','" + location_id + "','" + blist.get(0) + "','"
								+ start_time + "','" + end_time + "')";
						stmt.executeUpdate(sql);	
						sql = "INSERT INTO schedule (schedule_num,site_id,site,place_l_id,goto_l_id,employee_id,start_time,end_time)"
								+ "VALUES ('" +s_num2 + "'," + "'"+ clist.get(2) + "','"
								+ clist.get(3) + "','" + location_id + "','" + "999" + "','" + blist.get(1) + "','"
								+ start_time + "','" + end_time + "')";
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
					System.out.println("Goodbye!");
				}
			}

			start = new Date(start.getTime() + time);
		}
	}

	public void shiftWarehouse() {
		Date start = getTime("warehouse");
		Date end = getTime("");
		long time = 8 * 60 * 60 * 1000;// 8小時
		while (start.before(end)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date end_time_d = new Date(start.getTime() + time - 1);//
			String start_time = sdf.format(start);
			String end_time = sdf.format(end_time_d);
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("MMddhh");
			String set_time = sdf2.format(start);
			System.out.println(set_time);

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
				sql = "SELECT location_id,warehouse,country,city,town " + "FROM list_location WHere location_id<=820";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					alist.add(rs.getInt("location_id"));
					alist.add(rs.getString("warehouse"));
					alist.add(rs.getString("country"));
					alist.add(rs.getString("city"));
					alist.add(rs.getString("town"));
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
			
			for (int p = 0; p < counta; p++) {
				ArrayList blist = new ArrayList();
				blist.clear();

				String location_id = alist.get(p * 5).toString();
				String warehouse = alist.get(p * 5 + 1).toString();
				String country = alist.get(p * 5 + 2).toString();
				String city = alist.get(p * 5 + 3).toString();
				String town = alist.get(p * 5 + 4).toString();
				
				String s_num="";
				String name="";
				if(warehouse.equals("全國大倉庫")) {
					s_num=s_num+"AW";
					name=country+warehouse;
				}else if(warehouse.equals("物流中心")) {
					s_num=s_num+"BW";
					name=city+warehouse;
				}else if(warehouse.equals("營業所")) {
					s_num=s_num+"CW";
					name=town+warehouse;
				}else {
					
				}

				s_num=s_num+"G";
				s_num=s_num+String.format("%03d", Integer.parseInt(location_id));
				s_num=s_num+String.format("%03d", Integer.parseInt(location_id));
				s_num=s_num+set_time;
				
				System.out.print(s_num+"------------------------"+name+"------------------------------");
				
				conn = null;
				stmt = null;

				try {
					Class.forName(JDBC_DRIVER);
					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					stmt = conn.createStatement();

					String sql = "";
					sql = "SELECT employee_id\r\n" + "FROM employee\r\n" + "WHERE type='" + warehouse + "' \r\n"
							+ "AND location_id=" + location_id + " ORDER BY RAND()\r\n" + " LIMIT 1";
					ResultSet rs = stmt.executeQuery(sql);
					
					while (rs.next()) {
						blist.add(rs.getInt("employee_id"));
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

				conn = null;
				stmt = null;

				System.out.println(blist);
				try {
					Class.forName(JDBC_DRIVER);
					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					stmt = conn.createStatement();

					String sql = "";
					sql = "INSERT INTO schedule (schedule_num,site_id,site,place_l_id,goto_l_id,employee_id,start_time,end_time)"
							+ "VALUES ('" +s_num + "'," + "'"+ location_id + "','"
							+ name + "','" + location_id + "','" + location_id + "','" + blist.get(0) + "','"
							+ start_time + "','" + end_time + "')";
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
				System.out.println("Goodbye!");

			}

			start = new Date(start.getTime() + time);
		}
	}
}