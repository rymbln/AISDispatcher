package com.example.dispatchermobile;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 08.10.13
 * Time: 22:48
 * To change this template use File | Settings | File Templates.
 */
public class TaskProvider implements ITaskProvider{

    public ArrayList<TaskItem> getTasks()
    {
        if (!HttpHelpers.isInternetAvailable())
        {
            ArrayList<TaskItem> _tasks = new ArrayList<TaskItem>();
            TaskItem _item = new TaskItem(
                    "No tasks found",
                    null,
                    "Check the internet connection",
                    null,
                    null,
                    null,
                    null);
            _tasks.add(_item);
            return  _tasks;
        }
        Document _doc = HttpHelpers.downloadTasksFromNet("http://dispatcher-app.appspot.com/app/");
        ArrayList<TaskItem> _tasks = ParseToTasks(_doc);

        return _tasks;
    }
    private static ArrayList<TaskItem> ParseToTasks(Document _doc)
    {
        ArrayList<TaskItem> _tasks = new ArrayList<TaskItem>();
        NodeList _nl = _doc.getElementsByTagName("item");

        if (_nl.getLength() > 0) {
            for (int i = 0; i < _nl.getLength(); i++) {
                //reading tags for each task item between tags <item></item>
                Element _item = (Element) _nl.item(i);

                String _cn = (_item.getElementsByTagName("CompanyName").item(0)).getFirstChild().getNodeValue();
                String _dt = (_item.getElementsByTagName("DeliveryTime").item(0)).getFirstChild().getNodeValue();
                String _ad = (_item.getElementsByTagName("Address").item(0)).getFirstChild().getNodeValue();
                String _cm = (_item.getElementsByTagName("Comment").item(0)).getFirstChild().getNodeValue();
                String _ls = (_item.getElementsByTagName("LastStatus").item(0)).getFirstChild().getNodeValue();
                String _ld = (_item.getElementsByTagName("LastStatusDate").item(0)).getFirstChild().getNodeValue();
                String _dn = (_item.getElementsByTagName("DriverName").item(0)).getFirstChild().getNodeValue();

                // Create TaskItem object and add to ArrayList
                TaskItem _taskItem = new TaskItem(_cn, _dt, _ad, _cm, _ls, _ld, _dn);
                _tasks.add(_taskItem);

            }
        }
        return _tasks;
    }
}
