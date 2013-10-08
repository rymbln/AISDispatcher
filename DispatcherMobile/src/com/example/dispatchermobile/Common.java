package com.example.dispatchermobile;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 08.10.13
 * Time: 23:08
 * To change this template use File | Settings | File Templates.
 */
public class Common {
    public static ProgressDialog ProgressBar;

    public static void initializeProgressBar(Activity _parent)
    {
        final ProgressDialog _pd = new ProgressDialog(_parent);
        _pd.setMessage(_parent.getString(R.string.labelLoading));
        _pd.setCancelable(false);
        ProgressBar = _pd;
        ProgressBar.show();
    }

    public static void closeProgressBar()
    {
        ProgressBar.hide();
    }
}
