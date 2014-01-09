package com.example.DispatcherMobile;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.DispatcherMobile.adapters.CompanyArrayAdapterFragment;
import com.example.DispatcherMobile.models.CompanyItem;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 03.12.13
 * Time: 11:32
 * To change this template use File | Settings | File Templates.
 */
public class CompanyListFragment extends Fragment {
    public CompanyListFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_company_list, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listViewCompanies);
        Context context = getActivity();
        ArrayList<CompanyItem> companies = MyApplication.getDataProvider().getCompaniesLocal();
        CompanyArrayAdapterFragment adapter = new CompanyArrayAdapterFragment(context, companies);

        listView.setAdapter(adapter);


        return rootView;
    }
}
