package NewDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewRunDelivery {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://140.127.74.186/410977033";
	static final String USER = "410977033";
	static final String PASS = "410977033";

	public void runAllOrder() {
		int countab = 0;
		ArrayList alist = new ArrayList();
		alist.clear();
		ArrayList blist = new ArrayList();
		blist.clear();
		ArrayList elist = new ArrayList();
		elist.clear();

		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			String sql = "";
			sql = "SELECT o.package_id, o.order_time,i.nation,i.timeliness,tc.town_id, tc.country, tc.city, tc.town, tc.type,tr.town_id, tr.country, tr.city, tr.town, tr.type\r\n"
					+ "FROM ((((package_info AS i ,package_overall as o)\r\n"
					+ "INNER JOIN package_adress AS a ON o.adress_id = a.adress_id AND o.info_id=i.info_id) \r\n"
					+ "INNER JOIN list_town AS tc ON tc.town_id = a.client_town_id) \r\n"
					+ "INNER JOIN list_town AS tr ON tr.town_id = a.reciever_town_id)\r\n"
					+ "where o.package_id>10000" + " order by o.order_time";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				alist.add(rs.getString("o.package_id"));
				alist.add(rs.getString("o.order_time"));

				elist.add(rs.getString("i.nation"));
				elist.add(rs.getString("i.timeliness"));

				blist.add(rs.getString("tc.town_id"));
				blist.add(rs.getString("tc.town"));
				blist.add(rs.getString("tc.city"));
				blist.add(rs.getString("tc.country"));
				blist.add(rs.getString("tc.type"));
				blist.add(rs.getString("tr.town_id"));
				blist.add(rs.getString("tr.country"));
				blist.add(rs.getString("tr.city"));
				blist.add(rs.getString("tr.town"));
				blist.add(rs.getString("tr.type"));
				countab += 1;
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
		System.out.println("Goodbye!" + countab);

		for (int i = 0; i < countab; i++) {
			int p_id = Integer.parseInt(alist.get(i * 2).toString());
			String o_time = alist.get(i * 2 + 1).toString();

			String nation = elist.get(i * 2).toString();
			String timeliness = elist.get(i * 2 + 1).toString();

			String[] row = { "寄件人地址", "營業所", "物流中心", "全國大倉庫", "全國大倉庫", "物流中心", "營業所", "收件人地址" };
			ArrayList rowlist = new ArrayList();
			rowlist.clear();
			if (blist.get(i * 10 + 1).equals(blist.get(i * 10 + 8))) {// 鄉鎮內
				rowlist.add(1);// 寄件人地址,營業所
				rowlist.add(7);// 營業所", "收件人地址
			} else if (blist.get(i * 10 + 2).equals(blist.get(i * 10 + 7))) {// 縣市內
				rowlist.add(1);// 寄件人地址,營業所
				rowlist.add(2);// 營業所", "物流中心
				rowlist.add(6);// 物流中心", "營業所
				rowlist.add(7);// 營業所", "收件人地址
			} else if (blist.get(i * 10 + 3).equals(blist.get(i * 10 + 6))) {// 國內
				rowlist.add(1);// 寄件人地址,營業所
				rowlist.add(2);// 營業所", "物流中心
				rowlist.add(3);// 物流中心, "機場大倉庫"
				rowlist.add(5);// 機場大倉庫", "物流中心
				rowlist.add(6);// 物流中心", "營業所
				rowlist.add(7);// 營業所", "收件人地址
			} else {// 國際
				rowlist.add(1);// 寄件人地址,營業所
				rowlist.add(2);// 營業所", "物流中心
				rowlist.add(3);// 物流中心, "機場大倉庫"
				rowlist.add(4);// 機場大倉庫", "機場大倉庫
				rowlist.add(5);// 機場大倉庫", "物流中心
				rowlist.add(6);// 物流中心", "營業所
				rowlist.add(7);// 營業所", "收件人地址
			}
			System.out.println("分類" + rowlist);

			String shift_o_time = o_time;
			for (int j = 0; j < rowlist.size(); j++) {
				String trans_s_id = "", update_s_id = "";
				String new_time = o_time;

				int rownum = Integer.parseInt(rowlist.get(j).toString());

				String shift_town_id, shift_town_type = "";
				String cs_place, cs_goto;
				if (rownum < 4) {
					shift_town_id = blist.get(i * 10).toString();
					shift_town_type = blist.get(i * 10 + 4).toString();
					cs_goto = blist.get(i * 10 + rownum).toString();
					if (rownum == 1)
						cs_place = "000";
					else
						cs_place = blist.get(i * 10 + rownum - 1).toString();
				} else if (rownum == 4) {
					shift_town_id = blist.get(i * 10).toString();
					cs_place = blist.get(i * 10 + 3).toString();
					cs_goto = blist.get(i * 10 + 6).toString();

				} else {
					shift_town_id = blist.get(i * 10 + 5).toString();
					shift_town_type = blist.get(i * 10 + 9).toString();
					cs_place = blist.get(i * 10 + rownum + 1).toString();
					if (rownum == 7)
						cs_goto = "999";
					else
						cs_goto = blist.get(i * 10 + rownum + 2).toString();
				}
				System.out.println(p_id + "\t" + o_time + "\t" + shift_town_id + "\t" + cs_place + "\t" + cs_goto);

				String update_s_num = "";
				String trans_s_num = "";
				if (rownum == 1) {
					update_s_num = update_s_num + "System";
					trans_s_num = trans_s_num + "LT";
				} else if (rownum == 2) {
					update_s_num = update_s_num + "CW";
					trans_s_num = trans_s_num + "PT";
				} else if (rownum == 3) {
					update_s_num = update_s_num + "BW";
					if (shift_town_type.equals("本島"))
						trans_s_num = trans_s_num + "DT";
					else
						trans_s_num = trans_s_num + "DP";
				} else if (rownum == 4) {
					update_s_num = update_s_num + "AW";
					trans_s_num = trans_s_num + "NP";
				} else if (rownum == 5) {
					update_s_num = update_s_num + "AW";
					if (shift_town_type.equals("本島"))
						trans_s_num = trans_s_num + "DT";
					else
						trans_s_num = trans_s_num + "DP";
				} else if (rownum == 6) {
					update_s_num = update_s_num + "BW";
					trans_s_num = trans_s_num + "PT";
				} else if (rownum == 7) {
					update_s_num = update_s_num + "CW";
					trans_s_num = trans_s_num + "LT";
				}

				update_s_num = update_s_num + "G";
				if (timeliness.equals("快件")) {
					trans_s_num = trans_s_num + "E";
				} else {
					trans_s_num = trans_s_num + "G";
				}

				String cs_place_key = row[rownum - 1];
				if (cs_place_key.equals("營業所"))
					cs_place_key = shift_town_id;
				else if (cs_place_key.equals("寄件人地址"))
					cs_place_key = "000";
				else if (cs_place_key.equals("收件人地址"))
					cs_place_key = "999";
				else
					cs_place_key = cs_place;

				String cs_goto_key = row[rownum];
				if (cs_goto_key.equals("營業所"))
					cs_goto_key = shift_town_id;
				else if (cs_place_key.equals("寄件人地址"))
					cs_goto_key = "000";
				else if (cs_place_key.equals("收件人地址"))
					cs_goto_key = "999";
				else
					cs_goto_key = cs_goto;

				conn = null;
				stmt = null;

				try {
					Class.forName(JDBC_DRIVER);
					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					stmt = conn.createStatement();

					String sql = "";
					ResultSet rs;

					if (cs_place_key.equals("000") || cs_place_key.equals("999")) {

					} else {
						sql = "SELECT location_id\r\n" + "FROM list_location\r\n" + "where skey='" + cs_place_key + "';";
						rs = stmt.executeQuery(sql);
						while (rs.next()) {
							String tmp1 = rs.getString("location_id");
							cs_place_key = String.format("%03d", Integer.parseInt(tmp1));
						}
						rs.close();
					}

					if (cs_goto_key.equals("000") || cs_goto_key.equals("999")) {

					} else {
						sql = "SELECT location_id\r\n" + "FROM list_location\r\n" + "where skey='" + cs_goto_key + "';";
						rs = stmt.executeQuery(sql);
						while (rs.next()) {
							String tmp2 = rs.getString("location_id");
							cs_goto_key = String.format("%03d", Integer.parseInt(tmp2));
						}
						rs.close();
					}

					update_s_num = update_s_num + cs_place_key + cs_place_key;
					trans_s_num = trans_s_num + cs_place_key + cs_goto_key;

					String keytime2 = o_time.substring(5, 7) + o_time.substring(8, 10);
					String keytime3 = o_time.substring(11, 13);
					Date d2 = new Date();
					SimpleDateFormat sdf2 = new SimpleDateFormat("MMDD");
					try {
						d2 = sdf2.parse(keytime2.toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (Integer.parseInt(keytime3) < 17 && Integer.parseInt(keytime3) >= 9) {
						update_s_num = update_s_num + sdf2.format(d2.getTime()) + "09";
					} else if (Integer.parseInt(keytime3) < 9 && Integer.parseInt(keytime3) >= 1) {
						update_s_num = update_s_num + sdf2.format(d2.getTime()) + "01";
					} else if (Integer.parseInt(keytime3) < 1) {
						update_s_num = update_s_num + sdf2.format(new Date(d2.getTime() - 24 * 60 * 60 * 1000)) + "05";
					} else {
						update_s_num = update_s_num + sdf2.format(d2.getTime()) + "05";
					}
					if (trans_s_num.substring(1, 2).equals("P")) {
						if (Integer.parseInt(keytime3) < 13) {
							trans_s_num = trans_s_num + sdf2.format(d2.getTime()) + "01";
						} else {
							trans_s_num = trans_s_num + sdf2.format(new Date(d2.getTime() + 24 * 60 * 60 * 1000))
									+ "01";
						}
					} else {
						if (Integer.parseInt(keytime3) < 17 && Integer.parseInt(keytime3) >= 9) {
							trans_s_num = trans_s_num + sdf2.format(d2.getTime()) + "05";
						} else if (Integer.parseInt(keytime3) < 9 && Integer.parseInt(keytime3) >= 1) {
							trans_s_num = trans_s_num + sdf2.format(d2.getTime()) + "09";
						} else if (Integer.parseInt(keytime3) < 1) {
							trans_s_num = trans_s_num + sdf2.format(d2.getTime()) + "01";
						} else {
							trans_s_num = trans_s_num + sdf2.format(new Date(d2.getTime() + 24 * 60 * 60 * 1000))
									+ "01";
						}
					}
					System.out.print(update_s_num + "    " + trans_s_num);

					if (rownum == 1) {
						update_s_id = "System";
					} else {
						sql = "SELECT b.schedule_id\r\n" + "FROM schedule as b " + "where b.schedule_num='"
								+ update_s_num + "';";
						rs = stmt.executeQuery(sql);
						while (rs.next()) {
							update_s_id = rs.getString("b.schedule_id");
						}
						rs.close();
					}
					sql = "SELECT b.schedule_id\r\n" + "FROM schedule as b " + "where b.schedule_num='"
							+ trans_s_num + "';";
					rs = stmt.executeQuery(sql);
					while (rs.next()) {
						trans_s_id = rs.getString("b.schedule_id");
					}
					rs.close();
					System.out.println("    " + update_s_id + "-----" + trans_s_id + "    ///////");

					sql = "INSERT INTO delivery_log (package_id,update_time,update_s_id,trans_s_id)"
							+ "VALUES ('" + p_id + "','" + o_time + "','" + update_s_id + "','" + trans_s_id + "')";
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
				System.out.print("Goodbye!");

				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				long randtime = ((long) (Math.random() * 2 * 60 * 60 * 1000) + 1);// 120分鐘後的時間
				int transtime = 0;
				try {
					d = sdf.parse(o_time.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (trans_s_num.substring(0, 2).equals("NP")) {
					transtime = 24;
				} else if (trans_s_num.substring(0, 2).equals("DP")) {
					transtime = 8;
				} else if (trans_s_num.substring(0, 2).equals("DT")) {
					transtime = 8;
				} else if (trans_s_num.substring(0, 2).equals("PT")) {
					transtime = 6;
				} else {
					transtime = 3;
				}
				if (trans_s_num.substring(2, 3).equals("G")) {
					transtime = transtime * 2;
				}
				Date nd = new Date(d.getTime() + randtime + transtime * 60 * 60 * 1000);
				new_time = sdf.format(nd);

				o_time = new_time;

				if (rownum == 7) {
					try {
						Class.forName(JDBC_DRIVER);
						conn = DriverManager.getConnection(DB_URL, USER, PASS);
						stmt = conn.createStatement();

						String sql = "";
						sql = "INSERT INTO delivery_log (package_id,update_time,update_s_id,trans_s_id)"
								+ "VALUES ('" + p_id + "','" + o_time + "','" + "System" + "','" + "Arrive" + "')";
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
				}
			}
			System.out.println("------------------------------");
		}
	}
}
