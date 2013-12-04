package com.example.dispatchermobile.models;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.dispatchermobile.R;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 18.11.13
 * Time: 22:12
 * To change this template use File | Settings | File Templates.
 */
public class MessageItem {
    public String Id;
    public String MessageText;
    public String Author;
    public String Datetime;
    public int TypeMessage; // Driver - Dispetcher

    public MessageItem(String id, String messageText, String author, String datetime, int typeMessage)
    {
        this.Id = id;
        this.MessageText = messageText;
        this.Author = author;
        this.Datetime = datetime;
        this.TypeMessage = typeMessage;

    }

    public View getView(Context context)
    {
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemTemplate = inflater.inflate(R.layout.message_item, null);

        LinearLayout llMessage = (LinearLayout) itemTemplate.findViewById(R.id.llMessage);
        TextView tvMessageText = (TextView) itemTemplate.findViewById(R.id.tvMessageText);
        tvMessageText.setText(this.MessageText);
        TextView tvAuthor = (TextView) itemTemplate.findViewById(R.id.tvMessageAuthor);
        tvAuthor.setText(this.Author);
        TextView tvDatetime = (TextView) itemTemplate.findViewById(R.id.tvMessageDatetime);
        tvDatetime.setText(this.Datetime);

        switch (this.TypeMessage)
        {
            case 0:
                llMessage.setBackgroundResource(R.drawable.rounded_corner_message_green);
                llMessage.setGravity(Gravity.RIGHT);

                break;
            case 1:
                llMessage.setBackgroundResource(R.drawable.rounded_corner_message_yellow);
                llMessage.setGravity(Gravity.LEFT);
                break;
            default:
                break;

        }


        return  itemTemplate;
    }
}
