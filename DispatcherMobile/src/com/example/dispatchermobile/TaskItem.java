package com.example.dispatchermobile;

import org.json.JSONException;
import org.json.JSONObject;

public class TaskItem
{
	
	private String companyName;
	private String deliveryTime;
	private String address;
	private String comment;
	private String lastStatus;
	private String lastStatusDate;
	private String driverName;

    public TaskItem()
    {
        this.companyName = "";
        this.deliveryTime = "";
        this.address = "";
        this.comment = "";
        this.lastStatus = "";
        this.lastStatusDate = "";
        this.driverName = "";
    }

    public TaskItem(String _str)
    {
        try {
            JSONObject _jo = new JSONObject(_str);
            this.companyName = _jo.optString("companyName");
            this.deliveryTime = _jo.optString("deliveryTime");;
            this.address = _jo.optString("address");;
            this.comment = _jo.optString("comment");;
            this.lastStatus = _jo.optString("lastStatus");
            this.lastStatusDate = _jo.optString("lastStatusDate");;
            this.driverName = _jo.optString("driverName");;
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

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

    public JSONObject toJSON()
    {
        JSONObject _jo = new JSONObject();

        try {
            _jo.put("companyName",companyName);
            _jo.put("deliveryTime",deliveryTime);
            _jo.put("address",address);
            _jo.put("comment",comment);
            _jo.put("lastStatus",lastStatus);
            _jo.put("lastStatusDate",lastStatusDate);
            _jo.put("driverName",driverName);
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return _jo;
    }
	
}