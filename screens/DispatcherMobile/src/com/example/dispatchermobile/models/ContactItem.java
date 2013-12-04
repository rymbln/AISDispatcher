package com.example.dispatchermobile.models;

import android.*;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.example.dispatchermobile.R;

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

    public View getView(Context context) {
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemTemplate = inflater.inflate(R.layout.contact_list_item, null);

        TextView tvName = (TextView) itemTemplate.findViewById(R.id.tvContactItem_Name);
        tvName.setText(this.Name);
        TextView tvRank = (TextView) itemTemplate.findViewById(R.id.tvContactItem_Rank);
        tvRank.setText(this.Rank);
        TextView tvComment = (TextView) itemTemplate.findViewById(R.id.tvContactItem_Comment);
        tvComment.setText(this.Comment);

        LinearLayout llPhones = (LinearLayout) itemTemplate.findViewById(R.id.llPhones);
        llPhones.setOrientation(LinearLayout.VERTICAL);

        for (int j = 1; j <= this.Phones.size(); j++) {
            TextView _tv = new TextView(context);
            _tv.setText(this.Phones.get(j - 1));
            llPhones.addView(_tv);
        }

        return itemTemplate;
    }

}