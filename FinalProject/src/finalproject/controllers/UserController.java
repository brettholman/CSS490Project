package finalproject.controllers;

import finalproject.models.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
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
		String name = request.getParameter("name");
		String mail = request.getParameter("mail");
		Boolean isAdmin = Boolean.parseBoolean(request.getParameter("isAdmin"));
		
		User user = new User();
		user.setName(name);
		user.setMail(mail);
		user.setIsAdmin(isAdmin);
		
		if(user.isValid() && user.getIsAdmin()) { getServletContext().getRequestDispatcher("/./admin/index.jsp").forward(request,  response); }
		else if(user.isValid()) { getServletContext().getRequestDispatcher("/./shopping/index.jsp").forward(request,  response); }
		else { getServletContext().getRequestDispatcher("/index.jsp").forward(request,  response); }
	}
}
