package finalproject.models;

public class User {
	private String name = null;
	private String mail = null;
	private Boolean isAdmin = false;
	
	public Boolean isValid()
	{
		return (name != null && mail != null);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
