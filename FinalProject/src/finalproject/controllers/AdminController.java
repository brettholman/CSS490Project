package finalproject.controllers;

import finalproject.data.categoryDB;
import finalproject.data.inventoryDB;
import finalproject.data.marketingDB;
import finalproject.data.userDB;
import finalproject.models.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AdminController
 */
@WebServlet("/AdminController/*")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestURI = request.getRequestURI();
		System.out.println(requestURI); 
		String url = "";
		
		// Add a new item
		if(requestURI.endsWith("newItem")){

			HttpSession session = request.getSession(true);
			InventoryItem item = new InventoryItem();
			item.setAuthor("NewItem");
			item.setTitle("NewItem");	
			item.setDescription("NewItem");
			item.setPrice(1);
			item.setQuantityInStock(0);
			session.setAttribute("currentItem", item);

			item.setCategoryID(1);
			Category cat = categoryDB.getCategoryByID(1);
			item.setCategory(cat.getCategoryName());
			
			// Redirect the user to the inventoryItem view
			getServletContext().getRequestDispatcher("/admin/inventoryItem.jsp").forward(request, response);
		}

		// Edit a specific item
		else if(requestURI.endsWith("editItem")){

			// Get the itemID and add it to the session
			HttpSession session = request.getSession(true);
			InventoryItem item = inventoryDB.getInventoryItem(Integer.parseInt((String)request.getParameter("itemID")));
			session.setAttribute("currentItem", item);
			
			// Redirect the user to the inventoryItem view
			getServletContext().getRequestDispatcher("/admin/inventoryItem.jsp").forward(request, response);
		}

		// Edit a specific item
		else if(requestURI.endsWith("saveItem")){

			if(saveItem(request)) {
				getServletContext().getRequestDispatcher("/admin/inventory.jsp").forward(request, response);
			}
			else {
				HttpSession session = request.getSession(true);
				session.setAttribute("errorMsg", "Unable to save item.");
				getServletContext().getRequestDispatcher("/admin/error.jsp").forward(request, response);
			}
		}			
		
		else if(requestURI.endsWith("changeUserRole")) {			// Get the itemID and add it to the session
			HttpSession session = request.getSession(true);
			String userName = (String)request.getParameter("userIDUR");
			boolean newRoleIsAdmin = Boolean.parseBoolean((String)request.getParameter("newRoleIsAdmin"));
			if(newRoleIsAdmin) {
				userDB.setRole(userName);
			}
			else {
				userDB.removeRole(userName);
			}
			getServletContext().getRequestDispatcher("/admin/users.jsp").forward(request, response);
		}
		
		// Remove an item (by reducing "quantityInStock" to 0)
		else if(requestURI.endsWith("removeItem")){
			
			int itemID = Integer.parseInt((String)request.getParameter("itemID"));
			InventoryItem item = inventoryDB.getInventoryItem(itemID);
			
			// TODO: call a function to set the quantityInStock for the specified item to 0.  We can't actually delete the item
			// from the database without breaking our transaction history.
			
			getServletContext().getRequestDispatcher("/admin/inventoryItem.jsp").forward(request, response);
		}
		
		// Modify a user (allow setting the password and the isAdmin flag)
		else if(requestURI.endsWith("modifyUser")){
			
			int userID = Integer.parseInt((String)request.getParameter("userID"));
			User user = userDB.getUser(userID);
			user.setEmail((String)request.getParameter("mail"));
			user.setUserName((String)request.getParameter("name"));
			user.setfName((String)request.getParameter("fmail"));
			user.setlName((String)request.getParameter("lmail"));
			user.setPassword((String)request.getParameter("password"));
			
			// TODO: update the user's properties.
			
			getServletContext().getRequestDispatcher("/admin/users.jsp").forward(request, response);
		}
		
		// Delete a user
		else if(requestURI.endsWith("deleteUser")){
			
			int userID = Integer.parseInt((String)request.getParameter("userID"));

			// If successful, redirect back to the user management page
			if(userDB.deleteUser(userID)) {
				getServletContext().getRequestDispatcher("/admin/users.jsp").forward(request, response);
			}

			// Otherwise redirect the user to an error page
			else {
				getServletContext().getRequestDispatcher("/admin/users.jsp").forward(request, response);
			}
		}
		// View Item Marketing Data
		else if(requestURI.endsWith("viewItemMarketingDetails")){
			HttpSession session = request.getSession(true);
			int itemID = Integer.parseInt((String)request.getParameter("itemID"));
			InventoryItem item = inventoryDB.getInventoryItem(itemID);
			User[] users = marketingDB.getAllUsersPurchaseDetailsOnInventoryItem(itemID);
			session.setAttribute("currentItem", item);
			session.setAttribute("listOfUsers", users);
			getServletContext().getRequestDispatcher("/admin/inventoryItemMarketingDetails.jsp").forward(request, response);
		}
		// View Category Marketing Data
		else if(requestURI.endsWith("viewCategoryMarketingDetails")){
			HttpSession session = request.getSession(true);
			int categoryID = Integer.parseInt((String)request.getParameter("categoryID"));
			Category category = categoryDB.getCategoryByID(categoryID);
			User[] users = marketingDB.getAllUsersPurchaseDetailsOnCategory(categoryID);
			session.setAttribute("currentCategory", category);
			session.setAttribute("listOfUsers", users);
			getServletContext().getRequestDispatcher("/admin/categoryMarketingDetails.jsp").forward(request, response);
		}
	}
	
	private Boolean saveItem(HttpServletRequest request){
		
		HttpSession session = request.getSession(true);
		
		InventoryItem item = (InventoryItem)session.getAttribute("currentItem");
		if(item == null) { return false; }

		try {
			
			item.setTitle(request.getParameter("title"));
			item.setAuthor(request.getParameter("author"));
			item.setDescription(request.getParameter("description"));
			item.setPrice(Double.parseDouble(request.getParameter("price")));
			item.setCategory(request.getParameter("category"));
			item.setQuantityInStock(Integer.parseInt(request.getParameter("quantity")));
			
			// Is this a new item?
			if(item.getId() < 1) {
				return inventoryDB.insertItem(item);
			}
			// Or is this an update?
			else {
				return inventoryDB.updateItem(item);
			}
		} 
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}		
}
