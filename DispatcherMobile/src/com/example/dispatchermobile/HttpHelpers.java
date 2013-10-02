package com.example.dispatchermobile;

public class HttpHelpers
{
	private static Context context;

	public static void initialize(Context _context)
	{
		context = _context;
	}

	public static Boolean isInternetAvailable()
	{
		ConnectivityManager _cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (_cm == null)
			return false;

		NetworkInfo _ni = _cm.getActiveNetworkInfo();
		if (_ni != null && _ni.isAvailable() && _ni.isConnected())
			return true;
		return false;
	}

	public static ArrayList<TaskItem> getTasksFromNet(String _url)
	{
		ArrayList<TaskItem> _output = null;

		try
		{
			// DefaultHttpClient _cli = new DefaultHttpClient();
			// HttpResponse _res = _cli.execute(new HttpGet(url));
			// HttpEntity _entity = _res.getEntity();
			// _output = EntityUtils.toString(entity);
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
						_output.add(_taskItem);
					}
				}
				

		}
		catch (IOException _ex)
		{

		}

		return _output;
	}
}