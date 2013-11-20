package com.example.dispatchermobile;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 21.11.13
 * Time: 0:06
 * To change this template use File | Settings | File Templates.
 */
public class MessageListAdapter extends BaseAdapter {
    private ArrayList<MessageItem> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public MessageListAdapter(Context context, ArrayList<MessageItem> listData) {
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
        switch  (listData.get(position).TypeMessage) {
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

        holder.Author.setText(listData.get(position).Author);
        holder.Message.setText(listData.get(position).MessageText);
        holder.Date.setText(listData.get(position).Datetime);



        return convertView;

    }

    static class ViewHolder {
        TextView Author;
        TextView Message;
        TextView Date;
        LinearLayout LayoutMessage;

    }
}
