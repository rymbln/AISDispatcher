package com.example.dispatchermobile.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.dispatchermobile.R;
import com.example.dispatchermobile.models.ContactItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 26.11.13
 * Time: 15:20
 * To change this template use File | Settings | File Templates.
 */
public class CompanyItem {
    private String companyID;
    private String companyName;
    private String address;
    private String comment;

    private ArrayList<ContactItem> contacts;

    public CompanyItem()
    {
        this.companyID = "";
        this.companyName = "";
        this.address = "";
        this.comment = "";
        this.contacts = new ArrayList<ContactItem>();
    }

    public CompanyItem(String str)
    {
        try {
            JSONObject _jo = new JSONObject(str);
            this.companyID = _jo.optString("companyID");
            this.companyName = _jo.optString("companyName");
            this.address = _jo.optString("address");
            this.comment = _jo.optString("comment");
            this.contacts = new ArrayList<ContactItem>();
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public CompanyItem(String companyid, String companyname,String address, String comment)
    {
        this.companyID = companyid;
        this.companyName = companyname;
        this.address = address;
        this.comment = comment;
        this.contacts = new ArrayList<ContactItem>();
    }

    public String getCompanyID()
    {
        return this.companyID;
    }

    public String getCompanyName(){
        return this.companyName;

    }

    public String getAddress()
    {
        return this.address;
    }

    public String getComment()
    {
        return this.comment;
    }

    public ArrayList<ContactItem> getContacts()
    {
        return this.contacts;
    }

    public void addContact(ContactItem contact)
    {
        this.contacts.add(contact);
    }

    @Override
    public String toString()
    {
        String result = getCompanyID()
                + "\n" + getCompanyName()
                + "\n" + getAddress()
                + "\n" + getComment();
        return result;
    }

    public JSONObject toJSON()
    {
        JSONObject _jo = new JSONObject();

        try {
            _jo.put("companyID", companyID);
            _jo.put("companyName",companyName);
            _jo.put("address",address);
            _jo.put("comment",comment);
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return _jo;
    }


    public JSONArray contactsToJSONarray()
    {
        JSONArray arr = new JSONArray(contacts);
        return  arr;
    }

    public LinearLayout getView(Context context) {
        View itemTemplate;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        if (!this.getAddress().equals("")) {
            itemTemplate = inflater.inflate(R.layout.company_selected_info_item, null);
            ImageView img = (ImageView) itemTemplate.findViewById(R.id.imgCompanySelectedInfoItem);
            img.setImageResource(R.drawable.place);
            TextView textView = (TextView) itemTemplate.findViewById(R.id.tvCompanySelectedInfoItem);
            textView.setText(this.getAddress());
            layout.addView(itemTemplate);
        }

        if (!this.getComment().equals("")) {
            itemTemplate = inflater.inflate(R.layout.company_selected_info_item, null);
            ImageView img = (ImageView) itemTemplate.findViewById(R.id.imgCompanySelectedInfoItem);
            img.setImageResource(R.drawable.comment);
            TextView textView = (TextView) itemTemplate.findViewById(R.id.tvCompanySelectedInfoItem);
            textView.setText(this.getComment());
            layout.addView(itemTemplate);
        }


        return layout;

    }
}

