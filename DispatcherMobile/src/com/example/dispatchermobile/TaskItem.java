package com.example.dispatchermobile;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;

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
			//opening localtest file
			   			
			// opening connection for downloading list of tasks by xml data
			URL _url = new URL(_sourceUrl);
//			HttpURLConnection _conn = (HttpURLConnection)_url.openConnection();
//			
//			if (_conn.getResponseCode() == HttpURLConnection.HTTP_OK)
//			{
//				InputStream _is = _conn.getInputStream();
//				
//				// Create DocumentBuilder for parsing xml data
//				DocumentBuilderFactory _dbf = DocumentBuilderFactory.newInstance();
//				DocumentBuilder _db = _dbf.newDocumentBuilder();
//				
//				// Parsing
//				Document _doc = _db.parse(_is);
//				*/
				//Element _el = _doc.getDocumentElement(); 
				
				// take nodes to nodeList
				//NodeList _nl = _el.getElementsByTagName("item");
				
			URLConnection _conn = _url.openConnection();

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
						
						Element _companyName = (Element) _item.getElementsByTagName("CompanyName").item(0);
						Element _deliveryTime = (Element) _item.getElementsByTagName("DeliveryTime").item(0);
						Element _address = (Element) _item.getElementsByTagName("Address");
						Element _comment = (Element) _item.getElementsByTagName("Comment");
						Element _lastStatus = (Element) _item.getElementsByTagName("LastStatus");
						Element _lastStatusDate = (Element) _item.getElementsByTagName("LastStatusDate");
						Element _driverName = (Element) _item.getElementsByTagName("DriverName");
						
						String _cn = _companyName.getFirstChild().getNodeValue();
						String _dt = _deliveryTime.getFirstChild().getNodeValue();
						String _ad = _address.getFirstChild().getNodeValue();
						String _cm = _comment.getFirstChild().getNodeValue();
						String _ls = _lastStatus.getFirstChild().getNodeValue();
						String _ld = _lastStatusDate.getFirstChild().getNodeValue();
						String _dn = _driverName.getFirstChild().getNodeValue();
						
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