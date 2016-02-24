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

public class inventoryDB {
	private static String dbURL = "jdbc:mysql://localhost:3360/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	private static Calendar cal = Calendar.getInstance();
	
	// This will need some massive testing once the UI is using it. 
	public static InventoryItem[] getBestSellers(int maxBooks, Category category)
	{
		// flag to tell if the request wants all categories or not. 
		boolean allCategories = (category != null);
		
		ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		if(maxBooks <= 0)
		{
			return null;
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "select pd.itemID, sum(pd.quantity) as total,"
					+ "ii.id, ii.title, ii.quantity, ii.price, ii.description, c.categoryID, c.categoryName"
					+ "from PurchaseDetails as pd"
					+ "inner join InventoryItems as ii"
					+ "on ii.id = pd.itoemID"
					+ "inner join Category as c "
					+ "on ii.categoryID = c.id"
					+ (!allCategories ? "where ii.categoryID = ?" : "")
					+ "group by pd.itemID" 
					+ "order by total desc"
					+ "limit ?;";

			stmt = conn.prepareStatement(query);
			
			if(!allCategories)
				stmt.setString(1, Integer.toString(category.getId()));
			
			stmt.setString(allCategories ? 1 : 2, Integer.toString(maxBooks));
			
			rs = stmt.executeQuery();
			 
			if(rs != null)
			{
				do 
				{
					InventoryItem item = new InventoryItem();
					item.setId(rs.getInt("i.id"));
					item.setTitle(rs.getNString("title"));
					item.setQuantityInStock(rs.getInt("quantity"));
					item.setDescription(rs.getNString("description"));
					item.setPrice(rs.getDouble("price"));
					Category cat = new Category();
					cat.setCategoryName(rs.getNString("categoryName"));
					cat.setId(rs.getInt("c.id"));
					//item.setCategory(cat);
					items.add(item);
				}while(rs.next());
			}
			else 
			{
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
		return (InventoryItem[])items.toArray();
	}
	
	public static InventoryItem[] getAllItemsForCategory(int categoryID)
	{
		// If categoryID is < 0 then return items for all categories
		if(categoryID < 0) { categoryID = -1; }
		
		ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			// If the categoryID is < 0 then return all items 
			if(categoryID < 0) {
				String query = 
						"SELECT II.id, C.categoryName as category, II.title, II.description, II.price, II.quantity, avg(R.rating) AS rating FROM inventoryitems AS II " +
						"INNER JOIN category AS C ON II.categoryID = C.id " +
						"INNER JOIN ratings AS R ON R.itemID = II.id " +
						"GROUP BY II.id ORDER BY title";
				stmt = conn.prepareStatement(query);
			}
			// Get all items in the specified category
			else {
				String query = 
					"select ii.id, c.categoryName as category, ii.title, ii.description, ii.price, ii.quantity, avg(r.rating) as rating " +
					"from inventoryitems as ii " +
					"inner join category as c on ii.categoryid = c.id " +
					"inner join ratings as r on r.itemId = ii.id " +
					"where c.id = 1 " +
					"group by ii.id, c.id;";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, Integer.toString(categoryID));
			}
			
			rs = stmt.executeQuery();
			if(rs == null || rs.wasNull()) {
				return null;
			}
	
			// Get the first row and pull down the user data
			if(rs.first()) {
				do {
					InventoryItem item = new InventoryItem();
					item.setId(rs.getInt("id"));
					item.setCategory(rs.getString("category"));
					item.setTitle(rs.getString("title"));
					item.setDescription(rs.getNString("description"));
					item.setPrice(rs.getDouble("price"));
					item.setQuantityInStock(rs.getInt("quantity"));
					item.setAverageRating(rs.getDouble("rating"));
					items.add(item);
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
		return items.toArray(new InventoryItem[items.size()]);
	}

	public static InventoryItem getInventoryItemWithAverage(int itemID)
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
			
			String query = "select i.id, i.title, "
					+ "i.quantity, i.price, i.categoryID, c.ID as 'c.ID', "
					+ "c.categoryName, avg(r.rating) as average "
					+ "from InventoryItems as i "
					+ "inner join Category as c "
					+ "on c.id = i.categoryID "
					+ "inner join Ratings as r "
					+ "on r.itemID = i.id "
					+ "where i.id = ?;";
			
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, itemID);
			
			rs = stmt.executeQuery();
			
			if(rs == null){
				return null;
			}
			item.setId(rs.getInt("i.id"));
			item.setTitle(rs.getNString("title"));
			item.setQuantityInStock(rs.getInt("quantity"));
			item.setDescription(rs.getNString("description"));
			item.setPrice(rs.getDouble("price"));
			Category cat = new Category();
			cat.setCategoryName(rs.getNString("categoryName"));
			cat.setId(rs.getInt("c.id"));
			//item.setCategory(cat);
			item.setAverageRating(rs.getDouble("average"));
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

	public static InventoryItem getInventoryItem(int itemID) {
		
		InventoryItem item = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = 
					"SELECT II.id, C.categoryName as category, II.title, II.description, II.price, II.quantity, avg(R.rating) AS rating FROM inventoryitems AS II " +
					"INNER JOIN category AS C ON II.categoryID = C.id " +
					"INNER JOIN ratings AS R ON R.itemID = II.id " +
					"WHERE II.id = ? " +
					"GROUP BY II.id ORDER BY title";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, Integer.toString(itemID));
			
			rs = stmt.executeQuery();
			if(rs == null || rs.wasNull()) {
				return null;
			}
	
			// Get the first row and pull down the item data
			if(rs.first()) {
				item = new InventoryItem();
				item.setId(rs.getInt("id"));
				item.setCategory(rs.getString("category"));
				item.setTitle(rs.getString("title"));
				item.setDescription(rs.getNString("description"));
				item.setPrice(rs.getDouble("price"));
				item.setQuantityInStock(rs.getInt("quantity"));
				item.setAverageRating(rs.getDouble("rating"));
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
		return item;		
	}
}
