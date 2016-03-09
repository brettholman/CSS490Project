<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<section id="catalogToolbar" class="pagePart">

	<%
		int topCount = 5;
		if(session.getAttribute("topCount")	!= null) { topCount = (int)session.getAttribute("topCount"); }

		int categoryID = -1;
		if(session.getAttribute("categoryID") != null) {
			categoryID = (int)session.getAttribute("categoryID");
			if(categoryID == 0) { categoryID = -1; }
		}
		
		int timeframe = 0;
		if(session.getAttribute("timeframe") != null) {
			timeframe = (int)session.getAttribute("timeframe");
		}
	%>

    <form method="post" name="form1" id="form1" action="/AdminController/bestSellerFilter">

	  Top:<select id="topCount" name="topCount">
      	<option value='5' <% if(topCount == 5) { %>selected<% } %>>5</option>
      	<option value='10' <% if(topCount == 10) { %>selected<% } %>>10</option>
  	  </select>
	  
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

	  Timeframe:<select id="timeframe" name="timeframe">
      	<option value='0' <% if(timeframe == 0) { %>selected<% } %>>All Time</option>
      	<option value='1' <% if(timeframe == 1) { %>selected<% } %>>Last Month</option>
      	<option value='2' <% if(timeframe == 2) { %>selected<% } %>>Last 2 Weeks</option>
      	<option value='3' <% if(timeframe == 3) { %>selected<% } %>>Last Week</option>
  	  </select>

  	  <input type="hidden" name="sourceID" id="sourceID">
      <input type="submit" id="submit" value="Go"/>

    </form>

</section>