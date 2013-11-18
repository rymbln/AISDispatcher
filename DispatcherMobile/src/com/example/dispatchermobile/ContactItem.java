package com.example.dispatchermobile;

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
        public ArrayList<String> Phones;

        public ContactItem(String name, String rank) {
            this.Name = name;
            this.Rank = rank;
            this.Phones = new ArrayList<String>();
        }

        public void addPhone(String phone) {
            this.Phones.add(phone);
        }

}