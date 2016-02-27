<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<section id="catalogToolbar" class="pagePart">

	<%
	String searchText = (String)session.getAttribute("searchText");
	int categoryID = (int)session.getAttribute("categoryID");
	%>

    <form method="post" name="form1" id="form1" action="/UserController/filter">

      Search:<input type="text" id="searchText" name="searchText" value=""/>

      Category:<select id="categoryID" name="categoryID">
      	<option value='0'>All</option>

		<%
			Category[] categories = categoryDB.getAllCategories();
			for(Category cat: categories){
		%>
		<option <% if(categoryID == cat.getId()) { %>selected<% } %>
	  		value='<%=cat.getId()%>'><%=cat.getCategoryName()%></option>
		<% } %>   	

  	  </select>

  	  <input type="hidden" name="sourceID" id="sourceID">
      <input type="submit" id="submit" value="Go"/>

    </form>

</section>