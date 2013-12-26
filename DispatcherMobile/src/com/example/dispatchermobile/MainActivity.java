package com.example.dispatchermobile;


import android.app.*;
import android.content.*;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.example.dispatchermobile.adapters.NavDrawerListAdapter;
import com.example.dispatchermobile.models.NavDrawerItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static final int RESULT_SETTINGS = 1;
    private Context context;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private Integer lastPosition;
    private ActionBar actionBar;
    private Intent searchIntent;
    // nav drawer title
    private CharSequence mDrawerTitle;
    // Refresh menu item
    private MenuItem refreshMenuItem;
    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    // private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mTitle = mDrawerTitle = getTitle();
        actionBar = getActionBar();

        loadNavigationDrawer();

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // TODO: Переделать на нормальный broadcastReceiver
        //    http://developer.android.com/training/location/activity-recognition.html
        //
        MyApplication.setCurrentActivity(this);
        Intent intent = getIntent();


        if (savedInstanceState == null) {
            displayView(Common.ACTIVE_SCREEN - 1);
        }

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            if (Common.ACTIVE_SCREEN == 1) {
                searchIntent = new Intent("com.example.DispatcherMobile.selectedTaskItem");
            } else {
                searchIntent = new Intent("com.example.DispatcherMobile.selectedCompany");
            }
            searchIntent.putExtra("data", intent.getData().toString());
            startActivity(searchIntent);
            //    this.finish();
        }
    }

    private void loadNavigationDrawer() {
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Tasks
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1), true, MyApplication.getDataProvider().getTasksLocal().size()));
        // Companies
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1), true, MyApplication.getDataProvider().getCompaniesLocal().size()));
        // Settings
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Close
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));

        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();

            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(Integer position) {
        // update the main content by replacing fragments
        if (position != 2) {
            lastPosition = position;

        }
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TaskListFragment();
                Common.ACTIVE_SCREEN = 1;
                      break;
            case 1:
                fragment = new CompanyListFragment();
                Common.ACTIVE_SCREEN = 2;
                      break;
            case 2:
                Intent i = new Intent(this, UserSettingsActivity.class);
                startActivityForResult(i, RESULT_SETTINGS);
                break;
            case 3:
                this.finish();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
//        MenuItem searchItem = menu.findItem(R.id.taskReaderMenuSearch)   ;
//        mSearchView = (SearchView) searchItem.getActionView();
//
//          mSearchView.setIconifiedByDefault(false);
//           SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//          SearchView searchView = (SearchView) menu.findItem(R.id.taskReaderMenuSearch).getActionView();
//          searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this, UserSettingsActivity.class);
                startActivityForResult(i, RESULT_SETTINGS);
                return true;
            case R.id.taskReaderMenuRefresh:
                refreshMenuItem = item;
                new SyncData().execute();
                return true;
            case R.id.taskReaderMenuSearch:
                onSearchRequested();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Async task to load the data from server
     * *
     */
    private class SyncData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // set the progress bar view
            refreshMenuItem.setActionView(R.layout.action_progressbar);
            refreshMenuItem.expandActionView();
        }

        @Override
        protected String doInBackground(String... params) {
            // for now we use a timer to wait for sometime
            try {
                Thread.sleep(3000);
                MyApplication.getDataProvider().getTasksLocal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            refreshMenuItem.collapseActionView();
            // remove the progress bar view
            refreshMenuItem.setActionView(null);
            Toast.makeText(context, "Task updated at " + Common.getCurrentTime(), Toast.LENGTH_LONG).show();
            MyApplication.sendNotificationUpdatedData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("my-event"));
    }

    // handler for received Intents for the "my-event" event
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            String message = intent.getStringExtra("message");
            if (message.equals("update")) {
                displayView(lastPosition);
            }
            // Log.d("receiver", "Got message: " + message);
        }
    };

    @Override
    protected void onPause() {
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    /**
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO: Переделать на нормальную обработку всех приходящих результатов через requestCode
        switch (resultCode) {
            case RESULT_OK:
                this.displayView(lastPosition);
                break;
            case RESULT_SETTINGS:
                this.displayView(lastPosition);
                executeSettings();
                break;

        }
    }


    private void executeSettings() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        StringBuilder builder = new StringBuilder();

        builder.append("\n Username: "
                + sharedPrefs.getString("prefUsername", "NULL"));

        builder.append("\n PAssword: "
                + sharedPrefs.getString("prefUserpass", "NULL"));

        builder.append("\n Enable Notifications:"
                + sharedPrefs.getBoolean("prefEnableNotifications", false));

        builder.append("\n Sync Frequency: "
                + sharedPrefs.getString("prefSyncFrequency", "NULL"));

        Toast.makeText(getApplicationContext(), builder, Toast.LENGTH_LONG).show();
    }
}
