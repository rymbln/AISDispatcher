package com.example.dispatchermobile;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class HttpHelpers {
    private static Context context;

    public static void initialize(Context _context) {
        context = _context;
    }

    public static Boolean isInternetAvailable() {
        ConnectivityManager _cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (_cm == null)
            return false;

        NetworkInfo _ni = _cm.getActiveNetworkInfo();
        if (_ni != null && _ni.isAvailable() && _ni.isConnected())
            return true;
        return false;
    }



    public static Document downloadTasksFromNet(String _url) {

        Document _doc = null;
        try {
            // DefaultHttpClient _cli = new DefaultHttpClient();
            // HttpResponse _res = _cli.execute(new HttpGet(_url));
            // HttpEntity _entity = _res.getEntity();
            // _output = EntityUtils.toString(entity);
            // opening connection for downloading list of tasks by xml data
            URL _ur = new URL(_url);
            URLConnection _conn = _ur.openConnection();
            _conn.setRequestProperty("Accept", "application/xml");
            DocumentBuilderFactory _factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder _builder = null;

            try {
                _builder = _factory.newDocumentBuilder();
                try {
                    _doc = _builder.parse(_conn.getInputStream());
                } catch (SAXException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

            } catch (ParserConfigurationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }





        } catch (IOException _ex) {

        }

        return _doc;
    }
}