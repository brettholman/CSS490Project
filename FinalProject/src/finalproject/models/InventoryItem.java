package finalproject.models;

import java.sql.Date;

public class InventoryItem {
	
	private int id;
	private String title;
	private String author;
	private String description;
	private int quantityInStock;
	private double price; // Price for customer
	private double cost; // cost for store to buy
	private int categoryID;
	private String category;
	private double averageRating;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getQuantityInStock() {
		return quantityInStock;
	}
	
	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getCost() {
		return cost;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public int getCategoryID() {
		return categoryID;
	}
	
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCategory() 
	{
		return this.category;
	}
	
	public void setCategory(String cat) 
	{
		this.category = cat;
	}
	
	public double getAverageRating()
	{
		return this.averageRating;
	}
	
	public void setAverageRating(double avg)
	{
		this.averageRating = avg;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
