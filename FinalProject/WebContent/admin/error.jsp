<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<!-- start the middle column -->

<section>

	<%
		String errorMsg = (String)session.getAttribute("errorMsg");
	%>

	<h1>Error</h1>

	<br>
	<p><%=errorMsg%></p>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />