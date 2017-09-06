package com.example.amkastur.myphpexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText userNameET;
    EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameET = (EditText) findViewById(R.id.etUserName);
        passwordET = (EditText) findViewById(R.id.etPassword);
    }

    public void onLogin(View view){
        String userName = userNameET.getText().toString();
        String password = passwordET.getText().toString();
        String type = "login";

        BackgroundWorker  backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(userName,password,type);
    }

    public void openRegister(View view){
        startActivity(new Intent(this,Register.class));
    }
}
