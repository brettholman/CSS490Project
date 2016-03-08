package finalproject.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import finalproject.models.Category;
import finalproject.models.InventoryItem;
import finalproject.models.Rating;
import finalproject.models.User;

public class ratingDB {
	private static String dbURL = "jdbc:mysql://localhost:3360/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	private static Calendar cal = Calendar.getInstance();
	
	public static Rating[] getAllRatingsForABook(InventoryItem book)
	{
		if(book == null)
			return null;
		return getAllRatingsForABook(book.getId());
	}
	
	/*
	 * This can be modified if it turns out that there need to be more than the userid and other foreign keys
	 */
	public static Rating[] getAllRatingsForABook(int id)
	{
		ArrayList<Rating> list = new ArrayList<Rating>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "select r.id as 'r.id', r.itemId, r.rating, r.ratingDate, r.description, u.username "
					+ "from ratings as r "
					+ "inner join Users as u "
					+ "on u.id = r.userId "
					+ "where itemID = ?";
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, Integer.toString(id));
			
			rs = stmt.executeQuery();
			if(rs == null || rs.wasNull()) {
				return null;
			}
	
			// Get the first row and pull down the item data
			if(rs.first()) {
				do {
					Rating item = new Rating();
					item.setID(rs.getInt("r.id"));
					item.setUserName(rs.getString("username"));
					item.setRating(rs.getInt("rating"));
					item.setBookID(rs.getInt("itemId"));
					item.setRatingDate(rs.getDate("ratingDate"));
					item.setDescription(rs.getString("description"));
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
		return list.toArray(new Rating[list.size()]);
	}
	
	public static Boolean addRatingForBook(User user, InventoryItem item, String rating, String description)
	{
		boolean flag = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		if(user == null || item == null)
			return flag;
		if(description == null)
			description = "";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "insert into ratings (userID, itemID, rating, ratingDate, description) values "
					+ "(?, ?, ?, curdate(), ?);";
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, String.valueOf(user.getId()));
			stmt.setString(2, String.valueOf(item.getId()));
			stmt.setString(3, rating);
			stmt.setString(4, description);
			
			if(stmt.executeUpdate() > 0)
				flag = true;
		}
		catch(Exception e) {
			e.printStackTrace();
			flag = false;
		}
		finally {
			closeAll(stmt, conn);
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
