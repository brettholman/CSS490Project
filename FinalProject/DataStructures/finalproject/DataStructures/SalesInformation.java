package finalproject.DataStructures;

public class SalesInformation {
	private double currentTimePeriod;
	private double previousTimePeriod;
	// Different is meant to represent the 
	private double difference;
	
	public SalesInformation()
	{
		this(0, 0);
	}
	
	public SalesInformation(double current, double previous)
	{
		currentTimePeriod = current;
		previousTimePeriod = previous;
		difference = current - previous;
	}
	
	public double GetCurrentTimePeriod() {
		return this.currentTimePeriod;
	}
	
	public void SetCurrentTimePeriod(double amount) {
		this.currentTimePeriod = amount;
	}
	
	public double GetPreviousTimePeriod() {
		return this.previousTimePeriod;
	}
	
	public void SetPreviousTimePeriod(double amount) {
		this.previousTimePeriod = amount;
	}
	
	public double GetDifference() {
		return this.difference;
	}
	
	public void SetDifference(double amount) {
		this.difference = amount;
	}
	
}
