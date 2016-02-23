package finalproject.data;

import finalproject.models.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;
import java.util.Date;

import finalproject.DataStructures.*;


public class userDB {
	private static String dbURL = "jdbc:mysql://localhost:3360/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	private static Calendar cal = Calendar.getInstance();
	
	/* 
	 * returns a triplet of User, Previous Month Max purchases and all time max purchases
	 * Assuming that the first and last name are the names requested. 
	 */
	public static Quad<String, String, Integer, Integer>[] getAllUserAndTotals()
	{
		ArrayList<Quad<String, String, Integer, Integer>> items = new ArrayList<Quad<String, String, Integer, Integer>>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			// Pretty sure I'm going to end up taking out the password in the query. 
			String query = "select u.fName, u.lName, "
					+ "sum(if(t.purchaseDate <= (date_add(NOW(), interval 1 month)), quantity, 0)) as MonthBack, "
					+ "sum(quantity) as alltimetotal  "
					+ "from users as u "
					+ "inner join Transactions as t "
					+ "on t.userID = u.id "
					+ "inner join PurchaseDetails as pd "
					+ "on pd.transactionNumber = t.transactionNumber "
					+ "group by u.id;";

			stmt = conn.prepareStatement(query);
			
			rs = stmt.executeQuery();
			if(rs != null) {
				do {
					String fName = rs.getString("fName");
					String lName = rs.getString("lName");
					int monthMax = rs.getInt("MonthBack");
					int allTimeMax = rs.getInt("alltimetotal");
				
					items.add(new Quad<String, String, Integer, Integer>(fName, lName, monthMax, allTimeMax));
					
				}while(rs.next());
			}
			else {
				return null;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			items = null;
		}
		finally {
			closeAll(stmt, conn, rs);
		}
        // Might be an issue, will need to debug. 
		return (Quad<String, String, Integer, Integer>[])items.toArray();
	}
	
	public static User getUser(int id)
	{
        User user = new User();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        if(id < 0)
            return null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "select * from users where id = '?'";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, Integer.toString(id));
			
			rs = stmt.executeQuery();
			if(rs == null) {
				return null;
			}
				
			user.setfName(rs.getString("fName"));
			user.setlName(rs.getString("lName"));
			user.setEmail(rs.getString("email"));
			user.setIsAdmin(rs.getBoolean("isAdmin"));
			user.setUserName(rs.getString("userName"));
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
    
    public static boolean deleteUser(int id)
	{
    	boolean flag = false;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        if(id < 0)
        	return false;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "delete from users where id = '?';";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, Integer.toString(id));
			
			if(stmt.executeUpdate() > 0)
			{
				flag = true;
			}
        }
		catch(Exception e) {
			e.printStackTrace();
			flag = false;
		}
		finally {
			closeAll(stmt, conn, rs);
		}
		return flag;
	}	
	
	public static User getUser(String uName)
	{
		User user = new User();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		if(uName == null || uName.length() == 0)
			return getAnonymousUser();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "select * from users where userName = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, uName); 
			
			rs = stmt.executeQuery(); 
				
			if(rs == null || rs.wasNull()) {
				return getAnonymousUser();
			}

			// Get the first row and pull down the user data
			if(rs.first()) {
				user.setfName(rs.getString("fName"));
				user.setlName(rs.getString("lName"));
				user.setEmail(rs.getString("email"));
				user.setIsAdmin(rs.getBoolean("isAdmin"));
				user.setUserName(rs.getString("userName"));
				user.setLastLogin(rs.getDate("lastLogin"));
				user.setPassword(rs.getString("pass"));
				user.setAccountCreated(rs.getDate("accountCreated"));
			}
			else {
				user = null;
			}
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
	
	public static User getAnonymousUser()
	{
		User user = new User();
		user.setUserName("Anonymous");
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

			String query = "update user set fName = ?, lName = ?, email = ?, pass = ?, lastLogin = ? where userName = ?";
			stmt = conn.prepareStatement(query);
			
			stmt.setString(1, user.getfName());
			stmt.setString(2, user.getlName());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getPassword());
			
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
