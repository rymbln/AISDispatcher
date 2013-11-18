package com.example.dispatchermobile;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.widget.Toast;
import android.widget.ListView;



public class TaskReader extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);


        if (savedInstanceState != null) {
            int requireUpdate = savedInstanceState.getInt("requireUpdate");
            if (requireUpdate > 0) {
                updateView();
            }
        } else {
            initializeApp();
            updateView();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu _menu)
    {
        //TODO : Make real online checking
        Boolean isOnline = true;
        int menuId = isOnline ? R.menu.taskreader_menu : R.menu.taskreader_offlinemenu;

        _menu.clear();
        MenuInflater _inf = getMenuInflater();
        _inf.inflate(menuId, _menu);



        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu _menu)
    {
        MenuInflater _inf = getMenuInflater();
        _inf.inflate(R.menu.taskreader_menu, _menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        menu.setHeaderTitle("What would you like to do?");
        MenuInflater _inf = getMenuInflater();
        _inf.inflate(R.menu.taskreader_contextmenu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
          switch (item.getItemId())
          {
              case R.id.taskReaderMenuSetNew:
                  Toast.makeText(this, "THis are not correct worked yet", Toast.LENGTH_SHORT);
                  return true;
              case R.id.taskReaderMenuSetTaked:
                  Toast.makeText(this, "THis are not correct worked yet", Toast.LENGTH_SHORT);
                  return true;
              case R.id.taskReaderMenuSetCompleted:
                  Toast.makeText(this, "THis are not correct worked yet", Toast.LENGTH_SHORT);
                  return true;
              default:
                  return super.onContextItemSelected(item);
          }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem _item)
    {
         switch (_item.getItemId())
         {
/*             case R.id.taskReaderMenuExit:
                 finish();
                 System.exit(0);
                 return true;*/
             case R.id.taskReaderMenuRefresh:
                 updateView();
                 return true;
             case R.id.taskReaderMenuSettings:
                 Toast.makeText(this, "Settings are nit correct worked yet", Toast.LENGTH_SHORT);
                 return true;
             case R.id.groupMenuCloseApp:
                 finish();
                 System.exit(0);
                 return true;
             case R.id.groupMenuLogOff:
                 Toast.makeText(this, "LogOff are nit correct worked yet", Toast.LENGTH_SHORT);
                 return true;
             default:
                 return super.onOptionsItemSelected(_item);

         }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("requireUpdate", 1);
    }

    private void initializeApp() {
//        Button refreshButton = (Button) findViewById(R.id.refreshTask);
//        Button exitAppButton = (Button) findViewById(R.id.closeApp);
        ListView taskListView = getListView();

        HttpHelpers.initialize(this);
        // TODO - Для загрузки локальных Tasks - потом удалить или переработать
        TaskProvider.initializeTasks();

//        OnClickListener loadTasksListener = new OnClickListener() {
//            public void onClick(View v) {
//                updateView();
//            }
//        };
//
//        OnClickListener exitAppListner = new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                System.exit(0);
//            }
//        };
//
//        AdapterView.OnItemLongClickListener taskListLongClickListner = new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(TaskReader.this, "Long tap is not working yet", Toast.LENGTH_LONG).show();
//                return true;  //To change body of implemented methods use File | Settings | File Templates.
//            }
//        };

//        taskListView.setOnItemLongClickListener(taskListLongClickListner);

//        refreshButton.setOnClickListener(loadTasksListener);
//        exitAppButton.setOnClickListener(exitAppListner);

    }

    public void updateView() {
        refreshTaskList();
        TextView footerText = (TextView) findViewById(R.id.footerText);
        footerText.setText(Common.getCurrentTime());
    }


    private void refreshTaskList() {
        NetTaskOperations _op = new NetTaskOperations(this);
        _op.execute("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data.hasExtra("update")) {
                Boolean result = data.getExtras().getBoolean("update");
                if (result != null && result) {
                    this.updateView();
                }
            }
        }
    }

}