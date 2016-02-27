package finalproject.models;

import java.sql.Date;

public class Rating {
	private int id;
	private String userName;
	private int bookId;
	private int rating;
	private Date ratingDate;
	private String description; // This may end up getting taken out. 
	
	public int getID() {
		return this.id;
	}
	
	public void setID(int num) {
		this.id = num;
	}
	
	public String getuUserName() {
		return this.userName;
	}
	
	public void setUserName(String name) {
		this.userName = name;
	}
	
	public int getBookId() {
		return this.bookId;
	}
	
	public void setBookID(int num) {
		this.bookId= num;
	}
	
	public int getRating() {
		return this.rating;
	}
	
	public void setRating(int num) {
		this.rating = num;
	}
	
	public Date getRatingDate() {
		return this.ratingDate;
	}
	
	public void setRatingDate(Date date) {
		this.ratingDate = date;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String desc) {
		this.description = desc;
	}
}
