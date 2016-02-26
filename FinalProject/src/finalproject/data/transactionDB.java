package finalproject.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import finalproject.DataStructures.SalesInformation;

public class transactionDB {
	private static String dbURL = "jdbc:mysql://localhost:3360/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	private static Calendar cal = Calendar.getInstance();
	
	// month = month that is requested. 
	public SalesInformation getTotalSalesForCalendarMonth(int month, int year)
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
