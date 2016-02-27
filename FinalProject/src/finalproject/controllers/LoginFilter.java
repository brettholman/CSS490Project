package finalproject.controllers;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import finalproject.data.userDB;
import finalproject.models.User;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
	
    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        
		// Found this code here: http://stackoverflow.com/questions/14758429/accessing-user-details-after-logging-in-with-java-ee-form-authentication/14758549#14758549
		HttpServletRequest request = (HttpServletRequest) req;
        String remoteUser = request.getRemoteUser();

    	// Now check the session to see if we've already added the user to the session
        HttpSession session = request.getSession();

        // Setup the default stuff for the catalog toolbar
		try { int categoryID = (int)session.getAttribute("categoryID"); } catch(Exception e){ session.setAttribute("categoryID", 0); }
		
		String searchText = (String)session.getAttribute("searchText");        
		if(searchText == null) { session.setAttribute("searchText", ""); }	
        
        // Is the user logged in already via j_security_check?
        if (remoteUser != null) {
        	
            // If not, add the user to the session now
        	User user = (User)session.getAttribute("currentUser");
            if(user == null || user.getUserName() == "Anonymous") {
            	user = userDB.getUser(remoteUser);
                session.setAttribute("currentUser", user);
            }
        }

		// pass the request along the filter chain
		chain.doFilter(req, res);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
}
