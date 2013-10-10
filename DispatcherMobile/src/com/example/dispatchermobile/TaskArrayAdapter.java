package com.example.dispatchermobile;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.os.Bundle;

import java.util.ArrayList;

public class TaskArrayAdapter extends ArrayAdapter<TaskItem> {
    private final Activity context;
    private final ArrayList<TaskItem> tasks;

    public TaskArrayAdapter(Activity _context, ArrayList<TaskItem> _tasks) {
        super(_context, R.layout.list_item, _tasks);
        this.context = _context;
        this.tasks = _tasks;
    }

    @Override
    public View getView(int _pos, View _existingTemplate, ViewGroup _parent) {
        View _itemTemplate = null;

        if (_existingTemplate == null) {
            LayoutInflater _inflater = context.getLayoutInflater();
            _itemTemplate = _inflater.inflate(R.layout.list_item, null);

            final ViewHolder _holder = new ViewHolder();
            _holder.Company = (TextView) _itemTemplate.findViewById(R.id.company);
            _holder.DeliveryTime = (TextView) _itemTemplate.findViewById(R.id.deliveryTime);
            _holder.Address = (TextView) _itemTemplate.findViewById(R.id.address);
            _holder.LastStatus = (TextView) _itemTemplate.findViewById(R.id.lastStatus);

            _holder.IconShowTask = (ImageView) _itemTemplate.findViewById(R.id.iconShowTask);
            _holder.IconShowTask.setImageResource(R.drawable.navigation_next_item);
            _holder.IconShowTask.setTag(_pos);
            _holder.IconShowTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskItem _task = tasks.get((Integer) _holder.IconShowTask.getTag());
                    String _str = _task.toJSON().toString();

                    Intent _intent = new Intent("com.example.DispatcherMobile.selectedTaskItem");
                    _intent.putExtra("taskItem", _str);
                     context.startActivity(_intent);
                }
            });

            _holder.CheckLastStatus = (CheckBox) _itemTemplate.findViewById(R.id.checkLastStatus);
            _holder.CheckLastStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskItem _task = tasks.get((Integer) _holder.CheckLastStatus.getTag());
                    if (_task.getLastStatus().startsWith("Создано")) {
                        _task.setLastStatus("Принято курьером");
                        _task.setLastStatusDate(Common.getCurrentTime());
                        _holder.CheckLastStatus.setChecked(true);
                    }
                    else

                    if (_task.getLastStatus().startsWith("Принято")) {
                        _task.lastStatus = "Создано диспетчером";
                        _task.lastStatusDate = Common.getCurrentTime();
                        _holder.CheckLastStatus.setChecked(false);
                    }
                    else

                    if (_task.getLastStatus().startsWith("Выполнено")) {
                        _holder.CheckLastStatus.setChecked(true);
                        return;
                    }

                    Toast.makeText(context, "This function is not correct worked yet. " +
                            _task.getCompanyName() + " - " +
                            _task.getLastStatus() + " - " +
                            _task.getLastStatusDate(), Toast.LENGTH_SHORT).show();
                }
              });
//            _holder.CheckLastStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    TaskItem _task = tasks.get((Integer) _holder.CheckLastStatus.getTag());
//                    if (isChecked) {
//                        _task.lastStatus = "Принято курьером";
//                        _task.lastStatusDate = Common.getCurrentTime();
//                    } else {
//                        _task.lastStatus = "Создано диспетчером";
//                        _task.lastStatusDate = Common.getCurrentTime();
//                    }
//                    Toast.makeText(context, "This function is not correct worked yet. " +
//                            _task.getCompanyName() + " - " +
//                            _task.getLastStatus() + " - " +
//                            _task.getLastStatusDate(), Toast.LENGTH_LONG).show();
//                }
//            });


            _itemTemplate.setTag(_holder);
            _holder.CheckLastStatus.setTag(_pos);
        } else {
            _itemTemplate = _existingTemplate;
            ((ViewHolder) _itemTemplate.getTag()).CheckLastStatus.setTag(_pos);
        }
        ViewHolder _holder = (ViewHolder) _itemTemplate.getTag();

        TaskItem _task = tasks.get(_pos);
        _holder.Company.setText(_task.getCompanyName());
        _holder.DeliveryTime.setText(_task.getDeliveryTime());
        _holder.Address.setText(_task.getAddress());
        _holder.LastStatus.setText(_task.getLastStatus());
        _holder.IconShowTask = (ImageView) _itemTemplate.findViewById(R.id.iconShowTask);
        _holder.IconShowTask.setImageResource(R.drawable.navigation_next_item);



        _holder.CheckLastStatus = (CheckBox) _itemTemplate.findViewById(R.id.checkLastStatus);

        if (_task.getLastStatus().startsWith("Создано")) {
            _holder.CheckLastStatus.setChecked(false);
        } else {
            _holder.CheckLastStatus.setChecked(true);
        }

        return _itemTemplate;
        //return null;
    }

    static class ViewHolder {
        public TextView Company;
        public TextView DeliveryTime;
        public TextView Address;
        public TextView LastStatus;
        public ImageView IconShowTask;
        public CheckBox CheckLastStatus;
    }


}