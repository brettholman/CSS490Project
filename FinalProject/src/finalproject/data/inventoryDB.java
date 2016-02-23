package finalproject.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import finalproject.models.Category;
import finalproject.models.InventoryItem;

public class inventoryDB {
	private static String dbURL = "jdbc:mysql://localhost:3360/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	private static Calendar cal = Calendar.getInstance();
	
	public static InventoryItem getInventoryItem(int itemID)
	{
		InventoryItem item = new InventoryItem();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		if(itemID < 0)
			return null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "select i.id as 'i.id', i.title as 'i.title', "
					+ "i.quantity as 'i.quantity', i.price as 'i.price', "
					+ "i.categoryID as 'i.CategoryID', c.ID as 'c.ID', "
					+ "c.categoryName as 'c.categoryName' "
					+ "from InventoryItems as i "
					+ "inner join Category as c "
					+ "on c.id = i.categoryID "
					+ "where i.id = '?';";
			
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, itemID);
			
			rs = stmt.executeQuery();
			
			if(rs == null){
				return null;
			}
			
			// Still need to check logic on this. 
			item.setId(rs.getInt("i.id"));
			item.setTitle(rs.getNString("i.title"));
			item.setQuantityInStock(rs.getInt("i.quantity"));
			item.setDescription(rs.getNString("i.description"));
			Category cat = new Category();
			cat.setCategoryName(rs.getNString("c.categoryName"));
			cat.setId(rs.getInt("c.id"));
			item.setCategory(cat);
		}
		catch(Exception e) {
			e.printStackTrace();
			item = null;
		}
		finally {
			closeAll(stmt, conn, rs);
		}
		return item;
	}	
	
	public static InventoryItem[] getAllInventoryItems()
	{
		// Temp code, just going to generate some fake items for now
		InventoryItem[] retval = new InventoryItem[20];
		for(int i = 0; i < 20; i++)
		{
			retval[i] = new InventoryItem();
			retval[i].setId(i);
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
