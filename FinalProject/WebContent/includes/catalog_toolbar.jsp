<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<section id="catalogToolbar" class="pagePart">

	<%
	String searchText = (String)session.getAttribute("searchText");
	String categoryText = (String)session.getAttribute("categoryText");
	%>

    <form method="post" name="form1" id="form1" action="/UserController/filter">

      Search:<input type="text" id="searchText" name="searchText" value=""/>

      Category:<select id="categoryText" name="categoryText">
      	<option value="*">All</option>

		<%
			Category[] categories = categoryDB.getAllCategories();
			for(Category cat: categories){
		%>
		<option <% if(categoryText.equalsIgnoreCase(cat.getCategoryName())) { %>selected<% } %>
	  		value='<%=cat.getCategoryName()%>'><%=cat.getCategoryName()%></option>
		<% } %>   	

  	  </select>

  	  <input type="hidden" name="sourceID" id="sourceID">
      <input type="submit" id="submit" value="Go"/>

    </form>

</section>