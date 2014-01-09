package com.example.DispatcherMobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.DispatcherMobile.MyApplication;
import com.example.DispatcherMobile.R;
import com.example.DispatcherMobile.models.CompanyItem;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 03.12.13
 * Time: 16:12
 * To change this template use File | Settings | File Templates.
 */
public class CompanyArrayAdapterFragment extends ArrayAdapter<CompanyItem> {
    private final Context context;
    private final ArrayList<CompanyItem> companies;
   // private View.OnClickListener listener;
    public CompanyArrayAdapterFragment(Context context, ArrayList<CompanyItem> companies)
    {
        super(context, R.layout.company_list_item, companies);
        this.context = context ;
        this.companies = companies;

    }

    @Override
    public View getView(int _pos, View _existingTemplate, ViewGroup _parent) {
        View _itemTemplate = null;

        if (_existingTemplate == null) {
            LayoutInflater _inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                    Intent _intent = new Intent("com.example.DispatcherMobile.selectedCompany");
                    _intent.putExtra("data",  _holder.Company.getTag().toString());
                    // TODO: Переделать на нормальный broadcastReceiver
                    //    http://developer.android.com/training/location/activity-recognition.html
                    //
                    _intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    _intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    MyApplication.getCurrentActivity().startActivity(_intent);
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
