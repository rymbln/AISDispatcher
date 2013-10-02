package com.example.dispatchermobile;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<TaskItem> {
    private final Activity context;
    private final ArrayList<TaskItem> tasks;

    public TaskAdapter(Activity _context, ArrayList<TaskItem> _tasks) {
        super(_context, R.layout.list_item, _tasks);
        this.context = _context;
        this.tasks = _tasks;
    }

    @Override
    public View getView(int _pos, View _existingTemplate, ViewGroup _parent) {
        ViewHolder _holder;
        View _itemTemplate;

        _itemTemplate = _existingTemplate;
        if (_existingTemplate == null) {
            LayoutInflater _inflater = context.getLayoutInflater();
            _itemTemplate = _inflater.inflate(R.layout.list_item, null, true);
            _holder = new ViewHolder();
            _holder.Company = (TextView) _itemTemplate.findViewById(R.id.company);
            _holder.DeliveryTime = (TextView) _itemTemplate.findViewById(R.id.deliveryTime);
            _holder.Address = (TextView) _itemTemplate.findViewById(R.id.address);
            _itemTemplate.setTag(_holder);
        } else {
            _holder = (ViewHolder) _existingTemplate.getTag();
        }

        TaskItem _task = tasks.get(_pos);
        _holder.Company.setText(_task.getCompanyName());
        _holder.DeliveryTime.setText(_task.getDeliveryTime());
        _holder.Address.setText(_task.getAddress());

        return _itemTemplate;
    }

    static class ViewHolder {
        public TextView Company;
        public TextView DeliveryTime;
        public TextView Address;
    }
}