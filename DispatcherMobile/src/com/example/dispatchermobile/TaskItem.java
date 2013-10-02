package com.example.dispatchermobile;

public class TaskItem 
{
	
	private String companyName;
	private String deliveryTime;
	private String address;
	private String comment;
	private String lastStatus;
	private String lastStatusDate;
	private String driverName;
	
	public TaskItem(String companyName, String deliveryTime, String address, String comment, String lastStatus, String lastStatusDate, String driverName)
	{
		this.companyName = companyName;
		this.deliveryTime = deliveryTime;
		this.address = address;
		this.comment = comment;
		this.lastStatus = lastStatus;
		this.lastStatusDate = lastStatusDate;
		this.driverName = driverName;
	}
	
	public String getCompanyName()
	{	
		return companyName;	
	}
	public String getDeliveryTime()
	{	
		return deliveryTime;	
	}
	public String getAddress()
	{	
		return address;	
	}
	public String getComment()
	{	
		return comment;	
	}
	public String getLastStatus()
	{	
		return lastStatus;	
	}
	public String getLastStatusDate()
	{	
		return lastStatusDate;	
	}
	public String getDriverName()
	{
		return driverName;
	}
	
	
		
	@Override
	public String toString()
	{		
		String result = getCompanyName() 
				+ "\n" + getDeliveryTime()
				+ "\n" + getAddress()
				+ "\n" + getComment();
		return result;
	}
	
}