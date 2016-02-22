package finalproject.controllers;

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
		
		// Register a new user
		if(requestURI.endsWith("register")){
			// If we were able to add the user then redirect them back to the main page
			if(registerUser(request)) { getServletContext().getRequestDispatcher("/index.jsp").forward(request, response); }
			
			else { getServletContext().getRequestDispatcher("/user/registerError.jsp").forward(request, response); }
		}
		
		// Log the user on
		else if(requestURI.endsWith("logon")){
			
			HttpSession session = request.getSession(true);
			String name = (String)request.getParameter("username");
			String password = (String)request.getParameter("password");
			
			// ADD: need to check and see if the user provided the correct logon and password
			
			// TEMP: for now, just create and return the user object
			User user = new User();
			user.setUserName(name);
			user.setPassword(password);
			session.setAttribute("currentUser", user);
			
			// If we were able to add the user then redirect them back to the main page
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		}	
		
		// View the details for a specific item
		else if(requestURI.endsWith("viewItemDetails")){

			// Get the itemID and add it to the session
			HttpSession session = request.getSession(true);
			int itemID = Integer.parseInt((String)request.getParameter("itemID"));
			session.setAttribute("currentItem", itemID);
			
			// Redirect the user to the inventoryItem view
			getServletContext().getRequestDispatcher("/shopping/catalogItem.jsp").forward(request, response);
		}
		
		// Add an item to the cart
		else if(requestURI.endsWith("addItemToCart")){

			HttpSession session = request.getSession(true);
			int itemID = Integer.parseInt((String)request.getParameter("itemID"));
			
			// TODO: add the itemID to the cart (need to track the quantity too - if the item is already in
			//   the cart, then just increment the quantity.
			
			// Verify that we have quantity in stock before allowing the item to be added to the cart
			
			getServletContext().getRequestDispatcher("/shopping/cart.jsp").forward(request, response);
		}
		
		// Remove an item from the cart
		else if(requestURI.endsWith("removeItemFromCart")){

			HttpSession session = request.getSession(true);
			
			int itemID = Integer.parseInt((String)request.getParameter("itemID"));
			int itemQuantity = Integer.parseInt((String)request.getParameter("itemQuantity"));
			
			// TODO: remove the specified number of items for the itemID from the cart
			
			getServletContext().getRequestDispatcher("/shopping/cart.jsp").forward(request, response);
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
	}
	
	private Boolean registerUser(HttpServletRequest request){
		
		HttpSession session = request.getSession(true);
		  
		String username = request.getParameter("username");
		String passwd = request.getParameter("passwd");
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		// Temp code - for now just make the new user the current user
		User user = new User();
		user.setUserName(username);
		user.setPassword(passwd);
		user.setEmail(email);
		session.setAttribute("currentUser", user);

		return true;
	}	
}
