<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_home.jsp" />

<!-- start the middle column -->

<section>

	<% 
		session.invalidate();
		response.setHeader("Pragma","no-cache"); 
		response.setHeader("Cache-Control","no-store"); 
		response.setHeader("Expires","0"); 
		response.setDateHeader("Expires",-1);
	%>
	<p>You have been successfully logged out</p>
	
	<a href="/index.jsp">Back</a>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />