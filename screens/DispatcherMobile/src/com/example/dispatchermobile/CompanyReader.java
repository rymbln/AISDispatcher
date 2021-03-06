package com.example.dispatchermobile;

import android.app.ActionBar;
import android.app.ListActivity;
import android.os.Bundle;
import com.example.dispatchermobile.adapters.CompanyArrayAdapter;

public class CompanyReader extends ListActivity {
    // action bar
    private ActionBar actionBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.companieslist_main);

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
        actionBar.setTitle("Companies");

    }


//    @Override
//    public boolean onPrepareOptionsMenu(Menu _menu) {
//        //TODO : Make real online checking
//        Boolean isOnline = true;
//        int menuId = isOnline ? R.menu.taskreader_menu : R.menu.taskreader_offlinemenu;
//
//        _menu.clear();
//        MenuInflater _inf = getMenuInflater();
//        _inf.inflate(menuId, _menu);
//
//
//        return true;
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu _menu) {
//        MenuInflater _inf = getMenuInflater();
//        _inf.inflate(R.menu.taskreader_menu, _menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem _item) {
//        switch (_item.getItemId()) {
///*             case R.id.taskReaderMenuExit:
//                 finish();
//                 System.exit(0);
//                 return true;*/
//            case R.id.taskReaderMenuRefresh:
//                updateView();
//                return true;
//            case R.id.taskReaderMenuSettings:
//                Toast.makeText(this, "Settings are nit correct worked yet", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.groupMenuCloseApp:
//                finish();
//                System.exit(0);
//                return true;
//            case R.id.groupMenuLogOff:
//                Toast.makeText(this, "LogOff are nit correct worked yet", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(_item);
//
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putInt("requireUpdate", 1);
//    }

    private void initializeApp() {
        //HttpHelpers.initialize(this);
        // TODO - Для загрузки локальных Tasks - потом удалить или переработать
      //  DataProvider.initialize();


    }

    public void updateView() {
        DataProvider provider = new DataProvider();
        CompanyArrayAdapter _adp = new CompanyArrayAdapter(this, provider.getCompaniesLocal());
        this.setListAdapter(_adp);
    }


//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (data.hasExtra("update")) {
//                Boolean result = data.getExtras().getBoolean("update");
//                if (result != null && result) {
//                    this.updateView();
//                }
//            }
//        }
//    }

}