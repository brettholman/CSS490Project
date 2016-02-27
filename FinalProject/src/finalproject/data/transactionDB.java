package finalproject.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import finalproject.DataStructures.SalesInformation;
import finalproject.models.InventoryItem;
import finalproject.models.Rating;

public class transactionDB {
	private static String dbURL = "jdbc:mysql://localhost:3360/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	private static Calendar cal = Calendar.getInstance();
	
	// month = month that is requested. 
	public static SalesInformation getTotalSalesForCalendarMonth(int month, int year)
	{
		if(month < 1 || month > 12 || year < 0 || year > cal.get(Calendar.YEAR))
		{
			return new SalesInformation();
		}
		int previousYear = year;
		int PreviousMonth = (month - 1 % 12) == 0 ? 12 : month - 1;
		if((month - 1 % 12) == 0)
			previousYear--;
		
		
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select * from Category";
			stmt = conn.prepareStatement(query);
			
			rs = stmt.executeQuery();
			if(rs == null || rs.wasNull()) {
				return new SalesInformation();
			}
	
			// Get the first row and pull down the user data
			if(rs.first()) {
				
			}
			else {
				return new SalesInformation();
			}			
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			closeAll(stmt, conn, rs);
		}
		return null;
	}
	
	public static int InsertTransaction(int userID, double totalCost)
	{
		if(userID < 0 || totalCost < 0)
			return -1;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int transactionNumber = -1;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "insert into Transactions (userID, purchaseDate, totalCost) "
								+ "values (?, curdate(), ?);";
			
			stmt = conn.prepareStatement(query);
			
			stmt.setString(1, Integer.toString(userID));
			stmt.setString(2, Double.toString(totalCost));
			
			if(stmt.executeUpdate() == 0)
			{
				return transactionNumber;
			}
			
			query = "select max(transactionNumber) as num from transactions;";
			
			stmt = conn.prepareStatement(query);
			
			rs = stmt.executeQuery();
			if(rs.first())
			{
				transactionNumber = Integer.parseInt(rs.getString("num"));	
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			closeAll(stmt, conn, rs);
		}
		return transactionNumber;
	}
	
	public static boolean InsertPurchaseDetail(int transactionNumber, int itemID, int quantity)
	{
		boolean flag = false;
		if(transactionNumber < 0 || itemID < 0 || quantity < 0)
			return flag;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "insert into purchasedetails values (?, ?, ?);";
			
			stmt = conn.prepareStatement(query);
			
			stmt.setInt(1, transactionNumber);
			stmt.setInt(2, itemID);
			stmt.setInt(3, quantity);
			
			if(stmt.executeUpdate() != 0)
			{
				flag = true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			closeAll(stmt, conn, rs);
		}
		return flag;
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
