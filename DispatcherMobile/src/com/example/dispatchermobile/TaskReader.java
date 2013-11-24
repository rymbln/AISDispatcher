package com.example.dispatchermobile;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.widget.Toast;
import android.widget.ListView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class TaskReader extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Tasks");
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu _menu) {
        //TODO : Make real online checking
        Boolean isOnline = true;
        int menuId = isOnline ? R.menu.taskreader_menu : R.menu.taskreader_offlinemenu;

        _menu.clear();
        MenuInflater _inf = getMenuInflater();
        _inf.inflate(menuId, _menu);


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu _menu) {
        MenuInflater _inf = getMenuInflater();
        _inf.inflate(R.menu.taskreader_menu, _menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("What would you like to do?");
        MenuInflater _inf = getMenuInflater();
        _inf.inflate(R.menu.taskreader_contextmenu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.taskReaderMenuSetNew:
                Toast.makeText(this, "THis are not correct worked yet", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.taskReaderMenuSetTaked:
                Toast.makeText(this, "THis are not correct worked yet", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.taskReaderMenuSetCompleted:
                Toast.makeText(this, "THis are not correct worked yet", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem _item) {
        switch (_item.getItemId()) {
/*             case R.id.taskReaderMenuExit:
                 finish();
                 System.exit(0);
                 return true;*/
            case R.id.taskReaderMenuRefresh:
                updateView();
                return true;
            case R.id.taskReaderMenuSettings:
                Toast.makeText(this, "Settings are nit correct worked yet", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.groupMenuCloseApp:
                finish();
                System.exit(0);
                return true;
            case R.id.groupMenuLogOff:
                Toast.makeText(this, "LogOff are nit correct worked yet", Toast.LENGTH_SHORT).show();
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
        HttpHelpers.initialize(this);
        // TODO - Для загрузки локальных Tasks - потом удалить или переработать
        TaskProvider.initializeTasks();


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