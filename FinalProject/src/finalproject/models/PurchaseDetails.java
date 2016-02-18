package finalproject.models;

public class PurchaseDetails {
	private int orderNumber;
	private int bookId;
	private int quantity;
	
	public void setOrderNumber(int num) {
		this.orderNumber = num;
	}
	
	public int getOrderNumber() {
		return this.orderNumber;
	}
	
	public void setBookID(int num) {
		this.bookId = num;
	}
	
	public int getOBookID() {
		return this.bookId;
	}
	
	public void setQuantity(int num) {
		this.quantity = num;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
}
