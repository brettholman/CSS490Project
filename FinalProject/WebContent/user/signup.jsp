<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_home.jsp" />

<!-- start the middle column -->

<section>
	<form action="/UserController/register" method="post">
		<fieldset>
			<div><span class="star"><label> Username : </label>
				<input type="text" name="username" id="username" required="required"/> *</span></div>
			<br>
			
			<div><span class="star"><label> Password : </label>
				<input type="password" name="passwd" id="passwd"  required="required"/> *</span></div>
			<br>
			
			<div><span class="star"><label> Confirm Password : </label>
				<input type="password" name="repasswd" id="repasswd" required="required"/> *</span></div>
			<br>
			
			<div><span><label> Name : </label>
				<input type="text" name="name" /></span></div>
			<br>
			
			<div><span><label> Email : </label>
				<input type="text" name="email" id="email"/></span></div>
			<br>
		
			<div><span>* Mandatory Information</div>
			
			<br>
			<div id="warning" class="feedback"></div>
			<input type="submit" value="Sign Up" id="submit"/>
		</fieldset>

		<br/>
	</form>
	<script>
	 	var elMsg = document.getElementById('warning');
	 
		function loginCheck(){
	       if(document.getElementById('username').value==""){
	               elMsg.textContent= "Pleaes enter your username";
	               document.getElementById('username').focus();
	               return false;
	       }
	       if(document.getElementById('passwd').value==""){
	             	elMsg.textContent = "Please enter your password";
	               document.getElementById('passwd').focus();
	               return false;
	       }
	       if(document.getElementById('repasswd').value==""){
	       	elMsg.textContent = "Please confirm your password";
	              document.getElementById('repasswd').focus();
	              return false;
	       }
	       if(document.getElementById('passwd').value != document.getElementById('repasswd').value){
	              elMsg.textContent = "The new password and confirmation password do not match.";
	              document.getElementById('passwd').focus();
	              return false;
	      	}
	       if(document.getElementById('email').value==""){
	       	elMsg.textContent = "Please enter your email";
	              document.getElementById('email').focus();
	              return false;
	       }
		}
		
		var elSubmit = document.getElementById('submit');
		elSubmit.onclick = loginCheck;
	</script>
</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />