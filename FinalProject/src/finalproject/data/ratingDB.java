package finalproject.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import finalproject.models.Book;
import finalproject.models.Category;
import finalproject.models.Rating;

public class ratingDB {
	private static String dbURL = "jdbc:mysql://localhost:3360/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	private static Calendar cal = Calendar.getInstance();
	
	public static Rating[] getAllRatingsForABook(Book book)
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
			
			if(rs != null)
			{
				do {
					Rating item = new Rating();
					item.setID(rs.getInt("r.id"));
					item.setUserName(rs.getString("username"));
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
		return (Rating[])list.toArray();
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
