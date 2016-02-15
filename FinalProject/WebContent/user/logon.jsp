<jsp:include page="/includes/header2.jsp" />

<!-- start the middle column -->

<section>

  <div id="page">

    <form method="post" name="form1" id="form1" action="/UserController/Login">

      <label for="username">Enter username: </label>
      <input type="text" id="username" name="username" required/>
      <div id="feedback1" class="feedback"></div>

      <br>
      <label for="password">Enter password: </label>
      <input type="password" id="password" name="password"  required/>
      <div id="feedback2" class="feedback"></div>

      <br>
      <br>
      <input type="submit" id="submit" value="Login"/>

    </form>
      
    </div>
    <script>
   		var elMsg = document.getElementById('feedback1'); 
   		var elMsg1 = document.getElementById('feedback2');
		
   		function checkUsername() {                            // Declare function
		  if (this.value.length < 5) {                        // If username too short
		    elMsg.textContent = 'Username must be 5 characters or more';  // Set msg
		  } else {                                            // Otherwise
			elMsg.textContent = '';                           // Clear message
		  }
		}
		 
		function loginCheck(){
	        if(document.getElementById('username').value==""){
	                elMsg.textContent= "Pleaes enter your LOGIN ID";
	                document.getElementById('username').focus();
	                return false;
	        }
	        if(document.getElementById('password').value==""){
	                elMsg1.textContent = "Please enter your PASSWORD";
	                document.getElementById('password').focus();
	                return false;
	        }
		}

		var elUsername = document.getElementById('username'); // Get username input
		elUsername.onblur = checkUsername;  // When it loses focus call checkuserName()
		
		var elSubmit = document.getElementById('submit');
		elSubmit.onclick = loginCheck;
	</script>

    <br>
    <span>Are you a new user?  Click here to sign up now!<a href="/user/signup.jsp" style="margin-left:5px;"><button name="signupButton">Sign up</button></a></span>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />