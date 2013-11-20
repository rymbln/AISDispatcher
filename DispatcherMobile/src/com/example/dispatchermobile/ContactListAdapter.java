package com.example.dispatchermobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import android.view.View;


/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 19.11.13
 * Time: 22:29
 * To change this template use File | Settings | File Templates.
 */
public class ContactListAdapter extends BaseAdapter {
    private ArrayList<ContactItem> listData;
    private LayoutInflater layoutInflater;
    private Context context;


    public ContactListAdapter(Context context, ArrayList<ContactItem> listData) {
        this.listData = listData;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
      }

    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.contact_item, null);
            holder = new ViewHolder();
            holder.Layout = (RelativeLayout) convertView.findViewById(R.id.layoutContactItem);
            holder.Name = (TextView) convertView.findViewById(R.id.tvContactItem_Name);
            holder.Rank = (TextView) convertView.findViewById(R.id.tvContactItem_Rank);
            holder.Comment = (TextView) convertView.findViewById(R.id.tvContactItem_Comment);
            holder.LayoutPhones = (LinearLayout) convertView.findViewById(R.id.layoutPhones);
            for (int i = 1; i <= listData.get(position).Phones.size(); i++)
            {
                TextView _tv = new TextView(context);
                _tv.setId(i);
                String str = "";
                str = str + listData.get(position).Phones.get(i-1);
                _tv.setText(str);
                holder.LayoutPhones.addView(_tv);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.Name.setText(listData.get(position).Name);
        holder.Rank.setText(listData.get(position).Rank);
        holder.Comment.setText(listData.get(position).Comment);


        return convertView;
    }


    static class ViewHolder {
         TextView Name;
         TextView Rank;
         TextView Comment;
         RelativeLayout Layout;
        LinearLayout LayoutPhones;

    }

}
