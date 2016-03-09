package finalproject.models;

import java.sql.Date;

public class SalesStatItem {
	
	private String title;
	private String author;
	private String category;
	private int categoryID;
	private int quantitySold;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getCategoryID() {
		return categoryID;
	}
	
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	
	public String getCategory() 
	{
		return this.category;
	}
	
	public void setCategory(String cat) 
	{
		this.category = cat;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getQuantitySold() {
		return quantitySold;
	}

	public void setQuantitySold(int quantitySold) {
		this.quantitySold = quantitySold;
	}
}
