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

	//###################
	//Added in sublime
	//###################

	private View.OnClickListener loadTasksListener;
	private View.OnClickListener selectTaskListener;

	private Intent currentIntent;

	private Button refreshButton;
	private ListView taskListView;
	private TextView footerText;

	private bool requireUpdate = false;

	//--здесь требуется описать менеджер синглетон
	public static NetTskOperations NetTaskManager;

	private void initializeApp()
	{
		refreshButton = (Button) findViewById(R.id.refreshTask)
				// get the listview from layout.xml
		taskListView = (ListView)findViewById(R.id.taskListView);
		
		//
		//--Более лучший вариант, 
		//особенно если в последствии делать один класс, 
		//где будут все листнеры на все основные действия с задачами и сервером
		//

		loadTasksListener = new View.OnClickListener()
		{
			public void onClick(View v)
			{
				updateView();
			}
		};
		refreshButton.setOnClickListener(loadTasksListener)

		selectTaskListener = new View.OnClickListener()
		{
			public void onItemClick(AdapterView<?> _av, View _view, int _index, long _arg3)

		}

		// what to execute, when task is clicked
		taskListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> _av, View _view, int _index, long _arg3)
			{
				TaskItem _item = taskItems.get(_index);
				// Call activity shows selected task
				Intent _intent = new Intent(this, TaskItemSelected.class);
				_intent.putExtra("taskItem", _item);
				
				//startActivity(_intent);
				startActivityForResult(i,10);
			}
		});
		
		_aa = new ArrayAdapter<TaskItem>(this, R.layout.list_item, taskItems);
		taskListView.setAdapter(_aa);
		

		HttpHelpers.initialize(this);
		//
		//--Возможный вариант, если не потребуется выносить загрузку задач в отдельный листнер
		//

		// refreshButton.setOnClickListener(new OnClickListener()
		// {
		// 	@Override
		// 	public void onClick(View _v)
		// 	{
		// 		_sourceUrl = "http://dispatcher-app.appspot.com/app/";
		// 		refreshTaskList();
		// 	}
		// });
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		if (requestCode != 10)
			return;
		else
		{
			Bundle extras = intent.getExtras();
			String returned = extras.getInt("Res");
			Toast.makeText(this, "smth: "+returned, Toast.LENGTH_LONG).show();
				}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowsFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		initializeApp();
			
		if (savedInstanceState != null)
		{
			requireUpdate = savedInstanceState.getInt("requireUpdate");
			if (requireUpdate)
			{
				updateView();	
			} 
			
		}
		
		
		refreshTaskList();

	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		outState.putBool("requireUpdate", true);
	}

	private void updateView()
	{
		refreshTaskList();

		footerText = (TextView)findViewById(R.id.footerText);
		//Получаем и записываем в футер текущую дату
		footerText.setText(Calendar.getInstance().ToString())
	}

	//###################
	//Ended in sublime
	//###################

	String _sourceUrl = "";
	ListView taskListView = null;
	ArrayList<TaskItem> taskItems = new ArrayList<TaskItem>();
	ArrayAdapter<TaskItem> _aa = null;
	
	// public static final int TaskItemDialog = 1;
	
	// protected Dialog onCreateDialog(int _id, Bundle _args)
	// {
	// 	// TODO Auto-generated method stub
	// 			switch (_id) {
	// 			case TaskItemDialog: {
	// 				LayoutInflater li = LayoutInflater.from(this);
	// 				View _taskDetails = li.inflate(R.layout.task_details, null);

	// 				AlertDialog.Builder taskDialog = new AlertDialog.Builder(this);
	// 				taskDialog.setTitle("Task Item");
	// 				taskDialog.setView(_taskDetails);

	// 				return taskDialog.create();
	// 			}
	// 			}

	// 			return null;
	// }
	
	// protected void onPrepareDialog(int id, Dialog dialog, Bundle args) 
	// {
	// 	// TODO Auto-generated method stub
	// 	switch (id) {
	// 	case TaskItemDialog: {
	// 		AlertDialog taskDialog = (AlertDialog) dialog;

	// 		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd - hh:mm:ss");
	// 		taskDialog.setTitle(selectedTaskItem.getCompanyName() + " : "
	// 				+ sdf.format(selectedTaskItem.getDeliveryTime()));

	// 		String text = selectedTaskItem.getCompanyName() + " : "
	// 				+ selectedTaskItem.getDeliveryTime();

	// 		TextView tv = (TextView) taskDialog
	// 				.findViewById(R.id.taskDetailsTextView);
	// 		tv.setText(text);

	// 	}
	// 	}
	// }
	
	private void refreshTaskList() {

		// ArrayList<TaskItem> newItems = TaskItem.getTaskItems(_sourceUrl);

		// taskItems.clear();
		// taskItems.addAll(newItems);

		// _aa.notifyDataSetChanged();
		ITaskProvider provider = new FakeTasksProvider();
		ArrayList<TaskItem> tasks = provider.getTasks();
		Bind(tasks);
	}

	private void Bind(ArrayList<TaskItem> _tasks)
	{
		ArrayAdapter<taskItem> _adapter = new TasksAdapter(this, _tasks);
		setTaskListAdapter(_adapter);
	}
	
	

}