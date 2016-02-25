<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<section id="catalogToolbar" class="pagePart">

    <form method="post" name="form1" id="form1" action="/UserController/filter">

      Search:<input type="text" id="searchText" name="searchText"/>

      Category:<select id="category" name="category">
<%
	Category[] categories = categoryDB.getAllCategories();
	for(Category cat: categories){
%>
		  <option value=<%=cat.getId()%>><%=cat.getCategoryName()%></option>
<%
	}
%>      
  	  </select>

      <input type="submit" id="submit" value="Go"/>

    </form>

</section>