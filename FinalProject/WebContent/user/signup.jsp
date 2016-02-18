<jsp:include page="/includes/header_user.jsp" />
<jsp:include page="/includes/column_left_home.jsp" />

<!-- start the middle column -->

<link rel="stylesheet" href="<c:url value='/css/logon.css'/> ">

<section>
	<form action="/UserController/register" method="post">
		<fieldset>
			<legend class="legend_text"> Your Login Info !!!</legend>
			<div><span class="star">*</span><label> Username : </label>
				<input type="text" name="username" id="username" required="required"/></div>
			<br>
			<div><span class="star">*</span><label>  Password: </label>
				<input type="password" name="passwd" id="passwd"  required="required"/></div>
			<br>
			<div><span class="star">*</span><label>  Confirm Password:  </label>
				<input type="password" name="repasswd" id="repasswd" required="required"/></div>
			<br>
			<div><label>  Name: </label><input type="text" name="name" /></div>
			<div><span class="star">*</span>
			<br>
			<label>  Email: </label>
				<input type="text" name="email" id="email"/></div>
			<br>
			<div id="warning" class="feedback"></div>
			<input type="submit" value="Sign Up" id="submit"/>
		</fieldset>
		<div><span class="star">*</span><label>Mandatory Information</label></div><br/>
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
	elSubmit.onclick =loginCheck;
	</script>
</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />