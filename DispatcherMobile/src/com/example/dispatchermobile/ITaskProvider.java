package com.example.DispatcherMobile;

import com.example.DispatcherMobile.models.TaskItem;

import java.util.ArrayList;

public  interface ITaskProvider
{
	ArrayList<TaskItem> getTasks();
    ArrayList<TaskItem> getTasksLocal();
    void setTaskCreated(String _taskID);
    void setTaskTaked(String _taskID);
    void setTaskCompleted(String _taskID);

}

