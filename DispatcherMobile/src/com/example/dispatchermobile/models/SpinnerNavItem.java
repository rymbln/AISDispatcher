package com.example.dispatchermobile.models;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 26.11.13
 * Time: 20:36
 * To change this template use File | Settings | File Templates.
 */
public class SpinnerNavItem {

    private String title;
    private int icon;

    public SpinnerNavItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

    public String getTitle(){
        return this.title;
    }

    public int getIcon(){
        return this.icon;
    }
}
