package com.example.dispatchermobile.adapters;

import android.app.ListActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.dispatchermobile.R;
import com.example.dispatchermobile.models.TaskItem;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 17.12.13
 * Time: 22:43
 * To change this template use File | Settings | File Templates.
 */
public class SearchArrayAdapterTask extends ArrayAdapter<TaskItem> {
    private final ListActivity context;
    private final ArrayList<TaskItem> tasks;

    public SearchArrayAdapterTask(ListActivity _context, ArrayList<TaskItem> _tasks){
        super(_context,R.layout.activity_search_results, _tasks);
        this.context = _context;
        this.tasks = _tasks;

    }

    @Override
    public View getView(int pos, View existingTemplate, ViewGroup parent)
    {
        View itemTemplate = null;
        if (existingTemplate == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            itemTemplate = inflater.inflate(R.layout.activity_search_results, null);

            final ViewHolder holder = new ViewHolder();
            holder.item = (TextView) itemTemplate.findViewById(R.id.txtSearchResult)  ;
            holder.item.setTag(tasks.get(pos).getTaskID());
            holder.item.setText(tasks.get(pos).toString());
            itemTemplate.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent("com.example.DispatcherMobile.selectedTaskItem");
                    intent.putExtra("data",holder.item.getTag().toString());
                    context.startActivityForResult(intent,10);
                }
            });
        }                else
        {
            itemTemplate = existingTemplate;
            ((ViewHolder) itemTemplate.getTag()).item.setTag(pos);
        }

         return itemTemplate;
    }

    static class ViewHolder {
        public TextView item;

    }
}
