package com.example.dispatchermobile;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.example.dispatchermobile.adapters.SearchArrayAdapterCompany;
import com.example.dispatchermobile.adapters.SearchArrayAdapterTask;
import com.example.dispatchermobile.models.CompanyItem;
import com.example.dispatchermobile.models.TaskItem;

import java.util.ArrayList;

public class SearchResultsActivity extends ListActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // get the action bar
        ActionBar actionBar = getActionBar();

        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);

        handleIntent(getIntent());
    }

    public void onListItemClick(ListView l, View v, int position, long id) {

        // call detail activity for clicked entry

    }


    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    /**
     * Handling intent data
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query, Common.ACTIVE_SCREEN);

        }

    }

    private void doSearch(String queryStr, Integer searchSource) {
               switch (searchSource){
                   // Active screen - Tasks
                   case 1:
                       ArrayList<TaskItem> tasksAll = MyApplication.getDataProvider().getTasksLocal();
                       ArrayList<TaskItem> tasksSearched = new ArrayList<TaskItem>();
                       for (TaskItem item : tasksAll)
                       {
                           if (item.toString().toLowerCase().contains(queryStr))
                           {
                               tasksSearched.add(item);
                           }
                           SearchArrayAdapterTask adapter = new SearchArrayAdapterTask(this, tasksSearched);
                           setListAdapter(adapter);
                       }
                       break;
                    // Active screen - Companies
                   case 2:
                       ArrayList<CompanyItem> companyAll = MyApplication.getDataProvider().getCompaniesLocal();
                       ArrayList<CompanyItem> companySearched = new ArrayList<CompanyItem>();
                       for (CompanyItem item: companyAll)
                       {
                           if (item.toString().toLowerCase().contains(queryStr))
                           {
                               companySearched.add(item);
                           }
                           SearchArrayAdapterCompany adapter = new SearchArrayAdapterCompany(this, companySearched);
                           setListAdapter(adapter);
                       }
                       break;
                   // Something wrong
                   default:
                       break;
               }
    }
}
