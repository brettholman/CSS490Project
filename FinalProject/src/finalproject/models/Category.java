package finalproject.models;

public class Category {
	private int id;
	private String categoryName;
	
	public void setId(int num) {
		this.id = num;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getCategoryName() {
		return this.categoryName;
	}
	
	public void setCategoryName(String name) {
		this.categoryName = name;
	}
}
