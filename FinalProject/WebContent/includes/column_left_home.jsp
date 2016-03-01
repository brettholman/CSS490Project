<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>
<aside id="sidebarA">
    <nav>
      <ul>
          <li><a href="/">Home</a></li>
          <li><a href="/shopping/catalog.jsp">Browse Catalog</a></li>
          <li><a href="/shopping/cart.jsp">View Cart</a></li>
          <% 
          		User user = (User)session.getAttribute("currentUser");
          		if(user != null && !user.getUserName().equals("Anonymous")){          		
          %>
          <li><a href="/user/updateAccount.jsp">Modify Account</a></li>
          <% 	}%>
          <li><a href="/admin">Administration</a></li>
      </ul>
    </nav>
</aside>