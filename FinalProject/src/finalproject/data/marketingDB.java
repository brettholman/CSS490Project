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
