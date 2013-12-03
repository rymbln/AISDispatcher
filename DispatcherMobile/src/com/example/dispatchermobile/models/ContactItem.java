package com.example.dispatchermobile.models;

import android.*;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactItem {
    /**
     * Created with IntelliJ IDEA.
     * User: rymbln
     * Date: 18.11.13
     * Time: 21:39
     * To change this template use File | Settings | File Templates.
     */

    public String Name;
    public String Rank;
    public String Comment;
    public ArrayList<String> Phones;

    public ContactItem(String name, String rank, String comment) {
        this.Name = name;
        this.Rank = rank;
        this.Comment = comment;
        this.Phones = new ArrayList<String>();
    }

    public void addPhone(String phone) {
        this.Phones.add(phone);
    }

    public LinearLayout getView(Context context) {
        LinearLayout llContactInfo = new LinearLayout(context, null);

        llContactInfo.setOrientation(LinearLayout.VERTICAL);
        TextView tvName = new TextView(context);
        tvName.setText(this.Name);
        TextView tvRank = new TextView(context);
        tvRank.setText(this.Rank);
        TextView tvComment = new TextView(context);
        tvComment.setText(this.Comment);


        llContactInfo.addView(tvName);
        llContactInfo.addView(tvRank);
        llContactInfo.addView(tvComment);

        LinearLayout llPhones = new LinearLayout(context, null);
        llPhones.setOrientation(LinearLayout.VERTICAL);

        for (int j = 1; j <= this.Phones.size(); j++) {
            TextView _tv = new TextView(context);
            _tv.setText(this.Phones.get(j - 1));
            llPhones.addView(_tv);
        }
               // RelativeLayout rl = new RelativeLayout(context)
        LinearLayout llResult = new LinearLayout(context);
        llResult.setOrientation(LinearLayout.HORIZONTAL);
        llResult.addView(llContactInfo);
        llResult.addView(llPhones);

        return llResult;
    }

}