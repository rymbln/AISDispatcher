package com.example.DispatcherMobile;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.DispatcherMobile.models.CompanyItem;
import com.example.DispatcherMobile.models.ContactItem;


public class CompanySelected extends Activity {
    private Intent currentIntent;
    private LinearLayout llContacts;
    private LinearLayout llMain;
    private CompanyItem company = new CompanyItem();
    private ActionBar actionBar;
    private DataProvider dataProvider = new DataProvider();
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companyselected_main);


        currentIntent = getIntent();
        context = this;

        Bundle extras = currentIntent.getExtras();
        if (extras != null) {
            company = dataProvider.getCompanylocal(extras.getString("data"));
            initializeView();
            updateView(company);
        }


    }

    public void initializeView() {
        llContacts = (LinearLayout) findViewById(R.id.llContacts);
        llMain = (LinearLayout) findViewById(R.id.llCompanySelectedInfoMain);
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void updateView(CompanyItem company) {
        actionBar.setTitle(company.getCompanyName());
        actionBar.setIcon(R.drawable.ic_company);

        llMain.removeAllViews();
        llMain.addView(company.getView(context));

        llContacts.removeAllViews();
        if (company.getContacts().size() > 0) {
            TextView tvContacts = (TextView) findViewById(R.id.tvContacts);
            tvContacts.setText("Contacts (" + company.getContacts().size() + ")");

            for (ContactItem ci : company.getContacts()) {
                llContacts.addView(ci.getView(context));
            }
        } else {
            TextView tvContacts = (TextView) findViewById(R.id.tvContacts);
            tvContacts.setText("No Contacts ");
        }
    }

    @Override
    public void finish()
    {
//        currentIntent.putExtra("update", true);
//        currentIntent.putExtra("view", "companies");
//        setResult(RESULT_CANCELED, currentIntent);
        super.finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem _item) {
        switch (_item.getItemId()) {
            case android.R.id.home:
//                currentIntent.putExtra("update", true);
//                currentIntent.putExtra("view", "companies");
//                setResult(RESULT_OK, currentIntent);

                super.finish();
        }
        return (super.onOptionsItemSelected(_item));
    }
}