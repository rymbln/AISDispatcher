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


//TODO предусмотреть загрузку стоковых данных при отсутствии интернета для теста

public class TaskProvider implements ITaskProvider {

    // --Начало секции суррогатных методов работы с задачами

    private static ArrayList<TaskItem> tasks;

    public static void initializeTasks() {
        tasks = new ArrayList<TaskItem>()    ;
        TaskItem taskItem = null;
        String _id, _cn, _dt, _ad, _cm, _ls, _ld, _dn = "";

        _id = "99999999";
        _cn = "АЛЬФАТранс";
        _dt = "16:00";
        _ad = "ул.Петра Алексеева д.19 оф.78";
        _cm = "обычный комментарий";
        _ls = "Создано";
        _ld = "12:00";
        _dn = "Василий";
        taskItem = new TaskItem(_id, _cn, _dt, _ad, _cm, _ls, _ld, _dn);
        tasks.add(taskItem);

        _id = "1231313123";
        _cn = "ВТБ24";
        _dt = "14:00";
        _ad = "пр.Строителей д.19 оф.78";
        _cm = "обычный комментарий";
        _ls = "Принято";
        _ld = "12:00";
        _dn = "Василий";
        taskItem = new TaskItem(_id, _cn, _dt, _ad, _cm, _ls, _ld, _dn);
        tasks.add(taskItem);

        _id = "989898746";
        _cn = "Смоленский Банк";
        _dt = "17:00";
        _ad = "ул.Кирова д.19 оф.7";
        _cm = "обычный комментарий";
        _ls = "Выполнено";
        _ld = "15:00";
        _dn = "Василий";
        taskItem = new TaskItem(_id, _cn, _dt, _ad, _cm, _ls, _ld, _dn);
        tasks.add(taskItem);

    }

    private ArrayList<TaskItem> sortTasks(ArrayList<TaskItem> _tasks){
        // TODO Реализовать сортировку задач
        return _tasks;
    }


    public ArrayList<TaskItem> getTasksLocal() {
        tasks = sortTasks(tasks);
        return tasks;
    }

    public void setTaskCreated(String _taskID) {
        for (int i = 0; i < tasks.size(); i++)
        {
            if (tasks.get(i).getTaskID().equals(_taskID))
            {
                tasks.get(i).setLastStatus("Создано");
                tasks.get(i).setLastStatusDate(Common.getCurrentTime());
            }
        }
    }

    public void setTaskTaked(String _taskID) {
        for (int i = 0; i < tasks.size(); i++)
        {
            if (tasks.get(i).getTaskID().equals(_taskID))
            {
                tasks.get(i).setLastStatus("Принято");
                tasks.get(i).setLastStatusDate(Common.getCurrentTime());
            }
        }
    }

    public void setTaskCompleted(String _taskID) {
        for (int i = 0; i < tasks.size(); i++)
        {
            if (tasks.get(i).getTaskID().equals(_taskID))
            {
                tasks.get(i).setLastStatus("Выполнено");
                tasks.get(i).setLastStatusDate(Common.getCurrentTime());
            }
        }
    }

    // -- Конец секции суррогатных методов работы с задачами


    public ArrayList<TaskItem> getTasks() {
        if (!HttpHelpers.isInternetAvailable()) {
            ArrayList<TaskItem> _tasks = new ArrayList<TaskItem>();
            TaskItem _item = new TaskItem("_",
                    "No tasks found",
                    null,
                    "Check the internet connection",
                    null,
                    null,
                    null,
                    null);
            _tasks.add(_item);
            return _tasks;
        }
        Document _doc = HttpHelpers.downloadTasksFromNet("http://dispatcher-app.appspot.com/app/");
        ArrayList<TaskItem> _tasks = ParseToTasks(_doc);

        return _tasks;
    }

    private static ArrayList<TaskItem> ParseToTasks(Document _doc) {
        ArrayList<TaskItem> _tasks = new ArrayList<TaskItem>();
        NodeList _nl = _doc.getElementsByTagName("item");

        if (_nl.getLength() > 0) {
            for (int i = 0; i < _nl.getLength(); i++) {
                //reading tags for each task item between tags <item></item>
                Element _item = (Element) _nl.item(i);

                String _id = (_item.getElementsByTagName("TaskID").item(0)).getFirstChild().getNodeValue();
                String _cn = (_item.getElementsByTagName("CompanyName").item(0)).getFirstChild().getNodeValue();
                String _dt = (_item.getElementsByTagName("DeliveryTime").item(0)).getFirstChild().getNodeValue();
                String _ad = (_item.getElementsByTagName("Address").item(0)).getFirstChild().getNodeValue();
                String _cm = (_item.getElementsByTagName("Comment").item(0)).getFirstChild().getNodeValue();
                String _ls = (_item.getElementsByTagName("LastStatus").item(0)).getFirstChild().getNodeValue();
                String _ld = (_item.getElementsByTagName("LastStatusDate").item(0)).getFirstChild().getNodeValue();
                String _dn = (_item.getElementsByTagName("DriverName").item(0)).getFirstChild().getNodeValue();

                // Create TaskItem object and add to ArrayList
                TaskItem _taskItem = new TaskItem(_id, _cn, _dt, _ad, _cm, _ls, _ld, _dn);
                _tasks.add(_taskItem);

            }
        }
        return _tasks;
    }
}
