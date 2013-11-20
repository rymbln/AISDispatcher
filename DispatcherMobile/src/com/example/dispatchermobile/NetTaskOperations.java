package com.example.dispatchermobile;

//import android.app.Activity;
import android.app.ListActivity;
//import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class NetTaskOperations extends AsyncTask<String, Void , String>
{
    private ListActivity parent;
    private ArrayList<TaskItem> tasks;
   // private Intent currentIntent;

    public NetTaskOperations(ListActivity _act)
    {
        parent = _act;
       // currentIntent = _act.getIntent();
    }

    @Override
    protected String doInBackground(String... params)
    {

        tasks = loadTasks();
        return null;
    }

    @Override
    protected void onPostExecute(String result)
    {
        Bind(tasks);

       // parent.setResult(Activity.RESULT_OK, currentIntent);
        Common.closeProgressBar();
    }

    @Override
    protected void onPreExecute()
    {
        Common.initializeProgressBar(parent);
    }

    private ArrayList<TaskItem> loadTasks()
    {
        ITaskProvider _pr = new TaskProvider();

        // TODO - Для загрузки локальных Tasks - потом удалить или переработать
        return _pr.getTasksLocal();

        // Получение данных из интернета
        //return _pr.getTasks();

    }

    private void Bind(ArrayList<TaskItem> tasks)
    {
        TaskArrayAdapter _adp = new TaskArrayAdapter(parent, tasks);
        parent.setListAdapter(_adp);
    }
}
