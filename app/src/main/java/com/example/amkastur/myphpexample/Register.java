package com.example.amkastur.myphpexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    EditText mNameET;
    EditText mOwnerET;
    EditText mPasswordET;

  /*  String mName;
    String mOwner;
    String mPassword;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNameET = (EditText) findViewById(R.id.etName);
        mOwnerET = (EditText) findViewById(R.id.etOwner);
        mPasswordET = (EditText) findViewById(R.id.etPassword);
    }

    public void onRegister(View view){
        String name = mNameET.getText().toString();
        String owner = mOwnerET.getText().toString();
        String password = mPasswordET.getText().toString();
        String type = "register";

        BackgroundWorker  backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,name,owner,password);
    }
}
