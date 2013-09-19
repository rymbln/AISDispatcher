package com.example.dispatchermobile;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
	
	public static ArrayList<TaskItem> getTaskItems(String _sourceUrl)
	{
		ArrayList<TaskItem> _taskItems = new ArrayList<TaskItem>();
		
		try
		{
			   			
			// opening connection for downloading list of tasks by xml data
			URL _url = new URL(_sourceUrl);
				
			URLConnection _conn = _url.openConnection();
			_conn.setRequestProperty("Accept", "application/xml");
            DocumentBuilderFactory _factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder _builder = _factory.newDocumentBuilder();
            Document _doc = _builder.parse(_conn.getInputStream());
			
            NodeList _nl = _doc.getElementsByTagName("item");
			
				if (_nl.getLength() > 0)
				{
					for (int i = 0; i < _nl.getLength(); i++)
					{
						//reading tags for each task item between tags <item></item>
						Element _item = (Element) _nl.item(i);
						
						String _cn = ((Element) _item.getElementsByTagName("CompanyName").item(0)).getFirstChild().getNodeValue();
						String _dt = ((Element) _item.getElementsByTagName("DeliveryTime").item(0)).getFirstChild().getNodeValue();
						String _ad = ((Element) _item.getElementsByTagName("Address").item(0)).getFirstChild().getNodeValue();
						String _cm = ((Element) _item.getElementsByTagName("Comment").item(0)).getFirstChild().getNodeValue();
						String _ls = ((Element) _item.getElementsByTagName("LastStatus").item(0)).getFirstChild().getNodeValue();
						String _ld = ((Element) _item.getElementsByTagName("LastStatusDate").item(0)).getFirstChild().getNodeValue();
						String _dn = ((Element) _item.getElementsByTagName("DriverName").item(0)).getFirstChild().getNodeValue();
						
						// Create TaskItem object and add to ArrayList
						TaskItem _taskItem = new TaskItem(_cn, _dt, _ad, _cm, _ls, _ld, _dn);
						_taskItems.add(_taskItem);
						
						
					}
				}
				
			//}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return _taskItems;
		}
	
}