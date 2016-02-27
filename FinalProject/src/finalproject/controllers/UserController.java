package finalproject.controllers;

import finalproject.DataStructures.Quad;
import finalproject.DataStructures.cartItem;
import finalproject.data.inventoryDB;
import finalproject.data.transactionDB;
import finalproject.data.userDB;
import finalproject.models.*;
import javafx.util.Pair;

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

			HttpSession session = request.getSession(true);

			int sourceID = 0;
			Map<String, String[]> map = request.getParameterMap();
			if(map.containsKey("sourceID")) { sourceID = Integer.parseInt((String)request.getParameter("sourceID")); } 
			
			// Prompt the user to logon (since we're configured for FORM based auth)
			if(request.authenticate(response)) {
				
				switch(sourceID) {
					case 1:
						getServletContext().getRequestDispatcher("/shopping/checkout.jsp").forward(request, response);
						break;
						
					default:
						getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
						break;
				}
			}
			else {
				switch(sourceID) {
					case 1:
						session.setAttribute("errorMsg", "You must be logged on in order to place an order.");
						getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
						break;
						
					default:
						getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
						break;
				}
			}
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestURI = request.getRequestURI();
		//System.out.println(requestURI); 
		String url = "";
		
		// Register a new user
		if(requestURI.endsWith("register")){
			// If we were able to add the user then redirect them back to the main page
			if(registerUser(request)) { getServletContext().getRequestDispatcher("/index.jsp").forward(request, response); }
			
			else { getServletContext().getRequestDispatcher("/user/registerError.jsp").forward(request, response); }
		}
		
		// Handle the filter for the catalog and inventory management views
		else if(requestURI.endsWith("filter")) {
			
			HttpSession session = request.getSession(true);

			int sourceID = Integer.parseInt(request.getParameter("sourceID"));
			
			String categoryText = request.getParameter("categoryText");
			session.setAttribute("categoryText", categoryText); 
			
			String searchText = request.getParameter("searchText");
			session.setAttribute("searchText", searchText); 
			
			switch(sourceID) {
				case 1:
					getServletContext().getRequestDispatcher("/shopping/catalog.jsp").forward(request, response);
					break;
					
				default:
					getServletContext().getRequestDispatcher("/admin/inventory.jsp").forward(request, response);
					break;
			}			
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
			
			if(processOrder(request)) {
				getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
			}
			else {
				session.setAttribute("errorMsg", "Unable to process order.");
				getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
			}			
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
	
	private Boolean processOrder(HttpServletRequest request){
		
		HttpSession session = request.getSession(true);

		Map<Integer, Integer> shoppingCart = (Map<Integer, Integer>) session.getAttribute("shoppingCart");
		double validateTotalCost = (double)session.getAttribute("orderTotal");
		int userID = Integer.parseInt((String)request.getParameter("userID"));
		if(shoppingCart == null) { return false; }
		
		ArrayList<Pair<InventoryItem, Integer>> allItems = new ArrayList<Pair<InventoryItem, Integer>>();
		
		double totalCost = 0;
		
		// Will have to loop through this twice, first time to validate. 
		for (Map.Entry<Integer, Integer> entry : shoppingCart.entrySet()) {
			int itemID = entry.getKey();
			int quantity = entry.getValue();
			InventoryItem item = inventoryDB.getInventoryItem(itemID);
			
			allItems.add(new Pair<InventoryItem, Integer>(item, quantity));
			
			if((item.getQuantityInStock() - quantity) < 0)
			{
				return false;
			}
			
			totalCost += (item.getPrice() * quantity);
		}
		// This seems a little odd, since its the exact same calculation, but just to be totally sure its right. 
		if(totalCost != validateTotalCost)
			return false;
		
		int transactionID = transactionDB.InsertTransaction(userID, totalCost);
		
		// Transaction failed
		if(transactionID == -1)
			return false;
		
		// Second time to update the DB. 
		for (Pair<InventoryItem, Integer> val : allItems) {
			InventoryItem item = val.getKey();
			int quantity = val.getValue();
			
			transactionDB.InsertPurchaseDetail(transactionID, item.getId(), quantity);
			
			// Update the DB's item quantity.
			item.setQuantityInStock(item.getQuantityInStock() - quantity);
			inventoryDB.updateItem(item);
		}
		
		// Clear the shopping cart
		shoppingCart = new HashMap<Integer, Integer>();
		session.setAttribute("shoppingCart", shoppingCart);
		return true;
	}	
}
