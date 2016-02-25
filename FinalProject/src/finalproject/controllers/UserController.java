package finalproject.controllers;

import finalproject.DataStructures.Quad;
import finalproject.DataStructures.cartItem;
import finalproject.data.inventoryDB;
import finalproject.data.userDB;
import finalproject.models.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController/*")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String requestURI = request.getRequestURI();
		
		// Allow users to logon manually (via the button)
		if(requestURI.endsWith("logonManual")){

			// Prompt the user to logon (since we're configured for FORM based auth)
			if(request.authenticate(response)) {
				getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
			}
			else {
				getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
			}
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestURI = request.getRequestURI();
		System.out.println(requestURI); 
		String url = "";
		
		// Register a new user
		if(requestURI.endsWith("register")){
			// If we were able to add the user then redirect them back to the main page
			if(registerUser(request)) { getServletContext().getRequestDispatcher("/index.jsp").forward(request, response); }
			
			else { getServletContext().getRequestDispatcher("/user/registerError.jsp").forward(request, response); }
		}
		
		// View the details for a specific item
		else if(requestURI.endsWith("viewItemDetails")){

			// Get the itemID and add it to the session
			HttpSession session = request.getSession(true);
			InventoryItem item = inventoryDB.getInventoryItem(Integer.parseInt((String)request.getParameter("itemID")));
			session.setAttribute("currentItem", item);
			
			// Redirect the user to the inventoryItem view
			getServletContext().getRequestDispatcher("/shopping/catalogItem.jsp").forward(request, response);
		}	
		
		// Add an item to the cart
		else if(requestURI.endsWith("addItemToCart")){

			HttpSession session = request.getSession(true);
			
			if(addItemToCart(request)) {
				getServletContext().getRequestDispatcher("/shopping/cart.jsp").forward(request, response);
			}
			else {
				session.setAttribute("errorMsg", "Unable to add item to cart.");
				getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
			}
		}
		
		// Remove an item from the cart
		else if(requestURI.endsWith("removeItemFromCart")){

			HttpSession session = request.getSession(true);
			
			if(removeItemFromCart(request)) {
				getServletContext().getRequestDispatcher("/shopping/cart.jsp").forward(request, response);
			}
			else {
				session.setAttribute("errorMsg", "Unable to add item to cart.");
				getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
			}
		}	
		
		// Allow the user to place an order
		else if(requestURI.endsWith("processOrder")){

			HttpSession session = request.getSession(true);
			
			// TODO: pull the cart items from the session, add all of the transaction details to the database,
			// clear the cart, send the user to the orderProcessed.jsp page
			
			getServletContext().getRequestDispatcher("/shopping/orderProcessed.jsp").forward(request, response);
		}
		
		// Allow the user to modify their account details (EXCEPT for the isAdmin flag, which can only be changed
		// from the admin page.
		else if(requestURI.endsWith("modifyUser")){
			
			int userID = Integer.parseInt((String)request.getParameter("userID"));
			User user = userDB.getUser(userID);
			user.setEmail((String)request.getParameter("mail"));
			user.setUserName((String)request.getParameter("name"));
			user.setfName((String)request.getParameter("fmail"));
			user.setlName((String)request.getParameter("lmail"));
			
			// TODO: update the user's properties.
			
			getServletContext().getRequestDispatcher("/admin/users.jsp").forward(request, response);
		}
		
		// Allow the user to add a rating for the specified item.
		else if(requestURI.endsWith("addRating")){
			
			InventoryItem item = inventoryDB.getInventoryItem(Integer.parseInt((String)request.getParameter("itemID")));
			
			// TODO: update the user's properties.
			
			getServletContext().getRequestDispatcher("/admin/users.jsp").forward(request, response);
		}		
	}
	
	private Boolean registerUser(HttpServletRequest request){
		
		HttpSession session = request.getSession(true);
		  
		String username = request.getParameter("username");
		String passwd = request.getParameter("passwd");
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		User user = new User();
		user.setUserName(username);
		user.setPassword(passwd);
		user.setEmail(email);
		user.setfName(name);
		user.setlName("");
		
		if(userDB.insertUser(user)) {
			session.setAttribute("currentUser", user);
			return true;
		}
		else {
			return false;
		}
	}
	
	private Boolean addItemToCart(HttpServletRequest request){
		
		HttpSession session = request.getSession(true);
		int itemID = Integer.parseInt(request.getParameter("itemID"));
		int itemQuantity = Integer.parseInt(request.getParameter("itemQuantity"));
		
		// Get the shopping cart (create if needed)
		Map<Integer, Integer> shoppingCart = (Map<Integer, Integer>) session.getAttribute("shoppingCart");
		if(shoppingCart == null) {
			shoppingCart = new HashMap<Integer, Integer>();
			session.setAttribute("shoppingCart", shoppingCart);
		}
		
		// If the parameters are valid then add the item to the cart
		if(itemID > 0 && itemQuantity > 0) {

			// If this item is already in the cart then increase the quantity
			int count = 0;
			if(shoppingCart.containsKey(itemID)) { count = shoppingCart.get(itemID); }
			count += itemQuantity;
			shoppingCart.put(itemID, count);
			return true;
		}
		
		return false;
	}	
	
	private Boolean removeItemFromCart(HttpServletRequest request){
		
		HttpSession session = request.getSession(true);
		int itemID = Integer.parseInt((String)request.getParameter("itemID"));
		
		// Get the shopping cart (create if needed)
		Map<Integer, Integer> shoppingCart = (Map<Integer, Integer>) session.getAttribute("shoppingCart");
		if(shoppingCart == null) {
			shoppingCart = new HashMap<Integer, Integer>();
			session.setAttribute("shoppingCart", shoppingCart);
		}

		// If the parameters are valid then add the item to the cart
		if(itemID > 0 && shoppingCart.containsKey(itemID)) {
			shoppingCart.remove(itemID);
			return true;
		}		
		
		return false;
	}
}
