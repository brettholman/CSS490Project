package finalproject.models;

import java.sql.Date;

public class User {
	private int id = -1;
	private String userName = null;
	private String fName = null;
	private String lName = null;
	private String email = null;
	private String password = null;
	private Date lastLogin = null;
	private Date accountCreated = null;
	private int totalPurchased = 0;
	
	public Boolean isValid()
	{
		return (userName != null && fName != null 
				&& lName != null && email != null);
	}
	
	public String getUserName() {
		if(userName == null || userName.length() == 0)
			return "";
		return userName;
	}
	
	public void setUserName(String uName) {
		this.userName = uName;
	}
	
	public String getfName() {
		if(fName == null || fName.length() == 0)
			return "";
		return fName;
	}
	
	public void setfName(String name) {
		this.fName = name;
	}
	
	public void setlName(String name) {
		this.lName = name;
	}

	public String getlName() {
		if(lName == null || lName.length() == 0)
			return "";
		return lName;
	}
	
	public String getEmail() {
		if(email == null || email.length() == 0)
			return "";
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setTotalPurchased(int num)
	{
		this.totalPurchased = num;
	}
	
	public int getTotalPurchased()
	{
		return this.totalPurchased;
	}
}
