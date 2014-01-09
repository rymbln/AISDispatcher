package com.example.DispatcherMobile;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        MyApplication.setCurrentActivity(this);
        setContentView(R.layout.fragment_login);

        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        // Listening to register new account link
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                EditText edtLogin = (EditText) findViewById(R.id.edtEmail);
                EditText edtPass = (EditText) findViewById(R.id.edtPassword);
                if (Common.isCorrectEnteredLoginPassword(edtLogin.getText().toString(), edtPass.getText().toString())) {
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getCurrentActivity());
                    SharedPreferences.Editor prefEditor = sharedPrefs.edit();
                    prefEditor.putString("prefUsername", edtLogin.getText().toString() );
                    prefEditor.putString("prefUserpass", edtPass.getText().toString());
                    prefEditor.commit();
                    MyApplication.getCurrentActivity().finish();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    MyApplication.getCurrentActivity().finish();
                } else {
                    Toast.makeText(getBaseContext(), "o-oh!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}