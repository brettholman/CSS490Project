package finalproject.models;

public class User {
	private String name = null;
	private String password = null;
	private String email = null;
	
	public Boolean isValid()
	{
		return (name != null && email != null);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
}
