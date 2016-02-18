package finalproject.models;

import java.sql.Date;

public class Transaction {
	private int OrderNumber;
	private String userName;
	private Date purchaseDate;
	
	public void setOrderNumber(int num) {
		this.OrderNumber = num;
	}
	
	public int getOrderNumber() {
		return this.OrderNumber;
	}
	
	public void setUserName(String name) {
		this.userName = name;
	}
	
	public String getUserName() {
		return this.userName;
	}

	public void setPurchaseDate(Date date) {
		this.purchaseDate = date;
	}
	
	public Date getPurchaseDate() {
		return this.purchaseDate;
	}
}
