package finalproject.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.*;

import finalproject.models.Category;
import finalproject.models.InventoryItem;
import javafx.util.Pair;

public class inventoryDB {
	private static String dbURL = "jdbc:mysql://localhost:3360/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	private static Calendar cal = Calendar.getInstance();

	public static Boolean insertItem(InventoryItem item) {
		
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		if(item == null)
			return result;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "INSERT INTO inventoryitems (title, author, description, categoryID, quantity, price, cost) " +
					"SELECT ?, ?, ?, ?, ?, ?, ?";
			
			stmt = conn.prepareStatement(query);
			
			stmt.setString(1, item.getTitle());
			stmt.setString(2, item.getAuthor());
			stmt.setString(3, item.getDescription());
			stmt.setInt(4, item.getCategoryID());
			stmt.setInt(5, item.getQuantityInStock());
			stmt.setDouble(6, item.getPrice());
			stmt.setDouble(7, item.getCost());
			
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
	
	public static boolean updateItem(InventoryItem item) {
		
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		if(item == null)
			return result;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "UPDATE inventoryitems SET title=?, author=?, description=?, " + 
					"categoryID=coalesce((SELECT id FROM category WHERE categoryName=?),1), " +
					"quantity=?, price=?, cost=? WHERE id=? ";
			
			stmt = conn.prepareStatement(query);
			
			stmt.setString(1, item.getTitle());
			stmt.setString(2, item.getAuthor());
			stmt.setString(3, item.getDescription());
			stmt.setString(4, item.getCategory());
			stmt.setInt(5, item.getQuantityInStock());
			stmt.setDouble(6, item.getPrice());
			stmt.setDouble(7,  item.getCost());
			stmt.setInt(8, item.getId());
			
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
	
	public static InventoryItem getInventoryItem(int itemID) {
		
		InventoryItem item = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = 
					"SELECT II.id, C.categoryName as category, II.title, II.author, II.description, II.price, II.quantity, II.cost, avg(R.rating) AS rating FROM inventoryitems AS II " +
					"INNER JOIN category AS C ON II.categoryID = C.id " +
					"LEFT JOIN ratings AS R ON R.itemID = II.id " +
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
				item.setAuthor(rs.getString("author"));
				item.setDescription(rs.getNString("description"));
				item.setPrice(rs.getDouble("price"));
				item.setCost(rs.getDouble("cost"));
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
	
	public static InventoryItem[] getAllItems(String searchText, int categoryID)
	{
		ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			// If the categoryID is < 0 then return all items 
			if(categoryID <= 0) {
				String query = 
						"SELECT II.id, C.categoryName as category, II.categoryID as categoryID, II.title, II.author, II.description, II.price, II.quantity, II.cost, avg(R.rating) AS rating FROM inventoryitems AS II " +
						"INNER JOIN category AS C ON II.categoryID = C.id " +
						"LEFT JOIN ratings AS R ON R.itemID = II.id ";
				if(searchText.length() > 0) { query += "WHERE title LIKE ? "; }
				query += "GROUP BY II.id ORDER BY title";
				stmt = conn.prepareStatement(query);
				if(searchText.length() > 0) { stmt.setString(1, "%" + searchText + "%"); }
			}
			// Get all items in the specified category
			else {
				String query = 
					"select ii.id, ii.categoryID as categoryID, c.categoryName as category, ii.title, II.author, ii.description, ii.price, ii.quantity, ii.cost, avg(r.rating) as rating " +
					"from inventoryitems as ii " +
					"inner join category as c on ii.categoryid = c.id " +
					"LEFT join ratings as r on r.itemId = ii.id " +
					"where c.id = ? ";
				if(searchText.length() > 0) { query += "AND title LIKE ? "; }
				query += "group by ii.id, c.id";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, Integer.toString(categoryID));
				if(searchText.length() > 0) { stmt.setString(2, "%" + searchText + "%"); }
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
					item.setCategoryID(rs.getInt("categoryID"));
					item.setCategory(rs.getString("category"));
					item.setTitle(rs.getString("title"));
					item.setAuthor(rs.getString("author"));
					item.setDescription(rs.getNString("description"));
					item.setPrice(rs.getDouble("price"));
					item.setQuantityInStock(rs.getInt("quantity"));
					item.setAverageRating(rs.getDouble("rating"));
					item.setCost(rs.getDouble("cost"));
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
	
	public static InventoryItem[] getAllItemsWithSaleRecords()
	{
		ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
				String query = 
					"select ii.id, ii.categoryID as categoryID, c.categoryName as category, ii.title, II.author, ii.description, ii.price, ii.quantity, ii.cost, avg(r.rating) as rating " +
					"from inventoryitems as ii " +
					"inner join category as c on ii.categoryid = c.id " +
					"LEFT join ratings as r on r.itemId = ii.id " +
					"inner join purchaseDetails d on d.itemID = ii.id " + 
					"group by ii.id, c.id";
				stmt = conn.prepareStatement(query);
			
			rs = stmt.executeQuery();
			if(rs == null || rs.wasNull()) {
				return null;
			}
	
			// Get the first row and pull down the user data
			if(rs.first()) {
				do {
					InventoryItem item = new InventoryItem();
					item.setId(rs.getInt("id"));
					item.setCategoryID(rs.getInt("categoryID"));
					item.setCategory(rs.getString("category"));
					item.setTitle(rs.getString("title"));
					item.setAuthor(rs.getString("author"));
					item.setDescription(rs.getNString("description"));
					item.setPrice(rs.getDouble("price"));
					item.setCost(rs.getDouble("cost"));
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

	public static ArrayList<Pair<InventoryItem, String>> getBiWeeklyBestSellers(int maxBooks, int category)
	{
		ArrayList<Pair<InventoryItem, String>> items = new ArrayList<Pair<InventoryItem, String>>();
		// flag to tell if the request wants all categories or not. 
		boolean allCategories = (category == -1);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Calendar curCal = Calendar.getInstance();
		curCal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // Set to Sunday of this week. 
		curCal.add(Calendar.DAY_OF_YEAR, -14); // set two weeks back
		
		
		if(maxBooks <= 0)
		{
			return null;
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			// Test Me
			String query = "select pd.itemID, sum(pd.quantity) as total, "
					+ "ii.id, ii.title, ii.quantity, ii.price, ii.description, ii.author, c.id, ii.cost, c.categoryName, avg(r.rating) as avgRating "
					+ "from PurchaseDetails as pd "
					+ "inner join Transactions as t "
					+ "on t.transactionNumber = pd.transactionNumber "
					+ "inner join InventoryItems as ii "
					+ "on ii.id = pd.itemID "
					+ "inner join Category as c "
					+ "on ii.categoryID = c.id "
					+ "inner join ratings as r "
					+ "on r.itemID = ii.id "
					+ "where t.purchaseDate >= \'" 
					+ (new SimpleDateFormat("yyyy").format(curCal.getTime())) + "-" // Year
					+ (new SimpleDateFormat("MM").format(curCal.getTime())) + "-" // Month
					+ (new SimpleDateFormat("dd").format(curCal.getTime())) + "\'" // Day
					+ (!allCategories ? "and ii.categoryID = ? " : "")
					+ "group by pd.itemID " 
					+ "order by total desc "
					+ "limit ?;";
			
			stmt = conn.prepareStatement(query);
			
			
			
			if(!allCategories)
				stmt.setString(1, Integer.toString(category));
			
			stmt.setInt(allCategories ? 1 : 2, maxBooks);
			
			rs = stmt.executeQuery();
			if(rs.next()){
				do {
					InventoryItem item = new InventoryItem();
					item.setId(rs.getInt("ii.id"));
					item.setTitle(rs.getNString("title"));
					item.setAuthor(rs.getString("ii.author"));
					item.setQuantityInStock(rs.getInt("quantity"));
					item.setDescription(rs.getNString("description"));
					item.setPrice(rs.getDouble("price"));
					item.setCost(rs.getDouble("cost"));
					item.setCategoryID(rs.getInt("c.id"));
					item.setCategory(rs.getNString("categoryName"));
					item.setAverageRating(Double.parseDouble(rs.getString("avgRating")));
					items.add(new Pair<InventoryItem, String>(item, rs.getString("total")));
				} while(rs.next()); 
			}
			else {
				items = null;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			items = null;
		}
		finally {
			closeAll(stmt, conn, rs);
		}
		return items;
	}
	
	public static ArrayList<Pair<InventoryItem, String>> getWeeklyBestSellers(int maxBooks, int category)
	{
		ArrayList<Pair<InventoryItem, String>> items = new ArrayList<Pair<InventoryItem, String>>();
		// flag to tell if the request wants all categories or not. 
		boolean allCategories = (category == -1);
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;		
		
		Calendar curCal = Calendar.getInstance();
		curCal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // Set to Sunday of this week. 
		curCal.add(Calendar.DAY_OF_YEAR, -7); // set one week back
		
		if(maxBooks <= 0)
		{
			return null;
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			// Test Me
			String query = "select pd.itemID, sum(pd.quantity) as total, "
					+ "ii.id, ii.title, ii.quantity, ii.price, ii.description, ii.author, c.id, c.categoryName, ii.cost, avg(r.rating) as avgRating "
					+ "from PurchaseDetails as pd "
					+ "inner join Transactions as t "
					+ "on t.transactionNumber = pd.transactionNumber "
					+ "inner join InventoryItems as ii "
					+ "on ii.id = pd.itemID "
					+ "inner join Category as c "
					+ "on ii.categoryID = c.id "
					+ "inner join ratings as r "
					+ "on r.itemID = ii.id "
					+ "where t.purchaseDate >= \'" 
					+ (new SimpleDateFormat("yyyy").format(curCal.getTime())) + "-" // Year
					+ (new SimpleDateFormat("MM").format(curCal.getTime())) + "-" // Month
					+ (new SimpleDateFormat("dd").format(curCal.getTime())) + "\'" // Day
					+ (!allCategories ? "and ii.categoryID = ? " : "")
					+ "group by pd.itemID " 
					+ "order by total desc "
					+ "limit ?;";
			

			stmt = conn.prepareStatement(query);
			
			if(!allCategories)
				stmt.setString(1, Integer.toString(category));
			
			stmt.setInt(allCategories ? 1 : 2, maxBooks);
			
			rs = stmt.executeQuery();
			if(rs.next()){
				do {
					InventoryItem item = new InventoryItem();
					item.setId(rs.getInt("ii.id"));
					item.setTitle(rs.getNString("title"));
					item.setAuthor(rs.getString("ii.author"));
					item.setQuantityInStock(rs.getInt("quantity"));
					item.setDescription(rs.getNString("description"));
					item.setPrice(rs.getDouble("price"));
					item.setCost(rs.getDouble("cost"));
					item.setCategoryID(rs.getInt("c.id"));
					item.setCategory(rs.getNString("categoryName"));
					item.setAverageRating(Double.parseDouble(rs.getString("avgRating")));
					items.add(new Pair<InventoryItem, String>(item, rs.getString("total")));
				} while(rs.next()); 
			}
			else {
				items = null;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			items = null;
		}
		finally {
			closeAll(stmt, conn, rs);
		}
		return items;
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
					+ "i.quantity, i.price, i.cost, i.categoryID, c.ID as 'c.ID', "
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
			item.setCost(rs.getDouble("cost"));
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
}
