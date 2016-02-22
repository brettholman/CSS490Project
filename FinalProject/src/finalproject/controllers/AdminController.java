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
@WebServlet("/AdminController")
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
			item.setTitle((String)request.getParameter("title"));
			item.setDescription((String)request.getParameter("description"));
			item.setQuantityInStock(Integer.parseInt((String)request.getParameter("quantityInStock")));
			item.setPrice(Double.parseDouble((String)request.getParameter("price")));
			item.setCategoryID(Integer.parseInt((String)request.getParameter("categoryID")));
			
			// TODO: Add the new item to the database (not sure how we determine/set the ID?).  
			
			getServletContext().getRequestDispatcher("/admin/inventory.jsp").forward(request, response);
		}

		// Update an item's properties
		else if(requestURI.endsWith("changeItem")){
			
			int itemID = Integer.parseInt((String)request.getParameter("itemID"));
			InventoryItem item = inventoryDB.getInventoryItem(itemID);
			item.setTitle((String)request.getParameter("title"));
			item.setDescription((String)request.getParameter("description"));
			item.setQuantityInStock(Integer.parseInt((String)request.getParameter("quantityInStock")));
			item.setPrice(Double.parseDouble((String)request.getParameter("price")));
			item.setCategoryID(Integer.parseInt((String)request.getParameter("categoryID")));
			
			// TODO: save the modified item in the database.
			
			getServletContext().getRequestDispatcher("/admin/inventoryItem.jsp").forward(request, response);
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
			user.setIsAdmin(Boolean.parseBoolean((String)request.getParameter("isAdmin")));
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
	}
}
