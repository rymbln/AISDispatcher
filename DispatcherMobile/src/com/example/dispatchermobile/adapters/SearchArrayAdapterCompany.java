package com.example.dispatchermobile.adapters;

import android.app.ListActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.dispatchermobile.R;
import com.example.dispatchermobile.models.CompanyItem;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 17.12.13
 * Time: 22:44
 * To change this template use File | Settings | File Templates.
 */
public class SearchArrayAdapterCompany extends ArrayAdapter<CompanyItem> {
    private final ListActivity context;
    private final ArrayList<CompanyItem> companies;

    public SearchArrayAdapterCompany(ListActivity _context, ArrayList<CompanyItem> _tasks){
        super(_context, R.layout.activity_search_results, _tasks);
        this.context = _context;
        this.companies = _tasks;

    }

    @Override
    public View getView(int pos, View existingTemplate, ViewGroup parent)
    {
        View itemTemplate = null;
        if (existingTemplate == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            itemTemplate = inflater.inflate(R.layout.activity_search_results, null);

            final ViewHolder holder = new ViewHolder();
            holder.item = (TextView) itemTemplate.findViewById(R.id.txtSearchResult)  ;
            holder.item.setTag(companies.get(pos).getCompanyID());
            holder.item.setText(companies.get(pos).toString());
            itemTemplate.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent("com.example.DispatcherMobile.selectedCompany");
                    intent.putExtra("data",holder.item.getTag().toString());
                    context.startActivityForResult(intent,10);
                }
            });
        }                else
        {
            itemTemplate = existingTemplate;
            ((ViewHolder) itemTemplate.getTag()).item.setTag(pos);
        }

        return itemTemplate;
    }

    static class ViewHolder {
        public TextView item;

    }
}