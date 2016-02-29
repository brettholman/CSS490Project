package finalproject.controllers;

import finalproject.data.inventoryDB;
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
		if(requestURI.endsWith("addItem")){
			
			InventoryItem item = new InventoryItem();
			item.setTitle(request.getParameter("title"));
			item.setAuthor(request.getParameter("author"));
			item.setDescription(request.getParameter("description"));
			item.setQuantityInStock(Integer.parseInt(request.getParameter("quantityInStock")));
			item.setPrice(Double.parseDouble(request.getParameter("price")));
			item.setCategoryID(Integer.parseInt(request.getParameter("categoryID")));
			
			// TODO: Add the new item to the database (not sure how we determine/set the ID?).  
			
			getServletContext().getRequestDispatcher("/admin/inventory.jsp").forward(request, response);
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
		// View Marketing Data
		else if(requestURI.endsWith("viewMarketingDetails")){
			System.out.println("In View Marketing Details");
			getServletContext().getRequestDispatcher("/admin/inventoryItemMarketingDetails.jsp").forward(request, response);
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
