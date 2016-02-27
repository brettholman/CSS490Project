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
	
	public Boolean isValid()
	{
		return (userName != null && fName != null 
				&& lName != null && email != null);
	}
	
	public void setID(int id)
	{
		this.id = id;
	}
	
	public int getID()
	{
		return this.id;
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
	
	public void setlName(String name) {
		this.lName = name;
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
}
