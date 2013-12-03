package com.example.dispatchermobile.models;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    public LinearLayout getView(Context context)
    {
       LinearLayout llMessage = new LinearLayout(context, null);
          llMessage.setOrientation(LinearLayout.VERTICAL);
        TextView tvMessageText = new TextView(context);
        tvMessageText.setText(this.MessageText);
        TextView tvAuthor = new TextView(context);
        tvAuthor.setText(this.Author);
        TextView tvDatetime = new TextView(context);
        tvDatetime.setText(this.Datetime);

        switch (this.TypeMessage)
        {
            case 0:
                llMessage.setGravity(Gravity.LEFT);
                llMessage.setBackgroundColor(Color.YELLOW);
                break;
            case 1:
                llMessage.setGravity(Gravity.RIGHT);
                llMessage.setBackgroundColor(Color.GREEN);
                break;
            default:
                break;

        }

        llMessage.addView(tvAuthor);
        llMessage.addView(tvMessageText);
        llMessage.addView(tvDatetime);

        return  llMessage;
    }
}
