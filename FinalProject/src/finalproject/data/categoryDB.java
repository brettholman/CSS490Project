package finalproject.data;

import java.util.ArrayList;
import java.util.Calendar;

import finalproject.models.Category;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class categoryDB {
	private static String dbURL = "jdbc:mysql://localhost:3360/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	private static Calendar cal = Calendar.getInstance();
	
	
	public Category[] getAllCategories() 
	{
		ArrayList<Category> list = new ArrayList<Category>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "select * from Category";
			stmt = conn.prepareStatement(query);
			
			rs = stmt.executeQuery();
			
			if(rs != null)
			{
				do {
					Category tmp = new Category();
					tmp.setCategoryName(rs.getNString("categoryName"));
					tmp.setId(rs.getInt("id"));
					list.add(tmp);
					
				}while(rs.next());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			list = null;
		}
		finally {
			closeAll(stmt, conn, rs);
		}
		return (Category[])list.toArray();
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
