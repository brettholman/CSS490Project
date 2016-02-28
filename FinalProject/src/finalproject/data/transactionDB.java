package finalproject.data;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;

import finalproject.DataStructures.SalesInformation;
import finalproject.models.*;

public class transactionDB {
	private static String dbURL = "jdbc:mysql://localhost:3360/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	private static Calendar nCal = Calendar.getInstance();
	private static Calendar cCal = Calendar.getInstance();
	
	// month = month that is requested. 
	public static StringSet getTotalSalesForListOfCalendarMonths(ArrayList<Date> dates)
	{
		ArrayList<String> toReturn = new ArrayList<String>();
		if(dates == null) {
			return null;
		}
		int index = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String total = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "";
			for(Date date : dates) {
				if(index == 0){
					query = "SELECT Sum(totalCost) as amount FROM transactions where purchasedate >= ?;";
				}
				else {
					query = "SELECT SUM(totalCost) as amount FROM transactions where purchasedate >= ? and purchaseDate < ?";
				}
				nCal.setTime(date);
				nCal.add(Calendar.MONTH, + 1);
				cCal.setTime(date);
				int nMonth = nCal.get(Calendar.MONTH) + 1;
				int nYear = nCal.get(Calendar.YEAR);
				int month = cCal.get(Calendar.MONTH) + 1;
				int year = cCal.get(Calendar.YEAR);
				stmt = conn.prepareStatement(query);
			
				stmt.setString(1, 
						Integer.toString(year) + "-" + 
						Integer.toString(month) + "-" + 1);
				if(index > 0)
				{
					stmt.setString(2, 
							Integer.toString(nYear) + "-" +
							Integer.toString(nMonth) + "-" + 1);
				}
		
				rs = stmt.executeQuery();
				if(rs == null || rs.wasNull()) {
					return null;
				}
						// Get the first row and pull down the user data
				if(rs.first()) {
					total = rs.getString("amount");					
				}
				else {
					return null;
				}
				index++;
				toReturn.add(total);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			closeAll(stmt, conn, rs);
		}
		
		return new StringSet(toReturn);
	}
	
	
	public static StringSet getTotalSalesForListOfCalendarWeek(ArrayList<Date> dates)
	{
		ArrayList<String> toReturn = new ArrayList<String>();
		if(dates == null) {
			return null;
		}
		int index = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String total = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "";
			for(Date date : dates) {
				if(index == 0){
					query = "SELECT Sum(totalCost) as amount FROM transactions where purchasedate >= ?;";
				}
				else {
					query = "SELECT SUM(totalCost) as amount FROM transactions where purchasedate >= ? and purchaseDate < ?";
				}
				nCal.setTime(date);
				nCal.add(Calendar.DAY_OF_YEAR, + 7);
				cCal.setTime(date);
				int nDay = nCal.get(Calendar.DAY_OF_MONTH);
				int nMonth = nCal.get(Calendar.MONTH) + 1;
				int nYear = nCal.get(Calendar.YEAR);
				int day = cCal.get(Calendar.DAY_OF_MONTH);
				int month = cCal.get(Calendar.MONTH) + 1;
				int year = cCal.get(Calendar.YEAR);
				stmt = conn.prepareStatement(query);
			
				stmt.setString(1, 
						Integer.toString(year) + "-" + 
						Integer.toString(month) + "-" + Integer.toString(day));
				if(index > 0)
				{
					stmt.setString(2, 
							Integer.toString(nYear) + "-" +
							Integer.toString(nMonth) + "-" + Integer.toString(nDay));
				}
		
				rs = stmt.executeQuery();
				if(rs == null || rs.wasNull()) {
					return null;
				}
						// Get the first row and pull down the user data
				if(rs.first()) {
					total = rs.getString("amount") == null ? "0" : rs.getString("amount");					
				}
				else {
					return null;
				}
				index++;
				toReturn.add(total);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			closeAll(stmt, conn, rs);
		}
		
		return new StringSet(toReturn);
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
