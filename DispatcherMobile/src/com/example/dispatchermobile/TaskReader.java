package com.example.dispatchermobile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class TaskReader extends Activity {
	
	public static TaskItem selectedTaskItem = null;
	String _sourceUrl = "";
	ListView _taskListView = null;
	ArrayList<TaskItem> _taskItems = new ArrayList<TaskItem>();
	ArrayAdapter<TaskItem> _aa = null;
	
	public static final int TaskItemDialog = 1;
	
	// Called when activity is first created
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// get textview from our layout.xml
		//final TextView _taskUrlTV = (TextView) findViewById(R.id.sourceUrl);

		// get button from layout.xml
		Button _refresh = (Button) findViewById(R.id.refreshTask);

		// define the action that will be executed when the button is clicked.
		_refresh.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View _v)
			{
				_sourceUrl = "http://dispatcher-app.appspot.com/app/";
				refreshTaskList();
			}
		});
		
		// get the listview from layout.xml
		_taskListView = (ListView)findViewById(R.id.taskListView);
		
		// what to execute, when task is clicked
		_taskListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> _av, View _view, int _index, long _arg3)
			{
				selectedTaskItem = _taskItems.get(_index);
				// Call activity shows selected task
				Intent _intent = new Intent("com.example.DispatcherMobile.selectedTaskItem");
				startActivity(_intent);
			}
		});
		
		_aa = new ArrayAdapter<TaskItem>(this, R.layout.list_item, _taskItems);
		_taskListView.setAdapter(_aa);
		
		refreshTaskList();
		
	}
	
	protected Dialog onCreateDialog(int _id, Bundle _args)
	{
		// TODO Auto-generated method stub
				switch (_id) {
				case TaskItemDialog: {
					LayoutInflater li = LayoutInflater.from(this);
					View _taskDetails = li.inflate(R.layout.task_details, null);

					AlertDialog.Builder taskDialog = new AlertDialog.Builder(this);
					taskDialog.setTitle("Task Item");
					taskDialog.setView(_taskDetails);

					return taskDialog.create();
				}
				}

				return null;
	}
	
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) 
	{
		// TODO Auto-generated method stub
		switch (id) {
		case TaskItemDialog: {
			AlertDialog taskDialog = (AlertDialog) dialog;

			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd - hh:mm:ss");
			taskDialog.setTitle(selectedTaskItem.getCompanyName() + " : "
					+ sdf.format(selectedTaskItem.getDeliveryTime()));

			String text = selectedTaskItem.getCompanyName() + " : "
					+ selectedTaskItem.getDeliveryTime();

			TextView tv = (TextView) taskDialog
					.findViewById(R.id.taskDetailsTextView);
			tv.setText(text);

		}
		}
	}
	
	private void refreshTaskList() {

		ArrayList<TaskItem> newItems = TaskItem.getTaskItems(_sourceUrl);

		_taskItems.clear();
		_taskItems.addAll(newItems);

		_aa.notifyDataSetChanged();
	}
	
	

}