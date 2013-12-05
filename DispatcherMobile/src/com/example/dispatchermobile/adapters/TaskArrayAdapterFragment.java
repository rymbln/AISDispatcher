package com.example.dispatchermobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.dispatchermobile.DataProvider;
import com.example.dispatchermobile.MyApplication;
import com.example.dispatchermobile.R;
import com.example.dispatchermobile.models.TaskItem;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 03.12.13
 * Time: 12:46
 * To change this template use File | Settings | File Templates.
 */
public class TaskArrayAdapterFragment extends ArrayAdapter<TaskItem> {
    private final Context context;
    private final ArrayList<TaskItem> tasks;

    public TaskArrayAdapterFragment(Context context, ArrayList<TaskItem> tasks) {
        super(context, R.layout.tasklist_item, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int _pos, View _existingTemplate, ViewGroup _parent) {
        View _itemTemplate = null;

        if (_existingTemplate == null) {
            LayoutInflater _inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

                    } else if (_task.getLastStatus().startsWith("Принято")) {
                        dataProvider.setTaskCreated(_task.getTaskID());
                        // TODO Здесь надо реализовать вызов какого-нибудь события для обновления ListActivity
                        _holder.CheckLastStatus.setChecked(false);

                    } else if (_task.getLastStatus().startsWith("Выполнено")) {
                        Toast.makeText(context, "Sorry, but task is allready completed", Toast.LENGTH_LONG).show();
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
                    // TODO: Переделать на нормальный broadcastReceiver
                    //    http://developer.android.com/training/location/activity-recognition.html
                    //

                    MyApplication.getCurrentActivity().startActivityForResult(_intent, 10); //.startActivity(_intent);
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
        if (_task.isTaskClosed())
            _holder.IconShowTask.setImageResource(R.drawable.ic_close);
        else {
            _holder.IconShowTask.setImageResource(R.drawable.ic_open);
        }

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
