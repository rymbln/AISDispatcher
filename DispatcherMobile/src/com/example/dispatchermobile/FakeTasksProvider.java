package com.example.dispatchermobile;

import java.util.ArrayList;

public class FakeTasksProvider implements ITaskProvider {
    public ArrayList<TaskItem> getTasks() {
        ArrayList<TaskItem> _tasks = new ArrayList<TaskItem>();
        TaskItem _t1 = new TaskItem("Company1", "01:00", "Address1", "Comment1", "lastStatus1", "lastStatusDate1", "Driver1");
        TaskItem _t2 = new TaskItem("Company2", "02:00", "Address2", "Comment2", "lastStatus2", "lastStatusDate2", "Driver2");
        _tasks.add(_t1);
        _tasks.add(_t2);

        return _tasks;
    }
}