package com.example.dispatchermobile;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
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
public class ExpListMessagesAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<ArrayList<MessageItem>> listData;
    private LayoutInflater layoutInflater;

    public ExpListMessagesAdapter(Context context, ArrayList<MessageItem> listData)
    {
        this.context = context;
        this.listData = new ArrayList<ArrayList<MessageItem>>();
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
            convertView = inflater.inflate(R.layout.messages_groupview, null);
        }

        if (isExpanded){
            //Изменяем что-нибудь, если текущая Group раскрыта
        }
        else{
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroupMessages);
        textGroup.setText("Messages ( " + listData.get(groupPosition).size() + " )");

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.message_item, null);
            holder = new ViewHolder();
            holder.LayoutMessage = (LinearLayout) convertView.findViewById(R.id.layoutMessage);
            holder.Author = (TextView) convertView.findViewById(R.id.tvMessageAuthor);
            holder.Message = (TextView) convertView.findViewById(R.id.tvMessageText);
            holder.Date = (TextView) convertView.findViewById(R.id.tvMessageDatetime);
            convertView.setTag(holder);
        }                              else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch  (listData.get(groupPosition).get(childPosition).TypeMessage) {
            // Message from Dispetcher
            case 0:
                holder.LayoutMessage.setBackgroundColor(Color.YELLOW);
                holder.LayoutMessage.setGravity(Gravity.LEFT);
                break;
            // Message from Driver
            case 1:
                holder.LayoutMessage.setBackgroundColor(Color.GREEN);
                holder.LayoutMessage.setGravity(Gravity.RIGHT);
                break;
            default:
                break;


        }

        holder.Author.setText(listData.get(groupPosition).get(childPosition).Author);
        holder.Message.setText(listData.get(groupPosition).get(childPosition).MessageText);
        holder.Date.setText(listData.get(groupPosition).get(childPosition).Datetime);



        return convertView;
    }


    static class ViewHolder {
        TextView Author;
        TextView Message;
        TextView Date;
        LinearLayout LayoutMessage;

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
