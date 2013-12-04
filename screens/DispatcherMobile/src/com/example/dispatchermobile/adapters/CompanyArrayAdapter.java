package com.example.dispatchermobile.adapters;

import android.app.ListActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.dispatchermobile.R;
import com.example.dispatchermobile.models.CompanyItem;

import java.util.ArrayList;

public class CompanyArrayAdapter extends ArrayAdapter<CompanyItem> {
    private final ListActivity context;
    private final ArrayList<CompanyItem> companies;

    public CompanyArrayAdapter(ListActivity _context, ArrayList<CompanyItem> _companies) {
        super(_context, R.layout.tasklist_item, _companies);
        this.context = _context;
        this.companies = _companies;
    }

    @Override
    public View getView(int _pos, View _existingTemplate, ViewGroup _parent) {
        View _itemTemplate = null;

        if (_existingTemplate == null) {
            LayoutInflater _inflater = context.getLayoutInflater();
            _itemTemplate = _inflater.inflate(R.layout.company_list_item, null);

            final ViewHolder _holder = new ViewHolder();
            _holder.Company = (TextView) _itemTemplate.findViewById(R.id.tvCompanyName);
            _holder.Company.setTag(companies.get(_pos).getCompanyID());

            _holder.Address = (TextView) _itemTemplate.findViewById(R.id.tvAddress);
            _holder.Comment = (TextView) _itemTemplate.findViewById(R.id.tvComment);
            _holder.Layout = (LinearLayout) _itemTemplate.findViewById(R.id.llCompanyItem);

            _itemTemplate.setTag(_holder);
            _itemTemplate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent _intent = new Intent("com.example.DispatcherMobile.selectedCompanyItem");
                   _intent.putExtra("data",  _holder.Company.getTag().toString());
                    context.startActivityForResult(_intent, 10);
                }
            });
        } else {
            _itemTemplate = _existingTemplate;
            ((ViewHolder) _itemTemplate.getTag()).Company.setTag(_pos);
        }
        ViewHolder _holder = (ViewHolder) _itemTemplate.getTag();

        CompanyItem _company = companies.get(_pos);
        _holder.Company.setText(_company.getCompanyName());
        _holder.Address.setText(_company.getAddress());
        _holder.Comment.setText(_company.getComment());

        return _itemTemplate;
        //return null;
    }

    static class ViewHolder {
        public TextView Company;
        public TextView Address;
        public TextView Comment;
        public LinearLayout Layout;
    }


}