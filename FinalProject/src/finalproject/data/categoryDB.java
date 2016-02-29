package finalproject.data;

import java.util.ArrayList;
import java.util.Calendar;

import finalproject.models.Category;
import finalproject.models.InventoryItem;

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
	
	public static Category[] getAllCategories()
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
			if(rs == null || rs.wasNull()) {
				return null;
			}
	
			// Get the first row and pull down the user data
			if(rs.first()) {
				do {
					Category tmp = new Category();
					tmp.setCategoryName(rs.getNString("categoryName"));
					tmp.setId(rs.getInt("id"));
					list.add(tmp);
				}while(rs.next());
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
		return list.toArray(new Category[list.size()]);
	}	
	
	public static Category getCategoryByID(int id)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Category toReturn = new Category();
		
		if(id < 0)
			return null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select * from Category where id = ?";
			stmt = conn.prepareStatement(query);
			
			stmt.setInt(1, id);
			
			rs = stmt.executeQuery();
	
			// Get the first row and pull down the user data
			if(rs.first()) {
				toReturn.setCategoryName(rs.getNString("categoryName"));
				toReturn.setId(rs.getInt("id"));
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
		return toReturn;
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
