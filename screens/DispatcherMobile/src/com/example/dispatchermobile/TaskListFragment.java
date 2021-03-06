package com.example.dispatchermobile;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.dispatchermobile.adapters.TaskArrayAdapterFragment;
import com.example.dispatchermobile.models.TaskItem;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 03.12.13
 * Time: 11:28
 * To change this template use File | Settings | File Templates.
 */
public class TaskListFragment extends Fragment {
    public TaskListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_task_list, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listViewTasks);

        Context context = getActivity();
        ArrayList<TaskItem> tasks = MyApplication.getDataProvider().getTasksLocal();

        TaskArrayAdapterFragment adapter = new TaskArrayAdapterFragment(context, tasks);

        listView.setAdapter(adapter);

        return rootView;
    }


}
