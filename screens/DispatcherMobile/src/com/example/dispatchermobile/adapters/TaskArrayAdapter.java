package com.example.dispatchermobile.adapters;

import android.app.ListActivity;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import com.example.dispatchermobile.DataProvider;
import com.example.dispatchermobile.NetTaskOperations;
import com.example.dispatchermobile.R;
import com.example.dispatchermobile.models.TaskItem;

import java.util.ArrayList;

public class TaskArrayAdapter extends ArrayAdapter<TaskItem> {
    private final ListActivity context;
    private final ArrayList<TaskItem> tasks;

    public TaskArrayAdapter(ListActivity _context, ArrayList<TaskItem> _tasks) {
        super(_context, R.layout.tasklist_item, _tasks);
        this.context = _context;
        this.tasks = _tasks;
    }

    @Override
    public View getView(int _pos, View _existingTemplate, ViewGroup _parent) {
        View _itemTemplate = null;

        if (_existingTemplate == null) {
            LayoutInflater _inflater = context.getLayoutInflater();
            _itemTemplate = _inflater.inflate(R.layout.tasklist_item, null);

            final ViewHolder _holder = new ViewHolder();
            _holder.Company = (TextView) _itemTemplate.findViewById(R.id.tvCompany);
            _holder.Company.setTag(tasks.get(_pos).getTaskID());
            _holder.DeliveryTime = (TextView) _itemTemplate.findViewById(R.id.tvDeliveryTime);
            _holder.Address = (TextView) _itemTemplate.findViewById(R.id.tvAddress);
            _holder.Comment = (TextView) _itemTemplate.findViewById(R.id.tvComment);
            _holder.Layout = (RelativeLayout) _itemTemplate.findViewById(R.id.layoutListItem);
            _holder.Layout.setTag(tasks.get(_pos).getTaskID());


            _holder.IconShowTask = (ImageView) _itemTemplate.findViewById(R.id.iconShowTask);
            _holder.IconShowTask.setImageResource(R.drawable.navigation_next_item);


            _holder.CheckLastStatus = (CheckBox) _itemTemplate.findViewById(R.id.checkLastStatus);
            _holder.CheckLastStatus.setTag(tasks.get(_pos).getTaskID());
            _holder.CheckLastStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataProvider dataProvider = new DataProvider();
                    TaskItem _task = dataProvider.getTasklocal(_holder.Company.getTag().toString());
                    if (_task.getLastStatus().startsWith("Создано")) {
                        dataProvider.setTaskTaked(_task.getTaskID());
                        // TODO Здесь надо реализовать вызов какого-нибудь события для обновления ListActivity
                        _holder.CheckLastStatus.setChecked(true);
                        // обновление списка задач
                        NetTaskOperations _op = new NetTaskOperations(context);
                        _op.execute("");

                    } else if (_task.getLastStatus().startsWith("Принято")) {
                        dataProvider.setTaskCreated(_task.getTaskID());
                        // TODO Здесь надо реализовать вызов какого-нибудь события для обновления ListActivity
                        _holder.CheckLastStatus.setChecked(false);
                        // Обновление списка задач
                        NetTaskOperations _op = new NetTaskOperations(context);
                        _op.execute("");

                    } else if (_task.getLastStatus().startsWith("Выполнено")) {
                        _holder.CheckLastStatus.setChecked(true);

                    }


                }
            });

            _itemTemplate.setTag(_holder);
            _itemTemplate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent _intent = new Intent("com.example.DispatcherMobile.selectedTaskItem");
                    _intent.putExtra("data", _holder.Company.getTag().toString());
                    context.startActivityForResult(_intent, 10);
                }
            });
        } else {
            _itemTemplate = _existingTemplate;
            ((ViewHolder) _itemTemplate.getTag()).CheckLastStatus.setTag(_pos);
        }
        ViewHolder _holder = (ViewHolder) _itemTemplate.getTag();

        TaskItem _task = tasks.get(_pos);
        _holder.Company.setText(_task.getCompanyName());
        _holder.DeliveryTime.setText(_task.getDeliveryTime());
        _holder.Address.setText(_task.getAddress());
        _holder.Comment.setText(_task.getCommentSmall());
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
        public TextView Comment;
        public ImageView IconShowTask;
        public CheckBox CheckLastStatus;
        public RelativeLayout Layout;
    }


}