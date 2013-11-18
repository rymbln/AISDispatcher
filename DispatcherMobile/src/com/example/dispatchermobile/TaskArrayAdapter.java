package com.example.dispatchermobile;

import android.app.ListActivity;
import android.content.Intent;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

public class TaskArrayAdapter extends ArrayAdapter<TaskItem> {
    private final ListActivity context;
    private final ArrayList<TaskItem> tasks;

    public TaskArrayAdapter(ListActivity _context, ArrayList<TaskItem> _tasks) {
        super(_context, R.layout.TaskList_Item, _tasks);
        this.context = _context;
        this.tasks = _tasks;
    }

    @Override
    public View getView(int _pos, View _existingTemplate, ViewGroup _parent) {
        View _itemTemplate = null;

        if (_existingTemplate == null) {
            LayoutInflater _inflater = context.getLayoutInflater();
            _itemTemplate = _inflater.inflate(R.layout.TaskList_Item, null);

            final ViewHolder _holder = new ViewHolder();
            _holder.Company = (TextView) _itemTemplate.findViewById(R.id.company);
            _holder.DeliveryTime = (TextView) _itemTemplate.findViewById(R.id.deliveryTime);
            _holder.Address = (TextView) _itemTemplate.findViewById(R.id.address);
            _holder.LastStatus = (TextView) _itemTemplate.findViewById(R.id.lastStatus);
            _holder.Layout = (RelativeLayout) _itemTemplate.findViewById(R.id.layoutListItem);

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
                    context.startActivityForResult(_intent, 10);
                }
            });

            _holder.CheckLastStatus = (CheckBox) _itemTemplate.findViewById(R.id.checkLastStatus);
            _holder.CheckLastStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskItem _task = tasks.get((Integer) _holder.CheckLastStatus.getTag());
                    if (_task.getLastStatus().startsWith("Создано")) {
                        ITaskProvider _pr = new TaskProvider();
                        _pr.setTaskTaked(_task.getTaskID());
                        // TODO Здесь надо реализовать вызов какого-нибудь события для обновления ListActivity
                        _holder.CheckLastStatus.setChecked(true);
                        // обновление списка задач
                        NetTaskOperations _op = new NetTaskOperations(context);
                        _op.execute("");

                    } else if (_task.getLastStatus().startsWith("Принято")) {
                        ITaskProvider _pr = new TaskProvider();
                        _pr.setTaskCreated(_task.getTaskID());
                        // TODO Здесь надо реализовать вызов какого-нибудь события для обновления ListActivity
                        _holder.CheckLastStatus.setChecked(false);
                        // Обновление списка задач
                        NetTaskOperations _op = new NetTaskOperations(context);
                        _op.execute("");

                    } else if (_task.getLastStatus().startsWith("Выполнено")) {
                        _holder.CheckLastStatus.setChecked(true);
                        Toast.makeText(context, "Sorry, but you can't do this because task is completed. " +
                                _task.getCompanyName() + " - " +
                                _task.getLastStatus() + " - " +
                                _task.getLastStatusDate(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(context, "This function is not correct worked yet. " +
                            _task.getCompanyName() + " - " +
                            _task.getLastStatus() + " - " +
                            _task.getLastStatusDate(), Toast.LENGTH_SHORT).show();
                }
            });

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

        if (_task.getLastStatus().equals("Создано")) {
            _holder.CheckLastStatus.setChecked(false);

        }

        if (_task.getLastStatus().equals("Принято")) {
            _holder.CheckLastStatus.setChecked(true);
            _holder.Layout.setBackgroundColor(0xFFB1FF90);
        }

        if (_task.getLastStatus().equals("Выполнено")) {
            _holder.CheckLastStatus.setChecked(true);
            _holder.Layout.setBackgroundColor(0xFFff957a);
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
        public RelativeLayout Layout;
    }


}