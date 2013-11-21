package com.example.dispatchermobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 21.11.13
 * Time: 21:55
 * To change this template use File | Settings | File Templates.
 */
public class ExpListContactsAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<ArrayList<ContactItem>> listData;
    private LayoutInflater layoutInflater;

    public ExpListContactsAdapter (Context context, ArrayList<ContactItem> listData)
    {
        this.context = context;
        this.listData = new ArrayList<ArrayList<ContactItem>>();
        this.listData.add(listData);
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return listData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.contacts_groupview, null);
        }

        if (isExpanded){
            //Изменяем что-нибудь, если текущая Group раскрыта
        }
        else{
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroupContacts);
        textGroup.setText("Contacts ( " + listData.get(groupPosition).size() + " )");

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {


        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.contact_item, null);
            holder = new ViewHolder();
            holder.Layout = (RelativeLayout) convertView.findViewById(R.id.layoutContactItem);
            holder.Name = (TextView) convertView.findViewById(R.id.tvContactItem_Name);
            holder.Rank = (TextView) convertView.findViewById(R.id.tvContactItem_Rank);
            holder.Comment = (TextView) convertView.findViewById(R.id.tvContactItem_Comment);
            holder.LayoutPhones = (LinearLayout) convertView.findViewById(R.id.layoutPhones);
            for (int i = 1; i <= listData.get(groupPosition).get(childPosition).Phones.size(); i++)
            {
                TextView _tv = new TextView(context);
                _tv.setId(i);
                String str = "";
                str = str + listData.get(groupPosition).get(childPosition).Phones.get(i-1);
                _tv.setText(str);
                holder.LayoutPhones.addView(_tv);
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.Name.setText(listData.get(groupPosition).get(childPosition).Name);
        holder.Rank.setText(listData.get(groupPosition).get(childPosition).Rank);
        holder.Comment.setText(listData.get(groupPosition).get(childPosition).Comment);


        return convertView;
    }


    static class ViewHolder {
        TextView Name;
        TextView Rank;
        TextView Comment;
        RelativeLayout Layout;
        LinearLayout LayoutPhones;

    }
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.contact_item, null);
//        }
//
//        TextView textChild = (TextView) convertView.findViewById(R.id.textChild);
//        textChild.setText(mGroups.get(groupPosition).get(childPosition));
//
//        Button button = (Button)convertView.findViewById(R.id.buttonChild);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext,"button is pressed",5000).show();
//            }
//        });
//
//        return convertView;
//    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
