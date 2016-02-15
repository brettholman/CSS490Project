package finalproject.data;

import finalproject.models.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

public class userDB {
	private static String dbURL = "jdbc:mysql://localhost:3360/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	private static Calendar cal = Calendar.getInstance();
	
	public static User getUser(String uName)
	{
		// For testing until DB is fixed. 
		if(uName.equals("")) {
			User user = new User();
			user.setUserName("Anon");
			return user;
		}
		User user = new User();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		if(uName == null || uName.length() == 0)
			return null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "select * from users where userName = '?'";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, uName);
			
			rs = stmt.executeQuery();
			
			if(rs == null) {
				return null;
			}
				
			user.setfName(rs.getNString("fName"));
			user.setlName(rs.getNString("lName"));
			user.setEmail(rs.getString("email"));
			user.setIsAdmin(rs.getBoolean("isAdmin"));
			user.setUserName(rs.getNString("userName"));
			user.setLastLogin(rs.getDate("lastLogin"));
			user.setAccountCreated(rs.getDate("accountCreated"));
		}
		catch(Exception e) {
			e.printStackTrace();
			user = null;
		}
		finally {
			closeAll(stmt, conn, rs);
		}
		return user;
	}
	
	public static boolean insertUser(User user)
	{
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		if(user == null)
			return result;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "insert into users(userName, fName, lName, email, pass, lastLogin, accountCreated)" +
											"values('?', '?', '?', '?', '?', '?', '?')";
			
			stmt = conn.prepareStatement(query);
			
			stmt.setString(1, user.getUserName());
			stmt.setString(2, user.getfName());
			stmt.setString(3, user.getlName());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getPassword());
			stmt.setString(5, cal.getTime().toString());
			stmt.setString(6, cal.getTime().toString());
			
			// returns total rows effected. 
			if(stmt.executeUpdate() > 0)
			{
				result = true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeAll(stmt, conn);
		}
		return result;
	}
	
	public static int deleteUser(String uName) 
	{
		int flag = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		if(uName == null || uName.length() <= 0)
			return -1;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			stmt = conn.prepareStatement("delete from user where userName = ?");
			stmt.setString(1, uName);
			
			flag = stmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeAll(stmt, conn);
		}
		return flag;
	}
	
	public static int modifyUser(User user)
	{
		int flag = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		if(user == null || !user.isValid())
			return -1;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

			String query = "update user set fName = ?, lName = ?, email = ?, pass = ?, isAdmin = ?, lastLogin = ? where userName = ?";
			stmt = conn.prepareStatement(query);
			
			stmt.setString(1, user.getfName());
			stmt.setString(2, user.getlName());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getPassword());
			stmt.setString(5, String.valueOf(user.getIsAdmin()));
			
			flag = stmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
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
