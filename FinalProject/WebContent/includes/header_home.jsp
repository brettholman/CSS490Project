<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>

<%
	// Get the user if it exists, otherwise create an anonymous user
	User user = (User)session.getAttribute("currentUser");
	if(user == null) { 
		user = userDB.getUser(""); 
		session.setAttribute("currentUser", user); 
	}
	System.out.println("User = " + user.getUserName());
%>

<html>

<script>
	function pageLoaded() {
	   var username = "<%=user.getUserName()%>";
       if(username === 'Anonymous') {
    	   document.getElementById("btnLogout").style.display="none";  
       }
       else {
    	   document.getElementById("btnLogon").style.display="none";  
       }
	}
</script>

<head>
    <meta charset="utf-8">
    <title>The Bookstore</title>
    <link rel="shortcut icon" href="<c:url value='/images/favicon.ico'/>">
    <link rel="stylesheet" href="<c:url value='/css/main.css'/> ">
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
</head>

<body onload="pageLoaded()">
    
    <nav id="nav_bar">
	    <div id="logo">
		    <header>
		        <img src="<c:url value='/images/logo.jpg'/>" 
		             alt="The Bookstore Logo" width="58">
		        <h1>The Bookstore</h1>
		        <h2>Nunc semper odio eu augue porta egestas!</h2>
		    </header>
	    </div>
	    <div id="user">
	    	<span>
		    	Welcome <%=user.getUserName()%>!
				<a href="/user/logon.jsp"><button id="btnLogon" name="logonButton">Logon</button></a>
				<a href="/user/logout.jsp"><button id="btnLogout" name="logoutButton">Logout</button></a>
	    	</span>
	    </div>
    </nav>
    