package com.example.DispatcherMobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: rymbln
 * Date: 05.12.13
 * Time: 20:50
 * To change this template use File | Settings | File Templates.
 */
public class SelectPhoneDialog extends Dialog {
    private ArrayList<String> phonesArray;
    private CharSequence[] phonesChar;
    private Context context;

    public SelectPhoneDialog(Context context, ArrayList<String> phones) {
        super(context);
        this.context = context;
        this.phonesArray = phones;
        this.phonesChar =   phones.toArray(new CharSequence[phones.size()]);
    }

//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Build the dialog and set up the button click handlers
//        AlertDialog.Builder builder = new AlertDialog.Builder(MyApplication.getCurrentActivity());
//        builder.setMessage(R.string.select_phone_dialog_text)
//                .setPositiveButton(R.string.select_phone_dialog_text, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // Send the positive button event back to the host activity
//                       // mListener.onDialogPositiveClick(NoticeDialogFragment.this);
//                    }
//                })
//                .setNegativeButton(R.string.select_phone_dialog_text, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // Send the negative button event back to the host activity
//                       // mListener.onDialogNegativeClick(NoticeDialogFragment.this);
//                    }
//                });
//        return builder.create();
//    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.select_phone_dialog_text) ;
        builder.setItems(phonesChar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonesArray.get(position)));
                        MyApplication.getAppContext().startActivity(intent);
                    }
                });
//        builder.setPositiveButton(R.string.call_button, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // FIRE ZE MISSILES!
//            }
//        })  ;
//                builder.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User cancelled the dialog
//                    }
//                });

        return builder.create();
    }
}