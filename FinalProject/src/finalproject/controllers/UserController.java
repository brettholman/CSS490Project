package finalproject.controllers;

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
		
		if(requestURI.endsWith("register")){
			// If we were able to add the user then redirect them back to the main page
			if(registerUser(request)) { getServletContext().getRequestDispatcher("/index.jsp").forward(request, response); }
			
			else { getServletContext().getRequestDispatcher("/user/registerError.jsp").forward(request, response); }
		}
		
		//String name = request.getParameter("name");
		//String mail = request.getParameter("mail");
		//Boolean isAdmin = Boolean.parseBoolean(request.getParameter("isAdmin"));
		
		//User user = new User();
		//user.setName(name);
		//user.setMail(mail);
		//user.setIsAdmin(isAdmin);
		
		//if(user.isValid() && user.getIsAdmin()) { getServletContext().getRequestDispatcher("/./admin/index.jsp").forward(request,  response); }
		//else if(user.isValid()) { getServletContext().getRequestDispatcher("/./shopping/index.jsp").forward(request,  response); }
		//else { getServletContext().getRequestDispatcher("/index.jsp").forward(request,  response); }
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
