package finalproject.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import finalproject.models.InventoryItem;

public class inventoryDB {
	private static String dbURL = "jdbc:mysql://localhost:3360/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	private static Calendar cal = Calendar.getInstance();
	
	public static InventoryItem getInventoryItem(int itemID)
	{
		return null;
	}	
	
	public static InventoryItem[] getAllInventoryItems()
	{
		// Temp code, just going to generate some fake items for now
		InventoryItem[] retval = new InventoryItem[20];
		for(int i = 0; i < 20; i++)
		{
			retval[i] = new InventoryItem();
			retval[i].setId(i);
			retval[i].setCategoryID(i);
			retval[i].setTitle("Title" + i);
			retval[i].setDescription("Description Text " + i);
			retval[i].setQuantityInStock(i);
			retval[i].setPrice(9.99);
		}
		return retval;
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
