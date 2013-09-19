package com.example.dispatchermobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TaskItemSelected extends Activity
{
	@Override
	public void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_item_selected);
		
		TaskItem _selectedTaskItem = com.example.dispatchermobile.TaskReader.selectedTaskItem;
		TextView _companyNameTV = (TextView)findViewById(R.id.companyNameTextView);
		TextView _deliveryTimeTV = (TextView)findViewById(R.id.deliveryTimeTextView);
		TextView _addressTV = (TextView)findViewById(R.id.addressTextView);
		TextView _commentTV = (TextView)findViewById(R.id.commentTextView);
		TextView _lastStatusTV = (TextView)findViewById(R.id.lastStatusTextView);
		TextView _lastStatusDateTV = (TextView)findViewById(R.id.lastStatusDateTextView);
		TextView _driverNameTV = (TextView)findViewById(R.id.driverNameTextView);
		
		_companyNameTV.setText(_selectedTaskItem.getCompanyName());
		_deliveryTimeTV.setText(_selectedTaskItem.getDeliveryTime());
		_addressTV.setText(_selectedTaskItem.getAddress());
		_commentTV.setText(_selectedTaskItem.getComment());
		_lastStatusTV.setText(_selectedTaskItem.getLastStatus());
		_lastStatusDateTV.setText(_selectedTaskItem.getLastStatusDate());
		_driverNameTV.setText(_selectedTaskItem.getDriverName());
		
	}
	
}