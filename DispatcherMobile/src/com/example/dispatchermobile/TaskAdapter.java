package com.example.dispatchermobile;

import java.util.ArrayList;

public class Taskadapter extends ArrayAdapter<TaskItem>
{
	private final Activity context;
	private final ArrayList<TaskItem> tasks;

	public TaskAdapter(Activity _context, ArrayList<RaskItem> _tasks)
	{
		super(_context, R.layout.list_item, _tasks);
		this.context = context;
		this.tasks = _tasks;
	}

	@Override
	public View getView(int _pos, View _existingTemplate, ViewGroup _parent)
	{
		ViewHolder _holder;
		View _itemTemplate;

		_itemTemplate = _existingTemplate;
		if (_existingTemplate == null)
		{
			LayoutInflater _inflater = context.getLayoutInflater();
			_itemTemplate = _inflater.inflate(R.layout.list_item, null, true);
			_holder = new Viewholder();
			_holder.Company = (TextView) _itemTemplate.findViewById(R.id.company);
			_holder.DeliveryTime = (TextView) _itemTemplate.findViewById(R.id.deliveryTime);
			_holder.Address = (TextView) _itemTemplate.findViewById(R.id.address);
			_itemTemplate.setTag(_holder);
		}
		else
			{
				_holder = (ViewHolder) _existingTemplate.getTag();
			}

		TaskItem _task = _tasks.get(position);
			_holder.Company.setText(_task.getCompanyName())
			_holder.DeliveryTime.setText(_task.getDeliveryTime())
			_holder.Address.setText(_task.getAddress())
	}

	static class ViewHolder
	{
		public TextView Company;
		public TextView DeliveryTime;
		public TextView Address;
	}
}