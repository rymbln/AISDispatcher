package com.example.dispatchermobile;

import java.text.SimpleDateFormat;

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
		
		SimpleDateFormat _sdf = new SimpleDateFormat("hh:mm:ss");
		
		String _cn = _selectedTaskItem.getCompanyName();
		String _dt = _sdf.format(_selectedTaskItem.getDeliveryTime());
		String _ad = _selectedTaskItem.getDeliveryTime();
		String _cm = _selectedTaskItem.getComment();
		String _ls = _selectedTaskItem.getLastStatus();
		String _ld = _selectedTaskItem.getLastStatusDate();
		String _dn = _selectedTaskItem.getDriverName();
		
		_companyNameTV.setText(_cn);
		_deliveryTimeTV.setText(_dt);
		_addressTV.setText(_ad);
		_commentTV.setText(_cm);
		_lastStatusTV.setText(_ls);
		_lastStatusDateTV.setText(_ld);
		_driverNameTV.setText(_dn);
		
	}
}