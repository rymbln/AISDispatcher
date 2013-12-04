package com.example.dispatchermobile;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.dispatchermobile.models.CompanyItem;
import com.example.dispatchermobile.models.ContactItem;


public class CompanySelected extends Activity {
    private Intent currentIntent;
    private TextView tvAddress;
    private TextView tvComment;
    private LinearLayout llContacts;
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
        tvAddress = (TextView) findViewById(R.id.addressTextView);
        tvComment = (TextView) findViewById(R.id.commentTextView);
        llContacts = (LinearLayout) findViewById(R.id.llContacts);
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    public void updateView(CompanyItem company) {
        actionBar.setTitle(company.getCompanyName());
        tvAddress.setText(company.getAddress());
        tvComment.setText(company.getComment());

        llContacts.removeAllViews();
        if (company.getContacts().size() > 0) {
            TextView tvContacts = (TextView) findViewById(R.id.tvContacts);
            tvContacts.setText("Contacts ( " + company.getContacts().size() + " )");

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
        currentIntent.putExtra("update", true);
        currentIntent.putExtra("view", "companies");
        setResult(RESULT_CANCELED, currentIntent);
        super.finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem _item) {
        switch (_item.getItemId()) {
            case android.R.id.home:
                currentIntent.putExtra("update", true);
                currentIntent.putExtra("view", "companies");
                setResult(RESULT_OK, currentIntent);
                super.finish();
        }
        return (super.onOptionsItemSelected(_item));
    }
}