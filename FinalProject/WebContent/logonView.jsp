<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head><title>Sign Up Form</title>
		<link rel="stylesheet" href="css/logon.css" />
	</head>
	<body>
		<form action="UserController" method="post">
			<fieldset>
				<legend class="legend_text"> Your Login Info !!!</legend>
				<div><span class="star">*</span><label> Username : </label>
					<input type="text" name="username" id="username" required="required"/></div>
				<div><span class="star">*</span><label>  Email: </label>
					<input type="text" name="email" id="email"/></div>
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
	</body>
</html>