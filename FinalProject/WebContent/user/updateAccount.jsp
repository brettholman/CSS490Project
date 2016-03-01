<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_home.jsp" />

<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<!-- start the middle column -->

<%
	User user = (User)session.getAttribute("currentUser");
%>

<section>
<form action="/UserController/modifyUser" method="post">
		<fieldset>
			<label for="userName">User Name</label>
  			<input type="text" name="userName" id="userName" value="<%=user.getUserName()%>"><br>	

			<label for="fName">First Name</label>
  			<input type="text" name="fName" id="fName" value="<%=user.getfName()%>"><br>
		
			<label for="lName">Last Name</label>
  			<input type="text" name="lName" id="lName" value="<%=user.getlName()%>"><br>
  			
			<label for="email">Email</label>
  			<input type="text" name="email" id="email" value="<%=user.getEmail()%>"><br>
		
			<label for="nPass">New Password</label>
  			<input type="text" name="nPass" id="nPass"><br>
  			
			<label for="cPass">Confirm Password</label>
  			<input type="text" name="cPass" id="cPass"><br>
  			
			<div id="warning" class="feedback"></div>
			<input type="submit" value="Save" id="submit"/>
  			
  			<input type="hidden" name="userID" id="userID" value="<%=user.getId()%>">
		</fieldset>

		<br/>
	</form>

	<script>
	 	var elMsg = document.getElementById('warning');
	 	
   		function checkForm() { 
	  		if (this.value.length < 5) { // If title too short
	  			elMsg.textContent = 'User Name must be 5 or more characters.'; // Set msg
	  		} 			
			// check the first name
			else if(document.getElementById('fName').value.length <= 0) {
				elMsg.textContent = 'First Name must be 1 or more characters.'; // Set msg
			}

			// check the last name
			else if(document.getElementById('lName').value.length <= 0) {
				elMsg.textContent = 'Last Name must be 1 or more characters.'; // Set msg
			}
			
			// check the email
			// 5 characters. 1 for the '@' and the 4 for the domain '.com'
			else if(document.getElementById('email').value.length <= 5) {
				// If we wanted to get fancy, I would add some regex checking here. But im not fancy. 
				elMsg.textContent = 'First Name must be 5 or more characters.'; // Set msg
			}
	  	}
   		
   		function checkPasswords() {
	  		var cPass = document.getElementById("cPass").value;
	  		var nPass = document.getElementById("nPass").value;
	  		if(cPass != nPass) {
	  			elMsg.textContent = 'Passwords do not match'; // Set msg
	  			document.getElementByIf('submit').enabled = false;
	  		}
	  		else {
	  			document.getElementByIf('submit').enabled = true;
	  			elMsg.textContent = null; // Clear msg
	  		}
   		}
   		
   		function clearPasswordMsg() {
	  		var cPass = document.getElementById("cPass").value;
	  		var nPass = document.getElementById("nPass").value;
	  		if(cPass == nPass) 
	  			elMsg.textContent = ""; // Clear msg
   		}
	 	
		function saveItemCheck() {

			// Check the user name
			if(document.getElementById('userName').value.length < 5) {
				return false;
			}
			
			// check the first name
			else if(document.getElementById('fName').value.length <= 0) {
				return false;
			}

			// check the last name
			else if(document.getElementById('lName').value.length <= 0) {
				return false;
			}
			
			// check the email
			// (Regex) here too. 
			else if(document.getElementById('email').value.length <= 0) {
				return false;
			}
			// double check the passwords
			else if(document.getElementById('nPass').value != document.getElementById('cPass').value) {
				return false;
			}
		}
		
		document.getElementById('userName').onblur = checkForm;
		document.getElementById('fName').onblur = checkForm;
		document.getElementById('lName').onblur = checkForm;
		document.getElementById('email').onblur = checkForm;
		document.getElementById('cPass').onblur = checkPasswords;
		document.getElementById('nPass').onblur = clearPasswordMsg;
		
		var elSubmit = document.getElementById('submit');
		elSubmit.onclick = saveItemCheck;
	</script>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />