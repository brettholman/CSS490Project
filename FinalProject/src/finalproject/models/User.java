package finalproject.models;

import java.sql.Date;

public class User {
	private String userName = null;
	private String fName = null;
	private String lName = null;
	private String email = null;
	private String password = null;
	private Boolean isAdmin = false;
	private Date lastLogin = null;
	private Date accountCreated = null;
	
	public Boolean isValid()
	{
		return (userName != null && fName != null 
				&& lName != null && email != null);
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String uName) {
		this.userName = uName;
	}
	
	public String getfName() {
		return fName;
	}
	
	public void setfName(String name) {
		this.fName = name;
	}

	public String getlName() {
		return lName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String mail) {
		this.email = mail;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getLastLogin() {
		return lastLogin;
	}
	
	public void setLastLogin(Date date) {
		this.lastLogin = date;
	}
	
	public Date getAccountCreated() {
		return accountCreated;
	}
	
	public void setAccountCreated(Date date) {
		this.accountCreated = date;
	}
	
	public boolean getIsAdmin() {
		return isAdmin;
	}
	
	public void setIsAdmin(boolean admin) {
		this.isAdmin = admin;
	}
	
}