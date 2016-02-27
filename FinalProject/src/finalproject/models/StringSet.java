package finalproject.models;

import java.util.ArrayList;

public class StringSet {
	private String currentValue;
	private String previousValue;
	private String twoValuesBack;
	private String threeValuesBack;
	private String fourValuesBack;
	
	public StringSet(ArrayList<String> values)
	{
		this.currentValue = values.get(0);
		this.previousValue = values.get(1);
		this.twoValuesBack = values.get(2);
		this.threeValuesBack = values.get(3);
		this.fourValuesBack = values.get(4);
	}
	
	public String getCurrentValue()
	{
		return this.currentValue;
	}
	
	public String getPreviousValue()
	{
		return this.previousValue;
	}
	
	public String getTwoValuesBack()
	{
		return this.twoValuesBack;
	}
	
	public String getThreeValuesBack()
	{
		return this.threeValuesBack;
	}
	
	public String getFourValuesBack()
	{
		return this.fourValuesBack;
	}
}
