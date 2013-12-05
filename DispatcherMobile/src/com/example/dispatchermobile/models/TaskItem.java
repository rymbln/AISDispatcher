package com.example.dispatchermobile.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.dispatchermobile.R;
import com.example.dispatchermobile.models.ContactItem;
import com.example.dispatchermobile.models.MessageItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TaskItem {
    private String taskID;
    private String companyName;
    private String deliveryTime;
    private String address;
    private String comment;
    public String lastStatus;
    public String lastStatusDate;
    private String driverName;

    public ArrayList<ContactItem> Contacts;
    public ArrayList<MessageItem> Messages;


    public TaskItem() {
        this.taskID = "";
        this.companyName = "";
        this.deliveryTime = "";
        this.address = "";
        this.comment = "";
        this.lastStatus = "";
        this.lastStatusDate = "";
        this.driverName = "";
        this.Contacts = new ArrayList<ContactItem>();
        this.Messages = new ArrayList<MessageItem>();
    }

    public TaskItem(String _str) {
        try {
            JSONObject _jo = new JSONObject(_str);
            this.taskID = _jo.optString("taskID");
            this.companyName = _jo.optString("companyName");
            this.deliveryTime = _jo.optString("deliveryTime");
            this.address = _jo.optString("address");
            this.comment = _jo.optString("comment");
            this.lastStatus = _jo.optString("lastStatus");
            this.lastStatusDate = _jo.optString("lastStatusDate");
            this.driverName = _jo.optString("driverName");
            this.Contacts = new ArrayList<ContactItem>();
            this.Messages = new ArrayList<MessageItem>();
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public TaskItem(String taskID, String companyName, String deliveryTime, String address, String comment, String lastStatus, String lastStatusDate, String driverName) {
        this.taskID = taskID;
        this.companyName = companyName;
        this.deliveryTime = deliveryTime;
        this.address = address;
        this.comment = comment;
        this.lastStatus = lastStatus;
        this.lastStatusDate = lastStatusDate;
        this.driverName = driverName;
        this.Contacts = new ArrayList<ContactItem>();
        this.Messages = new ArrayList<MessageItem>();
    }

    public String getTaskID() {
        return taskID;
    }

    public void addContact(ContactItem contact) {
        this.Contacts.add(contact);
    }

    public void addMessage(MessageItem message) {
        this.Messages.add(message);
    }


    public String getCompanyName() {
        return companyName;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public String getAddress() {
        return address;
    }

    public String getComment() {
        return comment;
    }

    public String getCommentSmall() {
        if (comment.length() > 30) {
            return comment.substring(0, 29) + "...";
        } else {
            return comment;
        }

    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String _str) {
        lastStatus = _str;
    }

    public void setLastStatusDate(String _str) {
        lastStatusDate = _str;
    }

    public String getLastStatusDate() {
        return lastStatusDate;
    }

    public String getDriverName() {
        return driverName;
    }

    public int getLastStatusToInt() {
        if (lastStatus.equals("Создано")) {
            return 1;
        } else {
            if (lastStatus.equals("Принято")) {
                return 2;
            } else {
                return 3;
            }
        }

    }

    public int getDeliveryTimeToInt() {
        int _res;
        int _part1;
        int _part2;
        if (deliveryTime.length() == 5) {
            _part1 = Integer.parseInt(deliveryTime.substring(0, 2));
            _part2 = Integer.parseInt(deliveryTime.substring(3, 5));
        } else {
            _part1 = Integer.parseInt(deliveryTime.substring(0, 1));
            _part2 = Integer.parseInt(deliveryTime.substring(2, 4));
        }
        _res = _part1 * 100 + _part2;
        return _res;
    }


    @Override
    public String toString() {

        String result = getTaskID()
                + "\n" + getCompanyName()
                + "\n" + getDeliveryTime()
                + "\n" + getAddress()
                + "\n" + getComment();
        return result;
    }

    public JSONObject toJSON() {
        JSONObject _jo = new JSONObject();

        try {
            _jo.put("taskID", taskID);
            _jo.put("companyName", companyName);
            _jo.put("deliveryTime", deliveryTime);
            _jo.put("address", address);
            _jo.put("comment", comment);
            _jo.put("lastStatus", lastStatus);
            _jo.put("lastStatusDate", lastStatusDate);
            _jo.put("driverName", driverName);
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return _jo;
    }


    public JSONArray contactsToJSONArray() {
        JSONArray arr = new JSONArray(Contacts);
        return arr;
    }

    public boolean isTaskClosed() {
        return this.getLastStatus().equals("Выполнено");
    }

    public LinearLayout getView(Context context) {
        View itemTemplate;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        if (!this.getDeliveryTime().equals("")) {
            itemTemplate = inflater.inflate(R.layout.task_selected_info_item, null);
            ImageView img = (ImageView) itemTemplate.findViewById(R.id.imgTaskSelectedInfoItem);
            img.setImageResource(R.drawable.time);
            TextView textView = (TextView) itemTemplate.findViewById(R.id.tvTaskSelectedInfoItem);
            textView.setText(this.getDeliveryTime());
            textView.setTextSize(24);
            layout.addView(itemTemplate);
        }

        if (!this.getAddress().equals("")) {
            itemTemplate = inflater.inflate(R.layout.task_selected_info_item, null);
            ImageView img = (ImageView) itemTemplate.findViewById(R.id.imgTaskSelectedInfoItem);
            img.setImageResource(R.drawable.place);
            TextView textView = (TextView) itemTemplate.findViewById(R.id.tvTaskSelectedInfoItem);
            textView.setText(this.getAddress());
            textView.setTextSize(24);
            layout.addView(itemTemplate);
        }

        if (!this.getComment().equals("")) {
            itemTemplate = inflater.inflate(R.layout.task_selected_info_item, null);
            ImageView img = (ImageView) itemTemplate.findViewById(R.id.imgTaskSelectedInfoItem);
            img.setImageResource(R.drawable.comment);
            TextView textView = (TextView) itemTemplate.findViewById(R.id.tvTaskSelectedInfoItem);
            textView.setText(this.getComment());
            layout.addView(itemTemplate);
        }

        return layout;

    }
}