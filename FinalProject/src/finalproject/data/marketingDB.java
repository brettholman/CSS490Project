package finalproject.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import finalproject.models.*;

public class marketingDB {
	private static String dbURL = "jdbc:mysql://localhost:3360/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	private static Calendar cal = Calendar.getInstance();
	
	public static SalesStatItem[] getSalesStatItems(int topCount, int categoryID, int timeframe)
	{
		ArrayList<SalesStatItem> items = new ArrayList<SalesStatItem>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String cat = "";
			if(categoryID > 0) { cat = " categoryID = ?"; }
			
			String time = "";
			if(timeframe > 0) {
				
				switch(timeframe) {
				
				case 1: // last month
					time = "purchaseDate > DATE_SUB(CURDATE(), INTERVAL 30 DAY)";
					break;
					
				case 2: // last 2 weeks
					time = "purchaseDate > DATE_SUB(CURDATE(), INTERVAL 14 DAY)";
					break;
					
				case 3: // last week
					time = "purchaseDate > DATE_SUB(CURDATE(), INTERVAL 7 DAY)";
					break;
				}
			}
			
			String where = "";
			if(cat.length() > 0 && time.length() > 0) {
				where = cat + " AND " + time;
			}
			else {
				where = cat + " " + time;
				where = where.trim();
			}
			if(where.length() > 0) { where = "WHERE " + where + " "; }
			
			String query = 
				"SELECT II.id, title, author, categoryID, categoryName AS category, SUM(PD.quantity) AS quantitySold FROM css490.inventoryitems AS II " +
				"INNER JOIN css490.purchasedetails AS PD ON II.id = PD.itemID " +
				"INNER JOIN css490.transactions AS T ON PD.transactionNumber = T.transactionNumber " +
				"INNER JOIN css490.category AS C ON II.categoryID = C.id " + 
				where + 
				"group by II.id ORDER BY quantitySold DESC LIMIT ?";

			stmt = conn.prepareStatement(query);
			
			if(cat.length() > 0) {
				stmt.setInt(1, categoryID);
				stmt.setInt(2, topCount);
			}
			else {
				stmt.setInt(1, topCount);
			}
			
			rs = stmt.executeQuery();
			if(rs == null || rs.wasNull()) {
				return null;
			}
	
			// Get the first row and pull down the user data
			if(rs.first()) {
				do {
					SalesStatItem item = new SalesStatItem();
					item.setCategoryID(rs.getInt("categoryID"));
					item.setCategory(rs.getString("category"));
					item.setTitle(rs.getString("title"));
					item.setAuthor(rs.getString("author"));
					item.setQuantitySold(rs.getInt("quantitySold"));
					items.add(item);
				} while(rs.next());
			}
			else {
				return null;
			}			
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			closeAll(stmt, conn, rs);
		}
		return items.toArray(new SalesStatItem[items.size()]);		
	}	
	
	public static User[] getAllUsersPurchaseDetailsOnInventoryItem(int id)
	{
		ArrayList<User> list = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "select u.id, u.userName, u.fName, u.lName, u.email, Sum(p.quantity) as s "
					+ "from transactions as t "
					+ "inner join purchasedetails as p "
					+ "on t.transactionNumber = p.transactionNumber "
					+ "inner join users as u "
					+ "on u.id = t.userid "
					+ "where p.itemid = ? and t.purchasedate >= ? "
					+ "group by t.userID";
			
			stmt = conn.prepareStatement(query);
			
			stmt.setInt(1, id);
			cal.set(Calendar.MONTH, -1);
			String year = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + Integer.toString(01);
			stmt.setString(2, year);
			rs = stmt.executeQuery();
			if(rs == null || rs.wasNull()) {
				return null;
			}
	
			// Get the first row and pull down the item data
			if(rs.first()) {
				do {
					User item = new User();
					item.setId(rs.getInt("id"));
					item.setUserName(rs.getString("userName"));
					item.setfName(rs.getString("fName"));
					item.setlName(rs.getString("lName"));
					item.setEmail(rs.getString("email"));
					item.setTotalPurchased(rs.getInt("s"));
					list.add(item);
				}while(rs.next());
			}
			else {
				return null;
			}					
		}
		catch(Exception e) {
			e.printStackTrace();
			list = null;
		}
		finally {
			closeAll(stmt, conn, rs);
		}
		return list.toArray(new User[list.size()]);
		
	}
	
	public static User[] getAllUsersPurchaseDetailsOnCategory(int id)
	{
		ArrayList<User> list = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "select u.id, u.userName, u.fName, u.lName, u.email, Sum(p.quantity) as s from transactions as t "
					+ "inner join purchasedetails as p "
					+ "on t.transactionNumber = p.transactionNumber "
					+ "inner join users as u "
					+ "on u.id = t.userid "
					+ "inner join InventoryItems as i "
					+ "on i.id = p.itemID "
					+ "where i.categoryID = ? and t.purchasedate >= ? "
					+ "group by t.userID";
			
			stmt = conn.prepareStatement(query);
			
			stmt.setInt(1, id);
			cal.set(Calendar.MONTH, -1);
			String year = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + Integer.toString(01);
			stmt.setString(2, year);
			rs = stmt.executeQuery();
			if(rs == null || rs.wasNull()) {
				return null;
			}
	
			// Get the first row and pull down the item data
			if(rs.first()) {
				do {
					User item = new User();
					item.setId(rs.getInt("id"));
					item.setUserName(rs.getString("userName"));
					item.setfName(rs.getString("fName"));
					item.setlName(rs.getString("lName"));
					item.setEmail(rs.getString("email"));
					item.setTotalPurchased(rs.getInt("s"));
					list.add(item);
				}while(rs.next());
			}
			else {
				return null;
			}					
		}
		catch(Exception e) {
			e.printStackTrace();
			list = null;
		}
		finally {
			closeAll(stmt, conn, rs);
		}
		return list.toArray(new User[list.size()]);
	}
	
	private static void closeAll(Statement stmt, Connection conn)
	{
		if(stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		
		if(conn != null) {
			try {
				conn.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
	}
	
	private static void closeAll(Statement stmt, Connection conn, ResultSet rs)
	{
		if(stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		
		if(conn != null) {
			try {
				conn.close();
			}
			catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		
		if(rs != null) {
			try {
				rs.close();
			}
			catch(SQLException sqle) {
				sqle.printStackTrace();
			}
		}
	}

}
